package com.spotify.oauth2.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

	private ErrorDetails error;

	public ErrorDetails getError() {
		return error;
	}

	public void setError(ErrorDetails error) {
		this.error = error;
	}
	
	
}
