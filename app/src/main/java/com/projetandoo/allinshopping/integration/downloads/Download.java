package com.projetandoo.allinshopping.integration.downloads;

import java.util.List;

import com.projetandoo.allinshopping.exceptions.IntegrationException;

public interface Download<E>
{

	List<E> list()
        throws IntegrationException;

}
