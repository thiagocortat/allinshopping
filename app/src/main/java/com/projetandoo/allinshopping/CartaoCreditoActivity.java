package com.projetandoo.allinshopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.androidquery.AQuery;
import com.projetandoo.allinshopping.alerts.AbstractDialog;
import com.projetandoo.allinshopping.alerts.ActionDialog;
import com.projetandoo.allinshopping.alerts.ErrorAlert;
import com.projetandoo.allinshopping.components.MaskedWatcher;
import com.projetandoo.allinshopping.models.DadosPagamento;
import com.projetandoo.allinshopping.models.Pedido;
import com.projetandoo.allinshopping.services.PedidoService;
import com.projetandoo.allinshopping.utilities.Constante;
import com.projetandoo.allinshopping.utilities.ParseUtilities;
import com.projetandoo.allinshopping.utilities.PriceUtilities;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartaoCreditoActivity extends AbstractActivity
        implements
        OnClickListener {

    private AQuery aq;

    @Override
    public void onClick(final View view) {
        Pedido pedido = PriceUtilities.getPedido();
        Intent intent = null;

        if (view.getId() == R.id.salvar) {

            DadosPagamento dadospagamento = criarDadosPagamento(pedido);

            if (dadospagamento.isValid()) {
                (new PedidoService(this)).save(dadospagamento);

                if( this.isNetworkConnected() ) {
                    intent = new Intent(this, AutenticacaoParaEnvioDePedidoActivity.class);
                } else {
                    PriceUtilities.novoPedido();
                    new ActionDialog(this)
                            .setTitle("Pedido")
                            .setMessage("Seu pedido foi salvo com sucesso e será enviado tão logo tenha conexão com a internet disponível.")
                            .show();
                }
            } else {
                new ErrorAlert(this)
                        .setTitle("Dados de Cartão de crédito")
                        .setMessages(dadospagamento.errors())
                        .setCancelable(true)
                        .show();
            }
        } else {
            intent = new Intent(this, PagamentoActivity.class);
        }
        startActivity(intent);
        this.finish();
    }

    private DadosPagamento criarDadosPagamento(Pedido pedido) {
        DadosPagamento dadospagamento = new DadosPagamento();
        dadospagamento.setPedido(pedido);
        dadospagamento.setPortador(getPortador());
        dadospagamento.setNumero(getNumeroCartao());
        dadospagamento.setDataValidade(getDataValidade());
        dadospagamento.setCVV(getCVV());
        dadospagamento.setCPF(getCPF());
        dadospagamento.setQtdParcelas(getNumeroParcelas());
        return dadospagamento;
    }

    public Integer getNumeroParcelas() {
        if (aq.id(R.id.parcelas).getSelectedItemPosition() > 0) {
            return aq.id(R.id.parcelas).getSelectedItemPosition();
        } else {
            return 1;
        }
    }

    public String getCPF() {
        return aq.id(R.id.cpf).getText().toString();
    }

    public String getCVV() {
        return aq.id(R.id.cvv).getText().toString();
    }

    public Date getDataValidade() {
        return ParseUtilities.toDate(aq.id(R.id.validade).getText().toString(), "MM/yy");
    }

    public String getNumeroCartao() {
        return aq.id(R.id.cartao).getText().toString();
    }

    public String getPortador() {
        return aq.id(R.id.nome).getText().toString();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_cartao_credito);

        aq = new AQuery(this);
        aq.id(R.id.salvar).clicked(this);
        aq.id(R.id.cancelar).clicked(this);

        Spinner spinner = aq.id(R.id.parcelas).getSpinner();
        List<String> parcelas = new ArrayList<String>();

        parcelas.add("Seleciona a quantidade de parcelas");
        parcelas.add("À vista");
        parcelas.add("2 vezes");
        parcelas.add("3 vezes");
        parcelas.add("4 vezes");
        parcelas.add("5 vezes");
        parcelas.add("6 vezes");
        parcelas.add("7 vezes");
        parcelas.add("8 vezes");
        parcelas.add("9 vezes");
        parcelas.add("10 vezes");
        parcelas.add("11 vezes");
        parcelas.add("12 vezes");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, parcelas);
        spinner.setAdapter(adapter);

        new MaskedWatcher("##/##", aq.id(R.id.validade).getEditText()).setAcceptOnlyNumbers(true);
        new MaskedWatcher("###.###.###-##", aq.id(R.id.cpf).getEditText()).setAcceptOnlyNumbers(true);
    }

    public void setPortador(String portador) {
        aq.id(R.id.nome).text(portador);
    }

    public void setCPF(String cpf) {
        aq.id(R.id.cpf).text(cpf);
    }

    public void setDataValidade(Date dataValidade) {
        aq.id(R.id.validade).text(ParseUtilities.formatDate(dataValidade,"MM/yy"));
    }

    public void setNumeroCartao(String cartao) {
        aq.id(R.id.cartao).text(cartao);
    }

    public void setCVV(String CVV) {
        aq.id(R.id.cvv).text(CVV);
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(this,PagamentoActivity.class);
//        startActivity(intent);
//        this.finish();
//    }
}
