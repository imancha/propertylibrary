package com.propertylibrary.property;

import org.junit.Assert;
import org.junit.Test;

public class PropertyTest {

	@Test
	public void U3_S1_A() {
		Property property = new Property();

		try {
			property.calcKPR(0, 0.10, 5);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`price` tidak valid", e.getMessage().toString());
		}
	}

	@Test
	public void U3_S1_B() {
		Property property = new Property();

		try {
			property.calcKPR(100000000, 0, 5);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`interestRate` tidak valid", e.getMessage().toString
					                                                               ());
		}
	}

	@Test
	public void U3_S1_C() {
		Property property = new Property();

		try {
			property.calcKPR(100000000, 0.10, 0);
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("`numberOfYears` tidak valid", e.getMessage().toString
					                                                               ());
		}
	}

	@Test
	public void U3_S1_D() {
		Property property = new Property();

		Assert.assertEquals(2124704, (int) property.calcKPR(100000000, 0.10, 5));
	}
}
