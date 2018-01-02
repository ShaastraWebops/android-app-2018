package webops.shaastra.iitm.shaastra2018.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import webops.shaastra.iitm.shaastra2018.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventAboutFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventAboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventAboutFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "Fragment_ID";
    private static final String ARG_PARAM2 = "Content";

    // TODO: Rename and change types of parameters
    private int id;
    private JSONObject frag_content;

    private OnFragmentInteractionListener mListener;

    public EventAboutFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static EventAboutFragment newInstance(int id, String content) {
        EventAboutFragment fragment = new EventAboutFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
            try {
                frag_content = new JSONObject(getArguments().getString(ARG_PARAM2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = null;
        switch (id){
            case 0:
                view = inflater.inflate(R.layout.fragment_event_about, container, false);
                TextView tv_event_info,tv_rounds,tv_rounds_head;
                tv_event_info = (TextView)view.findViewById(R.id.tv_event_info);
                tv_rounds = (TextView)view.findViewById(R.id.tv_event_round);
                tv_rounds_head = (TextView)view.findViewById(R.id.tv_event_round_heading);
                try {
                    tv_event_info.setText(frag_content.getString("info"));
                    tv_event_info.setMovementMethod(ScrollingMovementMethod.getInstance());
                    if(frag_content.getString("rounds")!=null && !frag_content.getString("rounds").isEmpty()){
                        tv_rounds.setText(frag_content.getString("rounds"));
                        tv_rounds.setVisibility(View.VISIBLE);
                        tv_rounds_head.setVisibility(View.VISIBLE);
                    }

                    break;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            case 1:
                view = inflater.inflate(R.layout.fragment_event_reg, container, false);
                TextView tv_reg,tv_max,tv_min,tv_eventDate,tv_startReg,tv_endReg;
                tv_reg = (TextView)view.findViewById(R.id.tv_event_reg);
                tv_max = (TextView)view.findViewById(R.id.tv_event_max);
                tv_min = (TextView)view.findViewById(R.id.tv_event_min);
                tv_eventDate = (TextView)view.findViewById(R.id.tv_event_date);
                tv_startReg = (TextView)view.findViewById(R.id.tv_event_startReg);
                tv_endReg = (TextView)view.findViewById(R.id.tv_event_endReg);

                try {

                    tv_reg.setText(frag_content.getString("reg"));
                    tv_endReg.setText(frag_content.getString("endReg"));
                    tv_startReg.setText(frag_content.getString("startReg"));
                    tv_eventDate.setText(frag_content.getString("eventDate"));
                    tv_max.setText(frag_content.getString("max"));
                    tv_min.setText(frag_content.getString("min"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                view = inflater.inflate(R.layout.fragment_event_contact, container, false);
                RelativeLayout rel_Layout= (RelativeLayout)view.findViewById(R.id.rv_contacts);
                try {

                    TextView tv_email = (TextView)view.findViewById(R.id.tv_contact_email);

                    SpannableString content = new SpannableString(frag_content.getString("email"));
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    tv_email.setText(content);


                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    JSONArray contacts = new JSONArray(frag_content.getString("contact"));
                    TextView[] contactArray = new TextView[contacts.length()];



                    for (int i=0;i<contacts.length();i++){

                        contactArray[i].setText(contacts.getJSONObject(i).getString("name")+": "+contacts.getJSONObject(i).getString("phone"));
                        contactArray[i].setId(i + 1);
                        contactArray[i].setLayoutParams(params);
                        contactArray[i].setPadding(5,5,5,5);
                        contactArray[i].setGravity(Gravity.CENTER);

                        rel_Layout.addView(contactArray[i], new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                view = inflater.inflate(R.layout.fragment_event_prize, container, false);
                TextView tv_prize = (TextView)view.findViewById(R.id.tv_event_prize);
                try {
                    tv_prize.setText(frag_content.getString("prize"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                view = inflater.inflate(R.layout.fragment_event_tdp, container, false);
                break;
            case 5:
                view = inflater.inflate(R.layout.fragment_event_other, container, false);
                break;
            case 6:
                view = inflater.inflate(R.layout.fragment_event_venue, container, false);
                break;
//            case 6:
//                inflater.inflate(R.layout.fragment_event_other, container, false);
//                break;
            default:
                view = inflater.inflate(R.layout.fragment_event_about, container, false);


        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
}
