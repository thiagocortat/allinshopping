package com.projetandoo.allinshopping.integration.downloads;

import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.models.FormaPagamento;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;
import java.util.ResourceBundle;

@RunWith(RobolectricTestRunner.class)
public class TestDownloadFormaPagamento {

	private final ResourceBundle bundle = ResourceBundle
			.getBundle("json_message");

	@Before
	public void setup() {

		Robolectric.getFakeHttpLayer().addPendingHttpResponse(200,
				bundle.getString("formapagamento"));

	}

	@Test
	public void testDeCarregamentoDeLista() throws IntegrationException {
		final DownloadFormasPagamento download = new DownloadFormasPagamento("teste","teste");
		final List<FormaPagamento> faixas = download.list();

		Assert.assertFalse(faixas.isEmpty());
	}

}
