package net.hugonardo.feathermigre;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import static android.util.Log.d;
import static android.util.Log.w;

class ManifestMetadata {

	private static final String TAG = "ManifestMetadata";

	private static final String DB_NAME = "db_name";
	private static final String DB_VERSION = "db_version";

	public static String getString(Context context, String name) {
		String value = null;

		try {
			ApplicationInfo ai = getApplicationInfo(context);
			value = ai.metaData.getString(name);
		} catch (Exception e) {
			w(TAG, "Couldn't find string metadata: " + name);
		}

		return value;
	}

	public static Integer getInteger(Context context, String name) {
		Integer value = null;

		try {
			ApplicationInfo ai = getApplicationInfo(context);
			value = ai.metaData.getInt(name);
		} catch (Exception e) {
			w(TAG, "Couldn't find integer metadata: " + name);
		}

		return value;
	}

	private static ApplicationInfo getApplicationInfo(Context context) throws PackageManager.NameNotFoundException {
		PackageManager pm = context.getPackageManager();
		return pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
	}

	public static String getDbName(Context context) {
		String dbName = getString(context, DB_NAME);

		if (dbName == null) {
			dbName = "application";
			d(TAG, "Using default database name: '" + dbName + "'");
		}

		return dbName;
	}

	public static int getDbVersion(Context context) {
		Integer dbVersion = getInteger(context, DB_VERSION);

		if ((dbVersion == null) || (dbVersion.intValue() == 0)) {
			throw new RuntimeException("Couldn't to find " + DB_VERSION + " on AndroidManifest metadata. Are you sure that it is there?");
		}

		return dbVersion.intValue();
	}
}