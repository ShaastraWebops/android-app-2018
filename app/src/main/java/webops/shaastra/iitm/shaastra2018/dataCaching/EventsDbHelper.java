package webops.shaastra.iitm.shaastra2018.dataCaching;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rajat on 08-08-2017.
 */

public class EventsDbHelper extends SQLiteOpenHelper {

    public static int DB_VERSION=0;
    public static final String DB_NAME = "Events.db";

    public EventsDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EventsSchema.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(EventsSchema.SQL_DELETE_TABLE);
        onCreate(db);
    }
}
