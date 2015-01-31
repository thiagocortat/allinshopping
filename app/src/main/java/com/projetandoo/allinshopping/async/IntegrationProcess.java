package com.projetandoo.allinshopping.async;

import android.content.Context;
import android.util.Log;

import com.projetandoo.allinshopping.enumations.IntegrationType;
import com.projetandoo.allinshopping.enumations.ResourceType;
import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.exceptions.NoUniqueRegistryException;
import com.projetandoo.allinshopping.integration.Integration;
import com.projetandoo.allinshopping.integration.IntegrationFactory;
import com.projetandoo.allinshopping.models.*;
import com.projetandoo.allinshopping.repository.FormaPagamentoRepository;
import com.projetandoo.allinshopping.services.*;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IntegrationProcess {

    /*
     * Gets the number of available cores (not always the same as the maximum number of cores)
     */
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int MAX_POOL_SIZE = 10;
    // Sets the amount of time an idle thread waits before terminating
    private static final int KEEP_ALIVE_TIME = 1;
    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

	private Context context;
	private Integration integration;
//	private final ResourceBundle bundle = ResourceBundle
//			.getBundle("configuration");
	private String username;
	private String password;

	public Context getContext() {
		return context;
	}

	public IntegrationProcess() {
		this.integration = IntegrationFactory.getInstance().getIntegration(
				IntegrationType.PRESTASHOP);

	}

	public IntegrationProcess(String username, String password,Context context) {
		this();
		this.username = username;
		this.password = password;
		this.integration.setUserName(username)
						.setPassword(password);
        this.context = context;
	}

	@SuppressWarnings("unchecked")
	
	public void importarEstado() throws IntegrationException { 
		EstadoService service = new EstadoService(context);
		List<Estado> estados = integration.getDownload(
				ResourceType.ESTADOS).list();
		for (Estado estado : estados) {
			service.save(estado);
		}
	}

	public void enviarPedido() throws Exception {
        (new PedidoService(context)).send();
    }

	@SuppressWarnings("unchecked")
	public void importarCEP() throws IntegrationException { 

		List<CEP> ceps = integration.getDownload(ResourceType.CEP)
				.list();
		CEPService service = new CEPService(context);
		for (CEP cep : ceps) { 
			service.save(cep);
		}
	}

    public void importarFormasPagamento() throws IntegrationException {
        List<FormaPagamento> formas = integration.getDownload(ResourceType.FORMA_PAGAMENTO).list();
        FormaPagamentoRepository repository = new FormaPagamentoRepository(context);
        for(FormaPagamento formaPagamento : formas ){
            FormaPagamento existed = repository.getById(formaPagamento.getId());
            if (existed != null) {
                repository.delete(existed);
            }
            repository.insert(formaPagamento);
        }
    }

	@SuppressWarnings("unchecked")
	public void importarFaixaPreco() throws IntegrationException { 

		List<FaixaPreco> faixas = integration.getDownload(
				ResourceType.FAIXA_PRECO).list();
		CEPService service = new CEPService(context);
		for (FaixaPreco faixa : faixas) {
			service.save(faixa);
		}

	}

	@SuppressWarnings("unchecked")
	public void importarCliente() throws IntegrationException,
			NoUniqueRegistryException { 
	    ClienteService clienteservice = new ClienteService(context);
		List<Cliente> clientes = integration.getDownload(
				ResourceType.CLIENTE).list();

		for (Cliente cliente : clientes) {
			clienteservice.save(cliente);
		}
	}

	@SuppressWarnings("unchecked")
	public void importarProdutos() throws IntegrationException { 
		ProdutoService produtoservice = new ProdutoService(context);
		produtoservice.removeAll();

		List<Produto> produtos = integration.getDownload(
				ResourceType.PRODUTOS).list();

		final BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>();
		ThreadPoolExecutor t = new ThreadPoolExecutor(
                                    NUMBER_OF_CORES,       // Initial pool size
                                    MAX_POOL_SIZE,       // Max pool size
                                    KEEP_ALIVE_TIME,
                                    TimeUnit.SECONDS,
                                    blockingQueue);

		for (Produto produto : produtos) {
			t.execute(new IntegrationProdutoRunnable(produto, produtoservice));
		}

        // wait for all of the executor threads to finish
		do {
			if (t.getActiveCount() == 0
					&& t.getCompletedTaskCount() == produtos.size()) {
				t.shutdown();
				break;
			}
		} while (true);

        try {
            while (!t.awaitTermination(60, TimeUnit.SECONDS)) {
                Log.i("IntegrationProcess", "Awaiting completion of threads.");
            }
        } catch (InterruptedException ex) {
            t.shutdownNow();
            Thread.currentThread().interrupt();
        }
	}

	@SuppressWarnings("unchecked")
	public void importarSecao() throws IntegrationException { 

		SecaoService secaoservice = new SecaoService(context);
		List<Secao> secoes = integration.getDownload(ResourceType.SECOES)
				.list();
		for (Secao secao : secoes) {
			secaoservice.save(secao);
		}
	}

    public void setContext(Context context) {
        this.context = context;
    }
}
