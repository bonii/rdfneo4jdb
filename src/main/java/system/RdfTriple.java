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
