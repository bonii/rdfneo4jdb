package main.java.interfaces;

import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public interface RdfInterpreter extends RdfLoader {

	public StatementResult runCypherQuery(String query, Session session) throws GraphDBException;
}
