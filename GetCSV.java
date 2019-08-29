package com.tadigital.getcsv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.tadigital.authentication.Identity;
import com.tadigital.dao.ImportCsv;
import com.tadigital.leadActions.Formatingtocsv;

public class GetCSV {
	String marketoInstance = "https://876-swj-450.mktorest.com";// Replace this with the host from Admin Web Services
	String marketoIdURL = marketoInstance + "/identity";
	String clientId = "d72450d9-2bbb-4116-b7af-79db718cedc7"; // Obtain from your Custom Service in Admin>Launchpoint
	String clientSecret = "5Y1JEIL3UR3iKJyPeBt7XVhVbsk5GbRM";
	String tokenType = "bearer";
	int job_queueStatus = 0;
	String job_id = null;

	public void Util() throws IOException, InterruptedException, SQLException {

		GetCSV getcsv = new GetCSV();
		String startDate = null, endDate = null;
		String export_id = getcsv.CreateJob(startDate, endDate);
		System.out.println(export_id);
		TimeUnit.SECONDS.sleep(1); // Sleep for 1 seconds before enqueueing the job
		String enqueue_status = getcsv.StartingJob(export_id);
		System.out.println(enqueue_status);
		TimeUnit.SECONDS.sleep(10); // Sleep for 10 seconds before polling the job status after queueing
		String job_status, withoutQuotes_line1;
		do {
			job_status = getcsv.PollingJobStatus(export_id);
			withoutQuotes_line1 = job_status.replace("\"", "");
			System.out.println(withoutQuotes_line1);
			TimeUnit.SECONDS.sleep(20);
		} while (!(withoutQuotes_line1.equals("Completed"))); // Wait for 20 seconds and check the status of the queued
																// job
																// Repeat (Keep checking the status of the job) this
																// until the job status changes to Completed.
																// Once the job status is changed to completed issue the
																// GET request to get the CSV file in the form of
																// response
		TimeUnit.SECONDS.sleep(10); // Sleep for 10 seconds before making a GET request for the csv
		String csv_response = getcsv.retrieveData(export_id);
		boolean write_status = getcsv.writeToFile(csv_response);
		if (write_status == true) {
			System.out.println("File Written Succesfully");
		} else {
			System.out.println("File not written");
		}

		getcsv.writeToSQL();

	}

	public String CreateJob(String startDate, String endDate) throws IOException {

		String access_token = new Identity().getToken();
		String resp = null;
		String idEndpoint = marketoInstance + "/bulk/v1/leads/export/create.json?access_token=" + access_token
				+ "&token_type=" + tokenType;
		System.out.println(idEndpoint);
		URL url = new URL(idEndpoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		String jsonInputString = "{\r\n" + "   \"fields\": [\r\n" + "      \"id\",\r\n" + "      \"firstName\",\r\n"
				+ "      \"lastName\",\r\n" + "      \"country\",\r\n" + "      \"email\",\r\n"
				+ "      \"SfdcType\"\r\n" + "   ],\r\n" + "   \"format\": \"CSV\",\r\n"
				+ "   \"columnHeaderNames\": {\r\n" + "   	  \"id\": \"Lead ID\",\r\n"
				+ "      \"firstName\": \"First Name\",\r\n" + "      \"lastName\": \"Last Name\" ,\r\n"
				+ "      \"country\": \"Country\" ,\r\n" + "      \"email\": \"Email Address\", \r\n"
				+ "      \"SfdcType\": \"SFDC Type\"\r\n" + "   },\r\n" + "   \"filter\": {\r\n"
				+ "      \"createdAt\": {\r\n" + "         \"startAt\": \"" + startDate + "T00:00:00Z\",\r\n"
				+ "         \"endAt\": \"" + endDate + "T00:00:00Z\"\r\n" + "      }\r\n" + "   }\r\n" + "}";

		try (OutputStream os = con.getOutputStream()) {
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);
		}

		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}

			System.out.println(response.toString());

			JsonObject JSONObject = JsonObject.readFrom(response.toString());
			JsonArray jsonarray = (JsonArray) JSONObject.get("result");
			JsonObject jo1 = (JsonObject) jsonarray.get(0);

			job_id = jo1.get("exportId").toString();

		}

		return job_id;
	}

	public String StartingJob(String export_id) throws IOException {

		String access_token = new Identity().getToken();
		String id = export_id;
		String resp = null, status = "Default Status";
		String idEndPoint = marketoInstance + "/bulk/v1/leads/export/" + id + "/enqueue.json" + "?access_token="
				+ access_token + "&token_type=" + tokenType;
		String withoutQuotes_line1 = idEndPoint.replace("\"", "");
		System.out.println(withoutQuotes_line1);
		URL url = new URL(withoutQuotes_line1);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}

			System.out.println(response.toString());
			JsonObject JSONObject = JsonObject.readFrom(response.toString());
			JsonArray jsonarray = (JsonArray) JSONObject.get("result");
			if (jsonarray != null) {

				JsonObject jo1 = (JsonObject) jsonarray.get(0);

				status = jo1.get("status").toString();
			} else {
				JsonArray jsonarray1 = (JsonArray) JSONObject.get("errors");
				JsonObject jo1 = (JsonObject) jsonarray1.get(0);

				status = jo1.get("message").toString();

			}

		}
		return status;

	}

	public String PollingJobStatus(String export_id) throws IOException {

		String access_token = new Identity().getToken();
		String resp = null, status = "Default Status";
		String idEndPoint = marketoInstance + "/bulk/v1/leads/export/" + export_id + "/status.json" + "?access_token="
				+ access_token + "&token_type=" + tokenType;
		String withoutQuotes_line1 = idEndPoint.replace("\"", "");
		URL url = new URL(withoutQuotes_line1);
		HttpsURLConnection urlConn = (HttpsURLConnection) url.openConnection();
		urlConn.setRequestMethod("GET");
		urlConn.setAllowUserInteraction(false);
		urlConn.setDoOutput(true);
		urlConn.setRequestProperty("Content-type", "application/json");
		urlConn.setRequestProperty("accept", "application/json");
		try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}

			System.out.println(response.toString());
			JsonObject JSONObject = JsonObject.readFrom(response.toString());
			JsonArray jsonarray = (JsonArray) JSONObject.get("result");
			if (jsonarray != null) {

				JsonObject jo1 = (JsonObject) jsonarray.get(0);

				status = jo1.get("status").toString();
			} else {
				JsonArray jsonarray1 = (JsonArray) JSONObject.get("errors");
				JsonObject jo1 = (JsonObject) jsonarray1.get(0);

				status = jo1.get("message").toString();

			}

		}
		return status;
	}

	public String retrieveData(String export_id) throws IOException {
		String access_token = new Identity().getToken();
		String resp = null, status = "Default Status";
		String idEndPoint = marketoInstance + "/bulk/v1/leads/export/" + export_id + "/file.json" + "?access_token="
				+ access_token + "&token_type=" + tokenType;
		String withoutQuotes_line1 = idEndPoint.replace("\"", "");
		URL url = new URL(withoutQuotes_line1);
		HttpsURLConnection urlConn = (HttpsURLConnection) url.openConnection();
		urlConn.setRequestMethod("GET");
		urlConn.setAllowUserInteraction(false);
		urlConn.setDoOutput(true);
		urlConn.setRequestProperty("Content-type", "application/json");
		urlConn.setRequestProperty("accept", "application/json");
		try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}

			System.out.println(response.toString());
			resp = response.toString();
			if (resp == "")
				status = "Data not found";
			else
				status = "Data Found";
		}
		return resp;
	}

	public boolean writeToFile(String s) throws IOException {
		boolean status = false;
		Formatingtocsv fcsv = new Formatingtocsv();
		String result = fcsv.processData(s);
		File file = new File("C://Users//darshil.shah//Desktop//LeadDataJava.txt");

		// Create the file
		if (file.createNewFile()) {
			System.out.println("File is created!");
		} else {
			System.out.println("File already exists.");
		}

		// Write Content
		FileWriter writer = new FileWriter(file);
		writer.write(result);
		writer.close();
		status = true;
		return status;

	}

	public void writeToSQL() throws SQLException, IOException {

		ImportCsv im = new ImportCsv();
		im.readCsv();
	}

}
