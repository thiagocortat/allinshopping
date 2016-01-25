package com.projetandoo.allinshopping.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.projetandoo.allinshopping.AutenticacaoAtualizacaoSistemaActivity;
import com.projetandoo.allinshopping.R;
import com.projetandoo.allinshopping.responserules.HttpEntityResponseRule;
import com.projetandoo.allinshopping.utilities.Constante;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowAlertDialog;

import java.util.ResourceBundle;

@RunWith(RobolectricTestRunner.class)
public class AtualizacaoSistemaActivityTests {

	private final ResourceBundle integration = ResourceBundle
			.getBundle("integration");

	private final ResourceBundle response = ResourceBundle
			.getBundle("json_message");

	private AutenticacaoAtualizacaoSistemaActivity activity;
	
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
		
		this.activity = Robolectric.buildActivity(AutenticacaoAtualizacaoSistemaActivity.class).create().get();
		preferences = activity.getApplicationContext().getSharedPreferences(Constante.SHARED_PREFERENCES, Context.MODE_PRIVATE);
		
		final Editor editor = preferences.edit();
		editor.putString(Constante.DATA_ATUALIZACAO, null);
		editor.commit();
	}


    @Test
    public void atualizarSistema() {

        activity.setEmail("thiagocortat@gmail.com");
        activity.setPassword("2pk0#3ty?");
        activity.onClick(activity.findViewById(R.id.logar));

        final AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .callOnClick();

    }
}
