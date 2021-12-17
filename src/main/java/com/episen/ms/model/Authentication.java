package com.episen.ms.model;

public class Authentication {
	
    private String jwt;

	public Authentication() {
	}
	
	public Authentication(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}


}
