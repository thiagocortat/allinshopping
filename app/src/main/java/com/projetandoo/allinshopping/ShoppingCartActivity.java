package com.projetandoo.allinshopping;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.projetandoo.allinshopping.adapters.ShoppingCartAdapter;
import com.projetandoo.allinshopping.alerts.ClienteDialog;
import com.projetandoo.allinshopping.models.ItemPedido;
import com.projetandoo.allinshopping.models.Pedido;
import com.projetandoo.allinshopping.utilities.ParseUtilities;
import com.projetandoo.allinshopping.utilities.PriceUtilities;

public class ShoppingCartActivity extends AbstractActivity
		implements
			OnClickListener {

    @Override
	public void onClick(View view) {
		if (view.getId() == R.id.finalizar_pedido) {
			Pedido pedido = PriceUtilities.getPedido();
			new ClienteDialog(this)
                    .setPedido(pedido)
					.setMessage("Cadastro de cliente")
                    .show();
		} else if (view.getId() == R.id.continuar_comprando) {
//			Intent intent = new Intent(this, HomeActivity.class);
//			startActivity(intent);
            this.finish();
		}

	}

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_shopping_cart_main);
        AQuery aq = new AQuery(this);
		Pedido pedido = PriceUtilities.getPedido();
		ListView listview = aq.id(R.id.lista_itens_pedidos).getListView();
		ShoppingCartAdapter shoppingcartadapter = new ShoppingCartAdapter(this,
				new ArrayList<ItemPedido>(pedido.getItens()));
		listview.setAdapter(shoppingcartadapter);
		aq.id(R.id.total_pedido).text(
				ParseUtilities.formatMoney(pedido.getTotal()));
		aq.id(R.id.continuar_comprando).clicked(this);
		aq.id(R.id.finalizar_pedido).clicked(this);
	}

//    @Override
//    public void onBackPressed() {
//        final Intent intent = new Intent(this,HomeActivity.class);
//        startActivity(intent);
//        this.finish();
//    }
}
