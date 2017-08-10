package com.propertylibrary.property;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;

import com.propertylibrary.base.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Property extends Model {
	public Property() {
		super();
		super.setCollection("Property");
	}

	/**
	 * Hitung kalkulasi KPR.
	 *
	 * @param price total harga atau biaya
	 * @param interestRate  besaran bunga
	 * @param numberOfYears jumlah tahun
	 * @throws IllegalArgumentException jika jumlah biaya, bunga atau tahun
	 * tidak lebih besar dari 0
	 * @return  total biaya per bulan
	 */
	public double calcKPR(double price, double interestRate, int numberOfYears) {
		if (!(price > 0)) throw new IllegalArgumentException("`price` tidak valid");
		if (!(interestRate > 0)) throw new IllegalArgumentException("`interestRate` tidak valid");
		if (!(numberOfYears > 0)) throw new IllegalArgumentException("`numberOfYears` tidak valid");
		return Math.round(price * (interestRate / 12) * (1 / (1 - (1 / (Math.pow(1 + (interestRate / 12), numberOfYears * 12))))));
	}

	/**
	 * Bagikan info properti.
	 *
	 * @param context konteks dari aplikasi
	 * @param content isi atau konten untuk dibagikan
	 * @param exclusions daftar package yang tidak diinginkan
	 */
	public void share(Context context, String content, String[] exclusions) {
		if (content == "" || content == null)
			throw new IllegalArgumentException("`content` tidak valid");
		if (exclusions == null)
			exclusions = new String[0];

		Intent intent = new Intent();

		intent.setAction(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, content);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		if (intent.resolveActivity(context.getPackageManager()) != null)
			context.startActivity(generateCustomChooser(context, intent, exclusions));
	}

	private static Intent generateCustomChooser(Context context, Intent prototype,
	                                            String[] forbiddenChoices) {
		List<Intent> targetedShareIntents = new ArrayList<Intent>();
		List<HashMap<String, String>> intentMetaInfo = new ArrayList<HashMap<String, String>>();
		Intent chooserIntent = Intent.createChooser(prototype, "Share Property");
		chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Intent dummy = new Intent(prototype.getAction());
		dummy.setType(prototype.getType());
		dummy.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		dummy.putExtra(Intent.EXTRA_TEXT, prototype.getStringExtra(Intent.EXTRA_TEXT));
		dummy.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(dummy, 0);

		if (!resInfo.isEmpty()) {
			for (ResolveInfo resolveInfo : resInfo) {
				if (resolveInfo.activityInfo == null || Arrays.asList(forbiddenChoices).contains(resolveInfo.activityInfo.packageName))
					continue;

				HashMap<String, String> info = new HashMap<String, String>();
				info.put("packageName", resolveInfo.activityInfo.packageName);
				info.put("className", resolveInfo.activityInfo.name);
				info.put("simpleName", String.valueOf(resolveInfo.activityInfo.loadLabel(context.getPackageManager())));
				intentMetaInfo.add(info);
			}

			if (!intentMetaInfo.isEmpty()) {
				// sorting for nice readability
				Collections.sort(intentMetaInfo, new Comparator<HashMap<String, String>>() {
					@Override
					public int compare(HashMap<String, String> map, HashMap<String, String> map2) {
						return map.get("simpleName").compareTo(map2.get("simpleName"));
					}
				});

				// create the custom intent list
				for (HashMap<String, String> metaInfo : intentMetaInfo) {
					Intent targetedShareIntent = (Intent) prototype.clone();
					targetedShareIntent.setPackage(metaInfo.get("packageName"));
					targetedShareIntent.setClassName(metaInfo.get("packageName"), metaInfo.get("className"));
					targetedShareIntents.add(targetedShareIntent);
					targetedShareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				}

				chooserIntent = Intent.createChooser(targetedShareIntents.remove(targetedShareIntents.size() - 1), "Share Property");
				chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));

				return chooserIntent;
			}
		}

		return chooserIntent;
	}
}
