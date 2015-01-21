package com.projetandoo.allinshopping.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.androidquery.AQuery;
import com.projetandoo.allinshopping.ClienteActivity;
import com.projetandoo.allinshopping.R;
import com.projetandoo.allinshopping.models.Cliente;
import com.projetandoo.allinshopping.models.Pedido;
import com.projetandoo.allinshopping.utilities.Constante;


import java.util.List;

public class ClienteAdapter extends ArrayAdapter<Cliente>
        implements
        OnClickListener {

    private List<Cliente> clientes;
    private Pedido pedido;

    public ClienteAdapter(Context context, List<Cliente> clientes, Pedido pedido) {
        super(context, android.R.layout.simple_list_item_1, clientes);
        this.clientes = clientes;
        this.pedido = pedido;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {
        Activity activity = (Activity) getContext();
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.cliente, null);
        }

        Cliente cliente = clientes.get(i);
        Button btSecao = (Button) view.findViewById(R.id.cliente);
        btSecao.setText(cliente.getNomeCompleto());
        btSecao.setTag(cliente);
        btSecao.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Cliente cliente = (Cliente) view.getTag();
        Context context = getContext();
        Intent intent = new Intent(context, ClienteActivity.class);
        pedido.setCliente(cliente);
        intent.putExtra(Constante.PEDIDO, pedido);
        intent.putExtra(Constante.CLIENTE, cliente);
        getContext().startActivity(intent);
    }
}
