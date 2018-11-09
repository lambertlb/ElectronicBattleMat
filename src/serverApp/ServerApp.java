package serverApp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class ServerApp {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8088);
		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setContextPath("/");
		webAppContext.setWar(".\\ElectronicBattleMat.war");
		webAppContext.setParentLoaderPriority(true);
		webAppContext.setServer(server);
		webAppContext.setClassLoader(ClassLoader.getSystemClassLoader());
//		webAppContext.getSessionHandler().getS.getSessionManager().setMaxInactiveInterval(10);
		server.setHandler(webAppContext);
		server.start();
	}

}
