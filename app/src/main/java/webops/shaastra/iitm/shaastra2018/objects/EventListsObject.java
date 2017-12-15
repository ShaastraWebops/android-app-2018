package webops.shaastra.iitm.shaastra2018.objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rohithram on 15/12/17.
 */

public class EventListsObject implements Serializable {

    public String title;
    public String  info;
    public String imageid;
    public JSONArray events = new JSONArray();

    public EventListsObject(JSONObject jsonResult){

        try {

            title = jsonResult.getString("title");
            info = jsonResult.getString("info");
            imageid = jsonResult.getString("imageid");
            events = jsonResult.getJSONArray("events");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
