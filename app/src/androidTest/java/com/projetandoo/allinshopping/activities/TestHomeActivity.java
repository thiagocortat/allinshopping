package com.projetandoo.allinshopping.activities;

import android.os.AsyncTask;
import android.view.View;

import com.projetandoo.allinshopping.HomeActivity;
import com.projetandoo.allinshopping.async.IntegrationAsyncProcess;
import com.projetandoo.allinshopping.models.Secao;
import com.projetandoo.allinshopping.responserules.HttpEntityResponseRule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class TestHomeActivity {

	private final ResourceBundle integration = ResourceBundle
			.getBundle("integration");

	private final ResourceBundle response = ResourceBundle
			.getBundle("json_message");
	

	@Before
	public void setup() {
		
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("estado"),response.getString("estado"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cep"),response.getString("cep"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("faixapreco"),response.getString("faixa"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("secao"),response.getString("secao"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("produto"),response.getString("produto"));		
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cliente"),response.getString("cliente"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("formapagamento"), response.getString("formapagamento"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(new HttpEntityResponseRule());
		
		IntegrationAsyncProcess integration = new IntegrationAsyncProcess(Robolectric.application.getApplicationContext());
		integration.setUserName("teste")
				   .setPassword("teste")
				   .execute();
		
		Robolectric.runBackgroundTasks();
		assertEquals(integration.getStatus(), AsyncTask.Status.FINISHED);
						
	}
	
	@Test
	public void testMontarPrimeiraTela(){
		
		final HomeActivity activity = Robolectric.buildActivity(HomeActivity.class).create().start().get();
		final Integer count = activity.getSecoes().getCount();
		
		assertTrue(8 == count);
		
	}
	
//	@Test
//	public void listarProduto() {
//		final HomeActivity activity = Robolectric.buildActivity(HomeActivity.class).create().start().get();
//		final Secao secao = (Secao) activity.getSecoes().getItemAtPosition(0);
//		final View item = new View(Robolectric.application.getApplicationContext());
//		item.setTag(secao);
//		activity.onClick(item);
//
//		assertEquals( secao.getTitulo() , activity.getTitulo() );
//	}

}
