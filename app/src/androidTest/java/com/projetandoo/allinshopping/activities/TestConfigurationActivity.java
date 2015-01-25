package com.projetandoo.allinshopping.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.projetandoo.allinshopping.AutenticacaoAtualizacaoSistemaActivity;
import com.projetandoo.allinshopping.ConfigurationActivity;
import com.projetandoo.allinshopping.MainActivity;
import com.projetandoo.allinshopping.R;
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
public class TestConfigurationActivity {

	private final ResourceBundle integration = ResourceBundle
			.getBundle("integration");

	private final ResourceBundle response = ResourceBundle
			.getBundle("json_message");

	private ConfigurationActivity activity;
	
	private SharedPreferences preferences;
	
	@Before
	public void setup() {
		
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("estado"),response.getString("estado"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cep"),response.getString("cep"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("faixapreco"),response.getString("faixa"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("secao"),response.getString("secao"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("produto"),response.getString("produto"));		
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cliente"),response.getString("cliente"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(new HttpEntityResponseRule());
		
		this.activity = Robolectric.buildActivity(ConfigurationActivity.class).create().get();
		preferences = activity.getApplicationContext().getSharedPreferences(Constante.SHARED_PREFERENCES, Context.MODE_PRIVATE);		
		
		final Editor editor = preferences.edit();
		editor.putString(Constante.DATA_ATUALIZACAO, null);
		editor.commit();
		
	}
	
	@Test
	public void deveSalvarEAtualizarOBancoDeDados() {
		
		activity.setNomeLoja("Marcelo");
		activity.onClick(activity.findViewById(R.id.salvar));
		
		final AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
		dialog.getButton(AlertDialog.BUTTON_POSITIVE)
			  .callOnClick();
		
		final ShadowActivity actual = shadowOf(activity);
		final Intent next = actual.getNextStartedActivity();
		final ShadowIntent shadowIntent = shadowOf(next);
				
		assertEquals(AutenticacaoAtualizacaoSistemaActivity.class.getName(), shadowIntent.getComponent().getClassName());		
		
	}
	
	@Test
	public void deveApenasSalvar() {
		
		final Editor editor = preferences.edit();
		editor.putString(Constante.DATA_ATUALIZACAO, ParseUtilities.formatDate(DateTime.now().plusDays(1).toDate(),"yyyy-MM-dd HH:mm:ss"));
		editor.commit();
		
		activity.setNomeLoja("Marcelo");
		
		activity.onClick(activity.findViewById(R.id.salvar));
		
		final ShadowActivity actual = shadowOf(activity);
		final Intent next = actual.getNextStartedActivity();
		final ShadowIntent shadowIntent = shadowOf(next);
				
		assertEquals(MainActivity.class.getName(), shadowIntent.getComponent().getClassName());	
		
	}

	@Test
	public void naoPodeSalvarLojaSemNome() {
		
		activity.setNomeLoja("");
		activity.onClick(activity.findViewById(R.id.salvar));
		
		final AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
		final ShadowAlertDialog shadow = shadowOf(dialog);
		assertEquals("Erro na configuração do sistema",shadow.getTitle().toString());
		
	}

}
