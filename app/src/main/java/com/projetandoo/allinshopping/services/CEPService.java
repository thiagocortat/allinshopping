package com.projetandoo.allinshopping.services;

import java.sql.SQLException;

import android.content.Context;
import android.util.Log;

import com.projetandoo.allinshopping.models.CEP;
import com.projetandoo.allinshopping.models.Estado;
import com.projetandoo.allinshopping.models.FaixaPreco;
import com.projetandoo.allinshopping.repository.CEPRepository;
import com.projetandoo.allinshopping.repository.EstadoRepository;
import com.projetandoo.allinshopping.repository.FaixaPrecoRepository;

public class CEPService {

	private CEPRepository CEP_REPOSITORY;
	private FaixaPrecoRepository PRECO_REPOSITORY;
	private EstadoRepository ESTADO_REPOSITORY;
	private Estado estado;
	
	public CEPService(Context context) {
		CEP_REPOSITORY = new CEPRepository(context);
		PRECO_REPOSITORY = new FaixaPrecoRepository(context);
		ESTADO_REPOSITORY = new EstadoRepository(context);
		this.estado = ESTADO_REPOSITORY.findByUF("RJ");
	}

	public void save(CEP cep) {

		if (CEP_REPOSITORY.exists(cep)) {
			CEP_REPOSITORY.update(cep);		
			
		} else {
			CEP_REPOSITORY.insert(cep);			
		}
	
	}
	
	public void save(FaixaPreco preco) {
		
		if( PRECO_REPOSITORY.isExist(preco) ) {
			PRECO_REPOSITORY.update(preco);
		} else {
			PRECO_REPOSITORY.insert(preco);
		}
		
		
	}

	public FaixaPreco getFaixaPrecoByCEPDestino(Long cep) {
		try {
			Log.d("com.projetandoo.allinshopping", String.format("pesquisando o frete para o destino %s",cep));
			
			
			CEP destino = CEP_REPOSITORY.findCepByZipCode(cep);
			return PRECO_REPOSITORY.findFaixaPrecoByCEPAndPeso(estado, destino);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
