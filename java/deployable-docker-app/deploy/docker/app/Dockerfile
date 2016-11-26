FROM java:8-jdk


# Container setup
ADD ./deployable-docker-app.jar /home/deployable-docker-app.jar
ADD ./execute.sh /home/execute.sh
RUN [ "chmod", "+x", "/home/execute.sh" ]


# Container execution
ENTRYPOINT [ "/home/execute.sh", "/home/deployable-docker-app.jar" ]
