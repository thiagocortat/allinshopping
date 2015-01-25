package com.projetandoo.allinshopping.responserules;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.robolectric.tester.org.apache.http.HttpEntityStub;
import org.robolectric.tester.org.apache.http.HttpEntityStub.ResponseRule;

import java.io.IOException;
import java.io.InputStream;

public class HttpEntityResponseRule implements ResponseRule {

	@Override
	public boolean matches(final HttpRequest request) {
		return request.getRequestLine().getUri().startsWith("http://store.allinshopp.com.br");				
	}
	
	@Override
	public HttpResponse getResponse() throws HttpException, IOException {
		HttpEntityWrapper entity = new HttpEntityWrapper(new HttpEntityStub() {
			@Override
			public InputStream getContent() throws IOException,
					IllegalStateException {
				return ClassLoader.getSystemResourceAsStream("850.jpg");
			}
			
			@Override
			public long getContentLength() {
				return 100L;
			}
		});
		BasicHttpResponse response =  new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, 200, "OK"));
		
		response.setEntity(entity);
		
		return response;
		
	}

}
