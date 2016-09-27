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
 *  RdfTriple.java created on Sep 27, 2016
 *
**/
package main.java.system;

import java.io.InvalidObjectException;

public class RdfTriple {
	private String subject;
	private String property;
	private String object;

	public RdfTriple(String subject, String property, String object) {
		this.subject = subject;
		this.property = property;
		this.object = object;
	}

	public RdfTriple(String[] tokens) throws InvalidObjectException {
		if (tokens.length != 3) {
			throw new InvalidObjectException("Token string length must be exactly 3");
		}
		this.subject = tokens[0];
		this.property = tokens[1];
		this.object = tokens[2];
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	@Override
	public String toString() {
		StringBuffer rdfTriple = new StringBuffer();
		rdfTriple.append("(");
		rdfTriple.append(subject);
		rdfTriple.append(",");
		rdfTriple.append(property);
		rdfTriple.append(",");
		rdfTriple.append(object);
		rdfTriple.append(")");
		return rdfTriple.toString();
	}

	public String toNTripleFormat() {
		StringBuffer rdfTriple = new StringBuffer();
		rdfTriple.append(subject);
		rdfTriple.append(" ");
		rdfTriple.append(property);
		rdfTriple.append(" ");
		rdfTriple.append(object);
		rdfTriple.append(" .");
		return rdfTriple.toString();
	}
}
