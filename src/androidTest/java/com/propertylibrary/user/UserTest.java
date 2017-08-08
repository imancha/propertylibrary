package com.propertylibrary.user;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserTest {
	@Test
	public void U5_S1_A() {
		User user = new User();

		try {
			user.email(InstrumentationRegistry.getTargetContext(), null, null, null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`email` tidak valid", e.getMessage());
		}
	}

	@Test
	public void U5_S1_B() {
		User user = new User();

		try {
			user.email(InstrumentationRegistry.getTargetContext(),
					"imancha_266@ymail.com",
					null, null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`subject` tidak valid", e.getMessage());
		}
	}

	@Test
	public void U5_S1_C() {
		User user = new User();

		try {
			user.email(InstrumentationRegistry.getTargetContext(),
					"imancha_266@ymail.com", "Info Property", null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`content` tidak valid", e.getMessage());
		}
	}

	@Test
	public void U5_S1_D() {
		User user = new User();

		try {
			user.email(InstrumentationRegistry.getTargetContext(),
					"imancha_266@ymail.com", "Info Property", "Konfirmasi harga " +
							                                          "properti");
		} catch (IllegalArgumentException e) {
			Assert.assertNull(e);
		}
	}

	@Test
	public void U5_S2_A() {
		User user = new User();

		try {
			user.call(InstrumentationRegistry.getTargetContext(), null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`phone` tidak valid", e.getMessage());
		}
	}

	@Test
	public void U5_S2_B() {
		User user = new User();

		try {
			user.call(InstrumentationRegistry.getTargetContext(), "085399865986");
		} catch (IllegalArgumentException e) {
			Assert.assertNull(e);
		}
	}
}
