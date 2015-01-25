package com.projetandoo.allinshopping.services;

import com.projetandoo.allinshopping.MainActivity;
import com.projetandoo.allinshopping.async.IntegrationProcess;
import com.projetandoo.allinshopping.models.FaixaPreco;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ResourceBundle;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class TestCEPService {

	private final ResourceBundle integration = ResourceBundle
			.getBundle("integration");
	
	private final ResourceBundle response = ResourceBundle.getBundle("json_message");

    private MainActivity activity;
	
	
	@Before
	public void setup() throws Exception {
		
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("estado"),response.getString("estado"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cep"),response.getString("cep"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("faixapreco"),response.getString("faixa"));

        activity = Robolectric.buildActivity(MainActivity.class).create().get();

		final IntegrationProcess process = new IntegrationProcess("teste","teste",activity);
		process.importarEstado();
		process.importarCEP();
		process.importarFaixaPreco();
		
	}
	
	@Test
	public void pesquisarFaixaPreco() {

		final CEPService service = new CEPService(activity);
		final FaixaPreco faixaPreco = service.getFaixaPrecoByCEPDestino(22745L);
		assertNotNull(faixaPreco);
		
	}

}
