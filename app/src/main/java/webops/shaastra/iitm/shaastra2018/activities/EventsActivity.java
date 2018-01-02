package webops.shaastra.iitm.shaastra2018.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import webops.shaastra.iitm.shaastra2018.R;
import webops.shaastra.iitm.shaastra2018.imageCaching.ImageUtil;
import webops.shaastra.iitm.shaastra2018.objects.EventListsObject;
import webops.shaastra.iitm.shaastra2018.objects.EventObject;

import static java.security.AccessController.getContext;

public class EventsActivity extends AppCompatActivity implements Serializable{

    private EventListsObject eventListsObject;
    private RecyclerView rv_events;
    private EventAdapter adapter;
    private RequestQueue queue;
    private String event_url = "http://shaastra.org:8000/api/events/";
    private String tag = "eventRequest";
    private EventObject eventObject ;
    private ProgressDialog progress;
    private ImageLoader imageLoader;
    private String img_url = "http://shaastra.org/images/Mainwebsite_new/events/vertical_icon";
    private int eventNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        eventListsObject = (EventListsObject) args.getParcelable("events");
        eventNo = args.getInt("imgid");



        rv_events = (RecyclerView) findViewById(R.id.rv_events);
        rv_events.setItemAnimator(new DefaultItemAnimator());
        rv_events.setLayoutManager(new GridLayoutManager(this,2));

        JSONArray events = null;
        try {
            events = new JSONArray(eventListsObject.events);
            adapter = new EventAdapter(EventsActivity.this,events);
            rv_events.setAdapter(adapter);
            queue = Volley.newRequestQueue(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public class EventAdapter extends RecyclerView.Adapter<EventAdapter.Viewholder>{

        Context context;
        JSONArray events = new JSONArray();

        public EventAdapter(Context context, JSONArray events){
            this.context = context;
            this.events = events;

        }

        @Override
        public EventAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(context).inflate(R.layout.item_events, parent, false);
            return new Viewholder(itemView);
        }

        @Override
        public void onBindViewHolder(final EventAdapter.Viewholder holder, int position) {

            try {

                final JSONObject event = events.getJSONObject(holder.getAdapterPosition());
                holder.tv_event_name.setText(event.getString("name"));
                imageLoader = ImageUtil.getImageLoader(context);
                imageLoader.displayImage(img_url+eventNo+".png",holder.img_event);

                holder.cv_event.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        progress=new ProgressDialog(context);
                        progress.setMessage("Loading Event Info....");
                        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progress.setIndeterminate(true);
                        progress.setProgress(0);
                        progress.show();


                        try {

                            String id = event.getString("_id");

                            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,event_url+id,
                                    null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    eventObject = new EventObject(response);

                                    Intent i = new Intent(EventsActivity.this,EventInfoActivity.class);
                                    Bundle args = new Bundle();
                                    args.putParcelable("eventinfo",eventObject);
                                    i.putExtra("BUNDLE",args);
                                    progress.dismiss();
                                    startActivity(i);

                                }
                            },new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    VolleyLog.d(tag, "Error: " + error.getMessage());
                                    Log.e(tag, "Site Info Error: " + error.getMessage());
                                    Toast.makeText(context,
                                            error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            queue.add(req);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return events.length();
        }

        public class Viewholder extends RecyclerView.ViewHolder {
            public CardView cv_event;
            public TextView tv_event_name;
            public ImageView img_event;

            public Viewholder(View itemView) {
                super(itemView);
                cv_event = (CardView)itemView.findViewById(R.id.cv_event);
                tv_event_name  = (TextView)itemView.findViewById(R.id.tv_event_name);
                img_event = (ImageView)itemView.findViewById(R.id.img_events);
            }
        }

    }
}
