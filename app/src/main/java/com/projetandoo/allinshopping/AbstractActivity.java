package com.projetandoo.allinshopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Menu;
import android.view.MenuItem;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.projetandoo.allinshopping.database.DbHelper;
import com.projetandoo.allinshopping.models.Pedido;

public abstract class AbstractActivity extends OrmLiteBaseActivity<DbHelper>
{
    private Context context;
    private Pedido pedido;

    protected Pedido getPedido()
    {
		return this.pedido;
    }

	protected void setPedido(final Pedido pedido)
    {
		this.pedido = pedido;
    }
	
    public AbstractActivity() {
        super();
    }
    
    public AbstractActivity(final Context context){
        this.context = context;
    }
    
    @Override
	public boolean onCreateOptionsMenu(final Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
	public boolean onOptionsItemSelected(final MenuItem menuitem)
    {
    	Intent toStart = null; //NOPMD
        switch(menuitem.getItemId()){
        	case R.id.action_settings:
        		toStart = new Intent(this, ConfigurationActivity.class);
        		break;
        	case R.id.shopping_cart:
        		toStart = new Intent(this, ShoppingCartActivity.class);
        		break;
        	default:
        		toStart = new Intent(this,HomeActivity.class);
        		break;
        }
        startActivity(toStart);
        return super.onOptionsItemSelected(menuitem);
    }

    public boolean isNetworkConnected() {

        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info =  manager.getActiveNetworkInfo();
        if( info != null && ( info.isAvailable() || info.isConnected() ) ){
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
