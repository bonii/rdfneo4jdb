package main.java.system;

import java.util.ArrayList;
import java.util.List;

import main.java.interfaces.GraphDBException;

public class BGPQuery {
	private final List<String> selectionAttributes;
	private final List<BGPClause> clauses;

	public BGPQuery(String query) throws GraphDBException {
		String[] queryParts = query.split(":-");
		if (queryParts.length != 2) {
			throw new GraphDBException("Parsing error: Malformed BGP query");
		}

		String[] unparsedSelectionAttributes = queryParts[0].split("\\(")[1].split("\\)")[0].split(",");
		selectionAttributes = new ArrayList<>();
		for (String anAttribute : unparsedSelectionAttributes) {
			if (!anAttribute.trim().startsWith("?")) {
				throw new GraphDBException("Parsing error: Variables must start with ?");
			}
			selectionAttributes.add(anAttribute.trim().substring(1).trim());
		}

		String[] unparsedClauses = queryParts[1].split(";");
		clauses = new ArrayList<>();
		for (String aClause : unparsedClauses) {
			BGPClause parsedClause = new BGPClause(aClause.trim());
			clauses.add(parsedClause);
		}
	}

	public List<String> getSelectionAttributes() {
		return selectionAttributes;
	}

	public List<BGPClause> getClauses() {
		return clauses;
	}
	
	public String toCypherClause() {
		StringBuffer cypherClause = new StringBuffer("match ");
		for(BGPClause aClause: clauses) {
			cypherClause.append(aClause.toCypherClause());
			cypherClause.append(",");
		}
		cypherClause.deleteCharAt(cypherClause.length()-1);
		cypherClause.append(" return ");
		for(String attribute:selectionAttributes) {
			cypherClause.append(attribute);
			cypherClause.append(".value,");
		}
		cypherClause.deleteCharAt(cypherClause.length()-1);		
		return cypherClause.toString();
	}
}
