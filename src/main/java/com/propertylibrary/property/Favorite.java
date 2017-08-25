package com.propertylibrary.property;

import com.propertylibrary.base.Model;
import com.propertylibrary.user.User;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Favorite {
	private final Model model;
	private final Property property;
	private final User user;

	public Favorite() {
		this.model = new Model("Favorite");
		this.property = new Property();
		this.user = new User();
	}

	/**
	 * Tambah data favorit.
	 *
	 * @param _idProperty ID dari data properti yang akan ditambahkan
	 * @throws IllegalArgumentException jika ID properti kosong atau null
	 * @throws IllegalArgumentException jika data properti tidak ditemukan
	 */
	public void create(final String _idProperty, final String _idUser) {
		if (_idProperty == "" || _idProperty == null)
			throw new IllegalArgumentException("`_idProperty` tidak valid");
		if (_idUser == "" || _idUser == null)
			throw new IllegalArgumentException("`_idUser` tidak valid");
		if (property.findOne(_idProperty) == null)
			throw new IllegalArgumentException("data properti tidak ditemukan");
		if (user.findOne(_idUser) == null)
			throw new IllegalArgumentException("data user tidak ditemukan");
		if (model.find(new HashMap<String, Object>(){{
			put("property", new ObjectId(_idProperty));
			put("user", new ObjectId(_idUser));
		}}).isEmpty()) model.create(new HashMap<String, Object>(){{
			put("property", new ObjectId(_idProperty));
			put("user", new ObjectId(_idUser));
		}});
	}

	/**
	 * Lihat daftar properti favorit.
	 *
	 * @return  seluruh data favorit
	 */
	public List<Map<String, Object>> find(final String _idUser) {
		final List<Map<String, Object>> documents = model
				                                            .find(new HashMap<String, Object>(){{
					                                            put("user", new ObjectId(_idUser));
				                                            }});
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

	/**
	 * Hapus data favorit.
	 *
	 * @param _idProperty ID dari data properti yang akan dihapus
	 * @param _idUser ID dari user pemilik data properti yang akan dihapus
	 * @throws IllegalArgumentException jika ID properti kosong atau null
	 *
	 */
	public void remove(final String _idProperty, final String _idUser) {
		List<Map<String, Object>> docs;
		if (_idProperty == "" || _idProperty == null)
			throw new IllegalArgumentException("`_idProperty` tidak valid");
		if (_idUser == "" || _idUser == null)
			throw new IllegalArgumentException("`_idUser` tidak valid");
		docs = model.find(new HashMap<String, Object>(){{
			put("property", new ObjectId(_idProperty));
			put("user", new ObjectId(_idUser));
		}});
		if (docs.size() > 0) {
			model.remove(docs.get(0).get("_id").toString());
		}
	}
}
