package webops.shaastra.iitm.shaastra2018.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import webops.shaastra.iitm.shaastra2018.R;
import webops.shaastra.iitm.shaastra2018.activities.NavigationActivity;
import webops.shaastra.iitm.shaastra2018.objects.UserObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserProfilefragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserProfilefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfilefragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String token;
    private String tag = "UserProfile";
//    String userProfile_url = getString(R.string.shaastra_user_url);
    RequestQueue queue;
    private UserObject user;
    private RecyclerView rv_teams;
    private TeamAdapter adapter;
    private ProgressDialog progress;
    private OnFragmentInteractionListener mListener;
    public UserProfilefragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfilefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfilefragment newInstance(String param1, String param2) {
        UserProfilefragment fragment = new UserProfilefragment();
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
            token = getArguments().getString("session-token");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_user_profilefragment, container, false);

        final TableLayout t1 = (TableLayout)view.findViewById(R.id.table_ind_reg);
        final TextView tv_username = (TextView)view.findViewById(R.id.tv_username);
        final TextView tv_college = (TextView)view.findViewById(R.id.tv_college);
        final TextView tv_phone = (TextView)view.findViewById(R.id.tv_phone);
        final TextView tv_city = (TextView)view.findViewById(R.id.tv_city);
        final TextView tv_shaastraId = (TextView)view.findViewById(R.id.tv_shaastraId);
        final TextView tv_indReg_status = (TextView)view.findViewById(R.id.tv_indReg_status);
        final TextView tv_teamReg_status = (TextView)view.findViewById(R.id.tv_teamReg_status);


        rv_teams = (RecyclerView)view.findViewById(R.id.rv_teams);
        rv_teams.setItemAnimator(new DefaultItemAnimator());
        rv_teams.setLayoutManager(new LinearLayoutManager(getContext()));

        view.setVisibility(View.GONE);
        progress=new ProgressDialog(getContext());
        progress.setMessage("Loading UserProfile....");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();

        queue = Volley.newRequestQueue(getContext());

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,"http://shaastra.org:8000/api/users/me",
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
//                Log.d("UserProfile", response.toString());
                user = new UserObject(response);

                tv_username.setText(user.username);
                tv_phone.setText(user.phone.toString());
                tv_city.setText(user.city);
                tv_college.setText(user.college);
                tv_shaastraId.setText(user.ShaastraId);

                //populating teams and their registrations
                //dynamically adding tablerows to table

                if(user.teams.length()==0){
                    tv_teamReg_status.setVisibility(View.VISIBLE);
                }else {
                    tv_teamReg_status.setVisibility(View.GONE);
                    rv_teams.setVisibility(View.VISIBLE);
                    adapter = new TeamAdapter(getContext(), user.teams);
                    rv_teams.setAdapter(adapter);
                }

                //populating individual registrations
                //dynamically adding tablerows to table

                if(user.indReg.length()==0){
                    t1.setVisibility(View.GONE);
                    tv_indReg_status.setVisibility(View.VISIBLE);

                }else {
                    tv_indReg_status.setVisibility(View.GONE);
                    populateEventsReg(user.indReg, t1);
                    t1.setVisibility(View.VISIBLE);
                }

                progress.dismiss();
                view.setVisibility(View.VISIBLE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(tag, "Error: " + error.getMessage());
                Log.e(tag, "Site Info Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        queue.add(req);

        return  view;


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

    public Void populateEventsReg(JSONArray events,TableLayout t1){

            TextView[] eventNameArray = new TextView[events.length()];
            TextView[] eventStatusArray = new TextView[events.length()];

            TableRow[] eventsArray = new TableRow[events.length()];

            for (int i = 0; i < events.length(); i++) {
                try {

                    JSONObject event = events.getJSONObject(i);
                    String event_name = event.getString("name");
//                            String completed = event.getString("status");

                    //Create the tablerows
                    eventsArray[i] = new TableRow(getContext());
                    eventsArray[i].setId(i + 1);
                    eventsArray[i].setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));

                    // Here create the TextView dynamically
                    eventNameArray[i] = new TextView(getContext());
                    TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
//                            params = new TableRow.LayoutParams(1);
                    eventNameArray[i].setLayoutParams(params);
                    eventNameArray[i].setId(i + 11);
                    eventNameArray[i].setText(event_name);
                    eventNameArray[i].setMaxLines(2);
                    eventNameArray[i].setGravity(Gravity.CENTER);
                    eventNameArray[i].setPadding(5,5,5,5);
                    eventsArray[i].addView(eventNameArray[i]);

                    eventStatusArray[i] = new TextView(getContext());
                    TableRow.LayoutParams params1 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                    eventStatusArray[i].setLayoutParams(params1);
                    eventStatusArray[i].setId(i + 12);
                    eventStatusArray[i].setText("NO");
                    eventStatusArray[i].setPadding(5,5,5,5);
                    eventStatusArray[i].setGravity(Gravity.CENTER);
                    eventsArray[i].addView(eventStatusArray[i]);

                    t1.addView(eventsArray[i], new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        return null;
    }

    public class TeamAdapter extends RecyclerView.Adapter <TeamAdapter.ViewHolder> {

        Context context;
        JSONArray teams;

        public TeamAdapter(Context context, JSONArray teams) {
            this.context = context;
            this.teams = teams;
        }

        @Override
        public TeamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_team_events, parent, false);
            return new TeamAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final TeamAdapter.ViewHolder holder, int position) {
            try {
                JSONObject team = teams.getJSONObject(holder.getAdapterPosition());
                String teamName = team.getString("teamName");
                holder.tv_teamName.setText(teamName);
                final JSONArray eventsReg = team.getJSONArray("eventsRegistered");

                if(eventsReg.length()==0){
                    holder.table_team_reg.setVisibility(View.GONE);

                }else {
                    holder.tv_team_status.setVisibility(View.GONE);
                    populateEventsReg(eventsReg, holder.table_team_reg);
                }

                holder.cv_team.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onClick(View v) {
                        if(holder.table_team_reg.getVisibility() == View.GONE && holder.tv_team_status.getVisibility()==View.GONE){
                            //ITs collapsed , so expand it
                            holder.ibt_more.setVisibility(View.GONE);
                            holder.ibt_less.setVisibility(View.VISIBLE);
                            holder.table_team_reg.setVisibility(View.VISIBLE);
                            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.tv_teamName.getLayoutParams();
                            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                            holder.tv_teamName.setLayoutParams(lp);
                            if(eventsReg.length()==0){
                                holder.tv_team_status.setVisibility(View.VISIBLE);
                            }

                        }else{
                            //its expanded so collapse  it
                            holder.table_team_reg.setVisibility(View.GONE);
                            holder.tv_team_status.setVisibility(View.GONE);
                            holder.ibt_less.setVisibility(View.GONE);
                            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.tv_teamName.getLayoutParams();
                            lp.removeRule(RelativeLayout.CENTER_HORIZONTAL);
                            holder.tv_teamName.setLayoutParams(lp);
                            holder.ibt_more.setVisibility(View.VISIBLE);

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
            return teams.length();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tv_teamName,tv_team_status;
            public ImageButton ibt_less,ibt_more;
            public TableLayout table_team_reg;
            public CardView cv_team;

            public ViewHolder(View itemView) {
                super(itemView);

                tv_teamName = (TextView)itemView.findViewById(R.id.tv_teamName);
                tv_team_status= (TextView)itemView.findViewById(R.id.tv_team_status);
                ibt_less = (ImageButton)itemView.findViewById(R.id.ibt_less);
                ibt_more = (ImageButton)itemView.findViewById(R.id.ibt_more);
                table_team_reg = (TableLayout)itemView.findViewById(R.id.table_team_reg);
                cv_team = (CardView)itemView.findViewById(R.id.cv_team);
            }
        }
    }
}
