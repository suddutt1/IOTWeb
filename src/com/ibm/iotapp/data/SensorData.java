package com.ibm.iotapp.data;

import com.google.gson.Gson;

/**
 * Java bean to hold the sensor data
 * @author SUDDUTT1
 *
 */
public class SensorData {

	
	private String deviceId;
	private double motion;
	private double temp;
	private double obstacle;
	
	public SensorData()
	{
		super();
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
	 * @return the motion
	 */
	public double getMotion() {
		return motion;
	}

	/**
	 * @param motion the motion to set
	 */
	public void setMotion(double motion) {
		this.motion = motion;
	}

	/**
	 * @return the temp
	 */
	public double getTemp() {
		return temp;
	}

	/**
	 * @param temp the temp to set
	 */
	public void setTemp(double temp) {
		this.temp = temp;
	}
	/**
	 * @return the obstacle
	 */
	public double getObstacle() {
		return obstacle;
	}

	/**
	 * @param obstacle the obstacle to set
	 */
	public void setObstacle(double obstacle) {
		this.obstacle = obstacle;
	}
	
	public String toJSON()
	{
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
