package com.ibm.tools.mqtt.util.junit;

import static org.junit.Assert.assertNotNull;


import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import com.ibm.iotapp.data.ApplicationConstants;
import com.ibm.iotapp.data.IOTEvent;
import com.ibm.tools.mqtt.util.IOTUtilV2;

public class IOTUtilV2Test {

	private static final String ORG_ID = "ftwaq4";
	private static final String DEVICE_TYPE = "raspberrypi";
	private static final String DEVICE_ID = "b827ebe5bdc9";//b827ebe5bdc9//smi1
	private static final String API_AUTH_TOKEN = "@4ox@0dNvQ7wm+WTgP";
	private static final String API_KEY = "a-ftwaq4-fwiu8sk6or";
	
	private static final TimeZone UTC_TZ = TimeZone.getTimeZone("UTC");
    private static DateFormat DATE_FMT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+00:00");

	@Before
	public void setUp() throws Exception {
		DATE_FMT.setTimeZone(UTC_TZ);
	}

	@Test
	public void testRetrievalWithFilter()
	{
		try {
			setDebugLevel(Level.WARNING);
			DateTime currentTime = new DateTime(DateTimeZone.UTC);
			long endTime = currentTime.getMillis();
			long startTime = endTime-86400000L;//LAST one day
			//Get last 2 days data
			IOTUtilV2 iotUtil = new IOTUtilV2(ORG_ID, API_KEY, API_AUTH_TOKEN);
			List<IOTEvent> eventList = iotUtil.retrieveEventsTimeFiltered(DEVICE_TYPE, DEVICE_ID,
					ApplicationConstants.STATUS_EVENT, startTime); 
			/*List<IOTEvent> eventList = iotUtil.retriveEvents(DEVICE_TYPE, DEVICE_ID,
					ApplicationConstants.STATUS_EVENT, startTime,20); */
			assertNotNull(eventList);
			System.out.println("Number of events " + eventList.size());
			if (eventList.size() > 0) {
				// System.out.println(eventList);
				Collections.sort(eventList, new Comparator<IOTEvent>() {

					@Override
					public int compare(IOTEvent o1, IOTEvent o2) {
						return o1.getTimestamp().compareTo(o2.getTimestamp());
					}

				});
				//System.out.println(eventList.get(0));

				//System.out.println(eventList.get(eventList.size() - 1));
				buildCSV("C:\\Users\\SUDDUTT1\\Desktop\\events.csv",eventList);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void buildCSV(String csvPath,List<IOTEvent> eventList) throws Exception
	{
		FileOutputStream fs = new FileOutputStream(csvPath);
		StringBuilder header = new StringBuilder();
		header.append("\"ts\",\"motion\",\"temp\",\"obstacle\"\n");
		fs.write(header.toString().getBytes());
		for(IOTEvent event : eventList)
		{
			StringBuilder strBuf = new StringBuilder();
			strBuf.append("\"").append(DATE_FMT.format(event.getTimestamp())).append("\",");
			strBuf.append("\"").append(event.getSensorData().getMotion()).append("\",");
			strBuf.append("\"").append(event.getSensorData().getTemp()).append("\",");
			strBuf.append("\"").append(event.getSensorData().getObstacle()).append("\"\n");
			//System.out.println(strBuf);
			fs.write(strBuf.toString().getBytes());
		}
		fs.close();
	}
	public void testRetrival() {
		try {
			long time = 1;
			IOTUtilV2 iotUtil = new IOTUtilV2(ORG_ID, API_KEY, API_AUTH_TOKEN);
			List<IOTEvent> eventList = iotUtil.retriveEvents(DEVICE_TYPE, DEVICE_ID,
					ApplicationConstants.STATUS_EVENT, time,20);
			assertNotNull(eventList);
			System.out.println("Number of events " + eventList.size());
			if (eventList.size() > 0) {
				// System.out.println(eventList);
				Collections.sort(eventList, new Comparator<IOTEvent>() {

					@Override
					public int compare(IOTEvent o1, IOTEvent o2) {
						return o1.getTimestamp().compareTo(o2.getTimestamp());
					}

				});
				System.out.println(eventList.get(0));

				System.out.println(eventList.get(eventList.size() - 1));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void setDebugLevel(Level newLvl) {
	    Logger anonymousLogger = LogManager.getLogManager().getLogger("");
	    Handler[] handlers = anonymousLogger.getHandlers();
	    anonymousLogger.setLevel(newLvl);
	    for (Handler h : handlers) {
	       
	            h.setLevel(newLvl);
	    }
	}
}
