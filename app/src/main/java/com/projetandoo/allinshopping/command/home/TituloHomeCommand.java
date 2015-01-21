package com.projetandoo.allinshopping.command.home;

import com.projetandoo.allinshopping.HomeActivity;
import com.projetandoo.allinshopping.command.Command;

public class TituloHomeCommand implements Command {

	private final HomeActivity home;

	public TituloHomeCommand(final HomeActivity home) {
		this.home = home;
	}

	@Override
	public Command execute() {
		home.setTitulo(home.getSecao().getTitulo());
		Command command =  null;
		if (home.getSecao().getSubSecoes().isEmpty()) {				
			command = new ProdutosParaSecaoSemFilhosCommand(home);
		} else {
			command = new ProdutosParaSecaoComFilhosCommand(home);
		}
		return command.execute();
	}

}
