package com.projetandoo.allinshopping.async;

import android.content.Context;

import com.projetandoo.allinshopping.MainActivity;
import com.projetandoo.allinshopping.database.DbHelper;
import com.projetandoo.allinshopping.database.DbHelperFactory;
import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.exceptions.NoUniqueRegistryException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.sql.SQLException;
import java.util.ResourceBundle;

import com.projetandoo.allinshopping.responserules.HttpEntityResponseRule;

import static org.junit.Assert.assertNotSame;

@RunWith(RobolectricTestRunner.class)
public class TestIntegrationProcesses {

	private IntegrationProcess process;
	private Context context;
	
	private final ResourceBundle integration = ResourceBundle
			.getBundle("integration");
	
	private final ResourceBundle response = ResourceBundle.getBundle("json_message");
	
	private DbHelper dbhelper;

	@Before
	public void setup() {
		
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("estado"),response.getString("estado"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cep"),response.getString("cep"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("faixapreco"),response.getString("faixa"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("secao"),response.getString("secao"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("produto"),response.getString("produto"));		
		Robolectric.getFakeHttpLayer().addHttpResponseRule(integration.getString("cliente"),response.getString("cliente"));
		Robolectric.getFakeHttpLayer().addHttpResponseRule(new HttpEntityResponseRule());
		
		this.context = Robolectric.buildActivity(MainActivity.class).create().get();
		this.process = new IntegrationProcess("teste","teste",this.context);
		dbhelper = DbHelperFactory.getDbHelper(context);
	}

	@Test
	public void testImportarEstado() throws IntegrationException, SQLException {
		process.importarEstado();
		assertNotSame(0L , dbhelper.getEstadoDao().countOf());
	}

	@Test
	public void testImportarCEP() throws IntegrationException, SQLException {
		process.importarCEP();
		assertNotSame(0L , dbhelper.getCEPDao().countOf());
	}
	
	@Test
	public void testImportarFaixaPreco() throws IntegrationException, SQLException {
		process.importarFaixaPreco();
		assertNotSame(0L , dbhelper.getFaixaPrecoDao().countOf());
	}
	
	@Test
	public void testImportarCliente() throws IntegrationException,
			NoUniqueRegistryException, SQLException {
		process.importarCliente();
		assertNotSame(0L , dbhelper.getClienteDao().countOf());
	}

	@Test
	public void testImportarProdutos() throws IntegrationException, SQLException {
		process.importarProdutos();
		assertNotSame(0L , dbhelper.getProdutoDao().countOf());
	}

	@Test
	public void testImportarSecao() throws IntegrationException, SQLException {
		process.importarSecao();
		assertNotSame(0L , dbhelper.getSecaoDao().countOf());
	}
}
