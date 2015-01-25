package com.projetandoo.allinshopping.services;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.projetandoo.allinshopping.ConfigurationActivity;
import com.projetandoo.allinshopping.utilities.Constante;
import com.projetandoo.allinshopping.utilities.ParseUtilities;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(RobolectricTestRunner.class)
public class TestConfigurationService {

	private ConfigurationService service = null;
	private SharedPreferences preferences;
	
	@Before
	public void setup() {

			Activity activity = Robolectric.buildActivity(ConfigurationActivity.class).create().get();
		service = new ConfigurationService(activity);
		this.preferences = activity.getApplicationContext().getSharedPreferences(Constante.SHARED_PREFERENCES, Context.MODE_PRIVATE);	
		
	}
	
	@Test
	public void temQueAtualizar() {		
		assertTrue(service.precisaAtualizar());
	}
	
	@Test
	public void jaFoiAtualizado() {
		
		final Editor editor = preferences.edit();
		editor.putString(Constante.DATA_ATUALIZACAO, ParseUtilities.formatDate(DateTime.now().plusDays(1).toDate(),"yyyy-MM-dd HH:mm:ss"));
		editor.commit();
		assertFalse(service.precisaAtualizar());
	}
	
	@Test
	public void salvarNomeDaLoja() {
		service.setNomeLoja("TESTE");
		assertEquals("TESTE",preferences.getString(Constante.NOME_LOJA, null));
	}
	
	@Test
	public void recuperarNomeDaLoja() {
		service.setNomeLoja("TESTE");
		assertEquals("TESTE",preferences.getString(Constante.NOME_LOJA, null));
	}
	
	@Test
	public void marcarComoAtualizado() {
		
		final Editor editor = preferences.edit();
		editor.putString(Constante.DATA_ATUALIZACAO, ParseUtilities.formatDate(DateTime.now().minusDays(1).toDate(),"yyyy-MM-dd HH:mm:ss"));
		editor.commit();
		
		assertTrue(service.precisaAtualizar());
		service.atualizar();
		assertFalse(service.precisaAtualizar());
	}

}
