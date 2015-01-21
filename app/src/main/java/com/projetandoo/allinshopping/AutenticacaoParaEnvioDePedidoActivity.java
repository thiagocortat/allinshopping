package com.projetandoo.allinshopping;

import android.content.Intent;

import com.projetandoo.allinshopping.alerts.ActionDialog;
import com.projetandoo.allinshopping.async.SendPedidoIntegrationAsyncProcess;
import com.projetandoo.allinshopping.models.Pedido;
import com.projetandoo.allinshopping.services.PedidoService;
import com.projetandoo.allinshopping.utilities.Constante;
import com.projetandoo.allinshopping.utilities.PriceUtilities;


public class AutenticacaoParaEnvioDePedidoActivity extends AbstractLogarActivity {

	@Override
	protected void doAction() {

		Pedido pedido = PriceUtilities.getPedido();
		(new PedidoService(this)).save(pedido);

		SendPedidoIntegrationAsyncProcess process = new SendPedidoIntegrationAsyncProcess(this);
		process.execute();

		
	}

	@Override
	protected void doBack() {
//		Intent pagamento = new Intent(this, PagamentoActivity.class);
//		startActivity(pagamento);
        this.finish();
	}

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }


}
