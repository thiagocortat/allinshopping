package com.projetandoo.allinshopping.integration.downloads;

import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.models.Produto;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;
import java.util.ResourceBundle;

@RunWith(RobolectricTestRunner.class)
public class TestDownloadProduto {

	private final ResourceBundle bundle = ResourceBundle.getBundle("json_message");

	
	@Before
	public void setup() {
		
		Robolectric.getFakeHttpLayer()
				   .addPendingHttpResponse(200, bundle.getString("produto"));
		
	}
	

	@Test
	public void test() throws IntegrationException {
		DownloadProdutos download = new DownloadProdutos("teste","teste");
		List<Produto> produtos = download.list();
		Assert.assertFalse(produtos.isEmpty());
	}

}
