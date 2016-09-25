package main.java.interfaces;

import java.io.IOException;

import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public interface RdfInterpreter extends RdfLoader {

	public StatementResult runCypherQuery(String query, Session session) throws GraphDBException;

	public String runBGPQuery(String query, Session session) throws GraphDBException;

	public void runBGPQueries(String queryFilePath, String authenticationFilePath, String outputFilePath)
			throws IOException, GraphDBException;
}
