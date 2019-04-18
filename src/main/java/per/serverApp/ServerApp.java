package per.serverApp;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Application for client can run Jetty server locally to DM a session.
 * 
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
	 * 
	 * @param args arguments
	 * @throws Exception if error.
	 */
	public static void main(final String[] args) throws Exception {
		buildWelcome();
		Server server = new Server(8088);
		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setContextPath("/");
		webAppContext.setWar("./ElectronicBattleMat.war");
		webAppContext.setParentLoaderPriority(true);
		webAppContext.setServer(server);
		webAppContext.setTempDirectory(new File("./ElectronicBattleMatTemp"));
		webAppContext.setClassLoader(ClassLoader.getSystemClassLoader());
		server.setHandler(webAppContext);
		server.start();
	}

	/**
	 * Show pre-amble to user.
	 */
	private static void buildWelcome() {
		String ipAddress = getIpAddress();
		if (ipAddress == null) {
			System.out.println("No ip address found.");
			System.out.println("Must have network card to run this program.");
			return;
		}
		System.out.println("Enter the following URL to access dungeons");
		System.out.println("http://" + ipAddress + ":8088");
		System.out.println("");
		System.out.println("");
	}

	/**
	 * Get ip address of machine.
	 * 
	 * @return address of machine.
	 */
	private static String getIpAddress() {
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			return (null);
		}
		return (ip.getHostAddress());
	}

}
