package com.projetandoo.allinshopping;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.androidquery.AQuery;
import com.projetandoo.allinshopping.alerts.ErrorAlert;

public abstract class AbstractLogarActivity extends AbstractActivity
		implements
			OnClickListener {

	private AQuery aq;

	public AbstractLogarActivity() {
		super();
	}

	private boolean isValid() {

		List<String> messages = new ArrayList<String>();

		if (GenericValidator.isBlankOrNull(this.getEmail())) {
			messages.add("E-mail é obrigatória");
		} else if (!GenericValidator.isEmail(this.getEmail())) {
			messages.add("E-mail é inválido");
		}

		if (GenericValidator.isBlankOrNull(this.getPassword())) {
			messages.add("Senha é obrigatória");
		}
		if (!messages.isEmpty()) {
			new ErrorAlert(this).setTitle("Pedido").setMessages(messages)
					.show();
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public void onClick(View view) {

		if (view.getId() == R.id.logar) {

				if( isValid() ) {
					this.doAction();
				}

		}
        else {
			this.doBack();
		}

	}

	protected abstract void doAction();
	
	protected abstract void doBack();

    protected abstract int getLayout();

    protected AQuery getAquery() {
        return aq;
    }

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(getLayout());
		aq = new AQuery(this);
		aq.id(R.id.logar).clicked(this);
	}

	public String getEmail() {
		return aq.id(R.id.email).getText().toString();
	}

	public String getPassword() {
		return aq.id(R.id.password).getText().toString();
	}

    public void setEmail(String email) {
        aq.id(R.id.email).text(email);
    }

    public void setPassword(String password) {
        aq.id(R.id.password).text(password);
    }

    @Override
    public void onBackPressed() {
        this.doBack();
    }
}