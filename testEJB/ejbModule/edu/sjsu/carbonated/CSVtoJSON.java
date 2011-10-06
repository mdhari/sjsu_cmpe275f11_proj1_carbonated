package edu.sjsu.carbonated;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * 
 * @author Michael Hari
 * @date Oct 5th, 2011
 * @version 1.0
 * 
 */

public class CSVtoJSON {

	public CSVtoJSON() {
	} // default constructor

	/**
	 * 
	 * @param filename
	 *            Requires a filename like AA.dat which currently looks in the
	 *            same directory. Will modify to have directory paths if needed.
	 * @return JSON formatted string of {filename (without extension .dat)
	 *         [{data}]}
	 */
	public String convertCSVFile(String filename) {

		// HTTP/1.1 200 OK
		// Cache-Control: private
		// Connection: close
		// Date: Fri, 30 Sep 2011 05:49:51 GMT
		// Content-Type: text/csv
		// Client-Date: Fri, 30 Sep 2011 05:49:53 GMT
		// Client-Peer: 67.195.146.181:80
		// Client-Response-Num: 1
		// Client-Transfer-Encoding: chunked
		// P3P: policyref="http://info.yahoo.com/w3c/p3p.xml",
		// CP="CAO DSP COR CUR ADM DEV TAI PSA PSD IVAi IVDi CONi TELo OTPi OUR DELi SAMi OTRi UNRi PUBi IND PHY ONL UNI PUR FIN COM NAV INT DEM CNT STA POL HEA PRE LOC GOV"

		int numOfLinesToSkip = 11; // the reason why we skip is above
		String fileName = "AA.dat";

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONArray jsonArr = null;

		int i = 0;
		try {
			while (br.ready() && i < numOfLinesToSkip) {
				System.out.println(br.readLine());
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			jsonArr = CDL.toJSONArray(new JSONTokener(br));

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return "{\"" + fileName.split("\\.")[0] + "\":"
				+ jsonArr.toString() + "}";
	}

}
