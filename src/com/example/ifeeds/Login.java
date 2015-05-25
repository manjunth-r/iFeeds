package com.example.ifeeds;

public class Login {
	private String password ;
	final int id = 1;
	
	public Login(){
		password = null ;
	}
	
	public int getId() {
		return id;
	}

	public Login(String password){
		this.password = password ;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
