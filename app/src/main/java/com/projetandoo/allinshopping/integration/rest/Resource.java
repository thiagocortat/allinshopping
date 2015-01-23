package com.projetandoo.allinshopping.integration.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;

public class Resource {
	
	private DefaultHttpClient CLIENT;
	private String URL;


	public Resource(String URL,String username, String password) {
		super();
		this.URL = URL;
		CLIENT = new DefaultHttpClient();
		CLIENT.setCredentialsProvider(this.getCredential(username, password));		
	}

	protected CredentialsProvider getCredential(String username , String password) {

		BasicCredentialsProvider provider = new BasicCredentialsProvider();
		AuthScope authscope = new AuthScope(AuthScope.ANY_HOST,
				AuthScope.ANY_PORT);
		UsernamePasswordCredentials credential = new UsernamePasswordCredentials(username, password);
		provider.setCredentials(authscope, credential);
		return provider;
	}
	
	protected String toString(final InputStream stream)
	        throws IOException
	    {
			BufferedReader bufferedreader = null; 
	        try {
				bufferedreader = new BufferedReader(new InputStreamReader(stream,"ISO-8859-1"));
				StringBuffer buffer = new StringBuffer();
				String partial = null; 
				while ((partial = bufferedreader.readLine()) != null) { 
					buffer.append(partial);
				}

				return buffer.substring(buffer.indexOf("{")).toString();

			} finally {
				if( bufferedreader != null ) {
					bufferedreader.close();
				}
			}
	    }
	
	protected HttpClient getHttpClient() {
		return this.CLIENT;
	}

	protected String getURL() {
		return this.URL;
	}
}
