package com.projetandoo.allinshopping.command.home;

import com.projetandoo.allinshopping.HomeActivity;
import com.projetandoo.allinshopping.command.Command;

public class TituloHomeCommand implements Command {

	private HomeActivity home;

	public TituloHomeCommand(HomeActivity home) {
		this.home = home;
	}

	@Override
	public Command execute() {
        if (home.getSecao().getSecaoPai() == null) {
            home.setTitulo(home.getSecao().getTitulo());
            home.setActionBarSubtitle(null);
        }else {
            home.setActionBarSubtitle(home.getSecao().getTitulo());
        }

		Command command =  null;
		if (home.getSecao().getSubSecoes().isEmpty()) {				
			command = new ProdutosParaSecaoSemFilhosCommand(home);
		} else {
			command = new ProdutosParaSecaoComFilhosCommand(home);
		}
		return command.execute();
	}

}
