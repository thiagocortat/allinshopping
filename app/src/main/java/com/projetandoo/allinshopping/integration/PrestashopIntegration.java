package com.projetandoo.allinshopping.integration;

import com.projetandoo.allinshopping.enumations.ResourceType;
import com.projetandoo.allinshopping.integration.downloads.*;
import com.projetandoo.allinshopping.integration.upload.Upload;
import com.projetandoo.allinshopping.integration.upload.UploadPedido;

class PrestashopIntegration
    implements Integration
{

	private String username;
	
	private String password;
	
     @Override
	public Download getDownload(ResourceType resourcetype)
    {
        
        Object obj = null;
        if (resourcetype == ResourceType.CEP){
            obj = new DownloadCEP(this.username,this.password);
        } else if (resourcetype == ResourceType.SECOES) {
           obj = new DownloadSecoes(this.username,this.password);
        } else if (resourcetype == ResourceType.CLIENTE) {
           obj = new DownloadCliente(this.username,this.password);
        } else if (resourcetype == ResourceType.PRODUTOS){
           obj = new DownloadProdutos(this.username,this.password);
        } else if( resourcetype == ResourceType.ESTADOS ) {
        	obj = new DownloadEstado(this.username,this.password);
        } else if ( resourcetype == ResourceType.FAIXA_PRECO ){
        	obj = new DownloadFaixaPreco(this.username,this.password);
        } else if ( resourcetype == ResourceType.FORMA_PAGAMENTO ){
            obj = new DownloadFormasPagamento(this.username,this.password);
        }
        
        return ((Download) (obj));
    }

    @Override
	public Upload getUpload(ResourceType resourcetype)
    {   
        Object obj = null;
        if (resourcetype == ResourceType.PEDIDO) {
            obj = new UploadPedido(this.username,this.password);
        } 
        return ((Upload) (obj));
    }

	@Override
	public Integration setUserName(String username) {
		this.username = username;
		return this;
	}

	@Override
	public Integration setPassword(String password) {
		this.password = password;
		return this;
	}
}
