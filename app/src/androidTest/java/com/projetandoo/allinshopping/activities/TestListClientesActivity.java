package com.projetandoo.allinshopping.activities;

import android.content.Intent;
import android.widget.ListView;

import com.projetandoo.allinshopping.*;
import com.projetandoo.allinshopping.async.IntegrationProcess;
import com.projetandoo.allinshopping.repository.ClienteRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import java.util.ResourceBundle;

import com.projetandoo.allinshopping.responserules.HttpEntityResponseRule;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by Marceloo on 06/10/2014.
 */
@RunWith(RobolectricTestRunner.class)
public class TestListClientesActivity {

    private final ResourceBundle integration = ResourceBundle
            .getBundle("integration");

    private final ResourceBundle response = ResourceBundle
            .getBundle("json_message");

    @Test
    public void testListarClientes() throws Exception {

        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("estado"),response.getString("estado"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cep"),response.getString("cep"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cliente"),response.getString("cliente"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(new HttpEntityResponseRule());

        final IntegrationProcess integration = new IntegrationProcess("teste","teste",Robolectric.buildActivity(HomeActivity.class).create().get());
        integration.importarEstado();
        integration.importarCliente();
        integration.importarCEP();

        ListClientesActivity activity = Robolectric.buildActivity(ListClientesActivity.class).create().get();
        ClienteRepository repository = new ClienteRepository(activity);
        ListView clientes = (ListView) activity.findViewById(R.id.clientes);
        assertEquals(repository.count() , clientes.getCount());

    }

    @Test
    public void testShouldOpenClienteActivity() {

        ListClientesActivity activity = Robolectric.buildActivity(ListClientesActivity.class).create().get();

        final ShadowActivity actual = shadowOf(activity);
        final Intent next = actual.getNextStartedActivity();
        final ShadowIntent shadowIntent = shadowOf(next);
        assertEquals(ClienteActivity.class.getName(), shadowIntent.getComponent().getClassName());

    }
}
