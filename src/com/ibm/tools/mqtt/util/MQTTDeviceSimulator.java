package com.ibm.tools.mqtt.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * MQTT protocol based device simulator. Sends data to IBM IOT using MQTT protocol.
 * Each instance simulates a physical device 
 * @author suddutt1
 *
 */

public class MQTTDeviceSimulator {
	
	private static final Logger LOGGER = Logger
			.getLogger(MQTTDeviceSimulator.class.getName());
	private static final TimeZone UTC_TZ = TimeZone.getTimeZone("UTC");
    private static DateFormat DATE_FMT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+00:00");
    static{
    	DATE_FMT.setTimeZone(UTC_TZ);
    }
    
    
	private String deviceId;
	private String clientId;
	private String orgId;
	private String deviceType;
	private String broker;
	private String authToken;

	/**
	 * Device constructor. 
	 */
	public MQTTDeviceSimulator(String deviceId, String deviceType,
			String orgId, String authToken) {
		this.deviceId = deviceId;
		this.orgId = orgId;
		this.deviceType = deviceType;
		this.clientId = "d:" + this.orgId + ":" + this.deviceType + ":"
				+ this.deviceId;
		this.broker = "tcp://" + this.orgId
				+ ".messaging.internetofthings.ibmcloud.com:1883";

		this.authToken = authToken;
	}

	/**
	 * Sends a payload to IOT
	 * @param event String
	 * @param dataPayloadJson String in JSON format
	 * @return true/false depending on transmission success.
	 */
	public boolean simulateDataSend(String event, String dataPayloadJson) {
		boolean isSuccess = false;
		try {
			String topic = "iot-2/evt/" + event + "/fmt/json";
			String isoTimeStamp = DATE_FMT.format(new Date());
			String content = "{  \"d\": " + dataPayloadJson + ",\"ts\": \"" +isoTimeStamp+"\"  }";
			int qos = 0;
			MemoryPersistence persistence = new MemoryPersistence();
			MqttClient sampleClient = new MqttClient(this.broker,
					this.clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			connOpts.setUserName("use-token-auth");
			connOpts.setPassword(authToken.toCharArray());
			LOGGER.log(Level.INFO,"|MQTTDEVICE_SIMULATOR|Connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			LOGGER.log(Level.INFO,"|MQTTDEVICE_SIMULATOR|Connected");
			LOGGER.log(Level.INFO,"|MQTTDEVICE_SIMULATOR|Publishing message: " + content);
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			sampleClient.publish(topic, message);
			LOGGER.log(Level.INFO,"|MQTTDEVICE_SIMULATOR|Message published");
			sampleClient.disconnect();
			LOGGER.log(Level.INFO,"|MQTTDEVICE_SIMULATOR|Disconnected");
			isSuccess = true;
		} catch (MqttException me) {
			LOGGER.log(
					Level.WARNING,
					"|MQTTDEVICE_SIMULATOR|MQTT Publish failed reason "
							+ me.getReasonCode());
			LOGGER.log(
					Level.WARNING,
					"|MQTTDEVICE_SIMULATOR|MQTT Publish failedmsg "
							+ me.getMessage());
			LOGGER.log(
					Level.WARNING,
					"|MQTTDEVICE_SIMULATOR|MQTT Publish failedloc "
							+ me.getLocalizedMessage());
			LOGGER.log(
					Level.WARNING,
					"|MQTTDEVICE_SIMULATOR|MQTT Publish failedcause "
							+ me.getCause());
			LOGGER.log(Level.WARNING,
					"|MQTTDEVICE_SIMULATOR|MQTT Publish failedexcep " + me);
			LOGGER.log(Level.WARNING,
					"|MQTTDEVICE_SIMULATOR|MQTT Publish failed:", me);
			isSuccess = false;
		} catch (Exception ex) {
			LOGGER.log(Level.WARNING,
					"|MQTTDEVICE_SIMULATOR| MQTT Publish failed:", ex);
			isSuccess = false;
		}
		return isSuccess;
	}

}
