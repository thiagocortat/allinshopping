package com.projetandoo.allinshopping.services;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.misc.TransactionManager;
import com.projetandoo.allinshopping.exceptions.IntegrationException;
import com.projetandoo.allinshopping.exceptions.NoUniqueRegistryException;
import com.projetandoo.allinshopping.models.Cliente;
import com.projetandoo.allinshopping.models.Endereco;
import com.projetandoo.allinshopping.repository.ClienteRepository;
import com.projetandoo.allinshopping.repository.EnderecoRepository;

public class ClienteService {

	private ClienteRepository CLIENTE_DAO;
	private EnderecoRepository ENDERECO_DAO;

	public ClienteService(Context context) {
		CLIENTE_DAO = new ClienteRepository(context);
		ENDERECO_DAO = new EnderecoRepository(context);
	}

	public boolean exists(Cliente cliente) {
		return CLIENTE_DAO.exists(cliente);
	}

	public List<Cliente> listToBeSend() throws IntegrationException {
		return CLIENTE_DAO.listToBeSend();
	}

    public List<Endereco> listAddress() throws IntegrationException {
        return ENDERECO_DAO.getAll();
    }

	public Cliente save(Cliente cliente) throws NoUniqueRegistryException {
        if (!CLIENTE_DAO.exists(cliente)
                && (cliente.getId() == null || cliente.getId() == 0L)) {

            ENDERECO_DAO.insert(cliente.getEndereco());
            CLIENTE_DAO.insert(cliente);

        }
        else if (cliente.getId() != null && cliente.getId() > 0L) {
            this.update(cliente);
        }
        else if (cliente.getBackofficeId() != null && cliente.getBackofficeId() > 0L) {
            this.update(cliente);
		}
        else {
			throw new NoUniqueRegistryException(
					"Não foi possível salvar o cliente. Já existe outro cadastrado com o mesmo CPF ou E-mail");
		}

		return cliente;
	}

	public void update(Cliente cliente) {

            ENDERECO_DAO.update(cliente.getEndereco());
            CLIENTE_DAO.update(cliente);

	}
}
