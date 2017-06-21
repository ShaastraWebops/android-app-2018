package webops.shaastra.iitm.shaastra2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

}
