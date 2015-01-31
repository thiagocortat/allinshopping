package com.projetandoo.allinshopping.async;

import android.app.ProgressDialog;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.androidquery.AQuery;
import com.projetandoo.allinshopping.R;
import com.projetandoo.allinshopping.alerts.ActionDialog;
import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.exceptions.NoUniqueRegistryException;
import com.projetandoo.allinshopping.services.ConfigurationService;
import com.projetandoo.allinshopping.utilities.StringUtils;

public class IntegrationAsyncProcess extends AsyncTask<Void, String, String> {
	
	private ConfigurationService service;
	private String email;
	private String password;
	private Context context;
    private ProgressDialog progress;


    public IntegrationAsyncProcess(Context context) {

		this.context = context;
		this.service = new ConfigurationService(context);

	}
	
	@Override
	protected void onPreExecute() {
		Log.d("com.projetandoo.allinshopping.async","Iniciando a carga do tablet");
        this.progress = ProgressDialog.show(context,"Atualização do Catalógo Digital Ella S/A","Aguarde, estamos atualização do seu Catalógo Digital Ella S/A",true);
        publishProgress("Iniciando a carga do tablet");
	}

    @Override
	protected String doInBackground(Void... avoid)
    {
        String message = "";

        IntegrationProcess integration = new IntegrationProcess(email, password, this.context);
        try {
            Log.d("com.projetandoo.allinshopping.async", "Enviando os novos pedidos para o backoffice");
            integration.enviarPedido();
        }catch (Exception e) { message = " enviarPedido() \n" +  e.getMessage(); }

        try {
            Log.d("com.projetandoo.allinshopping.async","Recebendo a lista de meios de pagamento");
            integration.importarFormasPagamento();
        }catch (Exception e) { message += " importarFormasPagamento() \n" + e.getMessage(); }

        try {
            Log.d("com.projetandoo.allinshopping.async","Recebendo a lista de Estados do backoffice");
            integration.importarEstado();
        }catch (Exception e) { message += " importarEstado() \n" + e.getMessage(); }

        try {
            Log.d("com.projetandoo.allinshopping.async","Recebendo a lista de departamentos do backoffice");
            integration.importarSecao();
        }catch (Exception e) { message += " importarSecao() \n" + e.getMessage(); }

        try {
            Log.d("com.projetandoo.allinshopping.async","Recebendo a lista de produtos do backoffice");
            integration.importarProdutos();
        }catch (Exception e) { message += " importarProdutos() \n" + e.getMessage(); }

        try {
            Log.d("com.projetandoo.allinshopping.async","Recebendo a lista de clientes do backoffice");
           integration.importarCliente();
        }catch (Exception e) { message += " importarCliente() \n" + e.getMessage() ; }

        try {
            Log.d("com.projetandoo.allinshopping.async","Recebendo a tabela de preços de frete do backoffice");
            integration.importarCEP();
        }catch (Exception e) { message += " importarCEP() \n" + e.getMessage(); }


        if (StringUtils.isEmpty(message)) {
            service.atualizar();
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Dados importados com sucesso";
        }
        else {
            return "Ocorreu erros de atualização nos serviços de: " + message;
        }
			
//		} catch (IntegrationException e) {
//			Log.e("com.projetandoo.allinshopping.async","Erro no carga do tablet",e);
//			return e.getMessage();
//		} catch (JSONException e) {
//			Log.e("com.projetandoo.allinshopping.async","Erro na conversão dos valores recebidos do servidor",e);
//			return e.getMessage();
//		} catch (NoUniqueRegistryException e) {
//			Log.e("com.projetandoo.allinshopping.async","Erro no salvamento dos dados no tablet",e);
//			return e.getMessage();
//		} catch (Exception e) {
//			Log.e("com.projetandoo.allinshopping.async","Erro desconhecido",e);
//			return e.getMessage();
//		}
    }

	@Override
	protected void onPostExecute(String message) {
		super.onPostExecute(message);


        if (progress.isShowing()) {
            progress.dismiss();
        }

		new ActionDialog(this.context)
				.setTitle("Atualização da base de dados")
				.setCancelable(false)
				.setMessage(message)
				.show();
	}

	public IntegrationAsyncProcess setUserName(String email) {
		this.email = email;
		return this;
	}

	public IntegrationAsyncProcess setPassword(String password) {
		this.password = password;
		return this;		
	}

    public boolean isRunning() {
        return this.getStatus() == Status.RUNNING;
    }

}
