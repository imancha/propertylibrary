package com.propertylibrary.property;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.propertylibrary.base.Model;

public class Favorite {
	private final Model model;
	private final Property property;

	public Favorite() {
		this.model = new Model("Favorite");
		this.property = new Property();
	}

	public void create(final String _idProperty) {
		if (property.findOne(_idProperty) == null)
			throw new Error("Data properti tidak ditemukan.");
		if (model.find(new HashMap<String, Object>(){{
			put("property", new ObjectId(_idProperty));
		}}).isEmpty()) model.create(new HashMap<String, Object>(){{
			put("property", new ObjectId(_idProperty));
		}});
	}

	public List<Map<String, Object>> find() {
		final List<Map<String, Object>> documents = model.find();
		if (documents.size() > 0) {
			final List<Map<String, Object>> query = new ArrayList<>();
			for (int i = 0; i < documents.size(); i++) {
				final String _id = documents.get(i).get("property").toString();
				query.add(new HashMap<String, Object>(){{
					put("_id", new ObjectId(_id));
				}});
			}
			return property.find(new HashMap<String, Object>(){{
				put("$or", query);
			}});
		}
		return documents;
	}

	public void remove(String _id) {
		model.remove(_id);
	}
}
