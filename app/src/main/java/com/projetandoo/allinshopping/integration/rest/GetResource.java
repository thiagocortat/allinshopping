package com.projetandoo.allinshopping.integration.rest;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;

import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.exceptions.InternalServerException;
import com.projetandoo.allinshopping.exceptions.PageNotFoundException;

public class GetResource extends Resource {

	private final HttpGet GET;

	public GetResource(final String URL,final String username , final String password) {
		super(URL,username,password);
		GET = new HttpGet(URL);		
	}

	public JSONObject getJSON() throws IntegrationException {

		try {
			
			final HttpResponse httpresponse = this.getHttpClient().execute(GET);
			if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				final HttpEntity httpentity = httpresponse.getEntity();
				if (httpentity == null) {
					throw new IntegrationException(String.format(
							"Ocorreu um erro no processamento no endereço %s",
							super.getURL()));
				} else {
					return new JSONObject(this.toString(httpentity.getContent()));
				}
			} else if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
				throw new PageNotFoundException("Acesso negado");
			} else if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
				throw new PageNotFoundException(String.format(
						"endereço %s não encontrado ou errado", super.getURL()));
			} else if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
				throw new InternalServerException(String.format(
						"Ocorreu um erro no processamento no endereço %s", super.getURL()));
			} else {
				throw new IntegrationException(String.format(
						"Ocorreu um erro não documentado no endereço %s", super.getURL()));
			}
		} catch (ClientProtocolException e) {
			throw new IntegrationException(e.getMessage(), e);
		} catch (IllegalStateException e) {
			throw new IntegrationException(e.getMessage(), e);
		} catch (IOException e) {
			throw new IntegrationException(e.getMessage(), e);
		} catch (JSONException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ e.getMessage(), e);
		}

	}
}
