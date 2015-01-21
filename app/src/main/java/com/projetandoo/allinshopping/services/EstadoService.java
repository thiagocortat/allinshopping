package com.projetandoo.allinshopping.services;

import android.content.Context;

import com.projetandoo.allinshopping.models.Estado;
import com.projetandoo.allinshopping.repository.EstadoRepository;

public class EstadoService {

	private EstadoRepository repository;

	public EstadoService(Context context) {
		repository = new EstadoRepository(context);
	}

	public void save(Estado estado) {

        Estado saved = repository.findByUF(estado.getUf());
        if (saved == null) {
            repository.insert(estado);
        } else {
            repository.update(estado);
        }

	}

}
