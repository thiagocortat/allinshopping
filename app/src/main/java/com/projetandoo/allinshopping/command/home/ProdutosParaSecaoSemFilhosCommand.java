package com.projetandoo.allinshopping.command.home;

import com.projetandoo.allinshopping.HomeActivity;
import com.projetandoo.allinshopping.adapters.ProdutoAdapter;
import com.projetandoo.allinshopping.command.Command;

public class ProdutosParaSecaoSemFilhosCommand implements Command {

	private final HomeActivity home;

	public ProdutosParaSecaoSemFilhosCommand(final HomeActivity home) {
		this.home = home;
	}

	@Override
	public Command execute() {
		
		final ProdutoAdapter produtoadapter = new ProdutoAdapter(home,
				home.getSecao().getProdutos());
		home.getProdutos().setAdapter(produtoadapter);
		home.exibirBoneca(false);
		home.exibirListaProdutos(true);
		
		return null;
	}

}
