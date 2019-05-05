# **Electronic Battle Mat**

Electronic battle mat is a web application to aide a Dungeon Master make a more enjoyable 
gaming experience for the players. This allows the DM to setup ahead of time all 
the rooms and monsters in a dungeon so that there is much less down time during a 
gaming session.

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
   with new versions. You can get the JDK @ [Oracle Java JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. Install Eclipse. I use the enterprise addition@ [Eclipse IDE for Enterprise Java Developers](https://www.eclipse.org/downloads/packages/release/2019-03/r/eclipse-ide-enterprise-java-developers)
3. From eclipse marketplace install checkstyle.
4. From eclipse marketplace install gwt plugin.
5. Follow the instruction @ [Download and Install the GWT SDK](http://www.gwtproject.org/gettingstarted.html). 
After unzipping gwt 2.8.2 you need to go to preferences in eclipse and add the path 
to gwt under Window->Preferences->GWT>GWT Settings hit the add button and navigate to 
where you unzipped the SDK.

## Running Electronic Battle Mat
There are a couple of ways you can run the web app.
1. Use the supplied Jetty web service.
To make things more convenient this project also supplies a small java application called 
ElectronicBattleMat.jar that can be used to setup a local web server within your network. 
To run it on windows just double click the StartServer.bat file and answer yes if  a 
firewall dialog comes up. At the top of the console window to will tell you the url 
that people within your network will need to use to connect to the app. for example 
    http://192.168.115.128:8088
2. Install Tomcat [Apache Tomcat](https://tomcat.apache.org/download-80.cgi) and install 
ElectronicBattleMat.war in the tomcat webapp directory.
