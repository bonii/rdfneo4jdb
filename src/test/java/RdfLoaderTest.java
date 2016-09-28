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
 *  RdfLoaderTest.java created on Sep 27, 2016
 *
**/
package test.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import main.java.interfaces.GraphDBException;
import main.java.system.Neo4JConnectionManager;
import main.java.system.RdfNeo4JDBInterpreter;

/**
 * Minimal test class developed to trigger the RDFNeo4JDB methods.
 * 
 * XXX: Not much unit testing
 * 
 * @author bonii
 *
 */
public class RdfLoaderTest {
	private static RdfNeo4JDBInterpreter dbInterpreter = null;
	private static final String inputFilePath = "data/dataset.nt";
	private static final String outputFilePath = "data/dataset_res.nt";
	private static final String authFilePath = "data/auth.txt";
	private static final String queryInputFilePath = "data/queries.txt";
	private static final String queryOutputFilePath = "data/queries_out.txt";
	private static final String bashDiffScript = "data/check_diff.sh"; // To
																		// check
																		// import/export
																		// ->
																		// redirection
																		// does
																		// not
																		// work
																		// well
																		// with
																		// Java
																		// process
																		// execution

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
		String bashShellCommand = "./"+bashDiffScript+" "+inputFilePath+" "+outputFilePath;
		Process diffProcess = Runtime.getRuntime().exec(bashShellCommand);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(diffProcess.getInputStream()));

		if (stdInput.readLine() != null) {
			fail();
		}
	}

	@Test
	public void testBGPQueryCapability() throws IOException, GraphDBException {
		dbInterpreter.runBGPQueries(queryInputFilePath, authFilePath, queryOutputFilePath);
	}
}