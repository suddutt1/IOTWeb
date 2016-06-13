package com.ibm.tools.mqtt.util.junit;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ibm.iotapp.data.ApplicationConstants;
import com.ibm.iotapp.data.IOTEvent;
import com.ibm.tools.mqtt.util.IOTUtil;

public class IOTUtilTest {
	
	private static final String ORG_ID ="ftwaq4";
	private static final String DEVICE_TYPE ="raspberrypi";
	private static final String DEVICE_ID ="sim1";
	private static final String API_AUTH_TOKEN ="@4ox@0dNvQ7wm+WTgP";
	private static final String API_KEY ="a-ftwaq4-fwiu8sk6or";
	

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRetrival() {
		try{
			IOTUtil iotUtil = new IOTUtil(ORG_ID, API_KEY, API_AUTH_TOKEN);
			List<IOTEvent> eventList = iotUtil.retriveEvents(DEVICE_TYPE,ApplicationConstants.STATUS_EVENT);
			assertNotNull(eventList);
			System.out.println("Number of events " + eventList.size());
			System.out.println(eventList);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		/*try{
		IOTUtil iotUtil = new IOTUtil(ORG_ID, API_KEY, API_AUTH_TOKEN);
		String output = iotUtil.retriveDataForDeviceType(DEVICE_TYPE, ApplicationConstants.SIM_TEMP_EVENT);
		if(output.length()>0)
		{
			Gson gson = new GsonBuilder().registerTypeAdapter(IOTEvent.class, new IOTEventJsonDeserializer()).create();
			Type listType = new TypeToken<List<IOTEvent>>(){}.getType();
			
			List<IOTEvent> array = gson.fromJson(output, listType);
			System.out.println(array);
			Gson gson2 = new GsonBuilder().registerTypeAdapter(IOTEvent.class, new SensorDataSerializer()).create();
			
			String jsonOutput = gson2.toJson(array,listType);
			System.out.println(jsonOutput);
		}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}*/
	}

}
