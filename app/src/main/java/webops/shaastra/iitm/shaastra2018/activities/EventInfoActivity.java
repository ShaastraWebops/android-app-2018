package webops.shaastra.iitm.shaastra2018.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import webops.shaastra.iitm.shaastra2018.R;
import webops.shaastra.iitm.shaastra2018.objects.EventListsObject;
import webops.shaastra.iitm.shaastra2018.objects.EventObject;

public class EventInfoActivity extends AppCompatActivity {

    private EventObject eventObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        eventObject = (EventObject) args.getParcelable("eventinfo");

        TextView tv_event_name = (TextView)findViewById(R.id.tv_eventName);

        tv_event_name.setText(eventObject.name);
    }
}
