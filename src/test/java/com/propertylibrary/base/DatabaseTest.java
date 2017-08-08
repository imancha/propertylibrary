package com.propertylibrary.base;

import org.junit.Assert;
import org.junit.Test;

public class DatabaseTest {
	private final String localhost = "http://localhost";
	private final String mongo = "mongodb://localhost";
	private final String name = "property";
	private final String M1 = "connectionURI tidak valid";
	private final String M2 = "nama database tidak valid";
	private final String M3 = "tidak dapat terhubung dengan database";

	@Test
	public void U1_S1_A() {
		try {
			Database.setConnection("");
		} catch (IllegalArgumentException e) {
			Assert.assertEquals(M1, e.getMessage().toString());
		}
	}

	@Test
	public void U1_S1_B() {
		try {
			Database.setConnection(null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals(M1, e.getMessage().toString());
		}
	}

	@Test
	public void U1_S1_C() {
		try {
			Database.setConnection(localhost);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals(M3, e.getMessage().toString());
		}
	}

	@Test
	public void U1_S1_D() {
		try {
			Database.setConnection(mongo);
		} catch (IllegalArgumentException e) {
			Assert.assertNull(e);
		}
	}

	@Test
	public void U1_S2_A() {
		try {
			Database.setConnection("", "");
		} catch (IllegalArgumentException e) {
			Assert.assertEquals(M1, e.getMessage().toString());
		}
	}

	@Test
	public void U1_S2_B() {
		try {
			Database.setConnection(null, "");
		} catch (IllegalArgumentException e) {
			Assert.assertEquals(M1, e.getMessage().toString());
		}
	}

	@Test
	public void U1_S2_C() {
		try {
			Database.setConnection(mongo, "");
		} catch (IllegalArgumentException e) {
			Assert.assertEquals(M2, e.getMessage().toString());
		}
	}

	@Test
	public void U1_S2_D() {
		try {
			Database.setConnection(mongo, null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals(M2, e.getMessage().toString());
		}
	}

	@Test
	public void U1_S2_E() {
		try {
			Database.setConnection(mongo, name);
			Assert.assertEquals(name, Database.getDatabase().getName().toString());
		} catch (IllegalArgumentException e) {
			Assert.assertNull(e);
		}
	}

	@Test
	public void U1_S3_A() {
		try {
			Database.setName("");
		} catch (IllegalArgumentException e) {
			Assert.assertEquals(M2, e.getMessage().toString());
		}
	}

	@Test
	public void U1_S3_B() {
		try {
			Database.setName(null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals(M2, e.getMessage().toString());
		}
	}

	@Test
	public void U1_S3_C() {
		try {
			Database.setName(name);
			Assert.assertEquals(name, Database.getDatabase().getName().toString());
		} catch (IllegalArgumentException e) {
			Assert.assertNull(e);
		}
	}

	@Test
	public void U1_S4_A() {
		Assert.assertNotNull(Database.getDatabase());
	}

	@Test
	public void U1_S5_A() {
		Assert.assertNotNull(Database.getCollection("User"));
	}
}
