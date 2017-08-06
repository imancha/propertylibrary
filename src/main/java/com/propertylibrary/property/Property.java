package com.propertylibrary.property;

import com.propertylibrary.base.Model;

public class Property extends Model {
	public Property() {
		super();
		super.setCollection("Property");
	}

	public double calculateKPR(double price, double interestRate, int numberOfYears) {
		if (!(price > 0)) throw new Error("invalid price.");
		if (!(interestRate > 0)) throw new Error("invalid interestRate.");
		if (!(numberOfYears > 0)) throw new Error("invalid numberOfYears.");
		return price * (interestRate / 12) * (1 / (1 - (1 / (Math.pow(1 + (interestRate / 12),
				numberOfYears / 12)))));
	}
}
