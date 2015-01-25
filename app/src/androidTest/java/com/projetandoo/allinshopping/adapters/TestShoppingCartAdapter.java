package com.projetandoo.allinshopping.adapters;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.projetandoo.allinshopping.HomeActivity;
import com.projetandoo.allinshopping.R;
import com.projetandoo.allinshopping.ShoppingCartActivity;
import com.projetandoo.allinshopping.async.IntegrationProcess;
import com.projetandoo.allinshopping.models.Atributo;
import com.projetandoo.allinshopping.models.ItemPedido;
import com.projetandoo.allinshopping.models.Produto;
import com.projetandoo.allinshopping.repository.ProductRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.projetandoo.allinshopping.responserules.HttpEntityResponseRule;

/**
 * Created by Marceloo on 19/09/2014.
 */
@RunWith(RobolectricTestRunner.class)
public class TestShoppingCartAdapter {

    private final ResourceBundle integration = ResourceBundle
            .getBundle("integration");

    private final ResourceBundle response = ResourceBundle
            .getBundle("json_message");

    @Before
    public void setup() throws Exception {

        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("estado"),response.getString("estado"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cep"),response.getString("cep"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("faixapreco"),response.getString("faixa"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("secao"),response.getString("secao"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("produto"),response.getString("produto"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cliente"),response.getString("cliente"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(new HttpEntityResponseRule());

        final IntegrationProcess integration = new IntegrationProcess("teste","teste",Robolectric.buildActivity(ShoppingCartActivity.class).create().get());
        integration.importarEstado();
        integration.importarCEP();
        integration.importarFaixaPreco();
        integration.importarSecao();
        integration.importarProdutos();

    }

    @Test
    public void montarCarrinhoCompras() {

        final ShoppingCartActivity activity = Robolectric.buildActivity(ShoppingCartActivity.class).create().get();

        final ProductRepository service = new ProductRepository(activity);
        final List<ItemPedido> itens = new ArrayList<ItemPedido>();
        Produto produto = service.getById(256L);
        ItemPedido item = new ItemPedido(produto,null,null);
        itens.add(item);

        final ShoppingCartAdapter adapter = new ShoppingCartAdapter(activity,itens);
        final View view = adapter.getView(0,null,null);

        TextView nome = (TextView) view.findViewById(R.id.nome);

        Assert.assertEquals(produto.getTitulo(), nome.getText());
        Assert.assertEquals(item.getQuantidade(),new Long(((EditText)view.findViewById(R.id.quantidade)).getText().toString()));

    }

    @Test
    public void montarCarrinhoDeCompraComProdutoComAtributo() {

        final HomeActivity activity = Robolectric.buildActivity(HomeActivity.class).create().get();

        final ProductRepository service = new ProductRepository(activity);

        final List<ItemPedido> itens = new ArrayList<ItemPedido>();
        Produto produto = service.getById(256L);
        Atributo atributo = new ArrayList<Atributo>(produto.getAtributos()).get(0);
        ItemPedido item = new ItemPedido(produto,atributo,null);
        itens.add(item);

        final ShoppingCartAdapter adapter = new ShoppingCartAdapter(activity,itens);
        final View view = adapter.getView(0,null,null);

        TextView nome = (TextView) view.findViewById(R.id.nome);

        Assert.assertEquals(produto.getTitulo(), nome.getText());
        Assert.assertEquals(item.getQuantidade(),new Long(((EditText)view.findViewById(R.id.quantidade)).getText().toString()));
        Assert.assertEquals(item.getAtributo().getDescricao(),((TextView)view.findViewById(R.id.atributo)).getText());

    }

}
