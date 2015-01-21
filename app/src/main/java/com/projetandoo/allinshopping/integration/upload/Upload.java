package com.projetandoo.allinshopping.integration.upload;

import org.json.JSONObject;

import com.projetandoo.allinshopping.exceptions.IntegrationException;

public interface Upload<E>
{
	void send(E e) throws IntegrationException;
}
