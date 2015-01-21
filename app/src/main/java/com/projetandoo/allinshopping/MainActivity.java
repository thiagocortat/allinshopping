package com.projetandoo.allinshopping;

import org.apache.commons.validator.GenericValidator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.androidquery.AQuery;
import com.projetandoo.allinshopping.alerts.ErrorAlert;
import com.projetandoo.allinshopping.components.MaskedWatcher;
import com.projetandoo.allinshopping.models.FaixaPreco;
import com.projetandoo.allinshopping.services.CEPService;
import com.projetandoo.allinshopping.services.ConfigurationService;
import com.projetandoo.allinshopping.utilities.Constante;
import com.projetandoo.allinshopping.utilities.PriceUtilities;

public class MainActivity extends AbstractActivity implements OnClickListener {

	private AQuery aq;
	private CEPService cepservice;
	private ConfigurationService service;
	
	private static final Long UNDEFINED_ZIPCODE = 0L;
	
	public void setCEP(String cep) {
		aq.id(R.id.zipcode).text(cep);
	}
	
	public Long getCEP() {
		String zipcode = aq.id(R.id.zipcode).getText().toString().replace("-", "");
		Long cep = UNDEFINED_ZIPCODE;
		if( !GenericValidator.isBlankOrNull(zipcode) && GenericValidator.isLong(zipcode)){
			cep = Long.parseLong(zipcode.substring(0,5));
		} 
		return cep;
	}

	@Override
	public void onClick(View view) {

		Long cep = this.getCEP();
		if( cep > UNDEFINED_ZIPCODE ){
			Intent intent = new Intent(this, HomeActivity.class);
			FaixaPreco frete = cepservice.getFaixaPrecoByCEPDestino(cep);
			PriceUtilities.setFrete(frete);
			intent.putExtra(Constante.FRETE, frete);
			startActivity(intent);
		} else {
			new ErrorAlert(this)
					.setTitle("CEP é obrigatório")
					.setCancelable(true)
					.show();
		}

	}
	

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);		
		setContentView(R.layout.activity_home);
		cepservice = new CEPService(this);
		service = new ConfigurationService(this);
		aq = new AQuery(this);
		
//		aq.id(R.id.progressText).clicked(this);
//		aq.id(R.id.shopping).clicked(this);
		aq.id(R.id.progressText).text(service.getNomeLoja());
		aq.clickable(true);

        new MaskedWatcher("#####-###", aq.id(R.id.zipcode).getEditText()).setAcceptOnlyNumbers(true);
		
		if (service.precisaAtualizar()) {
			Intent intent = new Intent(this, ConfigurationActivity.class);
			startActivity(intent);
            this.finish();
		} 
	}

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
