package com.projetandoo.allinshopping.repository;

import com.projetandoo.allinshopping.MainActivity;
import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.integration.downloads.DownloadProdutos;
import com.projetandoo.allinshopping.models.Produto;
import com.projetandoo.allinshopping.services.ProdutoService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;
import java.util.ResourceBundle;

import static org.junit.Assert.assertNotSame;


@RunWith(RobolectricTestRunner.class)
public class TestImageRepository {

	private ImagemRepository repository;	
	private final ResourceBundle bundle = ResourceBundle.getBundle("json_message");
	private List<Produto> produtos;
	
	@Before
	public void setup() throws IntegrationException {
		
		Robolectric.getFakeHttpLayer()
				   .addPendingHttpResponse(200, bundle.getString("produto"));
        MainActivity context = Robolectric.buildActivity(MainActivity.class).create().get();
		this.repository = new ImagemRepository(context);
		final DownloadProdutos download = new DownloadProdutos("teste","teste");
		this.produtos = download.list();
		
		ProdutoService service = new ProdutoService(Robolectric.application.getApplicationContext());
		for(final Produto produto : this.produtos ){
			service.save(produto);
		}
		
	}
	
	@Test
	public void removerTodasAsImagensPorProduto() {
		
		Produto produto = this.produtos.get(0);
		repository.delete(produto);
		
		assertNotSame(Long.valueOf(produto.getImagens().size()) , repository.count());
	}

}
