package main.java.system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.v1.*;

import main.java.interfaces.GraphDBException;
import main.java.interfaces.RdfInterpreter;

public class RdfNeo4JDBInterpreter implements RdfInterpreter {

	public RdfNeo4JDBInterpreter() {

	}

	protected List<RdfTriple> parseRdfFile(String filePath) throws FileNotFoundException, IOException {
		BufferedReader file = null;
		try {
			String line = "";
			// Create the file reader
			file = new BufferedReader(new FileReader(filePath));
			List<RdfTriple> triples = new ArrayList<>();
			// Read the file line by line
			while ((line = file.readLine()) != null) {
				if (line == null || line.length() == 0 || line.startsWith("#")) {
					continue;
				}
				if (line.endsWith(" .")) {
					line = line.substring(0, line.lastIndexOf(" ."));
				}
				// Get all tokens available in line
				String delimiter = " ";
				String[] tokens = line.split(delimiter);
				String[] tripleTokens = new String[3];
				int pos = 0;
				for (int i = 0; i < tokens.length; i++) {
					String token = tokens[i];
					// Print all tokens
					if (token.startsWith("\"")) {
						StringBuffer literal = new StringBuffer(tokens[i]);
						if (token.indexOf("\"") == token.lastIndexOf("\"")) {
							literal.append(" ");
						}
						for (int j = i + 1; j < tokens.length; j++) {
							if (tokens[j].endsWith("\"")) {
								literal.append(tokens[j]);
								token = literal.toString();
								i = j + 1;
								break;
							} else {
								literal.append(tokens[j]);
								literal.append(" ");
							}
						}
					}
					tripleTokens[pos++] = token;
					if (pos == 3) {
						try {
							RdfTriple triple = new RdfTriple(tripleTokens);
							triples.add(triple);
						} catch (InvalidObjectException ex) {
							throw new IOException("Malformed file: Results in parsing error");
						}
						pos = 0;
					}
				}
			}
			return triples;
		} finally {
			if (file != null) {
				file.close();
			}
		}
	}

	protected void addTriplesToGraphDB(List<RdfTriple> triples, Session session) throws GraphDBException {
		for (RdfTriple aTriple : triples) {
			String subjectInsertString = "merge (s:Node {value:'" + aTriple.getSubject() + "'})";
			String objectInsertString = "merge (o:Node {value:'" + aTriple.getObject() + "'})";

			String propertyInsertString = "match (s:Node {value:'" + aTriple.getSubject() + "'}), (o:Node {value:'"
					+ aTriple.getObject() + "'})" + " merge (s)-[:property {value:'" + aTriple.getProperty()
					+ "'}]->(o)";

			runCypherQuery(subjectInsertString, session);
			runCypherQuery(objectInsertString, session);
			runCypherQuery(propertyInsertString, session);
			// Batch insertions would be way better
		}
	}

	@Override
	public void importFileIntoDb(String filePath, String serverUrl, String userName, String password)
			throws GraphDBException, IOException {
		Session session = Neo4JConnectionManager.getSession(serverUrl, userName, password);

		if (session == null) {
			throw new GraphDBException("Could not connect");
		}

		List<RdfTriple> parsedTriples = parseRdfFile(filePath);
		addTriplesToGraphDB(parsedTriples, session);
		Neo4JConnectionManager.closeSession(session);
	}

	protected List<RdfTriple> getTriplesFromGraphDb(Session session) throws GraphDBException {
		String readGraphDBQuery = "match (s)-[r:property]->(o) return s.value, r.value, o.value";
		StatementResult result = runCypherQuery(readGraphDBQuery, session);
		List<RdfTriple> dbTriples = new ArrayList<>();
		while (result.hasNext()) {
			Record record = result.next();
			RdfTriple rdfTriple = new RdfTriple(record.get(0).asString(), record.get(1).asString(),
					record.get(2).asString());
			dbTriples.add(rdfTriple);
		}
		return dbTriples;
	}

	protected void writeRdfTriplesToFile(String filePath, List<RdfTriple> dbTriples) throws IOException {
		BufferedWriter file = null;
		try {
			file = new BufferedWriter(new FileWriter(filePath));
			for (RdfTriple aTriple : dbTriples) {
				file.write(aTriple.toNTripleFormat());
				file.newLine();
				file.flush();
			}
		} finally {
			if (file != null) {
				file.close();
			}
		}
	}

	@Override
	public void exportDbIntoFile(String filePath, String serverUrl, String userName, String password)
			throws GraphDBException, IOException {
		Session session = Neo4JConnectionManager.getSession(serverUrl, userName, password);
		List<RdfTriple> dbTriples = getTriplesFromGraphDb(session);
		writeRdfTriplesToFile(filePath, dbTriples);
		Neo4JConnectionManager.closeSession(session);
	}

	@Override
	public StatementResult runCypherQuery(String query, Session session) throws GraphDBException {
		if (session == null) {
			throw new GraphDBException("Invalid session");
		}

		return session.run(query);
	}

	@Override
	public StatementResult runBGPQuery(String query, Session session) throws GraphDBException {
		// Parse the query first
		String[] queryParts = query.split(":-");
		if (queryParts.length != 2) {
			throw new GraphDBException("Malformed query");
		}
		String[] selectionAttributes = queryParts[0].split("(")[1].split(")")[0].split(",");
		String[] matchClauses = queryParts[1].split("AND");
		return null;
	}

}
