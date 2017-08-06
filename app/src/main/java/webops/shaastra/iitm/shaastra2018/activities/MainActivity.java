package webops.shaastra.iitm.shaastra2018.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import webops.shaastra.iitm.shaastra2018.objects.Location;
import webops.shaastra.iitm.shaastra2018.R;
import webops.shaastra.iitm.shaastra2018.imageCaching.ImageUtil;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static Button bt_qrscan, log_act_button;
    //qr code scanner object
    public static IntentIntegrator qrScan;
    boolean isClicked = true;
    PopupWindow popUpWindow;
    RelativeLayout mainLayout;
    CardView containerLayout;
    private Button reg_act_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Location> locs = new ArrayList<Location>();
        initLocations(locs);

        mainLayout = (RelativeLayout)findViewById(R.id.rl_main);

        containerLayout = (CardView) findViewById(R.id.cv_popup);

        popUpWindow = new PopupWindow(MainActivity.this);

        popUpWindow.setContentView(containerLayout);

        bt_qrscan = (Button)findViewById(R.id.bt_qr_scan);

        //intializing scan object
        qrScan = new IntentIntegrator(this);

        bt_qrscan.setOnClickListener(this);

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


        String testImageURL = "http://placehold.it/240x120&text=image1";

        ImageLoader imageLoader = ImageUtil.getImageLoader(this);


        ImageView imview = (ImageView) findViewById(R.id.imView);

        imageLoader.displayImage(testImageURL,imview);

        log_act_button = (Button) findViewById(R.id.login_act_button);
        log_act_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        reg_act_button = (Button) findViewById(R.id.register_act_button);
        reg_act_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
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

    //Getting the scan results
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        final String activity_name;
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                //converting the data to json
                //setting values to activity_variable

                activity_name = String.valueOf(result.getContents());
                initiatePopupWindow(bt_qrscan,activity_name);




            } }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initiatePopupWindow(final View v, final String qr_response) {

        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            popUpWindow.setTouchable(true);
            popUpWindow.setFocusable(true);

            LayoutInflater inflater = (LayoutInflater)MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.popup,
                    (ViewGroup) findViewById(R.id.cv_popup));

            final Button bt_dismiss_pop,bt_go;
            TextView tv_qr_response;

            tv_qr_response = (TextView) layout.findViewById(R.id.tv_qr_response);
            bt_dismiss_pop = (Button)layout.findViewById(R.id.bt_pop_dismiss);
            bt_go = (Button)layout.findViewById(R.id.bt_go);

            tv_qr_response.setText(qr_response);

            popUpWindow = new PopupWindow(layout,CardView.LayoutParams.WRAP_CONTENT,CardView.LayoutParams.WRAP_CONTENT,true);
            // display the popup in the center
            new Handler().postDelayed(new Runnable(){

                public void run() {
                    popUpWindow.showAtLocation( v, Gravity.CENTER, 0, 0);
                }

            }, 200L);

            bt_dismiss_pop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                                popUpWindow.dismiss();


                }
            });
            bt_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent navigate = new Intent(MainActivity.this,Main2Activity.class);
                    navigate.putExtra("Activityname",qr_response);
                    startActivity(navigate);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {

        qrScan.initiateScan();
    }
}
