package com.projetandoo.allinshopping.activities;

import android.app.AlertDialog;
import android.content.Intent;

import com.projetandoo.allinshopping.ClienteActivity;
import com.projetandoo.allinshopping.HomeActivity;
import com.projetandoo.allinshopping.MyApplication;
import com.projetandoo.allinshopping.PagamentoActivity;
import com.projetandoo.allinshopping.R;
import com.projetandoo.allinshopping.async.IntegrationProcess;
import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.models.Cliente;
import com.projetandoo.allinshopping.repository.ClienteRepository;
import com.projetandoo.allinshopping.repository.EstadoRepository;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowIntent;

import java.util.Properties;
import java.util.ResourceBundle;

import com.projetandoo.allinshopping.responserules.HttpEntityResponseRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by Marceloo on 06/10/2014.
 */
@RunWith(RobolectricTestRunner.class)
public class ClienteActivityTests {

   // Properties properties = MyApplication.loadProperties("integration.properties");

    private final ResourceBundle integration = ResourceBundle
            .getBundle("integration");

    private final ResourceBundle response = ResourceBundle
            .getBundle("json_message");

    @Before
    public void setup() throws IntegrationException {

        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("estado"), response.getString("estado"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cep"), response.getString("cep"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cliente"), response.getString("cliente"));
        Robolectric.getFakeHttpLayer().addHttpResponseRule(new HttpEntityResponseRule());

        final IntegrationProcess integration = new IntegrationProcess("teste", "teste", Robolectric.buildActivity(HomeActivity.class).create().get());
        integration.importarEstado();
        integration.importarCEP();

    }

    @Test
    public void shouldAddNewCliente() {

        Intent intent = new Intent(Robolectric.getShadowApplication().getApplicationContext(), ClienteActivity.class);
        intent.putExtra("teste", "teste");

        ClienteActivity activity = Robolectric.buildActivity(ClienteActivity.class).withIntent(intent).create().get();

        final EstadoRepository repository = new EstadoRepository(activity);
        final ClienteRepository clienteRepository = new ClienteRepository(activity);

        activity.setPrimeiroNome("TESTE");
        activity.setUltimoNome("TESTE");
        activity.setBairro("Pechincha");
        activity.setCelular("21981363699");
        activity.setCep("22743310");
        activity.setCidade("RIO DE JANEIRO");
        activity.setDataNascimento(DateTime.now().toDate());
        activity.setEmail("marcelosrodrigues@globo.com");
        activity.setEndereco("Estrada Campo da Areia, 84 apto 206");
        activity.setEstado(repository.findByUF("RJ"));
        activity.setTelefone("2133926222");
        activity.onClick(activity.findViewById(R.id.salvar));


        Cliente cliente = clienteRepository.list().get(0);
        assertNotNull(cliente);
        assertEquals("marcelosrodrigues@globo.com", cliente.getEmail());

        final ShadowActivity actual = shadowOf(activity);
        final Intent next = actual.getNextStartedActivity();
        final ShadowIntent shadowIntent = shadowOf(next);
        assertEquals(PagamentoActivity.class.getName(), shadowIntent.getComponent().getClassName());

    }

    @Test
    public void naoPodeSalvarClienteSemDataNascimento() {

        Intent intent = new Intent(Robolectric.getShadowApplication().getApplicationContext(), ClienteActivity.class);
        intent.putExtra("teste", "teste");

        ClienteActivity activity = Robolectric.buildActivity(ClienteActivity.class).withIntent(intent).create().get();

        final EstadoRepository repository = new EstadoRepository(activity);

        activity.setPrimeiroNome("TESTE");
        activity.setUltimoNome("TESTE");
        activity.setBairro("Pechincha");
        activity.setCelular("21981363699");
        activity.setCep("22743310");
        activity.setCidade("RIO DE JANEIRO");
        activity.setEmail("marcelosrodrigues@globo.com");
        activity.setEndereco("Estrada Campo da Areia, 84 apto 206");
        activity.setEstado(repository.findByUF("RJ"));
        activity.setTelefone("2133926222");
        activity.onClick(activity.findViewById(R.id.salvar));

        final AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
        final ShadowAlertDialog shadow = shadowOf(dialog);
        assertEquals("Não foi possível salvar o cliente.", shadow.getTitle().toString());

    }

    @Test
    public void naoPodeSalvarClienteComDadoInvalido() {

        Intent intent = new Intent(Robolectric.getShadowApplication().getApplicationContext(), ClienteActivity.class);
        intent.putExtra("teste", "teste");

        ClienteActivity activity = Robolectric.buildActivity(ClienteActivity.class).withIntent(intent).create().get();

        activity.setDataNascimento(DateTime.now().toDate());
        activity.onClick(activity.findViewById(R.id.salvar));

        final AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
        final ShadowAlertDialog shadow = shadowOf(dialog);
        assertEquals("Não foi possível salvar o cliente.", shadow.getTitle().toString());

    }
}
