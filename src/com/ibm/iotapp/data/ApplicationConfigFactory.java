package com.ibm.iotapp.data;

import java.util.HashMap;
import java.util.Map;

public class ApplicationConfigFactory {

	public static final String MOTION_DATA_HISTORY_LIMIT ="motion_data_historical_limit";
	public static final String MOTION_BASED_OCCUPENCY_TH ="motion_data_occupency_th";
	
	public static final String US_SENSOR_MIN_DIST ="us_min_dist";
	public static final String US_SENSOR_MAX_DIST ="us_max_dist";
	public static final String US_SENSOR_OCCUPENCY_TH ="us_occupency_th";
	public static final String HISTORY_SERVICE_API_BASE = "history.service.api.base";
	
	private static final Map<String,Long> longProperties = new HashMap<>();
	private static final Map<String,String> stringProperties  = new HashMap<>();
	
	static{
		longProperties.put(MOTION_DATA_HISTORY_LIMIT, 1200000L);
		longProperties.put(MOTION_BASED_OCCUPENCY_TH, 10L);
		longProperties.put(US_SENSOR_MIN_DIST, 10L);
		longProperties.put(US_SENSOR_MAX_DIST, 100L);
		longProperties.put(US_SENSOR_OCCUPENCY_TH, 10L);
		stringProperties.put(HISTORY_SERVICE_API_BASE, "http://iothistoryservice.mybluemix.net/api/historyservice/");
	}
	private ApplicationConfigFactory()
	{
		super();
	}
	
	public static long getLongProperty(String property,long defValue)
	{
		return (longProperties.containsKey(property)? longProperties.get(property): defValue);
	}
	public static int getIntProperty(String property,int defValue)
	{
		return (longProperties.containsKey(property)? (int)((long)longProperties.get(property)): defValue);
	}
	public static String getProperty(String property,String defValue)
	{
		return (stringProperties.containsKey(property)? stringProperties.get(property): defValue);
	}
}

