package com.projetandoo.allinshopping.command.home;

import java.sql.SQLException;

import com.projetandoo.allinshopping.HomeActivity;
import com.projetandoo.allinshopping.R;
import com.projetandoo.allinshopping.adapters.SecaoAdapter;
import com.projetandoo.allinshopping.command.Command;
import com.projetandoo.allinshopping.repository.SectionRepository;

public class HomeCommand implements Command {
	
	private HomeActivity home;
	private SectionRepository REPOSITORY;

	public HomeCommand(HomeActivity home) {
		this.home = home;
		REPOSITORY = new SectionRepository(home);
	}

	@Override
	public Command execute() {
		try {

			home.getSecoes().setAdapter(new SecaoAdapter(home, REPOSITORY
					.getSections()));
			home.exibirBoneca(true);
			home.setBoneca(R.drawable.lista_secoes);
			return null;
		} catch (SQLException sqlexception) {
			throw new RuntimeException(sqlexception);
		}
	}

}
