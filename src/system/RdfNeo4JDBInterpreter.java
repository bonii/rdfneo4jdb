package system;

import interfaces.RdfLoader;
import interfaces.UnableToLoadException;

public class RdfNeo4JDBInterpreter implements RdfLoader {

	public RdfNeo4JDBInterpreter() {
		
	}

	@Override
	public void load(String filePath) throws UnableToLoadException {
		
		throw new UnableToLoadException("Not yet implemented");
	}
	
}
