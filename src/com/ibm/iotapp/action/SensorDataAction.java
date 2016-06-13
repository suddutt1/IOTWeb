package com.ibm.iotapp.action;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.app.web.frmwk.WebActionHandler;
import com.ibm.app.web.frmwk.annotations.RequestMapping;
import com.ibm.app.web.frmwk.bean.ModelAndView;
import com.ibm.app.web.frmwk.bean.ViewType;
import com.ibm.iotapp.data.ApplicationConfigFactory;
import com.ibm.iotapp.data.ApplicationConstants;
import com.ibm.iotapp.data.HistoricalSensorDataSerializer;
import com.ibm.iotapp.data.IOTEvent;
import com.ibm.iotapp.data.SensorDataSerializer;
import com.ibm.iotapp.data.SensorField;
import com.ibm.tools.mqtt.util.IOTUtilV2;

import sun.org.mozilla.javascript.internal.ast.SwitchCase;

public class SensorDataAction implements WebActionHandler {

	private static final String ORG_ID = "ftwaq4";
	private static final String DEVICE_TYPE = "raspberrypi";
	private static final String API_AUTH_TOKEN = "@4ox@0dNvQ7wm+WTgP";
	private static final String API_KEY = "a-ftwaq4-fwiu8sk6or";

	@RequestMapping("retrieveMotionSensorData.wss")
	public ModelAndView retrieveMotionSensorData(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mvObject = new ModelAndView(ViewType.AJAX_VIEW);
		String deviceId = request.getParameter("deviceId");
		mvObject.setView(getMotionDataOutput(deviceId, SensorField.MOTION));
		return mvObject;
	}

	@RequestMapping("retrieveUsSenorData.wss")
	public ModelAndView retrieveUsSensorData(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mvObject = new ModelAndView(ViewType.AJAX_VIEW);
		String deviceId = request.getParameter("deviceId");
		mvObject.setView(getMotionDataOutput(deviceId, SensorField.USCDIST));
		return mvObject;
	}

	@RequestMapping("getHistoricalSensorData.wss")
	public ModelAndView getHistoricalSensorData(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mvObject = new ModelAndView(ViewType.AJAX_VIEW);
		String deviceId = request.getParameter("deviceId");
		String sensorType = request.getParameter("sensorType");
		String hoursStr = request.getParameter("hours");
		int hours = Integer.parseInt(hoursStr);
		SensorField sf = SensorField.ALL;
		switch (sensorType) {
		case "motion":
			sf = SensorField.MOTION;
			break;
		case "temp":
			sf = SensorField.TEMP;
			break;
		case "obstacle":
			sf = SensorField.USCDIST;
			break;

		}
		mvObject.setView(getHistoricalData(deviceId, sf, hours));
		return mvObject;
	}

	public String getHistoricalData(String deviceId, SensorField field, int hours) {
		DateTime currentTime = new DateTime(DateTimeZone.UTC);
		long endTime = currentTime.getMillis();
		// Get last 60 minutes data
		long startTime = endTime - hours * 3600000L;
		
		/*IOTUtilV2 iotUtil = new IOTUtilV2(ORG_ID, API_KEY, API_AUTH_TOKEN);
		// Get the last one hour data
		List<IOTEvent> eventList = iotUtil.retrieveEventsTimeFiltered(DEVICE_TYPE, deviceId,
				ApplicationConstants.STATUS_EVENT, startTime);
		if (eventList != null && eventList.size() > 0) {
			System.out.println("Size of the events list:"+ eventList.size());
			Collections.sort(eventList, new Comparator<IOTEvent>() {

				@Override
				public int compare(IOTEvent o1, IOTEvent o2) {
					return o1.getTimestamp().compareTo(o2.getTimestamp());
				}

			});
			int size = eventList.size();
			if(size>1000)
			{
				eventList = eventList.subList(size-500, size-1);
			}
			Gson gson = new GsonBuilder().registerTypeAdapter(IOTEvent.class, new HistoricalSensorDataSerializer(field)).create();

			return gson.toJson(eventList);
		}*/
		return "[{\"ts\":" + startTime + ",\"motion\": 0 }]";

	}

	public String getMotionDataOutput(String deviceId, SensorField field) {

		DateTime currentTime = new DateTime(DateTimeZone.UTC);
		long endTime = currentTime.getMillis();
		// Get last 60 minutes data
		long startTime = endTime
				- ApplicationConfigFactory.getLongProperty(ApplicationConfigFactory.MOTION_DATA_HISTORY_LIMIT, 120000L);

		IOTUtilV2 iotUtil = new IOTUtilV2(ORG_ID, API_KEY, API_AUTH_TOKEN);
		// Get the last one hour data
		List<IOTEvent> eventList = iotUtil.retrieveEventsTimeFiltered(DEVICE_TYPE, deviceId,
				ApplicationConstants.STATUS_EVENT, startTime);
		if (eventList != null && eventList.size() > 0) {
			Collections.sort(eventList, new Comparator<IOTEvent>() {

				@Override
				public int compare(IOTEvent o1, IOTEvent o2) {
					return o1.getTimestamp().compareTo(o2.getTimestamp());
				}

			});
			Gson gson = new GsonBuilder().registerTypeAdapter(IOTEvent.class, new SensorDataSerializer(field)).create();

			return gson.toJson(eventList);
		}
		return "[{\"ts\":" + startTime + ",\"motion\": 0 }]";
	}

}
