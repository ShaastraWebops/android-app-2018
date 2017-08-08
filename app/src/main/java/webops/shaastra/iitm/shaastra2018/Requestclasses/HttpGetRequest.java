package webops.shaastra.iitm.shaastra2018.Requestclasses;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohithram on 21/6/17.
 */

public class HttpGetRequest extends AsyncTask<String,Void,JSONObject> {
    public static final String REQ_METHOD_GET = "GET";
    public static final int REQ_TIMEOUT = 15000;
    public static final int CONNECT_TIMEOUT = 15000;

    @Override
    protected JSONObject doInBackground(String... strings){
        String url = strings[0];
        String line;
        StringBuilder builder = new StringBuilder();
        JSONObject finalresult;
        String result = "";
            try {
                URL murl = new URL(url);
                HttpURLConnection connect = (HttpURLConnection) murl.openConnection();

                connect.setRequestMethod(REQ_METHOD_GET);
                connect.setReadTimeout(REQ_TIMEOUT);
                connect.setConnectTimeout(CONNECT_TIMEOUT);

                connect.connect();

                InputStreamReader iReader = new InputStreamReader(connect.getInputStream());
                BufferedReader reader = new BufferedReader(iReader);

                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }

                reader.close();
                iReader.close();

                result = builder.toString();
                finalresult=convert_to_json(result);
            } catch (Exception e) {
                e.printStackTrace();
                finalresult = null;
            }


        return finalresult;

        }


    private static JSONObject convert_to_json(String response) {


        JSONObject final_response = null;
        try {
            final_response = new JSONObject(response);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return final_response;
    }

        @Override
    protected void onPostExecute(JSONObject result)
    {
        super.onPostExecute(result);

    }
}


