package com.ibm.iotapp.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class IOTEvent {
	private static final DateFormat DF_FMT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+00:00");
	static 
	{
		DF_FMT.setTimeZone(TimeZone.getTimeZone("UTC"));
	}
	private String deviceId;
	private String eventType;
	private Date timestamp;
	private String deviceType;
	private String event;
	
	private SensorData sensorData;
	

	/**
	 * @param deviceId
	 * @param eventType
	 * @param timestamp
	 * @param event
	 */
	public IOTEvent(String deviceId,String deviceType, String eventType, Date timestamp, String event) {
		super();
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.eventType = eventType;
		this.timestamp = timestamp;
		this.event = event;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}
	/**
	 * @return the sensorData
	 */
	public SensorData getSensorData() {
		return sensorData;
	}

	/**
	 * @param sensorData the sensorData to set
	 */
	public void setSensorData(SensorData sensorData) {
		this.sensorData = sensorData;
	}
	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IOTEvent [deviceId=");
		builder.append(deviceId);
		builder.append(", eventType=");
		builder.append(eventType);
		builder.append(", timestamp=");
		builder.append(DF_FMT.format(timestamp));
		builder.append(", deviceType=");
		builder.append(deviceType);
		builder.append(", sensorData=");
		builder.append(sensorData);
		builder.append("]");
		return builder.toString();
	}
	
	
}
