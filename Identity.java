package com.tadigital.authentication;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.DatatypeConverter;

import com.eclipsesource.json.JsonObject;

public class Identity {
	// Replace marketoInstance, clientId, and clientSecret values
	public String marketoInstance = "https://876-swj-450.mktorest.com/identity/oauth/token?grant_type=client_credentials";
	public String clientId = "d72450d9-2bbb-4116-b7af-79db718cedc7";
	public String clientSecret = "5Y1JEIL3UR3iKJyPeBt7XVhVbsk5GbRM";

	public static void main(String[] args) {
		String access_token = new Identity().getToken();
		System.out.println(access_token);
		System.exit(0);
	}

	// Build request URL to Get Lead API endpoint
	public String getToken() {
		String url = marketoInstance + "&client_id=" + clientId + "&client_secret=" + clientSecret;
		// System.out.println(url);
		String token = getData(url);
		return token;
	}

	// Make request
	private String getData(String endpoint) {
		String data = null, token = null;
		try {
			URL url = new URL(endpoint);
			HttpsURLConnection urlConn = (HttpsURLConnection) url.openConnection();
			urlConn.setRequestMethod("GET");
			urlConn.setAllowUserInteraction(false);
			urlConn.setDoOutput(true);
			urlConn.setRequestProperty("Content-type", "application/json");
			urlConn.setRequestProperty("accept", "application/json");
			int responseCode = urlConn.getResponseCode();
			if (responseCode == 200) {
				// System.out.println("Status: 200");
				InputStream inStream = urlConn.getInputStream();
				data = convertStreamToString(inStream);
				JsonObject JSONObject = JsonObject.readFrom(data);
				token = JSONObject.get("access_token").asString();
				System.out.println(JSONObject.get("access_token"));

			} else {
				System.out.println(responseCode);
				data = "Status:" + responseCode;
			}
		} catch (MalformedURLException e) {
			System.out.println("URL not valid.");
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
			e.printStackTrace();
		}

		return token;
	}

	@SuppressWarnings("resource")
	private String convertStreamToString(InputStream inputStream) {

		try {
			return new Scanner(inputStream).useDelimiter("\\A").next();
		} catch (NoSuchElementException e) {
			return "";
		}
	}
}