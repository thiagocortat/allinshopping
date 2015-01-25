package com.projetandoo.allinshopping.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.projetandoo.allinshopping.ConfigurationActivity;
import com.projetandoo.allinshopping.HomeActivity;
import com.projetandoo.allinshopping.MainActivity;
import com.projetandoo.allinshopping.async.IntegrationAsyncProcess;
import com.projetandoo.allinshopping.utilities.Constante;
import com.projetandoo.allinshopping.utilities.ParseUtilities;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowIntent;

import java.util.ResourceBundle;

import com.projetandoo.allinshopping.responserules.HttpEntityResponseRule;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class TestMainActivity {

	private final ResourceBundle integration = ResourceBundle
			.getBundle("integration");

	private final ResourceBundle response = ResourceBundle
			.getBundle("json_message");
	
	private final SharedPreferences preferences = Robolectric.application
															 .getApplicationContext()
															 .getSharedPreferences(Constante.SHARED_PREFERENCES, Context.MODE_PRIVATE);

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
		
		final Editor editor = preferences.edit();
		editor.putString(Constante.DATA_ATUALIZACAO, null);
		editor.commit();
		
				
	}
	
	
	@Test
	public void deveDirecionarParaTelaDeConfiguracao() {
		
		final MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
		final ShadowActivity actual = shadowOf(activity);
		final Intent next = actual.getNextStartedActivity();
        final ShadowIntent shadowIntent = shadowOf(next);
        assertEquals(ConfigurationActivity.class.getName(), shadowIntent.getComponent().getClassName());
		
	}
	
	@Test
	public void devePesquisarOFreteEDirecionarParaTelaPrincipal() {
		
		final Editor editor = preferences.edit();
		editor.putString(Constante.DATA_ATUALIZACAO, ParseUtilities.formatDate(DateTime.now().plusDays(1).toDate(),"yyyy-MM-dd HH:mm:ss"));
		editor.commit();
		
		final IntegrationAsyncProcess process = new IntegrationAsyncProcess(Robolectric.application.getApplicationContext());
		process.setUserName("teste").setPassword("teste").execute();
		Robolectric.runBackgroundTasks();
		assertEquals(process.getStatus(), AsyncTask.Status.FINISHED);
		
		final MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
		activity.setCEP("22743310");
		activity.onClick(null);
		
		final ShadowActivity actual = shadowOf(activity);
		final Intent next = actual.getNextStartedActivity();
        final ShadowIntent shadowIntent = shadowOf(next);
        assertEquals(HomeActivity.class.getName(), shadowIntent.getComponent().getClassName());		
	}
	
	
	@Test
	public void cepEObrigatorio() {
		
		final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Robolectric.application.getApplicationContext());
		final Editor editor = preferences.edit();
		editor.putString(Constante.DATA_ATUALIZACAO, ParseUtilities.formatDate(DateTime.now().plusDays(1).toDate(),"yyyy-MM-dd HH:mm:ss"));
		editor.commit();
		
		final IntegrationAsyncProcess process = new IntegrationAsyncProcess(Robolectric.application.getApplicationContext());
		process.execute();
		Robolectric.runBackgroundTasks();
		assertEquals(process.getStatus(), AsyncTask.Status.FINISHED);
		
		final MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
		activity.onClick(null);
		
		final AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
		final ShadowAlertDialog shadow = shadowOf(dialog);
		assertEquals("CEP é obrigatório",shadow.getTitle().toString());
		
	}
	
	
	
	


}
