package com.propertylibrary.base;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ModelTest {
	private final String M1 = "nama `collection` tidak valid";
	private final String M2 = "`document` tidak valid";
	private static Model model = new Model("User");

	@BeforeClass
	public static void before() {
		Database.getCollection("User").drop();
		model.create(new HashMap<String, Object>() {{
//				put("_id", "5988c15726b0446cae5405bf");
			put("name", "Jane Doe");
			put("email", "jane@email.com");
		}});
		model.create(new HashMap<String, Object>() {{
//				put("_id", "5988c15726b0446cae5405bf");
			put("name", "Foo Bar");
			put("email", "foo@email.com");
		}});
	}

	@Test
	public void U2_S1_A() {
		model = new Model();
		try {
			model.setCollection("");
		} catch (IllegalArgumentException e) {
			Assert.assertEquals(M1, e.getMessage().toString());
		}
	}

	@Test
	public void U2_S1_B() {
		model = new Model();
		try {
			model.setCollection(null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals(M1, e.getMessage().toString());
		}
	}

	@Test
	public void U2_S1_C() {
		model = new Model();
		try {
			model.setCollection("User");
		} catch (IllegalArgumentException e) {
			Assert.assertNull(e);
		}
	}

	@Test
	public void U2_S2_A() {
		model = new Model("User");

		try {
			model.create(null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals(M2, e.getMessage().toString());
		}
	}

	@Test
	public void U2_S2_B() {
		model = new Model("User");
		try {
			model.create(new HashMap<String, Object>() {{
//				put("_id", "5988c15726b0446cae5405bf");
				put("name", "John Doe");
				put("email", "john@email.com");
			}});
			Assert.assertEquals(3, Database.getCollection("User").count());
		} catch (IllegalArgumentException e) {
			Assert.assertNull(e);
		}
	}

	@Test
	public void U2_S3_A() {
		model = new Model("User");
		try {
			model.findOne("");
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`_id` tidak valid", e.getMessage().toString());
		}
	}

	@Test
	public void U2_S3_B() {
		model = new Model("User");
		try {
			model.findOne(null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`_id` tidak valid", e.getMessage().toString());
		}
	}

	@Test
	public void U2_S3_C() {
		model = new Model("User");

		Map<String, Object> document = model.findOne("5988c15726b0446cae5405be");

		Assert.assertEquals("5988c15726b0446cae5405bf", document.get("_id")
				                                                .toString());
		Assert.assertEquals("john@email.com", document.get("email").toString());
		Assert.assertEquals("John Doe", document.get("name").toString());
	}

	@Test
	public void U2_S3_D() {
		model = new Model("User");
		Map<String, Object> document = model.findOne("5988c17326b0446cd9a91486");

		Assert.assertNull(document);
	}

	@Test
	public void U2_S4_A() {
		model = new Model("User");

		Assert.assertEquals(3, model.find().size());
	}

	@Test
	public void U2_S5_A() {
		model = new Model("User");

		try {
			model.find(null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`query` tidak valid", e.getMessage().toString());
		}
	}

	@Test
	public void U2_S5_B() {
		model = new Model("User");

		Assert.assertEquals(0, model.find(new HashMap<String, Object>() {{
			put("email", "bar@email.com");
		}}).size());
	}

	@Test
	public void U2_S5_C() {
		model = new Model("User");

		Assert.assertEquals(1, model.find(new HashMap<String, Object>() {{
			put("email", "john@email.com");
		}}).size());
	}

	@Test
	public void U2_S6_A() {
		model = new Model("User");

		Assert.assertEquals(2, model.find(0, 2).size());
	}

	@Test
	public void U2_S7_A() {
		model = new Model("User");

		try {
			model.find(null, 0, 0);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`query` tidak valid", e.getMessage().toString());
		}
	}

	@Test
	public void U2_S7_B() {
		model = new Model("User");

		Assert.assertEquals(1, model.find(new HashMap<String, Object>() {{
			put("email", "jane@email.com");
		}}, 0, 1).size());
	}

	@Test
	public void U2_S8_A() {
		model = new Model("User");

		try {
			model.find(null, null, 0, 0);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`query` tidak valid", e.getMessage().toString());
		}
	}

	@Test
	public void U2_S7_C() {
		model = new Model("User");

		Assert.assertEquals(2, model.find(new HashMap<String, Object>() {{
			put("email", new HashMap<String, Object>() {{
				put("$regex", "@email.com");
			}});
		}}, new HashMap<String, Object>() {{
			put("email", 1);
		}}, 0, 2).size());
	}

	@Test
	public void U2_S8_B() {
		model = new Model("User");

		try {
			model.find(new HashMap<String, Object>() {{
				put("email", "jane@email.com");
			}}, null, 0, 0);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`sort` tidak valid", e.getMessage().toString());
		}
	}

	@Test
	public void U2_S9_A() {
		model = new Model("User");
		try {
			model.update("", null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`_id` tidak valid", e.getMessage().toString());
		}
	}

	@Test
	public void U2_S9_B() {
		model = new Model("User");
		try {
			model.update(null, null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`_id` tidak valid", e.getMessage().toString());
		}
	}

	@Test
	public void U2_S9_C() {
		model = new Model("User");
		try {
			model.update("5988e99826b0440d7331476f", null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals(M2, e.getMessage().toString());
		}
	}

	@Test
	public void U2_S9_D() {
		model = new Model("User");
		try {
			model.update("5988ec9826b0440fb380ba13", new HashMap<String, Object>() {{
				put("name", "Jane Doe");
			}});
			Assert.assertEquals("Jane Doe", model.findOne
					                                      ("5988ec9826b0440fb380ba13")
					                                .get("name").toString());
		} catch (IllegalArgumentException e) {
			Assert.assertNull(e);
		}
	}

	@Test
	public void U2_S9_E() {
		model = new Model("User");
		try {
			model.update("5988c17326b0446cd9a91486", new HashMap<String, Object>() {{
				put("name", "Jane Doe");
			}});
			Assert.assertNull(model.findOne("5988c17326b0446cd9a91486"));
		} catch (IllegalArgumentException e) {
			Assert.assertNull(e);
		}
	}

	@Test
	public void U2_S10_A() {
		model = new Model("User");
		try {
			model.remove("");
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`_id` tidak valid", e.getMessage().toString());
		}
	}

	@Test
	public void U2_S10_B() {
		model = new Model("User");
		try {
			model.remove(null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`_id` tidak valid", e.getMessage().toString());
		}
	}

	@Test
	public void U2_S10_C() {
		model = new Model("User");
		long total = Database.getCollection("User").count();

		try {
			model.remove("5988c15726b0446cae5405bf");
			Assert.assertEquals(total - 1, Database.getCollection("User").count());
		} catch (IllegalArgumentException e) {
			Assert.assertNull(e);
		}
	}

	@Test
	public void U2_S10_D() {
		model = new Model("User");
		long total = Database.getCollection("User").count();

		try {
			model.remove("5988c17326b0446cd9a91486");
			Assert.assertEquals(total, Database.getCollection("User").count());
		} catch (IllegalArgumentException e) {
			Assert.assertNull(e);
		}
	}
}
