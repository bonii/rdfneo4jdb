/**
 *
 *  Copyright (C) 2016 Vivek Shah 

 *  This file is part of RDFNeo4JDB

 *  RDFNeo4JDB is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 3 of the License, or
 *  (at your option) any later version.

 *  RDFNeo4JDB is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software Foundation,
 *  Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA

 *  Author Vivek Shah <bonii at kernelspace.in>
 *  Neo4JConnectionManager.java created on Sep 27, 2016
 *
**/
package main.java.system;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.neo4j.driver.v1.*;

import main.java.interfaces.Neo4JAuthenticationProps;

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
			Driver value = myConnectedDrivers.get(serverUrl + userName);
			if (value != null) {
				return value;
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

	public static Session getSession(Neo4JAuthenticationProps auth) {
		return getSession(auth.getServerUrl(), auth.getUserName(), auth.getPassword());
	}

	public static void closeSession(Session session) {
		if (session != null)
			session.close();
	}

	public static void close() {
		if (!alive.compareAndSet(true, false)) {
			return;
		}
		// Iterate over the map and shut down the driver
		Iterator<Entry<String, Driver>> entries = myConnectedDrivers.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, Driver> thisEntry = entries.next();
			thisEntry.getValue().close();
		}
	}
}
