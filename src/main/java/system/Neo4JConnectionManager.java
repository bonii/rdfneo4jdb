package main.java.system;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.neo4j.driver.v1.*;

public final class Neo4JConnectionManager {

	private static Map<String, Driver> myConnectedDrivers = new ConcurrentHashMap<>();
	private static AtomicBoolean alive = new AtomicBoolean(true);

	/**
	 * 
	 * @param serverUrl
	 *            (without bolt://)
	 * @param userName
	 * @param password
	 * @return
	 */
	public static Driver getDriver(String serverUrl, String userName, String password) {
		// Hacky with security concerns but ok for now
		if (serverUrl == null || userName == null || password == null || !alive.get()) {
			return null;
		}
		if (serverUrl.toLowerCase().startsWith("bolt://")) {
			if (myConnectedDrivers.containsKey(serverUrl + userName)) {
				return myConnectedDrivers.get(serverUrl + userName);
			}
		} else {
			serverUrl = "bolt://" + serverUrl;
		}
		Driver driver = GraphDatabase.driver(serverUrl, AuthTokens.basic(userName, password));
		if (driver != null) {
			Driver result = myConnectedDrivers.putIfAbsent(serverUrl + userName, driver);
			if (result != null) {
				driver = result;
			}
		}
		return driver;
	}

	public static Session getSession(Driver someDriver) {
		if (someDriver != null)
			return someDriver.session();

		return null;
	}

	/**
	 * 
	 * @param serverUrl
	 *            (without bolt://)
	 * @param userName
	 * @param password
	 * @return
	 */
	public static Session getSession(String serverUrl, String userName, String password) {
		Driver driver = getDriver(serverUrl, userName, password);
		return getSession(driver);
	}

	public static void closeSession(Session session) {
		if (session != null)
			session.close();
	}
	
	public static void close() {
		if(!alive.compareAndSet(true, false)) {
			return;
		}
		//Iterate over the map and shut down the driver
		Iterator<Entry<String, Driver>> entries = myConnectedDrivers.entrySet().iterator();
		while (entries.hasNext()) {
		  Entry<String, Driver> thisEntry = entries.next();
		  thisEntry.getValue().close();
		}
	}
}
