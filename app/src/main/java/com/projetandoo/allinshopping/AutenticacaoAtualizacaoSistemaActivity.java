package com.projetandoo.allinshopping;


import com.projetandoo.allinshopping.async.IntegrationAsyncProcess;

public class AutenticacaoAtualizacaoSistemaActivity
		extends
			AbstractLogarActivity {

	@Override
	protected void doAction() {

        IntegrationAsyncProcess integrationasyncprocess = new IntegrationAsyncProcess(this);

		integrationasyncprocess.setUserName(super.getEmail())
							   .setPassword(super.getPassword())
                               .execute();

	}

    @Override
	protected void doBack() {
//		Intent pagamento = new Intent(this, ConfigurationActivity.class);
//		startActivity(pagamento);
        this.finish();
	}

    @Override
    protected int getLayout() {
        return R.layout.activity_atualizar_sistema;
    }

}
