package system;

import interfaces.RdfLoader;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.neo4j.driver.v1.*;

import interfaces.GraphDBException;

public class RdfNeo4JDBInterpreter implements RdfLoader {

	public RdfNeo4JDBInterpreter() {

	}

	@Override
	public void importIntoDb(String filePath, String serverUrl, String userName, String password)
			throws GraphDBException, FileNotFoundException {
		Session session = Neo4JConnectionManager.getSession(serverUrl, userName, password);
		if(session == null) {
			throw new GraphDBException("Could not connect");
		}
		FileReader inputFile = new FileReader(filePath);
		//Iterate over the file and add it to the database

		Neo4JConnectionManager.closeSession(session);
		throw new GraphDBException("Not yet implemented");
	}

}
