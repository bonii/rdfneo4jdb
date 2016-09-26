package test.java;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.driver.v1.Session;

import main.java.interfaces.GraphDBException;
import main.java.system.Neo4JConnectionManager;
import main.java.system.RdfNeo4JDBInterpreter;

public class RdfLoaderTest {
	private static RdfNeo4JDBInterpreter dbInterpreter = null;
	private static final String serverUrl = "localhost";
	private static final String userName = "neo4j";
	private static final String password = "neo4jnew";
	private static final String inputFilePath = "data/dataset.txt";
	private static final String outputFilePath = "data/dataset_res.txt";
	private static final String authFilePath = "data/auth.txt";
	private static final String queryInputFilePath = "data/queries.txt";
	private static final String queryOutputFilePath = "data/queries_out.txt";

	public static void cleanUpGraphDb() throws Exception {
		Session session = Neo4JConnectionManager.getSession(serverUrl, userName, password);
		String cypherDeleteQuery = "MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n, r";
		dbInterpreter.runCypherQuery(cypherDeleteQuery, session);
		session.close();
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbInterpreter = new RdfNeo4JDBInterpreter();
		cleanUpGraphDb();
		dbInterpreter.importFileIntoDb(inputFilePath, authFilePath);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		cleanUpGraphDb();
		Neo4JConnectionManager.close();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFileImportExport() throws Exception {
		/*
		 * dbInterpreter.importFileIntoDb(inputFilePath, new
		 * Neo4JAuthenticationProps(serverUrl, userName, password));
		 * dbInterpreter.exportDbIntoFile(outputFilePath, new
		 * Neo4JAuthenticationProps(serverUrl, userName, password));
		 */
		cleanUpGraphDb();
		dbInterpreter.importFileIntoDb(inputFilePath, authFilePath);
		dbInterpreter.exportDbIntoFile(outputFilePath, authFilePath);
	}

	@Test
	public void testBGPQueryCapability() throws IOException, GraphDBException {
		dbInterpreter.importFileIntoDb(inputFilePath,authFilePath);
		dbInterpreter.runBGPQueries(queryInputFilePath, authFilePath, queryOutputFilePath);
	}

}
