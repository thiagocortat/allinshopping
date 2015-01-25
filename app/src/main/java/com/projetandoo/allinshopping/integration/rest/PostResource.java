package com.projetandoo.allinshopping.integration.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.exceptions.InternalServerException;
import com.projetandoo.allinshopping.exceptions.PageNotFoundException;

public class PostResource extends Resource {
	private HttpPost POST;

	public PostResource(String URL, String username,
			final String password) {
		super(URL, username, password);
		this.POST = new HttpPost(URL);
        this.POST.addHeader(BasicScheme.authenticate(new UsernamePasswordCredentials(username,password), "UTF-8", false));
	}

	public String sendJSON(String json)
			throws IntegrationException {
		 
		try {
			StringEntity stringentity = new StringEntity(json);
			BasicHeader basicheader = new BasicHeader("Content-Type",
					"application/json");
			stringentity.setContentType(basicheader);
			POST.setEntity(stringentity);
			HttpResponse httpresponse = this.getHttpClient().execute(POST);
            HttpEntity httpentity = httpresponse.getEntity();
			if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				if (httpentity == null) {
					throw new IntegrationException(String.format(
							"Ocorreu um erro no processamento no endereço %s",
							super.getURL()));

				} else {
					return EntityUtils.toString(httpentity);
				}
			} else if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
				throw new PageNotFoundException(String.format(
						"endereço %s não encontrado ou errado", super.getURL()));

			} else if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
				throw new InternalServerException(String.format(
						"Ocorreu um erro no processamento no endereço %s \r\n%s",
						super.getURL(),EntityUtils.toString(httpentity)));
			} else if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
				throw new PageNotFoundException("Acesso negado");
			} else {
				throw new IntegrationException(String.format(
						"Ocorreu um erro não documentado no endereço %s",
						super.getURL()));
			}
		} catch (UnsupportedEncodingException e) {
			throw new IntegrationException(
					"Ocorreu um erro no envio para o backoffice " 
							+ e.getMessage(), e); 
		} catch (ClientProtocolException e) {
			throw new IntegrationException(
					"Ocorreu um erro no envio para o backoffice "
							+ e.getMessage(), e);
		} catch (IllegalStateException e) {
			throw new IntegrationException(
					"Ocorreu um erro no envio para o backoffice "
							+ e.getMessage(), e);
		} catch (IOException e) {
			throw new IntegrationException(
					"Ocorreu um erro no envio para o backoffice "
							+ e.getMessage(), e);
		}

	}
}
