### FileLister

This is a program to inspect file and directory metadata. Currently, it has the following functionalities:

#### Create snapshots

It recursively creates a metadata snapshot of a file or directory. For instance, given the following directory structure:

![][SnapshotCreation]

We can execute the snapshot creation from the terminal with:

```bash
$ ls -1
    file-list-0.0.1-SNAPSHOT-jar-with-dependencies.jar

$ java -jar file-list-0.0.1-SNAPSHOT-jar-with-dependencies.jar -snapshot /tmp/my-dir

$ ls -1
    2016-03-20_my-dir.zip
    file-list-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

This command produces a zip file containing the metadata of the folder `/tmp/my-dir`, in the same folder where  the command was executed from.


#### List Files matching Regex

We can list files matching a regex to filter only `png` files. For instance:

```bash
$ ls -1
    file-list-0.0.1-SNAPSHOT-jar-with-dependencies.jar

$ java -jar file-list-0.0.1-SNAPSHOT-jar-with-dependencies.jar -list -fs /tmp/my-dir ".*[.]png" "\$0"

$ ls -1
    2016-03-20-18-10-00_lister.txt
    file-list-0.0.1-SNAPSHOT-jar-with-dependencies.jar

$ cat 2016-03-20-18-10-00_lister.txt
    /my-dir/files/sudoku_test.png: 1
    /my-dir/noize.png: 1
```

The program creates a `.txt` file containing the absolute path of matching files.

We can also list files from a created snapshot. For instance:

```bash
$ ls -1
    2016-03-20_my-dir.zip
    file-list-0.0.1-SNAPSHOT-jar-with-dependencies.jar

$ java -jar file-list-0.0.1-SNAPSHOT-jar-with-dependencies.jar -list -s 2016-03-20_my-dir.zip ".*(png|pdf)" "\$1"

$ ls -1
    2016-03-20-18-17-40_lister.txt
    2016-03-20_my-dir.zip
    file-list-0.0.1-SNAPSHOT-jar-with-dependencies.jar

$ cat 2016-03-20-18-17-40_lister.txt
    pdf: 1
    png: 2
```

In this case, we only take the extension of each file name, and the program groups together the number of files of each file extension.


#### Recursively compare directory metadata

Now, suppose we have a snapshot of this directory:

![][SnapshotCreation]

And we want to compare this snapshot with the snapshot of another directory:

![][ModifiedDirectory]

We can do it like so:

```bash
$ ls -1
    2016-03-20_my-dir.zip
    2016-03-22_my-dir.zip
    file-list-0.0.1-SNAPSHOT-jar-with-dependencies.jar

$ java -jar file-list-0.0.1-SNAPSHOT-jar-with-dependencies.jar -compare -s 2016-03-20_my-dir.zip 2016-03-22_my-dir.zip

$ ls -1
    2016-03-20-18-26-50_comparison.txt
    2016-03-20_my-dir.zip
    2016-03-22_my-dir.zip
    file-list-0.0.1-SNAPSHOT-jar-with-dependencies.jar

$ cat 2016-03-20-18-26-50_comparison.txt
    .
    <--  noize.png (3423811)
    -->  noize.jpg (843584)

    ./files
    >>>  a3/

    ./txts
    <--  gnome-games (287)
    -->  tree (147)
```

The program creates a `.txt` file showing the differences between both snapshots.

[SnapshotCreation]: md/directory-1.png "Directory structure (1)"
[ModifiedDirectory]: md/directory-2.png "Directory structure (2)"
