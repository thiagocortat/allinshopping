package com.projetandoo.allinshopping.services;

import com.projetandoo.allinshopping.MainActivity;
import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.integration.downloads.DownloadProdutos;
import com.projetandoo.allinshopping.models.Produto;
import com.projetandoo.allinshopping.repository.ImagemRepository;
import com.projetandoo.allinshopping.repository.ProductRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;
import java.util.ResourceBundle;

import static org.junit.Assert.assertNotSame;

@RunWith(RobolectricTestRunner.class)
public class TestProdutoService {

	private ProdutoService service;
	
	private final ResourceBundle bundle = ResourceBundle.getBundle("json_message");
	
	private final DownloadProdutos download = new DownloadProdutos("teste","teste");

	private List<Produto> produtos;
	
	private ProductRepository repository;
	
	private ImagemRepository imageRepository;

    private MainActivity activity;
	
	@Before
	public void setup() throws IntegrationException {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
		service = new ProdutoService(activity);
		repository = new ProductRepository(activity);
		imageRepository = new ImagemRepository(activity);
		Robolectric.getFakeHttpLayer().addPendingHttpResponse(200, bundle.getString("produto"));
		this.produtos = download.list();
	}
	
	@Test
	public void save() {
		final Produto produto = produtos.get(0);		
		service.save(produto);		
		assertNotSame(0L , repository.count());
		assertNotSame(produto.getImagens().size() , imageRepository.count());
	}
	

}
