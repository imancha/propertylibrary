package com.propertylibrary.base;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class Database {
	private static MongoClient mongo = new MongoClient();
	private static String name = "property";

	public static void setConnection(String connectionURI) {
		if (connectionURI == "" || connectionURI == null)
			throw new IllegalArgumentException("connectionURI tidak valid");

		try {
			Database.mongo = new MongoClient(new MongoClientURI(connectionURI));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("tidak dapat terhubung dengan " +
					                                   "database");
		}
	}

	public static void setConnection(String connectionURI, String databaseName) {
		Database.setConnection(connectionURI);
		Database.setName(databaseName);
	}

	public static void setName(String name) {
		if (name == "" || name == null)
			throw new IllegalArgumentException("nama database tidak valid");
		Database.name = name;
	}

	public static MongoDatabase getDatabase() {
		return Database.mongo.getDatabase(Database.name);
	}

	public static MongoCollection<Document> getCollection(String name) {
		return Database.getDatabase().getCollection(name);
	}
}
