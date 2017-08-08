package com.propertylibrary.property;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PropertyTest {

	@Test
	public void U3_S2_A() {
		Property property = new Property();

		try {
			property.share(InstrumentationRegistry.getTargetContext(), null, null);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`content` tidak valid", e.getMessage());
		}
	}

	@Test
	public void U3_S2_B() {
		Property property = new Property();

		try {
			property.share(InstrumentationRegistry.getTargetContext(), "Info " +
					                                                           "Properti", null);
		} catch (IllegalArgumentException e) {
			Assert.assertNull(e);
		}
	}
}
