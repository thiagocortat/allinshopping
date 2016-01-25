package com.projetandoo.allinshopping.integration.upload;

import com.google.gson.*;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.projetandoo.allinshopping.MyApplication;
import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.integration.Integration;
import com.projetandoo.allinshopping.integration.rest.PostResource;
import com.projetandoo.allinshopping.models.ItemPedido;
import com.projetandoo.allinshopping.models.Pedido;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Properties;
import java.util.ResourceBundle;

import static java.lang.String.format;

/**
 * Created by Thiago Cortat on 24/11/2014.
 */
public abstract class AbstractUpload<E> implements Upload<E> {

    Properties properties = MyApplication.loadProperties("integration.properties");
//    private final ResourceBundle bundle = ResourceBundle
//            .getBundle("integration");

    private final String username;
    private final String password;
    private final Class<E> persistentClass;
    private final String url;
    private final PostResource POST;

    public AbstractUpload(final String password, final String username) {
        this.password = password;
        this.username = username;

        final ParameterizedType type = (ParameterizedType) this.getClass()
                .getGenericSuperclass();
        this.persistentClass = (Class<E>) type.getActualTypeArguments()[0];
        this.url = properties.getProperty(this.persistentClass.getSimpleName().toLowerCase());
//        this.url = bundle.getString(this.persistentClass.getSimpleName()
//                .toLowerCase());
        this.POST = new PostResource(this.url,this.username,this.password);
    }

    public void send(final E e) throws IntegrationException {
        final String jsonmessage = toJSONString(e);
        send(jsonmessage);
    }

    protected void send(String jsonmessage) throws IntegrationException {
        POST.sendJSON(jsonmessage);
    }

    protected String toJSONString(final E e) {
        final Gson gson = new GsonBuilder()
                                .excludeFieldsWithoutExposeAnnotation()
                                .setDateFormat("yyyy-MM-dd HH:mm:ss.S z")
                                .create();
        return format("{ \""+ persistentClass.getSimpleName().toLowerCase() +"\" :%s }", gson.toJson(e));
    }
}
