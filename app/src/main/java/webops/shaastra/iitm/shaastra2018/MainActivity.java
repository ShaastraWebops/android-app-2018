package webops.shaastra.iitm.shaastra2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Location> locs = new ArrayList<Location>();
        initLocations(locs);

        //Log.i("Json", String.valueOf(locs));

        /*HttpPostRequest.sendparams("name","Shashank");
        HttpPostRequest.sendparams("Age","15");
        JSONObject p3 = HttpPostRequest.sendparams("Email","Shas5566@gmail.com");
        now that p3 is jsonobject of all params so now convert to string
        by p3.tostring()
        Note: give keys uniquely .
        and send it along with string url to execute() of asynctask.
        example: execute(url,p3.toString());
        will do the trick!
        */

    }

    public void initLocations(ArrayList<Location> locs){

        String samplesString = loadJSONStringFromAsset("loc.json");
        try {
            JSONObject sampleFile = new JSONObject(samplesString);
            // Setup the locations

            JSONArray locsjson = sampleFile.getJSONArray("Locations");
            for(int i=0;i<locsjson.length();i++){
                JSONObject locationjson = locsjson.getJSONObject(i);
                Location location = new Location(locationjson.getString("loc"),locationjson.getDouble("lat"),locationjson.getDouble("lng"));
                locs.add(location);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public String loadJSONStringFromAsset(String filename) {
        // Read a string from a JSON file
        String json;
        try {
            InputStream is = this.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
