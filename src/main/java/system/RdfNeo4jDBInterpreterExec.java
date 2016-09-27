package main.java.system;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public final class RdfNeo4jDBInterpreterExec {
	private static RdfNeo4JDBInterpreter myInterpreter = new RdfNeo4JDBInterpreter();

	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("c", "clean", false, "Clear the database");
		options.addOption("e", "export", false, "Export database to file");
		options.addOption("i", "import", false, "Import file into database");
		options.addOption("q", "query", false, "Run queries on the database");
		options.addOption("if", "inputfile", true, "File used for input for the invoked command");
		options.addOption("af", "authfile", true, "Absolute file path containing Neo4J login information");
		options.addOption("of", "outputfile", true, "File used for output for the invoked command");
		options.addOption("d", "debug", false, "Turn on stack trace");

		HelpFormatter formatter = new HelpFormatter();
		
		
		CommandLineParser parser = new DefaultParser();
		Boolean debug = false;
		try {
			CommandLine line = parser.parse(options, args);

			if(line.getOptions().length == 0) {
				formatter.printHelp("RDFNeo4JDB", options, true);
				return;
			}
			Boolean importCommand = line.hasOption("i");
			Boolean exportCommand = line.hasOption("e");
			Boolean queryCommand = line.hasOption("q");
			Boolean cleanCommand = line.hasOption("c");
			debug = line.hasOption("d");
			// We do not support multi-commands with query commands
			if (queryCommand) {
				if (importCommand || exportCommand) {
					System.err.println("Query command cannot be combined with import/export command");
					formatter.printHelp( "RdfNeo4JDB", options, true );
					return;
				}
			}
			Boolean authFilePresent = line.hasOption("af");
			if (!authFilePresent) {
				System.err.println("Command line error: Auth file must be present for all arguments");
				formatter.printHelp( "RdfNeo4JDB", options, true );
				return;
			}

			Boolean inputFilePresent = line.hasOption("if");
			Boolean outputFilePresent = line.hasOption("of");

			if (importCommand && !inputFilePresent) {
				System.err.println("Command line error: Import command must have an input file");
				formatter.printHelp( "RdfNeo4JDB", options, true );
				return;
			}

			if (exportCommand && !outputFilePresent) {
				System.err.println("Command line error: Export command must have an output file");
				formatter.printHelp( "RdfNeo4JDB", options, true );
				return;
			}

			if (queryCommand && (!inputFilePresent || !outputFilePresent)) {
				System.err.println("Command line error: Query command must have an input and output file");
				formatter.printHelp( "RdfNeo4JDB", options, true );
				return;
			}

			if (cleanCommand) {
				System.out.println("Cleaning the DB");
				myInterpreter.cleanDB(line.getOptionValue("af"));
			}

			if (importCommand) {
				System.out.println("Running import");
				myInterpreter.importFileIntoDb(line.getOptionValue("if"), line.getOptionValue("af"));
			}

			if (exportCommand) {
				System.out.println("Running export");
				myInterpreter.exportDbIntoFile(line.getOptionValue("of"), line.getOptionValue("af"));
			}

			if (queryCommand) {
				System.out.println("Running queries");
				myInterpreter.runBGPQueries(line.getOptionValue("if"), line.getOptionValue("af"),
						line.getOptionValue("of"));
			}
		} catch (Exception ex) {
			System.err.println("Error running commands");
			if (debug) {
				ex.printStackTrace();
			}
		}
	}
}
