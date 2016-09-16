package system;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.neo4j.driver.v1.*;

public final class Neo4JConnectionManager {

	private static Map<String, Driver> myConnectedDrivers = new ConcurrentHashMap<>();

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
		if (serverUrl == null || userName == null || password == null) {
			return null;
		}
		if (serverUrl.toLowerCase().startsWith("bolt://"))
			if (myConnectedDrivers.containsKey(serverUrl + userName)) {
				return myConnectedDrivers.get(serverUrl + userName);
			}
		Driver driver = GraphDatabase.driver("bolt://" + serverUrl, AuthTokens.basic(userName, password));
		if (driver != null) {
			return myConnectedDrivers.putIfAbsent(serverUrl + userName, driver);
		}
		return null;
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
}
