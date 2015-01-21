package com.projetandoo.allinshopping.commandfactory;

import android.app.Activity;

import com.projetandoo.allinshopping.HomeActivity;
import com.projetandoo.allinshopping.command.Command;

public abstract class CommandFactory {
	
	public static CommandFactory getFactory(Activity activity) {
		if( activity instanceof HomeActivity ){
			return new ProdutosCommandFactory(activity);
		} else {
			return null;
		}
	}
	
	public abstract Command createCommand();
}
