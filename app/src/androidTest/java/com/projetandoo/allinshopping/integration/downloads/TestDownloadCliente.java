package com.projetandoo.allinshopping.integration.downloads;

import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.models.Cliente;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;
import java.util.ResourceBundle;

@RunWith(RobolectricTestRunner.class)
public class TestDownloadCliente {
	
	private final ResourceBundle bundle = ResourceBundle.getBundle("json_message");
	
	@Before
	public void setup() {
		
		Robolectric.getFakeHttpLayer().addPendingHttpResponse(200, bundle.getString("cliente"));
		
	}

	@Test
	public void testGetAll() throws IntegrationException {
		final DownloadCliente downloadCliente = new DownloadCliente("teste","teste");
		final List<Cliente> clientes = downloadCliente.list();
		Assert.assertFalse(clientes.isEmpty());
	}

}
