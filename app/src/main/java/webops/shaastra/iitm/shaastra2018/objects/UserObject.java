package webops.shaastra.iitm.shaastra2018.objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Rajat on 08-08-2017.
 */

public class UserObject implements Serializable{
    public String username;
    public String college;
    public Number phone;
    public String city;
    public String ShaastraId;
    public JSONArray teams = new JSONArray();
    public JSONArray indReg = new JSONArray();

    public UserObject(String jsonResult){
        // TODO: parse json result of user login task into a user object
    }

    public UserObject(JSONObject jsonResult){
        // TODO: parse json result of user profile task into a user object
        try {
            username = jsonResult.getString("name");
            college = jsonResult.getString("college");
            city = jsonResult.getString("city");
            phone = jsonResult.getLong("mob_no");
            ShaastraId = jsonResult.getString("festID");
            teams = jsonResult.getJSONArray("teams");
            indReg = jsonResult.getJSONArray("indReg");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
