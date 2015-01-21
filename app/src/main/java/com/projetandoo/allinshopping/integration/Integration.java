package com.projetandoo.allinshopping.integration;

import com.projetandoo.allinshopping.enumations.ResourceType;
import com.projetandoo.allinshopping.integration.downloads.Download;
import com.projetandoo.allinshopping.integration.upload.Upload;

public interface Integration
{

	@SuppressWarnings("rawtypes")
	Download getDownload(ResourceType resourcetype);

	Upload getUpload(ResourceType resourcetype);

	Integration setUserName(String username);

	Integration setPassword(String password);
}
