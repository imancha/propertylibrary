package com.propertylibrary.user;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.propertylibrary.base.Model;

public class User extends Model {
	public User() {
		super();
		super.setCollection("User");
	}

	public void email(Context context, String email, String subject, String content) {
		if (email == "" || email == null)
			throw new Error("email is required.");

		if (content == "" || content == null)
			throw new Error("content is required.");

		Intent intent = new Intent();

		intent.setAction(Intent.ACTION_SENDTO);
		intent.setData(Uri.parse("mailto:" + email));
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, content);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		if (intent.resolveActivity(context.getPackageManager()) != null)
			context.startActivity(intent);
	}

	/**
	 * @param context
	 * @param phone
	 * @throws Exception
	 */
	public void call(Context context, String phone) throws
			Exception {
		if (phone == "" || phone == null)
			throw new Exception("phone is required.");

		Intent intent = new Intent();

		intent.setAction(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:" + phone));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		if (intent.resolveActivity(context.getPackageManager()) != null)
			context.startActivity(intent);
	}
}
