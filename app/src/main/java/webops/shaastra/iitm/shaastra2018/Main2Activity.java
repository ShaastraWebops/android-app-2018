package webops.shaastra.iitm.shaastra2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    String Activity_name;
    TextView tv_activity_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent i = getIntent();
        Activity_name  = i.getStringExtra("Activityname");
        tv_activity_name = (TextView)findViewById(R.id.textView);
        tv_activity_name.setText(Activity_name);

    }
}
