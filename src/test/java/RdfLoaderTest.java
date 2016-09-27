package test.java;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.interfaces.GraphDBException;
import main.java.system.Neo4JConnectionManager;
import main.java.system.RdfNeo4JDBInterpreter;

public class RdfLoaderTest {
	private static RdfNeo4JDBInterpreter dbInterpreter = null;
	private static final String inputFilePath = "data/dataset.nt";
	private static final String outputFilePath = "data/dateset_res.nt";
	private static final String authFilePath = "data/auth.txt";
	private static final String queryInputFilePath = "data/queries.txt";
	private static final String queryOutputFilePath = "data/queries_out.txt";

	public static void cleanUpGraphDb() throws Exception {
		dbInterpreter.cleanDB(authFilePath);
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
		dbInterpreter.exportDbIntoFile(outputFilePath, authFilePath);
	}

	@Test
	public void testBGPQueryCapability() throws IOException, GraphDBException {
		dbInterpreter.runBGPQueries(queryInputFilePath, authFilePath, queryOutputFilePath);
	}
}
