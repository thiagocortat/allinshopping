package com.projetandoo.allinshopping.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.projetandoo.allinshopping.AutenticacaoParaEnvioDePedidoActivity;
import com.projetandoo.allinshopping.CartaoCreditoActivity;
import com.projetandoo.allinshopping.R;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowNetworkInfo;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by Marceloo on 09/10/2014.
 */
@RunWith(RobolectricTestRunner.class)
public class CartaoCreditoActivityTests {

    private CartaoCreditoActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(CartaoCreditoActivity.class).create().get();
    }

    @Test
    public void geraPagamentoEEnviaParaPagamento() {

        enableInternetConnection(true);

        activity.setPortador("TESTE");
        activity.setCPF("07032327702");
        activity.setDataValidade(DateTime.now().plusYears(1).toDate());
        activity.setNumeroCartao("4012001038443335");
        activity.setCVV("123");
        activity.onClick(activity.findViewById(R.id.salvar));

        final ShadowActivity actual = shadowOf(activity);
        final Intent next = actual.getNextStartedActivity();
        final ShadowIntent shadowIntent = shadowOf(next);
        assertEquals(AutenticacaoParaEnvioDePedidoActivity.class.getName(), shadowIntent.getComponent().getClassName());

    }

    @Test
    public void geraPagamentoESalvaParaEnviarQuandoHouverInternetDisponivel() {
        enableInternetConnection(false);

        activity.setPortador("TESTE");
        activity.setCPF("07032327702");
        activity.setDataValidade(DateTime.now().plusYears(1).toDate());
        activity.setNumeroCartao("4012001038443335");
        activity.setCVV("123");
        activity.onClick(activity.findViewById(R.id.salvar));

        final AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
        final ShadowAlertDialog shadow = shadowOf(dialog);
        assertEquals("Seu pedido foi salvo com sucesso e será enviado tão logo tenha conexão com a internet disponível.",shadow.getMessage().toString());
    }

    public void enableInternetConnection(boolean enable) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        ShadowNetworkInfo shadow = shadowOf(network);
        shadow.setConnectionStatus(enable);
        shadow.setAvailableStatus(enable);
    }
}
