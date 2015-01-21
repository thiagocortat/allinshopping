package com.projetandoo.allinshopping.services;

import org.joda.time.DateTime;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.projetandoo.allinshopping.MyApplication;
import com.projetandoo.allinshopping.utilities.Constante;
import com.projetandoo.allinshopping.utilities.ParseUtilities;

public class ConfigurationService {

	private SharedPreferences preferences;
	
	public ConfigurationService(Context context) {
		preferences = context.getApplicationContext()
							 .getSharedPreferences(Constante.SHARED_PREFERENCES, Context.MODE_PRIVATE);
	}
	
	public ConfigurationService() {
		preferences = MyApplication.getAppContext().getSharedPreferences(Constante.SHARED_PREFERENCES, Context.MODE_PRIVATE);
	}

	private void add(String key, String value ){
		
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.apply();
		
	}
	
	private String get(String key ){
		return preferences.getString(key, null);
	}
	
	public boolean precisaAtualizar() {
	
			DateTime ultimaAtualizacao = new DateTime(ParseUtilities.toDate(preferences.getString(Constante.DATA_ATUALIZACAO,
																					   ParseUtilities.formatDate(DateTime.now()
																							   							 .minusDays(1)
																							   							 .toDate(),"yyyy-MM-dd HH:mm:ss")),
																					   "yyyy-MM-dd HH:mm:ss"));			

			DateTime agora = DateTime.now()
										   .withMinuteOfHour(0)
										   .withHourOfDay(0)
										   .withSecondOfMinute(0);
			return !( agora.isBefore(ultimaAtualizacao.getMillis()) );
				
			
	}
	
	public void atualizar() {
		this.add(Constante.DATA_ATUALIZACAO, ParseUtilities.formatDate(DateTime.now().toDate() , "yyyy-MM-dd HH:mm:ss"));
	}
	
	public void setNomeLoja(String nomeLoja) {
		this.add(Constante.NOME_LOJA, nomeLoja);
	}
	
	public String getNomeLoja() {
		return this.get(Constante.NOME_LOJA);
	}

	public String getPassword() {
		return this.get(Constante.PASSWORD);
	}

	public String getUserName() {
		return this.get(Constante.USERNAME);
	}

}
