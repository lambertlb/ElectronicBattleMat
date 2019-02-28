package per.serverApp;

import java.io.File;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Application for client can run Jetty server locally to DM a session.
 * @author LLambert
 *
 */
public final class ServerApp {
	/**
	 * Constructor.
	 */
	private ServerApp() {
		
	}
	/**
	 * Main entry point.
	 * @param args arguments
	 * @throws Exception if error.
	 */
	public static void main(final String[] args) throws Exception {
		Server server = new Server(8088);
		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setContextPath("/");
		webAppContext.setWar(".\\ElectronicBattleMat.war");
		webAppContext.setParentLoaderPriority(true);
		webAppContext.setServer(server);
		webAppContext.setTempDirectory(new File("./ElectronicBattleMatTemp"));
		webAppContext.setClassLoader(ClassLoader.getSystemClassLoader());
		server.setHandler(webAppContext);
		server.start();
	}

}
