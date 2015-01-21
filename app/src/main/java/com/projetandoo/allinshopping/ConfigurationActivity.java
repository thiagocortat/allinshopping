package com.projetandoo.allinshopping;

import org.apache.commons.validator.GenericValidator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.androidquery.AQuery;
import com.projetandoo.allinshopping.alerts.ErrorAlert;
import com.projetandoo.allinshopping.alerts.UpdateApplicationDialog;
import com.projetandoo.allinshopping.services.ConfigurationService;

public class ConfigurationActivity extends AbstractActivity implements OnClickListener {

    private AQuery aq; //NOPMD
	private ConfigurationService service;
    
    public String getNomeLoja() {
    	return aq.id(R.id.nomeLoja).getText().toString();
    }
    
    public void setNomeLoja(String nomeLoja) {
		aq.id(R.id.nomeLoja).text(nomeLoja);		
	}

    @Override
	public void onClick(final View view)
    {
        if (view.getId() == R.id.salvar ){
        	
        	if( GenericValidator.isBlankOrNull(this.getNomeLoja()) ) {
		    	
        		ErrorAlert alert = new ErrorAlert(this);
				alert.setTitle("Erro na configuração do sistema")
        			 .setCancelable(true)
					 .setMessage("Nome da Loja é obrigatório")
        			 .show();
		    	
        	} else {
        		
		    	service.setNomeLoja(this.getNomeLoja());
		    	
		    	if( service.precisaAtualizar() ) {
		    		(new UpdateApplicationDialog(this))
	            		.setCancelable(true)
	            		.setTitle("Deseja atualizar a base de dados?")
	            		.setMessage("Enviaremos suas vendas em aberto e atualizaremos o seu banco de dados")
	            		.show();
		    	} else {
		    		
		    		Intent intent = new Intent(this, MainActivity.class);
		            startActivity(intent);
                    this.finish();
		    		
		    	}
        		
        	}
        	
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    @Override
	public void onCreate(final Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_config);
        aq = new AQuery(this);
        aq.id(R.id.atualizar).clicked(this);
        aq.id(R.id.salvar).clicked(this);
        aq.id(R.id.cancelar).clicked(this);
        
        this.service = new ConfigurationService(this);
    	
    	if( !GenericValidator.isBlankOrNull(service.getNomeLoja()) ){
    		this.setNomeLoja(service.getNomeLoja());    		
    	}
        
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(this,HomeActivity.class);
//        startActivity(intent);
//        this.finish();
//    }
}
