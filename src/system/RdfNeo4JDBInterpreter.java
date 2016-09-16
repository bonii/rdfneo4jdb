package system;

import interfaces.RdfLoader;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.neo4j.driver.v1.*;

import interfaces.FileHandlingException;

public class RdfNeo4JDBInterpreter implements RdfLoader {

	public RdfNeo4JDBInterpreter() {

	}

	@Override
	public void load(String filePath, String serverUrl, String userName, String password) throws FileHandlingException, FileNotFoundException {
		Session session = Neo4JConnectionManager.getSession(serverUrl, userName, password);
		FileReader inputFile = new FileReader(filePath);

		Neo4JConnectionManager.closeSession(session);
		throw new FileHandlingException("Not yet implemented");
	}

}
