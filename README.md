# **Electronic Battle Mat**

Electronic battle mat is a web application to aide a Dungeon Master make a more enjoyable 
gaming experience for the players. This allows the DM to setup ahead of time all 
the rooms and monsters in a dungeon so that there is much less down time during a 
gaming session. Once the web site is up and running DMs and players only need a platform 
that supports a browser with HTML5 to use it.This includes mobile devices like tablets.

## Building
Electronic Battle mat is based on [Google Web Toolkit](http://www.gwtproject.org/). 
This allows the application to be written in Java and then compiled to javascript.

**NOTE** *A prebuilt version of the web app is kept in a sub-directory called ElectronicBattleMat. 
This allows a user to just try out the program without having to build it.*

### Building with Maven
To build with maven just set console default to main folder and type.

    mvn clean package

### Building with Eclipse
This project is also an eclipse project. I found it easier to develop and debug using 
a standard eclipse project instead of a Maven one. To build it using eclipse you will obviously 
need to setup a few things first.
1. Install Java JDK. I found JDK 1.8 works best. Eclipse and Jetty seem to have issues 
   with newer versions. You can get the JDK @ [Oracle Java JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. Install Eclipse. I use the enterprise addition@ [Eclipse IDE for Enterprise Java Developers](https://www.eclipse.org/downloads/packages/release/2019-03/r/eclipse-ide-enterprise-java-developers)
3. From eclipse marketplace install checkstyle.
4. From eclipse marketplace install gwt plugin.
5. Follow the instruction @ [Download and Install the GWT SDK](http://www.gwtproject.org/gettingstarted.html). 
After un-zipping gwt 2.8.2 you need to go to preferences in eclipse and add the path 
to gwt under Window->Preferences->GWT>GWT Settings hit the add button and navigate to 
where you un-zipped the SDK.
6. From eclipse you then just need to import the project General->Import Existing Project 
   into Workspace.

## Running Electronic Battle Mat Server
There are a couple of ways you can run the web app. Both methods require that you have 
Java installed. This is because the server side servlet is written in java and both 
web servers require it. This only needs to be done once within a local network.
1. Use the supplied Jetty web service.
To make things more convenient this project also supplies a small java application called 
ElectronicBattleMat.jar that can be used to setup a local web server within your network. 
To run it on windows just double click the StartServer.bat file and answer yes if  a 
firewall dialog comes up. At the top of the console window it will tell you the url 
that people within your network will need to use to connect to get to that main page. For example 
    http://192.168.115.128:8088. **The ElectronicBattlemat.war must be in the same directory as the jar file.**
2. Install [Apache Tomcat](https://tomcat.apache.org/download-80.cgi) and install 
ElectronicBattleMat.war in the tomcat webapp directory.

## Included Software Licenses
| Name                    | License            | Location                                              |
|-------------------------|--------------------|-------------------------------------------------------|
| **Drag and Drop Touch** | Mit                | https://github.com/Bernardo-Castilho/dragdroptouch    |
| **Gson**                | Apache License 2.0 | https://github.com/google/gson                        |
| **Commons FileUpload**  | Apache License 2.0 | https://commons.apache.org/proper/commons-fileupload/ |
|                         |                    |                                                       |

### Included Dungeon
This deliverable also includes a dungeon. I made it from the free module [Dawn of the 
Scarlet Sun](https://paizo.com/products/btpy8rgh). All pictures and text come from the 
PDF and should abide by its rules. Later I might give a small tutorial on how I used 
GIMP 2 to prepare the assets to include in the battle mat. I haven't had time to do this 
yet.


