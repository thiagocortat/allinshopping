package com.projetandoo.allinshopping.integration.upload;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.models.ItemPedido;
import com.projetandoo.allinshopping.models.Pedido;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.String.format;

public class UploadPedido extends AbstractUpload<Pedido>{

    public UploadPedido(String username, String password) {
        super(password, username);
    }

    @Override
    public void send(Pedido pedido) throws IntegrationException {
        try {

            JSONObject json = new JSONObject(super.toJSONString(pedido));
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                                               .create();
            JSONArray itens = new JSONArray();

            for(ItemPedido i : pedido.getItens()){
                    JSONObject item = new JSONObject(gson.toJson(i));
//                JSONObject itemName = new JSONObject();
//                itemName.put("item", item);
                itens.put(item);
//                    itens.put(format("{ \"item\" :%s }",item.toString()));//Eu acho que é neste pedaço aqui. provalemte não está escapando
////                itens.put(item);
            }

            json.getJSONObject("pedido").put("itens",itens);


            super.send(json.toString());
        } catch (JSONException e) {
            throw new IntegrationException(e.getMessage());
        }
    }
}
