# MY SHELFIE APPLICATION

### Final project for Ingegneria del Software by professor Cugola, at Polimi

![Display_1](https://github.com/yexin01/ing-sw-2023-ye-rossi-rondi-russo/assets/126525735/e7d79104-8706-4850-84dc-f98e4fe96e7a)


## Group GC41 components

- [Andrea Rondi](https://github.com/andrearondi)

- [Giulia Rossi](https://github.com/GiuliaRossi2)

- [Samuele Russo](https://github.com/SamRusso01)

- [Xin Ye](https://github.com/yexin01)

## REQUIREMENTS

Following the table provided within the project specifications, the satisfied requirements are:

- Complete Rules ✅

- CLI ✅

- GUI ✅

- RMI ✅

- Socket ✅

*Advanced features*

- Multiple games ✅

- Persistence ✅

- Resilience to disconnections ✅

## HOW TO START THE GAME
Inside the "deliverables" directory there is a single jar file `ing-sw-2023-ye-rossi-rondi-russo.jar`. 

It is hardly recommended to use a jar built on your own operative system.

In order to run the jar and start the game, you firstly need to type `java -jar ing-sw-2023-ye-rossi-rondi-russo.jar` in the command line.

*Note*: You must be inside the directory where the jar file is placed `cd *path_to_directory`.

<sub>Example for macOS terminal</sub>
```
cd /Users/Player/Downloads
java -jar ing-sw-2023-ye-rossi-rondi-russo.jar it.polimi.ingsw.Start
```

<sub>Example for Windows terminal</sub>
```
C:\Users\Player\Download
java -jar .\ing-sw-2023-ye-rossi-rondi-russo.jar
```

### SERVER
The server can be created by typing `0` after running the jar. 

You need to type the port and the ip on which the server will accept connections.

### CLIENT
The client can be instantiated by typing `1` after running the jar. Then you can choose between CLI and GUI:

- CLI

The CLI can be runned by inserting `0` after running the jar. 

For the best game experience, it is suggested to run the CLI on a default Mac power shell or on Windows Terminal for Windows users.

Then you need to insert your nickname (2-20 characters) and the desidered connection (port and ip for socket/RMI).

Inside the global lobby, you will be able to create a new lobby or join a specific/random lobby.

*Note*: It is hardly recommended to use a black background power shell to fully enjoy the CLI functionalities.

<sub>Examples of CLI</sub>

![Board4](https://github.com/yexin01/ing-sw-2023-ye-rossi-rondi-russo/assets/126389691/39193de6-3098-47d3-a7d0-b4aa411ae65b)

![PersonalGoal](https://github.com/yexin01/ing-sw-2023-ye-rossi-rondi-russo/assets/126389691/82baee79-5b92-4a91-934b-43878e3f4848)

![Bookshelf](https://github.com/yexin01/ing-sw-2023-ye-rossi-rondi-russo/assets/126389691/62fcfef3-951b-4261-8dfc-c6f7bf7da2d4)



<sub>Example turn phases in game.</sub>
<sub>Notice the separation in actions for each phase of the game.</sub>


![common](https://github.com/yexin01/ing-sw-2023-ye-rossi-rondi-russo/assets/126525735/95d2074f-8905-45f9-aadb-19a0761e00c0)

<sub>Example of Common Goal Cards and description on macOS.</sub>


- GUI

The GUI can be runned by inserting `1` after running the jar.

For the best game experience, it is suggested to run the GUI in full-size window on a 1920x1080 resolution.

Then you need to insert your nickname (2-20 characters) and set it.

Now you will be able to set up the desidered connection: insert port and ip on the corresponding row.

Inside the global lobby, you will be able to create a new lobby or join a specific/random lobby.

<sub>Example of GUI</sub>

![image](https://github.com/yexin01/ing-sw-2023-ye-rossi-rondi-russo/assets/126389691/a4eeb347-d5ee-4b09-a5a3-5d5139d19d4d)

![selectTiles](https://github.com/yexin01/ing-sw-2023-ye-rossi-rondi-russo/assets/126389691/47e5b734-1bf3-4937-8e9e-853224792c97)

![Column](https://github.com/yexin01/ing-sw-2023-ye-rossi-rondi-russo/assets/126389691/cc7b4a27-e484-4f36-b72c-3c1f0a0b08c7)



