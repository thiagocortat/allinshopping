package com.projetandoo.allinshopping.command.home;

import java.util.ArrayList;
import java.util.List;

import com.projetandoo.allinshopping.HomeActivity;
import com.projetandoo.allinshopping.R;
import com.projetandoo.allinshopping.adapters.SecaoAdapter;
import com.projetandoo.allinshopping.command.Command;
import com.projetandoo.allinshopping.models.Secao;

public class ProdutosParaSecaoComFilhosCommand implements Command {
	
	private Secao TODOS = new Secao("Todos");
	
	private HomeActivity home;

	public ProdutosParaSecaoComFilhosCommand(HomeActivity home) {
		this.home = home;
	}

	@Override
	public Command execute() {

		List<Secao> secoes = new ArrayList<Secao>(
				home.getSecao().getSubSecoes());
		TODOS.setSecaoPai(home.getSecao().getSecaoPai());
		TODOS.addProdutos(home.getSecao().getProdutos());
		secoes.add(TODOS);

		home.getSecoes().setAdapter(new SecaoAdapter(home, secoes));
        home.exibirBoneca(true);
		home.setBoneca(R.drawable.lista_produtos);
		
		return null;
	}

}
