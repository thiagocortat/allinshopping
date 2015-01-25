package com.projetandoo.allinshopping.repository;

import com.projetandoo.allinshopping.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class TestClienteRepository {

	private ClienteRepository repository;

	@Before
	public void setup() {
        MainActivity context = Robolectric.buildActivity(MainActivity.class).create().get();
		repository = new ClienteRepository(context);
	}

	@Test
	public void test() {
		repository.list();
	}

}
