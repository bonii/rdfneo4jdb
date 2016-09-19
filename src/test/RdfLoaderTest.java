package test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import interfaces.GraphDBException;
import system.RdfNeo4JDBInterpreter;

public class RdfLoaderTest {
	private static RdfNeo4JDBInterpreter dbInterpreter = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbInterpreter = new RdfNeo4JDBInterpreter();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadFileIntoDB() throws FileNotFoundException, GraphDBException, IOException {
		dbInterpreter.importFileIntoDb("data/dataset.txt", "localhost", "neo4j", "neo4jnew");
	}

}
