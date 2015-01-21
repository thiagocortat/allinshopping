package com.projetandoo.allinshopping.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.projetandoo.allinshopping.alerts.ActionDialog;
import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.exceptions.NoUniqueRegistryException;
import com.projetandoo.allinshopping.services.ConfigurationService;

import org.json.JSONException;

public class SendPedidoIntegrationAsyncProcess extends
        AsyncTask<Void, String, String> {

    private IntegrationProcess integration = new IntegrationProcess();
    private ConfigurationService service;
    private Context context;
    private ProgressDialog progress;

    public SendPedidoIntegrationAsyncProcess(Context context) {
        this.context = context;
        this.service = new ConfigurationService(context);
    }

    @Override
    protected void onPreExecute() {
        Log.d("com.projetandoo.allinshopping.async", "Iniciando a carga do tablet");
        this.progress = ProgressDialog.show(context, "Catalógo Digital Ella S/A", "Aguarde, estamos enviando o seu pedido para a nossa central", true);
        publishProgress("Iniciando a carga do tablet");
    }

    @Override
    protected String doInBackground(Void... avoid) {
        try {

            integration.enviarPedido();

            return "Pedido enviado com sucesso";

        } catch (IntegrationException e) {
            return e.getMessage();
        } catch (JSONException e) {
            return e.getMessage();
        } catch (NoUniqueRegistryException e) {
            return e.getMessage();
        } catch (Exception e) {
            return e.getMessage();
        }
        //return "Pedido enviado com sucesso";
    }

    @Override
    protected void onPostExecute(String message) {

        super.onPostExecute(message);

        if (progress.isShowing()) {
            progress.dismiss();
        }

        (new ActionDialog(context))
                .setTitle("Catalogo Digital Ella S/A")
                .setMessage(
                        "Seu pedido foi concluído com sucesso, obrigado pela preferência")
                .show();
    }
}
