package com.projetandoo.allinshopping.models;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.projetandoo.allinshopping.utilities.Constante;

import java.io.Serializable;
import java.math.BigDecimal;

@DatabaseTable
public class ItemPedido
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId = true)
    private Long id = 0L;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Pedido pedido;

    @Expose
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Produto produto;

    @Expose
    @DatabaseField
    private Long quantidade;

    @Expose
    @DatabaseField(foreign = true)
    private Atributo atributo;

    public ItemPedido() {
    }

    public ItemPedido(Produto produto, Atributo atributo, Pedido pedido) {
        this();
        this.produto = produto;
        this.quantidade = 1L;
        this.atributo = atributo;
        this.pedido = pedido;
    }

    public void aumentar() {
        quantidade++;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ItemPedido) {
            ItemPedido other = (ItemPedido) obj;
            if (other.getProduto().equals(this.getProduto())) {
                if ((other.getAtributo() == null && this.getAtributo() == null) ||
                        (other.getAtributo().equals(this.getAtributo()))) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public Atributo getAtributo() {
        return atributo;
    }

    public BigDecimal getTotal() {
        return produto.getPreco()
                .multiply(new BigDecimal(quantidade))
                .add(getMargemProjetandoo())
                .add(getMargemTI())
                .add(getMargemGestor())
                .add(getMargemVendedoras())
                .add(getMargemLider())
                .divide(Constante.PERCENTUAL_GATEWAY_PAGAMENTO, 2, BigDecimal.ROUND_UP)
                .add(Constante.TAXA_GATEWAY_PAGAMENTO);
    }

    public BigDecimal getMargemGestor() {
        return produto.getMargemGestor().multiply(new BigDecimal(quantidade));
    }

    public BigDecimal getMargemLider() {
        return produto.getMargemLider().multiply(new BigDecimal(quantidade));
    }

    public BigDecimal getMargemTI() {
        return produto.getMargemTI().multiply(new BigDecimal(quantidade));
    }

    public BigDecimal getMargemVendedoras() {
        return produto.getMargemVendedoras().multiply(new BigDecimal(quantidade));
    }

    public BigDecimal getMargemProjetandoo() {
        return produto.getMargemProjetandoo().multiply(new BigDecimal(quantidade));
    }

    public BigDecimal getTotalLiquido() {
        return produto.getPreco()
                .multiply(new BigDecimal(quantidade));
    }


    public int hashCode() {
        return produto.hashCode() + (this.getAtributo() != null ? this.getAtributo().hashCode() : 0);
    }

    public void setPedido(Pedido pedido1) {
        pedido = pedido1;
    }

    public void setQuantidade(Long long1) {
        quantidade = long1;
    }

    public BigDecimal getAkatus() {

        return this.getTotal().subtract(produto.getPreco()
                .multiply(new BigDecimal(quantidade))
                .add(this.getMargemGestor())
                .add(this.getMargemProjetandoo())
                .add(this.getMargemTI())
                .add(this.getMargemVendedoras()));
    }
}
