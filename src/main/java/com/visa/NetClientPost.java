
package com.visa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.SignatureException;

import javax.net.ssl.HttpsURLConnection;

public class NetClientPost {

	public String getResponse(String payload, String endpoint,
	        String xpaytoken) throws SignatureException, IOException {

		System.setProperty("javax.net.ssl.trustStore", getClass()
		        .getClassLoader().getResource("sandbox.jks")
		        .getFile());
		System.setProperty("javax.net.ssl.trustStorePassword",
		        "changeit");
		// Enter host, username, password if you are using proxy
		/*
		 * System.setProperty("https.proxyHost", (String)new
		 * ConfigValues().getPropValues().get("hostname"));
		 * System.setProperty("https.proxyPort", (String)new
		 * ConfigValues().getPropValues().get("hostport"));
		 * System.setProperty("https.proxyUser",(String)new
		 * ConfigValues().getPropValues().get("proxyusername"));
		 * System.setProperty("https.proxyPassword", (String)new
		 * ConfigValues().getPropValues().get("proxypassword"));
		 */

		HttpsURLConnection conn = null;

		URL url = new URL(endpoint);

		conn = (HttpsURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("x-pay-token", xpaytoken);

		OutputStream os = conn.getOutputStream();
		os.write(payload.getBytes());
		os.flush();
		BufferedReader br = null;
		InputStream is;
		if (conn.getResponseCode() >= 400) {
			is = conn.getErrorStream();

			System.out.println("get response code"
			        + conn.getResponseCode());
		} else {
			is = conn.getInputStream();
		}

		br = new BufferedReader(new InputStreamReader(is));

		String output;
		String op = "";

		while ((output = br.readLine()) != null) {
			op += output;

		}

		conn.disconnect();
		return op;

	}

}
