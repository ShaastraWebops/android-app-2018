package webops.shaastra.iitm.shaastra2018.dataCaching;

import android.provider.BaseColumns;

/**
 * Created by Rajat on 08-08-2017.
 */

public class EventsSchema {

    private void EventsSchema(){}

    public static final String SQL_TYPE_TEXT = "TEXT";
    public static final String TABLE_NAME = "eventsTable";

    public static final String SQL_CREATE_TABLE ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            Event._ID + " INTEGER PRIMARY KEY," +
            Event.COLUMN_NAME +     " %s,".format(SQL_TYPE_TEXT) +
            Event.COLUMN_VERTICAL + " %s,".format(SQL_TYPE_TEXT) +
            Event.COLUMN_TIME +     " %s,".format(SQL_TYPE_TEXT) +
            Event.COLUMN_PLACE +    " %s,".format(SQL_TYPE_TEXT) +
            Event.COLUMN_CONTACT +  " %s)".format(SQL_TYPE_TEXT);

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " +  TABLE_NAME;

    public class Event implements BaseColumns {
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_VERTICAL = "vertical";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_PLACE = "place";
        public static final String COLUMN_CONTACT = "contact";
    }

}
