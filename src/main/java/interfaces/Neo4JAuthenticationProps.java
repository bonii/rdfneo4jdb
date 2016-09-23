package main.java.interfaces;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Neo4JAuthenticationProps {
	private final String serverUrl;
	private final String userName;
	private final String password;

	public Neo4JAuthenticationProps(String authenticationFilePath) throws IOException {
		Properties authProps = new Properties();
		FileInputStream authFileStream = new FileInputStream(authenticationFilePath);
		authProps.load(authFileStream);
		authFileStream.close();
		serverUrl = authProps.getProperty("server_url");
		userName = authProps.getProperty("user_name");
		password = authProps.getProperty("password");
	}

	public Neo4JAuthenticationProps(String serverUrl, String userName, String password) {
		this.serverUrl = serverUrl;
		this.userName = userName;
		this.password = password;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
}
