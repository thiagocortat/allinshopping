package com.projetandoo.allinshopping.adapters;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.projetandoo.allinshopping.HomeActivity;
import com.projetandoo.allinshopping.R;
import com.projetandoo.allinshopping.ShoppingCartActivity;
import com.projetandoo.allinshopping.async.IntegrationProcess;
import com.projetandoo.allinshopping.models.Atributo;
import com.projetandoo.allinshopping.models.Produto;
import com.projetandoo.allinshopping.repository.ProductRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowIntent;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.projetandoo.allinshopping.responserules.HttpEntityResponseRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class TestProdutoAdapter {
	
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

		final IntegrationProcess integration = new IntegrationProcess("teste","teste",Robolectric.buildActivity(HomeActivity.class).create().get());
		integration.importarEstado();
		integration.importarCEP();
		integration.importarFaixaPreco();
		integration.importarSecao();
		integration.importarProdutos();

	}
	
	@Test
	public void montarListaDeProdutos() {
		
		final HomeActivity activity = Robolectric.buildActivity(HomeActivity.class).create().get();
		
		final ProductRepository service = new ProductRepository(activity);

        final List<Produto> produtos = new ArrayList<Produto>();
        Produto produto = service.getById(256L);
        produtos.add(produto);

		final ProdutoAdapter adapter = new ProdutoAdapter(activity, new ArrayList<Produto>(produtos));
		final View view  = adapter.getView(0, null, null);
		
		assertNotNull(view.findViewById(R.id.imagem));
		assertEquals(produto.getTitulo() , ((TextView)view.findViewById(R.id.titulo)).getText().toString());
        assertEquals(produto, view.findViewById(R.id.adicionar).getTag());

        RadioGroup radiogroup = (RadioGroup) view.findViewById(R.id.tamanho);
        assertEquals(View.VISIBLE, radiogroup.getVisibility());

        assertEquals(produto.getAtributos().size(), radiogroup.getChildCount());
		
	}

    @Test
    public void montarListaDeProdutosComAtributo() {
        final HomeActivity activity = Robolectric.buildActivity(HomeActivity.class).create().get();

        final ProductRepository service = new ProductRepository(activity);

        final List<Produto> produtos = new ArrayList<Produto>();
        Produto produto = service.getById(762L);
        produtos.add(produto);

        final ProdutoAdapter adapter = new ProdutoAdapter(activity, new ArrayList<Produto>(produtos));
        final View view  = adapter.getView(0, null, null);

        assertNotNull(view.findViewById(R.id.imagem));
        assertEquals(produto.getTitulo() , ((TextView)view.findViewById(R.id.titulo)).getText().toString());
        assertEquals(produto, view.findViewById(R.id.adicionar).getTag());
    }
    @Test
    public void adicionarProdutoAoCarrinho() {

        final HomeActivity activity = Robolectric.buildActivity(HomeActivity.class).create().get();

        final ProductRepository service = new ProductRepository(activity);

        final List<Produto> produtos = new ArrayList<Produto>();
        Produto produto = service.getById(762L);
        produtos.add(produto);
        final ProdutoAdapter adapter = new ProdutoAdapter(activity, new ArrayList<Produto>(produtos));
        final View view  = adapter.getView(0, null, null);

        Button button = (Button) view.findViewById(R.id.adicionar);
        button.callOnClick();

        final ShadowActivity actual = shadowOf(activity);
        final Intent next = actual.getNextStartedActivity();
        final ShadowIntent shadowIntent = shadowOf(next);

        assertEquals(ShoppingCartActivity.class.getName(), shadowIntent.getComponent().getClassName());
    }




    @Test
    public void adicionarUmCalcadoNaListaDeCompras() {


        final HomeActivity activity = Robolectric.buildActivity(HomeActivity.class).create().get();

        final ProductRepository service = new ProductRepository(activity);

        final List<Produto> produtos = new ArrayList<Produto>();
        Produto produto = service.getById(256L);
        produtos.add(produto);

        final ProdutoAdapter adapter = new ProdutoAdapter(activity, produtos);
        final View view  = adapter.getView(0, null, null);
        final ArrayList<Atributo> atributos = new ArrayList<Atributo>(produto.getAtributos());

        RadioGroup group = (RadioGroup) view.findViewById(R.id.tamanho);
        RadioButton radio = (RadioButton) group.findViewWithTag(atributos.get(0));
        group.check(radio.getId());

        Button button = (Button) view.findViewById(R.id.adicionar);
        button.callOnClick();

        final ShadowActivity actual = shadowOf(activity);
        final Intent next = actual.getNextStartedActivity();
        final ShadowIntent shadowIntent = shadowOf(next);

        assertEquals(ShoppingCartActivity.class.getName(), shadowIntent.getComponent().getClassName());

    }


    @Test
    public void naoPodeAdicionarUmSapatoSemTamanho() {
        final HomeActivity activity = Robolectric.buildActivity(HomeActivity.class).create().get();

        final ProductRepository service = new ProductRepository(activity);

        final List<Produto> produtos = new ArrayList<Produto>();
        produtos.add(service.getById(256L));

        final ProdutoAdapter adapter = new ProdutoAdapter(activity, produtos);
        final View view  = adapter.getView(0, null, null);

        Button button = (Button) view.findViewById(R.id.adicionar);
        button.callOnClick();

        final AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
        final ShadowAlertDialog shadow = shadowOf(dialog);
        assertEquals("Tamanho é obrigatório",shadow.getMessage().toString());
    }

}
