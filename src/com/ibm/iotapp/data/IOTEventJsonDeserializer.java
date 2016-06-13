package com.ibm.iotapp.data;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class IOTEventJsonDeserializer implements JsonDeserializer<IOTApiResponse> {

	private long eventTimeFilter;

	public IOTEventJsonDeserializer(long eventTimeFilter) {
		this.eventTimeFilter = eventTimeFilter;
	}

	@Override
	public IOTApiResponse deserialize(JsonElement json, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		String bookmark = (jsonObject.get("bookmark") != null ? jsonObject.get("bookmark").getAsString() : null);
		IOTApiResponse apiResponse = new IOTApiResponse(bookmark);
		apiResponse.setEventTimeFilter(eventTimeFilter);
		JsonArray eventsJson = jsonObject.get("events").getAsJsonArray();
		if (eventsJson.size() > 0) {

			int size = eventsJson.size();
			for (int index = 0; index < size; index++) {
				JsonObject eventDetails = eventsJson.get(index).getAsJsonObject();
				String deviceId = eventDetails.get("device_id").getAsString();
				String deviceType = eventDetails.get("device_type").getAsString();
				String event_type = eventDetails.get("evt_type").getAsString();
				long timeStamp = eventDetails.get("timestamp").getAsJsonObject().get("$date").getAsLong();
				String event = eventDetails.get("evt").toString();
				JsonObject sensorDataJsonObject = eventDetails.get("evt").getAsJsonObject();
				SensorData sensorData = new SensorData();
				sensorData.setDeviceId(sensorDataJsonObject.get("deviceId").getAsString());
				sensorData.setMotion(sensorDataJsonObject.get("motion").getAsDouble());
				sensorData.setTemp(sensorDataJsonObject.get("temp").getAsDouble());
				if(sensorDataJsonObject.get("obstacle")!=null)
				{
					sensorData.setObstacle(sensorDataJsonObject.get("obstacle").getAsDouble());
				}
				IOTEvent eventObject = new IOTEvent(deviceId, deviceType, event_type, new Date(timeStamp), event);
				eventObject.setSensorData(sensorData);
				apiResponse.addEvent(eventObject);
			}

		} else {

		}
		return apiResponse;

	}

}
