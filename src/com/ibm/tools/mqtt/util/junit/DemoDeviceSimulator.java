package com.ibm.tools.mqtt.util.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ibm.iotapp.data.ApplicationConstants;
import com.ibm.iotapp.data.SensorData;
import com.ibm.tools.mqtt.util.MQTTDeviceSimulator;

public class DemoDeviceSimulator {

	private static final String ORG_ID ="ftwaq4";
	private static final String DEVICE_TYPE ="raspberrypi";
	private static final String DEVICE_ID ="sim1";
	private static final String AUTH_TOKEN ="sim1auth";
	
	
	//Loop control items
	private static final int SAMPLE_SIZE = 1000;
	private static final long DELAY = 10;
	
	@Test
	public void sendData() throws Exception {
		//fail("Not yet implemented");
		MQTTDeviceSimulator rasberrypiDevice = new MQTTDeviceSimulator(DEVICE_ID, DEVICE_TYPE, ORG_ID, AUTH_TOKEN);
		
		
				
		for(int index=0;index<SAMPLE_SIZE;index++)
		{
			SensorData data = new SensorData();
			data.setDeviceId(DEVICE_ID);
			System.out.println("Sending data "+ index);
			data.setMotion(((int)(Math.random()*10))%2);
			//data.setMotion(0);
			data.setTemp(20+Math.random()*10.0);
			data.setObstacle(Math.random()*500.00);
			rasberrypiDevice.simulateDataSend(ApplicationConstants.STATUS_EVENT, data.toJSON());
			Thread.sleep(DELAY);
		}
		
	}

}
