package com.projetandoo.allinshopping;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.androidquery.AQuery;
import com.projetandoo.allinshopping.models.FormaPagamento;
import com.projetandoo.allinshopping.models.Pedido;
import com.projetandoo.allinshopping.repository.FormaPagamentoRepository;
import com.projetandoo.allinshopping.services.PedidoService;
import com.projetandoo.allinshopping.utilities.Constante;
import com.projetandoo.allinshopping.utilities.ParseUtilities;
import com.projetandoo.allinshopping.utilities.PriceUtilities;

public class PagamentoActivity extends AbstractActivity
        implements
        OnClickListener {

    private AQuery aq;

    private Pedido pedido;

    private PedidoService pedidoservice = new PedidoService(this);

    @Override
    public void onClick(View view) {
        Intent intent = null;
        if (view.getId() == R.id.salvar) {

            FormaPagamento formapagamento = this.getFormaPagamento();
            pedido.setFormaPagamento(formapagamento);
            pedidoservice.save(pedido);
            intent = new Intent(this, CartaoCreditoActivity.class);

        } else if (view.getId() == R.id.cancelar) {
            intent = new Intent(this, ShoppingCartActivity.class);
        }
        startActivity(intent);
//        this.finish();
    }

//    @Override
//    public void onBackPressed() {
//        final Intent intent = new Intent(this,ShoppingCartActivity.class);
//        startActivity(intent);
//        this.finish();
//    }

    public FormaPagamento getFormaPagamento() {
        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.formapagamento);
        int i = radiogroup.getCheckedRadioButtonId();
        return (FormaPagamento)radiogroup.findViewById(i).getTag();
    }

    @Override
    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.activity_forma_pagamento_main);
        pedido = PriceUtilities.getPedido();
        aq = new AQuery(this);
        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.formapagamento);
        List<FormaPagamento> formasPagamento = new FormaPagamentoRepository(this).list();

        int i = 0;
        for (FormaPagamento forma : formasPagamento) {
            RadioButton radiobutton = new RadioButton(this);
            radiobutton.setId(forma.getId().intValue());
            radiobutton.setTag(forma);
            radiobutton.setText(forma.getNome());
            radiobutton.setHint(forma.getNome());
            radiogroup.addView(radiobutton);
            if (i == 0) {
                radiobutton.setChecked(true);
            }
            i++;
        }
        aq.id(R.id.total_pedido).text(ParseUtilities.formatMoney(pedido.getTotal()));
        aq.id(R.id.salvar).clicked(this);
        aq.id(R.id.cancelar).clicked(this);

    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        RadioGroup radio = (RadioGroup)findViewById(R.id.formapagamento);
        radio.check(formaPagamento.getId().intValue());
    }
}
