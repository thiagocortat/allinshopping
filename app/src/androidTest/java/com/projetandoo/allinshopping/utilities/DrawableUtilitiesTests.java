package com.projetandoo.allinshopping.utilities;

import android.graphics.drawable.Drawable;

import com.projetandoo.allinshopping.async.IntegrationProcess;
import com.projetandoo.allinshopping.models.Produto;
import com.projetandoo.allinshopping.repository.ProductRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ResourceBundle;

import com.projetandoo.allinshopping.responserules.HttpEntityResponseRule;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class DrawableUtilitiesTests {

    private final ResourceBundle integration = ResourceBundle
            .getBundle("integration");

    private final ResourceBundle response = ResourceBundle
            .getBundle("json_message");

    @Before
    public void setUp() throws Exception {

        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("estado"),response.getString("estado"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cep"),response.getString("cep"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("faixapreco"),response.getString("faixa"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("secao"),response.getString("secao"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("produto"),response.getString("produto"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cliente"),response.getString("cliente"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(new HttpEntityResponseRule());

        IntegrationProcess process = new IntegrationProcess("teste","teste",Robolectric.application.getApplicationContext());
        process.importarSecao();
        process.importarProdutos();

    }

    @Test
    public void testGetImage() throws Exception {
        ProductRepository repository = new ProductRepository(Robolectric.application.getApplicationContext());
        Produto produto = repository.getById(762L);

        assertNotNull(produto);

        Drawable image = DrawableUtilities.getImage(produto.getDefaultImage());

        assertNotNull(image);


    }
}