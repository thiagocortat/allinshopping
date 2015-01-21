package com.projetandoo.allinshopping.commandfactory;

import android.app.Activity;

import com.projetandoo.allinshopping.HomeActivity;
import com.projetandoo.allinshopping.command.Command;
import com.projetandoo.allinshopping.command.home.HomeCommand;
import com.projetandoo.allinshopping.command.home.TituloHomeCommand;

class ProdutosCommandFactory extends CommandFactory{

	private HomeActivity home;

	public ProdutosCommandFactory(Activity activity) {
		this.home = (HomeActivity) activity;
	}

	@Override
	public Command createCommand() {
		if (home.getSecao() == null) {
			return new HomeCommand(home);
		} else {			
			return new TituloHomeCommand(home);
		}
	}
	
	

}
