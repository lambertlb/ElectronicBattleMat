package per.lambert.ebattleMat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * This class provides support for reading a ResponseCOnfiguration.txt file located in the
 * sub-folder named in its constructor.
 *
 */
public class ResponseUtils {

	private class ResponseConfiguration {
		int repetitions;
		String fileName;
		public ResponseConfiguration(int repetitions, String fileName) {
			this.repetitions = repetitions;
			this.fileName = fileName;
		}
	}
	
	private List<ResponseConfiguration> responses;
	private int currentResponse;
	private int currentIncrement;
	private String responseFolder;
	private HttpServlet servlet;
	
	private final String fileLocation = "/usr/share/zerofootprint/";
	
	/**
	 * The constructor
	 * @param responseFolder
	 * @param servlet
	 * @throws ServletException
	 */
	public ResponseUtils(String responseFolder, final HttpServlet servlet) throws ServletException {
		this.responseFolder = responseFolder;
		this.servlet = servlet;
		responses = new ArrayList<ResponseConfiguration>();
		getResponseConfiguration();
	}

	/**
	 * Reads the ResponseConfiguration file and parses it into ResponseConfiguration objects. There
	 * is one entry per line in the file. The first value in a line is the number of repetitions that
	 * the response will be provided, the second entry (separated by a semicolon) is the name of the
	 * file containing the complete response.  There is no limit to the number of lines that are 
	 * defined in the file. The simplest ResponseConfiguration.txt has just a single line such as:
	 * 		1;filename.ext
	 * This would result in the contents of filename.ext being provided every time getNextResponse
	 * is called. 
	 * A line that starts with a "//" is treated as a comment and is not parsed or added to the list.
	 * @throws ServletException
	 */
	private void getResponseConfiguration() throws ServletException {
		try {
			InputStream is = servlet.getServletContext().getResourceAsStream(fileLocation + responseFolder + "/" + "ResponseConfiguration.txt");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			try {
				while (true) {
					String line = br.readLine();
					if (line == null) {
						break;
					}
					else {
						if(line.isEmpty() || line.startsWith("//")) {
							continue;
						}
						String [] config = line.split(";");						
						ResponseConfiguration response = new ResponseConfiguration(Integer.parseInt(config[0]),config[1]);
						responses.add(response);
					}
				}
			} finally {
			br.close();
			}
		} catch (IOException ie) {
			throw new ServletException();
		}
		
	}

	/**
	 * Gets the next response based on the definitions provided in the ResponseConfiguration.txt file
	 * @return a string with the complete response
	 * @throws ServletException
	 */
	public String getNextResponse() throws ServletException {
		setCurrentResponse();
		StringBuilder sb = new StringBuilder();
		try {
			InputStream is = servlet.getServletContext().getResourceAsStream(fileLocation + responseFolder + "/" + responses.get(currentResponse).fileName);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			try {
				while (true) {
					String line = br.readLine();
					if (line == null) {
						break;
					}
					sb.append(line);
				}
			} finally {
			br.close();
			}
		} catch (IOException ie) {
			throw new ServletException();
		}
		currentIncrement++;
		return sb.toString();
	}


	/**
	 * Determine what the indexes are for the current response. There are two values that are
	 * needed. If the current response has been provided for the required number of repetitions
	 * then move to the next response. If we are at the last response, then restart at the first 
	 * response (i.e., we do an endless loop through the list of response definitions.  
	 */
	private void setCurrentResponse() {
		if(currentIncrement >= responses.get(currentResponse).repetitions) {
			currentResponse++;
			currentIncrement = 0;
			if(currentResponse >= responses.size())
				currentResponse = 0;
		}
	}
	

}
