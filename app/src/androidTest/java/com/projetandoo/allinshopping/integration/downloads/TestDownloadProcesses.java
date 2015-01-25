package com.projetandoo.allinshopping.integration.downloads;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public abstract class TestDownloadProcesses {

	@Before
	public void setup() {
		Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
	}


}
