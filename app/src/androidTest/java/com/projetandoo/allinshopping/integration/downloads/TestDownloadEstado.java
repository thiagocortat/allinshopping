package com.projetandoo.allinshopping.integration.downloads;

import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.models.Estado;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;
import java.util.ResourceBundle;

@RunWith(RobolectricTestRunner.class)
public class TestDownloadEstado  {
	
	private final ResourceBundle bundle = ResourceBundle.getBundle("json_message");

	
	@Before
	public void setup() {
		
		Robolectric.getFakeHttpLayer().addPendingHttpResponse(200, bundle.getString("estado"));
		
	}
	

	@Test
	public void testGetAll() throws IntegrationException {
		DownloadEstado download = new DownloadEstado("teste","teste");
		List<Estado> estados = download.list();
		Assert.assertFalse(estados.isEmpty());
	}

}
