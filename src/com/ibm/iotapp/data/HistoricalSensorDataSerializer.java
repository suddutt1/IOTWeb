package com.ibm.iotapp.data;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class HistoricalSensorDataSerializer implements JsonSerializer<IOTEvent> {

	private SensorField sensorField;

	public HistoricalSensorDataSerializer(SensorField sf) {
		this.sensorField = sf;
	}

	@Override
	public JsonElement serialize(IOTEvent event, Type arg1, JsonSerializationContext arg2) {
		// TODO Auto-generated method stub
		final JsonObject jsonObject = new JsonObject();

		jsonObject.add("ts", new JsonPrimitive(event.getTimestamp().getTime()));
		switch (sensorField) {
		case MOTION:
			jsonObject.add("y", new JsonPrimitive(event.getSensorData().getMotion()));
			break;
		case TEMP:
			jsonObject.add("y", new JsonPrimitive(event.getSensorData().getTemp()));
			break;
		case USCDIST:
			jsonObject.add("y", new JsonPrimitive(event.getSensorData().getObstacle()));
			break;
		default:
			jsonObject.add("motion", new JsonPrimitive(event.getSensorData().getMotion()));
			jsonObject.add("temp", new JsonPrimitive(event.getSensorData().getTemp()));
			break;
		}

		return jsonObject;
	}

}
