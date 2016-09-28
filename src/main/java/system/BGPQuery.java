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
 *  BGPQuery.java created on Sep 27, 2016
 *
**/
package main.java.system;

import java.util.ArrayList;
import java.util.List;

import main.java.interfaces.GraphDBException;

/**
 * Datastructure to represent a BGP Query
 * 
 * @author bonii
 *
 */
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

		// Each clause is separated by a ;
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
		Boolean firstClause = true;
		for (BGPClause aClause : clauses) {
			if (!firstClause) {
				cypherClause.append(",");
			} else {
				firstClause = false;
			}
			cypherClause.append(aClause.toCypherClause());

		}
		cypherClause.append(" return ");
		Boolean firstAttribute = true;
		for (String attribute : selectionAttributes) {
			if (!firstAttribute) {
				cypherClause.append(",");
			} else {
				firstAttribute = false;
			}
			cypherClause.append(attribute + ".value");
		}
		return cypherClause.toString();
	}
}
