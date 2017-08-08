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
			throw new IllegalArgumentException("`email` tidak valid");
		if (subject == "" || subject == null)
			throw new IllegalArgumentException("`subject` tidak valid");
		if (content == "" || content == null)
			throw new IllegalArgumentException("`content` tidak valid");

		Intent intent = new Intent();

		intent.setAction(Intent.ACTION_SENDTO);
		intent.setData(Uri.parse("mailto:" + email));
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, content);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		if (intent.resolveActivity(context.getPackageManager()) != null)
			context.startActivity(intent);
	}

	public void call(Context context, String phone) {
		if (phone == "" || phone == null)
			throw new IllegalArgumentException("`phone` tidak valid");

		Intent intent = new Intent();

		intent.setAction(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:" + phone));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		if (intent.resolveActivity(context.getPackageManager()) != null)
			context.startActivity(intent);
	}
}
