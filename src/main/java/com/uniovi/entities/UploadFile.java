package com.uniovi.entities;

import com.uniovi.util.ArgumentChecks;


public class UploadFile  {

	private String statement;
	
	public UploadFile() {
	}
	
	public UploadFile(String statement) {
		ArgumentChecks.isNotEmpty(statement);
		this.statement = statement;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	

	@Override
	public String toString() {
		return "UploadFile [statement=" + statement + "]";
	}
	
}
