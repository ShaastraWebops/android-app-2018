package webops.shaastra.iitm.shaastra2018.objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rohithram on 15/12/17.
 */

public class EventListsObject implements Parcelable {

    public String title;
    public String  info;
    public String imageid;
    public String events;

    public EventListsObject(JSONObject jsonResult){

        try {

            title = jsonResult.getString("title");
            info = jsonResult.getString("info");
            imageid = jsonResult.getString("imageid");
            events = jsonResult.getJSONArray("events").toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected EventListsObject(Parcel in) {
        title = in.readString();
        info = in.readString();
        imageid = in.readString();
        events = in.readString();
    }

    public static final Creator<EventListsObject> CREATOR = new Creator<EventListsObject>() {
        @Override
        public EventListsObject createFromParcel(Parcel in) {
            return new EventListsObject(in);
        }

        @Override
        public EventListsObject[] newArray(int size) {
            return new EventListsObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(info);
        dest.writeString(imageid);
        dest.writeString(events);
    }
}
