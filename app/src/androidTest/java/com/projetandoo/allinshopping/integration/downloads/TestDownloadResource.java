package com.projetandoo.allinshopping.integration.downloads;

import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.models.Produto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;
import java.util.ResourceBundle;

import com.projetandoo.allinshopping.responserules.HttpEntityResponseRule;

@RunWith(RobolectricTestRunner.class)
public class TestDownloadResource {

	private final ResourceBundle bundle = ResourceBundle
			.getBundle("json_message");

	@Before
	public void setup() {
	
		Robolectric.getFakeHttpLayer().addPendingHttpResponse(200,
				bundle.getString("produto"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(new HttpEntityResponseRule());

	}

	@Test
	public void testGetResourceByProduto() throws IntegrationException {
		final DownloadProdutos downloadProdutos = new DownloadProdutos("teste","teste");
		final List<Produto> produtos = downloadProdutos.list();

		final Produto produto = produtos.get(0);

		final DownloadResource resource = new DownloadResource();
		resource.getResourceByProduto(produto, null);

	}

}
