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
			/*
			 * this.value = ((value.trim().startsWith("\"")) ? "" : "\"") +
			 * value.trim() + ((value.trim().endsWith("\"")) ? "" : "\"");
			 */
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
				cypherClause.append("value:property]");
			} else {
				cypherClause.append(":property {value:'");
				cypherClause.append(value);
				cypherClause.append("'}");
				cypherClause.append("]");
			}
		} else {
			cypherClause.append("(");
			/*
			 * if (value.startsWith("\"")) { valueWithoutQuotes =
			 * value.substring(1, value.length() - 1); }
			 * 
			 * cypherClause.append(valueWithoutQuotes);
			 */
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
