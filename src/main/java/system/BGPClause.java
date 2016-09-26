package main.java.system;

import main.java.interfaces.GraphDBException;

public final class BGPClause {
	private final BGPToken subject;
	private final BGPToken relationship;
	private final BGPToken object;

	public BGPClause(String clause) throws GraphDBException {
		// Parse the clause and construct the token

		String[] unparsedClause = clause.trim().split("\\(")[1].split("\\)")[0].split(",");
		if (unparsedClause.length != 3) {
			throw new GraphDBException("Parsing error: Malformed clause");
		}
		this.subject = new BGPToken(unparsedClause[0],false);
		this.relationship = new BGPToken(unparsedClause[1],true);
		this.object = new BGPToken(unparsedClause[2],false);
	}

	public BGPToken getSubject() {
		return subject;
	}

	public BGPToken getRelationship() {
		return relationship;
	}

	public BGPToken getObject() {
		return object;
	}

	public String toCypherClause() {
		StringBuffer cypherClause = new StringBuffer();
		cypherClause.append(subject.toCypherClause());
		cypherClause.append("-");
		cypherClause.append(relationship.toCypherClause());
		cypherClause.append("->");
		cypherClause.append(object.toCypherClause());
		return cypherClause.toString();
	}

}
