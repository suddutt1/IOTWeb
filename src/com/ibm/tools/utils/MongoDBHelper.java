package com.ibm.tools.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
/*import com.ibm.nosql.json.api.BasicDBList;
import com.ibm.nosql.json.api.BasicDBObject;
import com.ibm.nosql.json.util.JSON;*/
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDBHelper {

	private static Logger LOGGER = Logger.getLogger(MongoDBHelper.class
			.getName());

	// To stop instantiation and access only static way
	private MongoDBHelper() {
		super();
	}

	private static MongoClient mongo;
	private static boolean isInitialized = false;
	private static DB db;

	private static boolean init() {
		try {
			if (mongo == null) {
				String serviceJSON = System.getenv("VCAP_SERVICES");
				if (serviceJSON != null) {
						LOGGER.log(Level.INFO, "|MONGODB_HELPER|Parsing VCAP_SERVICES: "
							+ serviceJSON);
						String dbUri = getURIFromVCAP(serviceJSON,"mongolab");
						MongoClientURI uri = new MongoClientURI(dbUri
								+ "?connectTimeoutMS=300000");
						mongo = new MongoClient(uri);
						db = mongo.getDB(uri.getDatabase());
						LOGGER.log(Level.INFO, "|MONGODB_HELPER|DBURL found: "
								+ dbUri);
					
					System.out.println("COULD ENVIRONMENT ");
				} else {
					MongoClientURI uri = new MongoClientURI(
							LocalVCAPProperties.getLocalProperty("mongodb.url")
									+ "?connectTimeoutMS=300000");
					mongo = new MongoClient(uri);
					db = mongo.getDB(uri.getDatabase());
					System.out.println("LOCAL ENVIRONMENT ");
				}
				isInitialized = true;
			}
		} catch (Exception ex) {
			LOGGER.log(Level.WARNING, "Error in initizlizing MongoBB", ex);
			isInitialized = false;
		}
		return isInitialized;
	}

	public static DBCollection getCollection(String collectionName) {

		if (init()) {
			return db.getCollection(collectionName);
		} else {
			return null;
		}

	}
	
	private static String getURIFromVCAP(String vcapString, String serviceName)
	{
		Gson gson  = new Gson();
		Object obj = gson.fromJson(vcapString, Object.class);
		Map<String,Object> map = ( Map<String,Object>) obj;
		List<Object> serviceDetails = (List<Object>)(map.get(serviceName));
		if(serviceDetails!=null )
		{
			Map<String,Object> serviceAttrs= (Map<String,Object>)serviceDetails.get(0);
			Map<String,String> credentials =( Map<String,String>)serviceAttrs.get("credentials");
			String uri = credentials.get("uri");
			System.out.println(uri);
			return uri;
		}
		return null;
	}
}
