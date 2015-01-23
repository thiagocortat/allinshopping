package com.projetandoo.allinshopping;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.projetandoo.allinshopping.alerts.ErrorAlert;
import com.projetandoo.allinshopping.commandfactory.CommandFactory;
import com.projetandoo.allinshopping.models.Secao;

public class HomeActivity extends AbstractActivity implements OnClickListener {

	private Secao secao;
	private ListView secoes;
	private ListView produtos;
	private AQuery aq = new AQuery(this);
	
	@Override
	protected void onCreate(final Bundle bundle) {
		super.onCreate(bundle);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_main);
		aq.id(R.id.promocoes).invisible();
		this.secoes = aq.id(R.id.secoes).getListView();
		this.produtos = aq.id(R.id.promocoes).getListView();

	}

	public ListView getSecoes() {
		return this.secoes;
	}

	public ListView getProdutos() {
		return this.produtos;
	}

	@Override
	protected void onStart() {

        try {

            super.onStart();
            CommandFactory.getFactory(this)
                    .createCommand()
                    .execute();

        }catch( Exception e) {
            new ErrorAlert(this)
                    .setMessage(e.getMessage())
                    .setCancelable(true)
                    .setTitle("Catalogo Digital Ella S/A")
                    .show();
        }
	}


	@Override
	public void onBackPressed() {

		if (this.secao == null) {
//            final Intent intent = new Intent(this,MainActivity.class);
//            startActivity(intent);
            this.finish();
		} else {
			this.secao = secao.getSecaoPai();
			CommandFactory.getFactory(this)
	    	  .createCommand()
	    	  .execute();
		}
	}

	@Override
	public void onClick(View view) {
		this.secao = (Secao) view.getTag();
		CommandFactory.getFactory(this)
			    	  .createCommand()
			    	  .execute();
	}

	public void setTitulo(String titulo) {
		aq.id(R.id.promocao).text(titulo);
	}
	
	public String getTitulo() {
		return aq.id(R.id.promocao).getText().toString();
	}
	
	public Secao getSecao() {
		return this.secao;
	}

	public void setBoneca(int image) {
		aq.id(R.id.boneca).image(image);
		
	}

	public void exibirBoneca(boolean show) {
		if(show) {
			aq.id(R.id.boneca).visible();
		} else {
			aq.id(R.id.boneca).invisible();
		}
	}

	public void exibirListaProdutos(boolean show) {
		if(show) {
			aq.id(R.id.promocoes).visible();
		} else {
			aq.id(R.id.promocoes).invisible();
		}
		
	}

}
