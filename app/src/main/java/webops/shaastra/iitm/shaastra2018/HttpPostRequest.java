package webops.shaastra.iitm.shaastra2018;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rohithram on 21/6/17.
 */

public class HttpPostRequest extends AsyncTask<String,Void,JSONObject> {
    public static final String REQ_METHOD_POST = "POST";
    public static final int REQ_TIMEOUT = 15000;
    public static final int CONNECT_TIMEOUT = 15000;
    public static JSONObject param1 = new JSONObject();


    @Override
    protected JSONObject doInBackground(String... strings) {
        String url = strings[0];
        String params = strings[1];     //strings[1] contains jsonobject of all parameters required for post request which is converrted to string when calling execute()
        String line;
        StringBuilder builder = new StringBuilder();
        JSONObject finalresult;
        String result = "";
        try {
            URL murl = new URL(url);
            HttpURLConnection connect = (HttpURLConnection) murl.openConnection();

            connect.setRequestMethod(REQ_METHOD_POST);
            connect.setReadTimeout(REQ_TIMEOUT);
            connect.setConnectTimeout(CONNECT_TIMEOUT);
            connect.setDoInput(true);
            connect.setDoOutput(true);
            connect.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            OutputStream os = connect.getOutputStream();
            os.write(params.toString().getBytes("UTF-8"));
            os.close();




            connect.connect();

            InputStreamReader iReader = new InputStreamReader(connect.getInputStream());
            BufferedReader reader = new BufferedReader(iReader);

            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }

            reader.close();
            iReader.close();

            result = builder.toString();
            finalresult = convert_to_json(result);
        } catch (Exception e) {
            e.printStackTrace();
            finalresult = null;
        }


        return finalresult;
    }

    //for creating jsonobject of parameters required for post request
    //this will be called before calling execute() from any activity, such as param1 = sendparams("name","Shaastra");
    //so when they keep on calling it will append all the params and finally need to be converted to string for calling execute().


    public static JSONObject sendparams(String key , String value){
        try {
            param1.accumulate(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return param1;

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
