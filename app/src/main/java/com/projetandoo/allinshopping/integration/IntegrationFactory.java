package com.projetandoo.allinshopping.integration;

import java.util.Hashtable;

import com.projetandoo.allinshopping.enumations.IntegrationType;

public class IntegrationFactory
{

    private static final IntegrationFactory instance = new IntegrationFactory();
    private final Hashtable<IntegrationType,Integration> integrations= new Hashtable<IntegrationType,Integration>();

    private IntegrationFactory()
    {
        
    }

    public static IntegrationFactory getInstance()
    {
        return instance;
    }

    public Integration getIntegration(IntegrationType integrationtype)
    {
        Integration obj = integrations.get(integrationtype);
		if (obj == null && integrationtype == IntegrationType.PRESTASHOP) {
			obj = new PrestashopIntegration();
			integrations.put(integrationtype, obj);
		}
		return obj;
    }

}
