package com.propertylibrary.property;

import com.propertylibrary.base.Database;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class FavoriteTest {
	private static Favorite favorite;
	private static Property property = new Property();

	public static void before() {
		Database.getCollection("Property").drop();
		property.create(new HashMap<String, Object>() {{
			put("title", "Wisma Tubagus Ismail");
			put("address", "Jalan tubagus ismail");
		}});
		property.create(new HashMap<String, Object>() {{
			put("title", "Pondok Dago Lestari");
			put("address", "Jalan dago lestari");
		}});
	}

	@Test
	public void U4_S1_A() {
		favorite = new Favorite();
		try {
			favorite.create(null, null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`_idProperty` tidak valid", e.getMessage());
		}
	}

	@Test
	public void U4_S1_B() {
		favorite = new Favorite();
		try {
			favorite.create("59817a9eddceff1310b05691", "59817a9eddceff1310b05691");
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("data properti tidak ditemukan", e.getMessage());
		}
	}

	@Test
	public void U4_S1_C() {
		favorite = new Favorite();
		try {
			favorite.create("598922880166fb14303ca476", "59817a9eddceff1310b05691");
			Assert.assertNotNull(
					Database.getCollection("Favorite")
							.find(new Document("property", new ObjectId("598922880166fb14303ca476")))
							.first()
			);
		} catch (IllegalArgumentException e) {
			Assert.assertNull(e);
		}
	}

	@Test
	public void U4_S2_A() {
		favorite = new Favorite();
		Assert.assertEquals(1, favorite.find("59817a9eddceff1310b05691").size());
	}

	@Test
	public void U4_S3_A() {
		favorite = new Favorite();
		try {
			favorite.remove("", "");
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`_idProperty` tidak valid", e.getMessage());
		}
	}

	@Test
	public void U4_S3_B() {
		favorite = new Favorite();
		try {
			favorite.remove(null, null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`_idProperty` tidak valid", e.getMessage());
		}
	}

	@Test
	public void U4_S3_C() {
		favorite = new Favorite();
		long total = Database.getCollection("Favorite").count();

		try {
			favorite.remove("598923560166fb14fdde52a8", "59817a9eddceff1310b05691");
			Assert.assertEquals(total - 1, Database.getCollection("Favorite").count());
		} catch (IllegalArgumentException e) {
			Assert.assertNull(e);
		}
	}

	@Test
	public void U2_S10_D() {
		favorite = new Favorite();
		long total = Database.getCollection("Favorite").count();

		try {
			favorite.remove("598a5722a988c182c69eb843", "59817a9eddceff1310b05691");
			Assert.assertEquals(total, Database.getCollection("Favorite").count());
		} catch (IllegalArgumentException e) {
			Assert.assertNull(e);
		}
	}
}
