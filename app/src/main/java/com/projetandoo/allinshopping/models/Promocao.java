// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 
// Source File Name:   Promocao.java

package com.projetandoo.allinshopping.models;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Promocao
    implements Serializable
{

    private static final long serialVersionUID = 1L;
    @DatabaseField(generatedId=true)
    private Long id;
    @DatabaseField(foreign=true)
    private Produto produto;
    @DatabaseField(foreign=true)
    private Secao secao;

    public Promocao()
    {
    }

    public Promocao(Long long1, Produto produto1)
    {
        this();
        id = long1;
        produto = produto1;
    }

    public Long getId()
    {
        return id;
    }

    public Produto getProduto()
    {
        return produto;
    }

    public Secao getSecao()
    {
        return secao;
    }

    public void setProduto(Produto produto1)
    {
        produto = produto1;
    }

    public void setSecao(Secao secao1)
    {
        secao = secao1;
    }
}
