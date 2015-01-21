package com.projetandoo.allinshopping.services;

import android.content.Context;

import com.projetandoo.allinshopping.models.Secao;
import com.projetandoo.allinshopping.repository.SectionRepository;

public class SecaoService {

	private SectionRepository REPOSITORY;

	public SecaoService(Context context) {
		REPOSITORY = new SectionRepository(context);
	}

	public void save(Secao secao) {

		Secao founded = REPOSITORY.getById(secao.getId());

		if (founded == null) {
			REPOSITORY.insert(secao);
		} else {
			REPOSITORY.update(founded);
		}

	}
}
