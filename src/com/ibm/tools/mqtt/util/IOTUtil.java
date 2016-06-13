package com.ibm.tools.mqtt.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ibm.iotapp.data.IOTApiResponse;
import com.ibm.iotapp.data.IOTEvent;
import com.ibm.iotapp.data.IOTEventJsonDeserializer;
import com.ibm.misc.BASE64Encoder;

/**
 * Utility class that talks to IBM IOT Foundation for reading IOT data using REST API  
 * @author suddutt1
 *
 */
@Deprecated
public class IOTUtil {

	private static final Logger LOGGER = Logger.getLogger(IOTUtil.class
			.getName());
	private static final String BASE_URL = "https://internetofthings.ibmcloud.com/api/v0001/historian/";
	private static final BASE64Encoder BASE64_ENC = new BASE64Encoder();

	private String orgId;
	private String apiKey;
	private String authToken;
	private String authKeyBase64;

	/**
	 * Constructor
	 * @param orgId String
	 * @param apiKey String 
	 * @param authToken String
	 */
	public IOTUtil(String orgId, String apiKey, String authToken) {
		this.orgId = orgId;
		this.apiKey = apiKey;
		this.authToken = authToken;
		this.authKeyBase64 = BASE64_ENC.encode((new String(this.apiKey + ":"
				+ this.authToken)).getBytes());
	}

	/**
	 * Retrieve data for the give device types
	 * @param deviceType String
	 * @param eventType String
	 * @return JSON output string
	 */
	public String retriveDataForDeviceType(String deviceType,
			String eventType) {

		String finalResponse = null;
		String line = null;
		try {
			long lastOneDay  = System.currentTimeMillis()-86400*1000;
			DefaultHttpClient client = new DefaultHttpClient();
			String url = "https://"+this.orgId+".internetofthings.ibmcloud.com/api/v0002/historian/types/"+deviceType+"?_bookmark=&evt_type="+eventType+"&start="+lastOneDay; ;
			LOGGER.log(Level.INFO, "|IOTUTIL| BASEURL TO BE USER:" + url);
			HttpGet getRequest = new HttpGet(url);
			getRequest.setHeader("Content-Type",
					"application/json; charset=UTF-8");
			getRequest.setHeader("Authorization", "Basic " + this.authKeyBase64);
			//getRequest.setHeader("cursorId", "");
			HttpResponse response = client.execute(getRequest);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			StringBuilder responseString = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				responseString.append(line);
			}
			finalResponse = responseString.toString();
			 
			LOGGER.log(Level.INFO, "|IOTUTIL|Response received" + finalResponse);
			
			//getRequest.setHeader("cursorId","0");
			//HttpResponse response2 = client.execute(getRequest);
			//response2.containsHeader("cursorId");

		} catch (Exception ex) {
			LOGGER.log(Level.WARNING, "|IOTUTIL|exception thrown", ex);
			finalResponse = null;
		}
		return finalResponse;
	}
	
	
	/**
	 * Retrieve events 
	 * @param deviceType
	 * @param eventType
	 * @return List<IOTEvent>
	 */
	public List<IOTEvent> retriveEvents(String deviceType,
			String eventType) {
		{
			List<IOTEvent> retList = null;
			try{
			String output = retriveDataForDeviceType(deviceType, eventType);
			if(output.length()>0)
			{
				Gson gson = new GsonBuilder().registerTypeAdapter(IOTApiResponse.class, new IOTEventJsonDeserializer(0)).create();
				
				IOTApiResponse response = gson.fromJson(output,IOTApiResponse.class);
				System.out.println("bookmark -->"+response.getBookmark());
				retList = response.getEvents();
			}
			else
			{
				retList = Collections.emptyList();
			}
			}catch(Exception ex)
			{
				LOGGER.log(Level.WARNING, "|IOTUTIL|Failed to parse the events", ex);
				retList = Collections.emptyList();
			}
			return retList;
		}
	}
}