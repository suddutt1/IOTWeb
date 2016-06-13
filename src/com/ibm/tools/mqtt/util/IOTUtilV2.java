package com.ibm.tools.mqtt.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.iotapp.data.IOTApiResponse;
import com.ibm.iotapp.data.IOTEvent;
import com.ibm.iotapp.data.IOTEventJsonDeserializer;
import com.ibm.misc.BASE64Encoder;

/**
 * Utility class that talks to IBM IOT Foundation for reading IOT data using
 * REST API
 * 
 * @author suddutt1
 *
 */
public class IOTUtilV2 {

	private static final Logger LOGGER = Logger.getLogger(IOTUtilV2.class.getName());
	private static final String BASE_URL = ".internetofthings.ibmcloud.com/api/v0002/historian/types/";
	private static final BASE64Encoder BASE64_ENC = new BASE64Encoder();

	private String orgId;
	private String apiKey;
	private String authToken;
	private String authKeyBase64;

	/**
	 * Constructor
	 * 
	 * @param orgId
	 *            String
	 * @param apiKey
	 *            String
	 * @param authToken
	 *            String
	 */
	public IOTUtilV2(String orgId, String apiKey, String authToken) {
		this.orgId = orgId;
		this.apiKey = apiKey;
		this.authToken = authToken;
		this.authKeyBase64 = BASE64_ENC.encode((new String(this.apiKey + ":" + this.authToken)).getBytes());
	}

	/**
	 * Retrieve data for the give device types
	 * 
	 * @param deviceType
	 *            String
	 * @param eventType
	 *            String
	 * @return JSON output string
	 */
	private String retriveDataForDeviceType(DefaultHttpClient client, String deviceType, String deviceId,
			String eventType, long maxSize, String bookmark) {

		String finalResponse = null;
		String line = null;
		String url = null;
		try {
			if(bookmark!=null && bookmark.length()==0)
			{
				url = "https://" + this.orgId + BASE_URL + deviceType + "/devices/" + deviceId + "?evt_type="
					+ eventType + "&top=" + maxSize + "&_bookmark=" + URLEncoder.encode(bookmark, "UTF-8");
			}
			else
			{
				url = "https://" + this.orgId + BASE_URL + deviceType + "/devices/" + deviceId + "?evt_type="
						+ eventType + "&top=" + maxSize + "";
			}
			LOGGER.log(Level.INFO, "|IOTUTIL| BASEURL TO BE USER:" + url);
			HttpGet getRequest = new HttpGet(url);
			getRequest.setHeader("Content-Type", "application/json; charset=UTF-8");
			getRequest.setHeader("Authorization", "Basic " + this.authKeyBase64);
			HttpResponse response = client.execute(getRequest);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder responseString = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				responseString.append(line);
			}
			finalResponse = responseString.toString();

			LOGGER.log(Level.INFO, "|IOTUTIL|Response received" + finalResponse);

		} catch (Exception ex) {
			LOGGER.log(Level.WARNING, "|IOTUTIL|exception thrown", ex);
			finalResponse = null;
		}
		return finalResponse;
	}

	private String retriveDataForDeviceType(DefaultHttpClient client, String deviceType, String deviceId,
			String eventType, String bookmark,long timeFilter) {

		String finalResponse = null;
		String line = null;
		try {

			String url = "https://" + this.orgId + BASE_URL + deviceType + "/devices/" + deviceId + "?evt_type="
					+ eventType +"&start="+timeFilter+ "&_bookmark=" + URLEncoder.encode(bookmark, "UTF-8");
			LOGGER.log(Level.INFO, "|IOTUTIL| BASEURL TO BE USER:" + url);
			HttpGet getRequest = new HttpGet(url);
			getRequest.setHeader("Content-Type", "application/json; charset=UTF-8");
			getRequest.setHeader("Authorization", "Basic " + this.authKeyBase64);
			HttpResponse response = client.execute(getRequest);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder responseString = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				responseString.append(line);
			}
			finalResponse = responseString.toString();

			LOGGER.log(Level.INFO, "|IOTUTIL|Response received" + finalResponse);

		} catch (Exception ex) {
			LOGGER.log(Level.WARNING, "|IOTUTIL|exception thrown", ex);
			finalResponse = null;
		}
		return finalResponse;
	}

	public List<IOTEvent> retriveEvents(String deviceType, String deviceId, String eventType, long timeFilter,
			int maxSize) {
		{
			List<IOTEvent> retList = null;
			try {
				Gson gson = new GsonBuilder()
						.registerTypeAdapter(IOTApiResponse.class, new IOTEventJsonDeserializer(timeFilter)).create();
				DefaultHttpClient client = new DefaultHttpClient();
				String bookmark = "";
				while (bookmark != null) {
					String output = retriveDataForDeviceType(client, deviceType, deviceId, eventType, maxSize,
							bookmark);
					if (output.length() > 0) {
						IOTApiResponse response = gson.fromJson(output, IOTApiResponse.class);
						bookmark = response.getBookmark();
						LOGGER.log(Level.INFO, "|IOTUTIL|bookmark -->" + bookmark);
						if (retList == null) {
							retList = new ArrayList<>();
						}

						retList.addAll(response.getEvents());
					} else {
						retList = Collections.emptyList();
						break;
					}
				}
			} catch (Exception ex) {
				LOGGER.log(Level.WARNING, "|IOTUTIL|Failed to parse the events", ex);
				retList = Collections.emptyList();
			}
			return retList;
		}
	}

	/**
	 * Retrieves events from IBM IOT gateway those are posted after start time
	 * parameter
	 * 
	 * @param deviceType
	 *            String
	 * @param deviceId
	 *            String
	 * @param eventType
	 *            String
	 * @param startTime
	 *            long ( EPOC time for event start marker)
	 * @return List<IOTEvent>
	 */
	public List<IOTEvent> retrieveEventsTimeFiltered(String deviceType, String deviceId, String eventType,
			long startTime) {

		List<IOTEvent> retList = null;
		try {
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(IOTApiResponse.class, new IOTEventJsonDeserializer(0)).create();
			DefaultHttpClient client = new DefaultHttpClient();
			String bookmark = "";
			while (bookmark != null) {
				String output = retriveDataForDeviceType(client, deviceType, deviceId, eventType, bookmark,startTime);
				if (output.length() > 0) {
					IOTApiResponse response = gson.fromJson(output, IOTApiResponse.class);
					bookmark = response.getBookmark();
					LOGGER.log(Level.INFO, "|IOTUTIL|bookmark -->" + bookmark);
					if (retList == null) {
						retList = new ArrayList<>();
					}

					retList.addAll(response.getEvents());
				} else {
					retList = Collections.emptyList();
					break;
				}
			}
		} catch (Exception ex) {
			LOGGER.log(Level.WARNING, "|IOTUTIL|Failed to parse the events", ex);
			retList = Collections.emptyList();
		}
		return retList;

	}
	
}