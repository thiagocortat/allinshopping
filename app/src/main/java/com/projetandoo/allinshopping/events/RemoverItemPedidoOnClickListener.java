package com.projetandoo.allinshopping.events;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import com.androidquery.AQuery;
import com.projetandoo.allinshopping.R;
import com.projetandoo.allinshopping.adapters.ShoppingCartAdapter;
import com.projetandoo.allinshopping.models.ItemPedido;
import com.projetandoo.allinshopping.models.Pedido;
import com.projetandoo.allinshopping.utilities.ParseUtilities;
import com.projetandoo.allinshopping.utilities.PriceUtilities;

import java.util.ArrayList;
import java.util.List;

public class RemoverItemPedidoOnClickListener
        implements OnClickListener {

    private AQuery aq;

    @Override
    public void onClick(View view) {
        Activity activity = (Activity) view.getContext();
        if (aq == null) {
            aq = new AQuery(activity);
        }
        Pedido pedido = PriceUtilities.getPedido();
        ItemPedido itempedido = (ItemPedido) view.getTag();
        pedido.remover(itempedido);

        ListView listview = aq.id(R.id.lista_itens_pedidos).getListView();
        Context context = view.getContext();
        List<ItemPedido> itens = new ArrayList<ItemPedido>(pedido.getItens());
        ShoppingCartAdapter shoppingcartadapter = new ShoppingCartAdapter(context, itens);
        listview.setAdapter(shoppingcartadapter);

        aq.id(R.id.total_pedido).text(ParseUtilities.formatMoney(pedido.getTotal()));

    }
}
