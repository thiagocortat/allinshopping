package com.projetandoo.allinshopping.integration.downloads;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.integration.rest.GetResource;
import com.projetandoo.allinshopping.models.Cliente;
import com.projetandoo.allinshopping.services.ConfigurationService;

public class DownloadCliente extends AbstractDownload<Cliente> {

	public DownloadCliente(String username, String password) {
		super(username,password);
	}

	@Override
	public List<Cliente> list() throws IntegrationException {

		try {
			final JSONObject json = new GetResource(this.getURL(),super.getUserName(),super.getPassword()).getJSON();
			final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
					.create();
			
			return toList(gson, json.get("list"));
			
		} catch (JSONException e) {
			throw new IntegrationException(
					"Ocorreu um erro para converter a resposta do servidor "
							+ e.getMessage(), e);
		}

	}	
}
