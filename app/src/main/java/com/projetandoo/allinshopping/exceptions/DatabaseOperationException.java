package com.projetandoo.allinshopping.exceptions;

public class DatabaseOperationException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

	public DatabaseOperationException(String message ,Throwable throwable) {
		super(message ,throwable);
	}

	public DatabaseOperationException(String message) {
		super(message);
	}

	

}
