package com.propertylibrary.base;

import com.mongodb.client.MongoCursor;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Model {
	private String collection;
	private Map<String, Object> document;
	private List<Map<String, Object>> documents;

	public Model() {
		//
	}

	public Model(String collection) {
		this.setCollection(collection);
	}

	public void setCollection(String collection) {
		if (collection == null || collection == "")
			throw new IllegalArgumentException("nama `collection` tidak valid");

		this.collection = collection;
	}

	public void create(Map<String, Object> document) {
		if (document == null || document.isEmpty())
			throw new IllegalArgumentException("`document` tidak valid");

		Database.getCollection(collection)
				.insertOne(new Document(document));
	}

	public Map<String, Object> findOne(String _id) {
		if (_id == null || _id == "")
			throw new IllegalArgumentException("`_id` tidak valid");

		document = Database.getCollection(collection)
				           .find(new Document("_id", new ObjectId(_id)))
				           .first();

		return document;
	}

	public List<Map<String, Object>> find() {
		MongoCursor<Document> cursor;

		documents = new LinkedList<>();
		cursor = Database.getCollection(collection)
				         .find()
				         .iterator();

		try {
			while (cursor.hasNext()) {
				documents.add(cursor.next());
			}
		} finally {
			cursor.close();
		}

		return documents;
	}

	public List<Map<String, Object>> find(Map<String, Object> query) {
		MongoCursor<Document> cursor;

		if (query == null || query.isEmpty())
			throw new IllegalArgumentException("`query` tidak valid");

		documents = new LinkedList<>();
		cursor = Database.getCollection(collection)
				         .find(new Document(query))
				         .iterator();

		try {
			while (cursor.hasNext()) {
				documents.add(cursor.next());
			}
		} finally {
			cursor.close();
		}

		return documents;
	}

	public List<Map<String, Object>> find(int skip, int limit) {
		MongoCursor<Document> cursor;

		documents = new LinkedList<>();
		cursor = Database.getCollection(collection)
				         .find()
				         .skip(skip)
				         .limit(limit)
				         .iterator();

		try {
			while (cursor.hasNext()) {
				documents.add(cursor.next());
			}
		} finally {
			cursor.close();
		}

		return documents;
	}

	public List<Map<String, Object>> find(Map<String, Object> query,
	                                      int skip, int limit) {
		MongoCursor<Document> cursor;

		if (query == null || query.isEmpty())
			throw new IllegalArgumentException("`query` tidak valid");

		documents = new LinkedList<>();
		cursor = Database.getCollection(collection)
				         .find(new Document(query))
				         .skip(skip)
				         .limit(limit)
				         .iterator();

		try {
			while (cursor.hasNext()) {
				documents.add(cursor.next());
			}
		} finally {
			cursor.close();
		}

		return documents;
	}

	public List<Map<String, Object>> find(Map<String, Object> query,
	                                      Map<String, Object> sort,
	                                      int skip, int limit) {
		MongoCursor<Document> cursor;

		if (query == null || query.isEmpty())
			throw new IllegalArgumentException("`query` tidak valid");
		if (sort == null || sort.isEmpty())
			throw new IllegalArgumentException("`sort` tidak valid");

		documents = new LinkedList<>();
		cursor = Database.getCollection(collection)
				         .find(new Document(query))
				         .sort(new Document(sort))
				         .skip(skip)
				         .limit(limit)
				         .iterator();

		try {
			while (cursor.hasNext()) {
				documents.add(cursor.next());
			}
		} finally {
			cursor.close();
		}

		return documents;
	}

	public void update(String _id, Map<String, Object> document) {
		if (_id == null || _id == "")
			throw new IllegalArgumentException("`_id` tidak valid");
		if (document == null || document.isEmpty())
			throw new IllegalArgumentException("`document` tidak valid");

		Database.getCollection(collection)
				.updateOne(
						new Document("_id", new ObjectId(_id)),
						new Document("$set", document)
				);
	}

	public void remove(String _id) {
		if (_id == null || _id == "")
			throw new IllegalArgumentException("`_id` tidak valid");

		Database.getCollection(collection)
				.deleteOne(new Document("_id", new ObjectId(_id)));
	}
}
