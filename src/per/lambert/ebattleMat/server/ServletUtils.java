package per.lambert.ebattleMat.server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * Servlet Utilities
 * 
 * @author LLambert
 * 
 */
public final class ServletUtils {
	/**
	 * Hide constructor
	 */
	private ServletUtils() {

	}

	/**
	 * Build a map of request parameters
	 * 
	 * @param request
	 *            request
	 * @return Map of parameters
	 */
	public static Map<String, String> getParameters(
			final HttpServletRequest request) {
		Map<String, String> parameters = new HashMap<String, String>();
		@SuppressWarnings("rawtypes")
		Enumeration values = request.getParameterNames();
		while (values.hasMoreElements()) {
			String name = (String) values.nextElement();
			String value = request.getParameterValues(name)[0];
			parameters.put(name, value);
		}
		return (parameters);
	}

	/**
	 * Common method for distributing web requests
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param webServices
	 *            map of supported services
	 * @param servlet
	 *            servlet making the request
	 * @throws ServletException
	 *             exception
	 * @throws IOException
	 *             exception
	 */
	public static void handleGetRequest(final HttpServletRequest request,
			final HttpServletResponse response,
			final Map<String, IWebRequestHandler> webServices,
			final HttpServlet servlet) throws ServletException, IOException {
		Map<String, String> parameters = ServletUtils.getParameters(request);
		String command = parameters.get("command");
		//if command is null, then we assume that the query string is an encoded ZFSIF service request
		//At the moment this is basically a proof of concept for supporting file download., and hopefully
		//this can be changed to use a PUT instead of a GET, but it might not be possible. If not, then
		//we should change the query string to be conformant with standard query strings, and contain
		//several parameter names and values (i.e., token, request type, file name, etc.)
		if (command == null) {
			String decodedParameter = URLDecoder.decode(request.getQueryString(),"UTF-8");
			Document document = null;
			try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream is = new ByteArrayInputStream(decodedParameter.getBytes(StandardCharsets.UTF_8));
			document = builder.parse(is);
			}
			catch (Exception ex) {
				throw new ServletException(ex);
			}
			command = getCommand(document);
			if (command == null) {
				throw new ServletException();
			}
			IWebRequestHandler handler = webServices.get(command);
			if (handler != null) {
				handler.handleRequest(request, response, servlet, document, null);
				return;
			}
		}
		IWebRequestHandler handler = webServices.get(command);
		if (handler != null) {
			// handler.handleRequest(request, response, servlet);
			return;
		}
	}

	public static void handlePostRequest(final HttpServletRequest request,
			final HttpServletResponse response,
			final Map<String, IWebRequestHandler> webServices,
			final HttpServlet servlet) throws ServletException, IOException {
		Document document = null;
		String fileContents = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
		
//			ServletInputStream stream = request.getInputStream();
//			byte [] buffer = new byte[request.getContentLength()];
//			int bytesRead = stream.read(buffer);
//			stream.close();

			DocumentBuilder builder = factory.newDocumentBuilder();
			BufferedReader reader = request.getReader();
			InputSource is = new InputSource(reader);
			
			StringBuffer xmlString = new StringBuffer();
			String line; 
			
			char[] chars = new char[request.getContentLength()];
			reader.read(chars);
			line = new String(chars);
			
//		    while ((line = reader.readLine()) != null)
//		    	xmlString.append(line);
//  		    line = xmlString.toString();	    
		    int index = line.indexOf("</req>");
			if(index + 6 < line.length()){
				//we have a file being uploaded
			    fileContents = line.substring(index + 6);
				line = line.substring(0, index + 6);
			}
//			document = builder.parse(is);
		    InputSource xml = new InputSource(new ByteArrayInputStream(line.getBytes("utf-8")));
			document = builder.parse(xml);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
		String command = getCommand(document);
		if (command == null) {
			throw new ServletException();
		}
		IWebRequestHandler handler = webServices.get(command);
		if (handler != null) {
			handler.handleRequest(request, response, servlet, document, fileContents);
			return;
		}
	}

	private static String getCommand(Document document) {
		Element service = (Element)document.getElementsByTagName("svc").item(0);
		if (service == null)
			return null;
		Attr command = service.getAttributeNode("name");
		if (command == null)
			return null;
		return command.getValue();
	}

	/**
	 * MAke sure legal user
	 * 
	 * @param token
	 *            user token
	 * @throws ServletException
	 *             exception
	 */
	public static void validateUser(final String token) throws ServletException {
		if (token == null || token.isEmpty()) {
			throw new ServletException();
		}
	}
}
