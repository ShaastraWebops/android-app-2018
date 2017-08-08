package webops.shaastra.iitm.shaastra2018.objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by rohithram on 22/6/17.
 */

public class Location implements Serializable{

    String loc;
    double lat, lng;

    public Location(String location,double lat,double lng){
        this.loc = location;
        this.lat = lat;
        this.lng = lng;
    }

    public String toString(){
        return loc;
    }
}

