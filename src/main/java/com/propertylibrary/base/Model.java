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

	/**
	 * @param collection  nama collection
	 * @throws IllegalArgumentException jika nama collection kosong atau null
	 */
	public Model(String collection) {
		this.setCollection(collection);
	}

	/**
	 * @param collection  nama collection
	 * @throws IllegalArgumentException jika nama collection kosong atau null
	 */
	public void setCollection(String collection) {
		if (collection == null || collection == "")
			throw new IllegalArgumentException("nama `collection` tidak valid");

		this.collection = collection;
	}

	/**
	 * @param document  data yang akan disimpan
	 * @throws IllegalArgumentException  jika data yang disimpan kosong atau null
	 */
	public void create(Map<String, Object> document) {
		if (document == null || document.isEmpty())
			throw new IllegalArgumentException("`document` tidak valid");

		Database.getCollection(collection)
				.insertOne(new Document(document));
	}

	/**
	 * @param _id ID dari data pada collection
	 * @throws IllegalArgumentException jika ID kosong atau null
	 * @return  data pada collection sesuai ID
	 */
	public Map<String, Object> findOne(String _id) {
		if (_id == null || _id == "")
			throw new IllegalArgumentException("`_id` tidak valid");

		document = Database.getCollection(collection)
				           .find(new Document("_id", new ObjectId(_id)))
				           .first();

		return document;
	}

	/**
	 * @return  seluruh data pada collection
	 */
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

	/**
	 * @param query kriteria pencarian
	 * @throws IllegalArgumentException jika kriteria pencarian kosong atau null
	 * @return  data pada collection sesuai kriteria
	 */
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

	/**
	 * @param skip  jumlah skip dari data pada collection
	 * @param limit jumlah limit dari data pada collection
	 * @return      sejumlah data pada collection
	 */
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

	/**
	 * @param query kriteria pencarian
	 * @param skip  jumlah skip dari data pada collection
	 * @param limit jumlah limit dari data pada collection
	 * @throws IllegalArgumentException jika kriteria pencarian kosong atau null
	 * @return      sejumlah data pada collection
	 */
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

	/**
	 * @param query kriteria pencarian
	 * @param sort  kriteria pengurutan data
	 * @param skip  jumlah skip dari data pada collection
	 * @param limit jumlah limit dari data pada collection
	 * @throws IllegalArgumentException jika kriteria pencarian kosong atau null
	 * @return  sejumlah data pada collection
	 */
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

	/**
	 * @param _id ID dari data pada collection yang akan diubah
	 * @param document  data yang digunakan untuk perubahan data
	 * @throws IllegalArgumentException jika ID kosong atau null
	 * @throws IllegalArgumentException jika data ubah kosong atau null
	 */
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

	/**
	 * @param _id ID dari data pada collection yang akan dihapus
	 * @throws IllegalArgumentException jika ID kosong atau null
	 */
	public void remove(String _id) {
		if (_id == null || _id == "")
			throw new IllegalArgumentException("`_id` tidak valid");

		Database.getCollection(collection)
				.deleteOne(new Document("_id", new ObjectId(_id)));
	}
}
