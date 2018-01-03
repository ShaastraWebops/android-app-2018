package webops.shaastra.iitm.shaastra2018.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Queue;

import webops.shaastra.iitm.shaastra2018.R;
import webops.shaastra.iitm.shaastra2018.activities.EventsActivity;
import webops.shaastra.iitm.shaastra2018.activities.NavigationActivity;
import webops.shaastra.iitm.shaastra2018.imageCaching.ImageUtil;
import webops.shaastra.iitm.shaastra2018.mainUI.LoginActivity;
import webops.shaastra.iitm.shaastra2018.objects.EventListsObject;
import webops.shaastra.iitm.shaastra2018.objects.Event_vertical;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Eventsfragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Eventsfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Eventsfragment extends Fragment implements Serializable{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "event_verticals";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam2;

    private ArrayList<Event_vertical> event_verticals;
    private OnFragmentInteractionListener mListener;
    private RecyclerView rv_verticals;
    private VerticalAdapter adapter;
    private String vertical_url = "http://shaastra.org:8000/api/eventLists/";
    private RequestQueue queue;
    private String tag = "eventVerticalRequest";
    private EventListsObject eventListsObject;
    private ProgressDialog progress;
    private ImageLoader imageLoader;
    private String img_url = "http://shaastra.org/images/Mainwebsite_new/events/vertical_icon";
    private String w_img_url = "http://shaastra.org/images/Mainwebsite_new/workshops/vertical_icon";
    private boolean isEvent = true;

    public Eventsfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Eventsfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Eventsfragment newInstance(String param1, String param2) {
        Eventsfragment fragment = new Eventsfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event_verticals = (ArrayList<Event_vertical>) getArguments().getSerializable(ARG_PARAM1);
            isEvent = getArguments().getBoolean("isEvent");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_events, container, false);

        rv_verticals = (RecyclerView)view.findViewById(R.id.rv_verticals);
        rv_verticals.setItemAnimator(new DefaultItemAnimator());
//        rv_verticals.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_verticals.setLayoutManager(new GridLayoutManager(getContext(), 2));
        TextView tv_vertical = (TextView)view.findViewById(R.id.tv_eventVerticals);
        if(!isEvent)
            tv_vertical.setText("Workshop Verticals");


        adapter = new VerticalAdapter(event_verticals,getContext());
        rv_verticals.setAdapter(adapter);

        queue = Volley.newRequestQueue(getContext());

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.ViewHolder>

    {
        Context context;
        ArrayList<Event_vertical> event_verticals;

        public VerticalAdapter(ArrayList<Event_vertical> event_verticals, Context context){
            this.event_verticals = event_verticals;
            this.context = context;
        }

        @Override
        public VerticalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_event_verticals, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final VerticalAdapter.ViewHolder holder, int position) {
            holder.tv_verticalName.setText(event_verticals.get(holder.getAdapterPosition()).title);
            imageLoader = ImageUtil.getImageLoader(getContext());
            int position1 = holder.getAdapterPosition()+1;
            if(!isEvent){
                imageLoader.displayImage(w_img_url+position1+".png",holder.img_vertical);
            }else {
                imageLoader.displayImage(img_url+position1+".png",holder.img_vertical);
            }

            holder.cv_vertical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    progress=new ProgressDialog(context);
                    if(!isEvent){
                        progress.setMessage("Fetching Workshops....");
                    }else {
                        progress.setMessage("Fetching Events....");
                    }
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(true);
                    progress.setProgress(0);
                    progress.show();

                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,vertical_url+event_verticals.get(holder.getAdapterPosition()).id,
                            null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            eventListsObject = new EventListsObject(response);

                            Intent i = new Intent(getContext() , EventsActivity.class);
                            i.putExtra("events",eventListsObject);
                            i.putExtra("imgid",holder.getAdapterPosition()+1);
                            i.putExtra("isEvent",isEvent);
                            i.putExtra("vertical",event_verticals.get(holder.getAdapterPosition()).title);
                            progress.dismiss();
                            startActivity(i);

                        }
                    },new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(tag, "Error: " + error.getMessage());
                            Log.e(tag, "Site Info Error: " + error.getMessage());
                            Toast.makeText(getActivity().getApplicationContext(),
                                    error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    queue.add(req);
                }

        });
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
            return event_verticals.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tv_verticalName;
            public CardView cv_vertical;
            public ImageView img_vertical;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_verticalName = (TextView)itemView.findViewById(R.id.tv_eventVerticals_name);
                cv_vertical = (CardView)itemView.findViewById(R.id.cv_event_vertical);
                img_vertical = (ImageView)itemView.findViewById(R.id.img_verticals);
            }
        }
    }


}
