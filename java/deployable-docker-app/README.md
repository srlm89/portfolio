### Docker-deployable App

![][image-deployable-docker]

The objective is to be able to run a *java* application with its own "embedded" [*postgres* database][docker-postgres]. One way to do this is with *Docker* containers. That is, to run two docker containers with `docker-compose`: the *postgres* database container and the *java* application container (which, in turn, uses JDBC to connect to the database.) To achieve this, we create a folder named `deploy` with the following files:

- `deploy/docker-compose.yml`: file specifying the [container configuration][docker-compose-file] for the database and application containers.
- `deploy/launcher.sh`: script to run the `docker-compose` command, wait for the *java* application container to finish and then shut down the *postgres* database container.
- `deploy/docker/volumes/postgres/data`: folder of a *postgres* database snapshot (holding all schema and table information.)
- `deploy/docker/app/Dockerfile`: a *Dockerfile* specifying the configuration of a *java-image-based* container to run our *java* application.
- `deploy/docker/app/deployable-docker-app.jar`: the assembled `jar` file with dependencies.

----

### The pom.xml Explained

![][image-plugin-docker]

The maven lifecycle consists -among others- of the following phases (refer to [this link][mvn-lifecycle]):

- clean
- generate-sources
- compile
- test
- package
- verify
- install
- deploy

We can specify in the `pom.xml` which plugins to attach to any of these phases in order to customize the build process of our application. In this case, we use five plugins:

- The [docker-maven-plugin][docker-maven-plugin]
- The [flyway-maven-plugin][flyway-maven-plugin]
- The [jooq-codegen-plugin][jooq-codegen-plugin]
- The [maven-assembly-plugin][maven-assembly-plugin]
- The [maven-dependency-plugin][maven-dependency-plugin]

#### Docker Maven Plugin

```xml
<plugin>
  <groupId>io.fabric8</groupId>
  <artifactId>docker-maven-plugin</artifactId>
  <executions>
    <execution>
      <phase>generate-sources</phase>
      <id>docker-start</id>
      <goals>
        <goal>start</goal>
      </goals>
    </execution>
    <execution>
      <phase>clean</phase>
      <id>docker-stop</id>
      <goals>
        <goal>stop</goal>
      </goals>
      <configuration>
        <keepRunning>false</keepRunning>
        <keepContainer>false</keepContainer>
        <removeVolumes>false</removeVolumes>
      </configuration>
    </execution>
  </executions>
  <configuration>
    <watchInterval>500</watchInterval>
    <logDate>default</logDate>
    <autoPull>always</autoPull>
    <watchPostGoal>org.apache.maven.plugins:maven-help-plugin:help</watchPostGoal>
    <images>
      <image>
        <name>${maven-plugin-docker-postgres.image}</name>
        <alias>${maven-plugin-docker-postgres.name}</alias>
        <run>
          <namingStrategy>alias</namingStrategy>
          <privileged>true</privileged>
          <ports>
            <port>${maven-plugin-docker-postgres.port}:5432</port>
          </ports>
          <env>
            <POSTGRES_USER>${maven-plugin-docker-postgres.user}</POSTGRES_USER>
            <POSTGRES_PASSWORD>${maven-plugin-docker-postgres.pass}</POSTGRES_PASSWORD>
          </env>
          <volumes>
            <bind>
              <volume>${deploy-docker-dir.path}/volumes/postgres/data:/var/lib/postgresql/data</volume>
            </bind>
          </volumes>
          <wait>
            <time>3000</time>
          </wait>
        </run>
      </image>
    </images>
  </configuration>
</plugin>
```

- We run this plugin twice:
    - In `clean` phase, to stop running -if present- a docker container previously started with the `pom.xml` file.
    - In `generate-sources` phase, to start a *postgres* docker container where to run any necessary migrations and integration tests.
- We specify the following configuration to the plugin:
    - The image name (`<name>` tags.)
    - The container name (`<alias>` tags.)
    - A port mapping (`<port>` tags) so we can connect to the *postgres* container from our *localhost*.
    - The user/password of the database (environment variables `POSTGRES_USER` and `POSTGRES_PASSWORD`.)
    - The volumes folder for the container, linking the *postgres* container folder holding database files (`/var/lib/postgresql/data`) to a *localhost* folder (`${project.basedir}/deploy/docker/volumes/postgres/data`.) By doing this, all modifications to the database are held in a local folder we can directly access. That is, the *localhost* folder acts as a database snapshot which we can use from any other machine to boot up a new *postgres* container holding the same tables and schemas. (Warning: make sure the parent folder of the specified docker volume directory exists, otherwise *docker* will create intermediate non-existing folders with root privileges.)
    - And we indicate the plugin to wait for the container startup to finish (a pause of 3.0 seconds.)
- Note that no further executions of the plugin are carried out. This means that the docker container is kept running after the *maven* lifecycle ends. You can stop the container by running `mvn clean`.

#### Flyway Maven Plugin

```xml
<plugin>
  <groupId>org.flywaydb</groupId>
  <artifactId>flyway-maven-plugin</artifactId>
  <executions>
    <execution>
      <phase>generate-sources</phase>
      <goals>
        <goal>migrate</goal>
      </goals>
    </execution>
  </executions>
  <configuration>
    <baselineOnMigrate>true</baselineOnMigrate>
    <user>${maven-plugin-docker-postgres.user}</user>
    <password>${maven-plugin-docker-postgres.pass}</password>
    <url>jdbc:postgresql://localhost:${maven-plugin-docker-postgres.port}/</url>
    <schemas>
      <schema>bogus</schema>
    </schemas>
    <locations>
      <location>filesystem:src/main/psql/migration/bogus</location>
    </locations>
  </configuration>
</plugin>
```

- We run this plugin once in the `generate-sources` phase, and it must be executed *after* the `docker-maven-plugin`.
- The goal of the plugin is to run migration scripts in the *postgres* database container.
- We specify the following configuration to the plugin:
    - The user/password of the database.
    - The url of the database (using the port mapping specified in the `docker-maven-plugin`.)
    - The target schemas referenced in the migration scripts. A table named `schema_version` is generated for each specified schema, which holds information of the migrations executed by the plugin.
    - The folders containing the migration scripts. The names of these files should look like: `V1_2__Description.sql` (see [name format][flyway-name-format]).


#### JOOQ Codegen Plugin

```xml
<plugin>
  <groupId>org.jooq</groupId>
  <artifactId>jooq-codegen-maven</artifactId>
  <executions>
    <execution>
      <phase>generate-sources</phase>
      <goals>
        <goal>generate</goal>
      </goals>
    </execution>
  </executions>
  <configuration>
    <jdbc>
      <driver>org.postgresql.Driver</driver>
      <user>${maven-plugin-docker-postgres.user}</user>
      <password>${maven-plugin-docker-postgres.pass}</password>
      <url>jdbc:postgresql://localhost:${maven-plugin-docker-postgres.port}/</url>
    </jdbc>
    <generator>
      <database>
        <name>org.jooq.util.postgres.PostgresDatabase</name>
        <includes>.*</includes>
        <excludes/>
        <schemata>
          <schema>
            <inputSchema>bogus</inputSchema>
          </schema>
        </schemata>
      </database>
      <target>
        <packageName>${maven-plugin-jooq-codegen.package}</packageName>
        <directory>target/generated-sources/jooq</directory>
      </target>
    </generator>
  </configuration>
</plugin>
```

- We run this plugin once in the `generate-sources` phase, and it must be executed *after* the `flyway-maven-plugin`.
- The goal of the plugin is to generate `Java` code from the schemas and tables of the *postgres* database container.
- We specify the following configuration to the plugin:
    - The user/password of the database.
    - The url of the database (using the port mapping specified in the `docker-maven-plugin`.)
    - The schemas to generate `Java` code from.
    - The output directory and package name of the generated code.

#### Maven Assembly Plugin

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-assembly-plugin</artifactId>
  <configuration>
    <descriptorRefs>
      <descriptorRef>jar-with-dependencies</descriptorRef>
    </descriptorRefs>
    <finalName>${project.artifactId}</finalName>
    <appendAssemblyId>true</appendAssemblyId>
  </configuration>
  <executions>
    <execution>
      <id>make-assembly</id>
      <phase>package</phase>
      <goals>
        <goal>single</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

- We run this plugin in order to build a `jar` file which includes all dependencies.

#### Maven Dependency Plugin

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-dependency-plugin</artifactId>
  <executions>
    <execution>
      <id>copy</id>
      <phase>package</phase>
      <goals>
        <goal>copy</goal>
      </goals>
      <configuration>
        <artifactItems>
          <artifactItem>
            <groupId>${project.groupId}</groupId>
            <artifactId>${project.artifactId}</artifactId>
            <version>${project.version}</version>
            <overWrite>true</overWrite>
            <classifier>jar-with-dependencies</classifier>
            <outputDirectory>${deploy-docker-dir.path}/app</outputDirectory>
            <destFileName>${project.artifactId}.jar</destFileName>
            <type>jar</type>
          </artifactItem>
        </artifactItems>
      </configuration>
    </execution>
  </executions>
</plugin>
```

- We run this plugin to copy the generated `jar` with dependencies to the deploy folder.


----

### How to Get Docker

#### [Install Docker][docker-install]
```bash
$ wget -qO- https://get.docker.com/ | sh
$ sudo usermod -aG docker $(whoami)
# Log out and log in from your machine to activate your new groups
```

#### [Install Docker compose][docker-compose-install]
```
$ sudo apt-get -y install python-pip
$ sudo pip install docker-compose
```

[image-plugin-docker]: md/pom-explained.png "Plugin-docker interactions"
[image-deployable-docker]: md/compose-explained.png "Deployable docker structure"
[docker-install]: https://docs.docker.com/engine/installation/
[docker-compose-install]: https://docs.docker.com/compose/install/
[docker-compose-file]: https://docs.docker.com/compose/compose-file/
[docker-maven-plugin]: https://fabric8io.github.io/docker-maven-plugin/
[flyway-maven-plugin]: https://flywaydb.org/documentation/maven/
[flyway-name-format]: https://flywaydb.org/documentation/migration/sql
[jooq-codegen-plugin]: http://www.jooq.org/doc/3.4/manual/code-generation/
[docker-postgres]: https://hub.docker.com/_/postgres/
[mvn-lifecycle]: https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html
[maven-assembly-plugin]: http://maven.apache.org/plugins/maven-assembly-plugin/assembly-mojo.html
[maven-dependency-plugin]: http://maven.apache.org/plugins/maven-dependency-plugin/copy-mojo.html

