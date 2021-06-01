package com.uniovi.entities;

import org.springframework.stereotype.Component;

@Component
public class EmailConfig {
	private String host ="smtp.gmail.com";
	private Integer port = 587;
	private String username = "testmariaTFGEII@gmail.com";
	private String password = "maria2021_TFG_EII"; 
	private String protocol ="smtp";	
	public String getHost() {
		return host;
	}
	public Integer getPort() {
		return port;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getProtocol() {
		return protocol;
	}
	public EmailConfig() {}
	
/*	public void setHost(String host) {
		this.host = host;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}*/

}
