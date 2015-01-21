package com.projetandoo.allinshopping.events;

import android.app.Activity;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import com.androidquery.AQuery;
import com.projetandoo.allinshopping.R;
import com.projetandoo.allinshopping.models.ItemPedido;
import com.projetandoo.allinshopping.models.Pedido;
import com.projetandoo.allinshopping.utilities.Constante;
import com.projetandoo.allinshopping.utilities.ParseUtilities;
import com.projetandoo.allinshopping.utilities.PriceUtilities;

public class AtualizarQuantidadeItemPedidoEvent
        implements
        OnFocusChangeListener {

    private AQuery aq;

    @Override
    public void onFocusChange(View view, boolean flag) {
        if (!flag) {
            Activity activity = (Activity) view.getContext();
            if (aq == null) {
                aq = new AQuery(activity);
            }
            Pedido pedido = PriceUtilities.getPedido();
            ItemPedido itempedido = (ItemPedido) view.getTag();
            String quantidade = ((EditText) view).getText().toString();
            if (!"".equalsIgnoreCase(quantidade)) {
                Long qtd = Long.parseLong(quantidade);
                if (qtd.longValue() > 0L) {
                    itempedido.setQuantidade(qtd);
                } else {
                    pedido.remover(itempedido);
                }
            }
            aq.id(R.id.total_pedido).text(ParseUtilities.formatMoney(pedido.getTotal()));
            activity.getIntent().putExtra(Constante.PEDIDO, pedido);
        }
    }
}
