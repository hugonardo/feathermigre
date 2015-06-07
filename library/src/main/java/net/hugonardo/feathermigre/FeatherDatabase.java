package net.hugonardo.feathermigre;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeatherDatabase extends SQLiteOpenHelper {

	private static final String DB_FILE_EXTENSION = ".db";

	private final Context mContext;
	private final Feathermigre mFeathermigre;

	public FeatherDatabase(Context context) {
		super(context, ManifestMetadata.getDbName(context) + DB_FILE_EXTENSION, null, ManifestMetadata.getDbVersion(context));
		mContext = context;
		mFeathermigre = new Feathermigre(mContext);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		mFeathermigre.createDatabase(db, ManifestMetadata.getDbVersion(mContext), ManifestMetadata.getDbName(mContext));
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		mFeathermigre.upgradeDatabase(db, oldVersion, newVersion, ManifestMetadata.getDbName(mContext));
	}

	public static void deleteDatabase(Context context) {
		context.deleteDatabase(ManifestMetadata.getDbName(context));
	}

}
