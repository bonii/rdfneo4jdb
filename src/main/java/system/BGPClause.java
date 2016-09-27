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
 *  BGPClause.java created on Sep 27, 2016
 *
**/
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
