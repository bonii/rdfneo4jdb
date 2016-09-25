package main.java.system;

public final class BGPToken {
	private final String value;
	private final Boolean variable;

	public BGPToken(String value) {
		if (value.trim().startsWith("?")) {
			this.value = value.trim().substring(1).trim();
			this.variable = true;
		} else {
			this.value = ((value.trim().startsWith("\"")) ? "" : "\"") + value.trim()
					+ ((value.trim().endsWith("\"")) ? "" : "\"");
			this.variable = false;
		}
	}

	public String getValue() {
		return value;
	}

	public Boolean isVariable() {
		return variable;
	}
}
