package system;

import interfaces.RdfLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.v1.*;

import interfaces.GraphDBException;

public class RdfNeo4JDBInterpreter implements RdfLoader {

	public RdfNeo4JDBInterpreter() {

	}

	protected List<RdfTriple> parseRdfFile(String filePath) throws FileNotFoundException, IOException {
		String line = "";
		// Create the file reader
		BufferedReader file = new BufferedReader(new FileReader(filePath));
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
	}

	@Override
	public void importFileIntoDb(String filePath, String serverUrl, String userName, String password)
			throws GraphDBException, FileNotFoundException, IOException {
		Session session = Neo4JConnectionManager.getSession(serverUrl, userName, password);

		if (session == null) {
			throw new GraphDBException("Could not connect");
		}

		List<RdfTriple> parsedTriples = parseRdfFile(filePath);
		System.out.println(parsedTriples);

		// Iterate over the file and add it to the database

		Neo4JConnectionManager.closeSession(session);

	}

	@Override
	public void exportDbIntoFile(String filePath, String serverUrl, String userName, String password)
			throws GraphDBException, FileNotFoundException {
		// TODO Auto-generated method stub

	}

}
