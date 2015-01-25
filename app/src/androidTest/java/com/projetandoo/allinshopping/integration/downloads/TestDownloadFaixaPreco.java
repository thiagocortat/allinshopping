package com.projetandoo.allinshopping.integration.downloads;

import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.models.FaixaPreco;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;
import java.util.ResourceBundle;

@RunWith(RobolectricTestRunner.class)
public class TestDownloadFaixaPreco {

	private final ResourceBundle bundle = ResourceBundle
			.getBundle("json_message");

	@Before
	public void setup() {

		Robolectric.getFakeHttpLayer().addPendingHttpResponse(200,
				bundle.getString("faixa"));

	}

	@Test
	public void testDeCarregamentoDeLista() throws IntegrationException {
		final DownloadFaixaPreco download = new DownloadFaixaPreco("teste","teste");
		final List<FaixaPreco> faixas = download.list();

		Assert.assertFalse(faixas.isEmpty());
	}

}
