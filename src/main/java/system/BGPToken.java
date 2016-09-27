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
 *  BGPToken.java created on Sep 27, 2016
 *
**/
package main.java.system;

public final class BGPToken {
	private final String value;
	private final Boolean variable;
	private final Boolean relationship;

	public BGPToken(String value, Boolean relationship) {
		if (value.trim().startsWith("?")) {
			this.value = value.trim().substring(1).trim();
			this.variable = true;
		} else {
			this.value = value.trim();
			this.variable = false;
		}
		this.relationship = relationship;
	}

	public String getValue() {
		return value;
	}

	public Boolean isVariable() {
		return variable;
	}

	public String toCypherClause() {
		StringBuffer cypherClause = new StringBuffer();
		// String valueWithoutQuotes = null;
		if (relationship) {
			cypherClause.append("[");
			if (isVariable()) {
				cypherClause.append(value+":property]");
			} else {
				cypherClause.append(":property {value:'");
				cypherClause.append(value);
				cypherClause.append("'}");
				cypherClause.append("]");
			}
		} else {
			cypherClause.append("(");
			if (!isVariable()) {
				cypherClause.append(" {value: '" + value + "'}");
			} else {
				cypherClause.append(this.value);
			}
			cypherClause.append(")");
		}
		return cypherClause.toString();
	}
}
