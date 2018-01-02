package webops.shaastra.iitm.shaastra2018.objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by rohithram on 15/12/17.
 */

public class EventObject implements Parcelable {

    public String name,info,venue;
    public String contactemail, registration,prizedetails,tdpdetails;
    public Boolean isEvent,isWorkshop,requireTDP,paidEvent,limitteam;
    public String eventDate,startReg,endReg,contact,otherinfo,imageid;
    public String rounds,eventCategory,maxTeamMembers,minTeamMembers,fees;

    public EventObject(JSONObject jsonResult){

        try {

            name = jsonResult.getString("name");
            info = jsonResult.getString("info");
            venue = jsonResult.getString("venue");
            registration = jsonResult.getString("registration");
            eventDate = jsonResult.get("eventDate").toString();
            startReg =  jsonResult.get("startReg").toString();
            endReg = jsonResult.get("endReg").toString();
            prizedetails =  jsonResult.getString("prizedetails");
            tdpdetails =  jsonResult.getString("tdpdetails");
            contactemail =  jsonResult.get("contactemail").toString();
            contact = jsonResult.getJSONArray("contact").toString();
            isEvent = jsonResult.getBoolean("isEvent");
            isWorkshop = jsonResult.getBoolean("isWorkshop");
            rounds = jsonResult.get("rounds").toString();
            eventCategory = jsonResult.get("eventCategory").toString();
            maxTeamMembers = jsonResult.get("maxTeamMembers").toString();
            minTeamMembers = jsonResult.get("minTeamMembers").toString();
            requireTDP = jsonResult.getBoolean("requireTDP");
            paidEvent = jsonResult.getBoolean("paidEvent");
            fees = jsonResult.get("fees").toString();
            limitteam = jsonResult.getBoolean("limitteam");
            otherinfo = jsonResult.getJSONArray("otherinfo").toString();
            imageid = jsonResult.getString("imageid");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected EventObject(Parcel in) {
        name = in.readString();
        info = in.readString();
        venue = in.readString();
        contactemail = in.readString();
        contact = in.readString();
        registration = in.readString();
        prizedetails = in.readString();
        tdpdetails = in.readString();
        eventDate = in.readString();
        startReg = in.readString();
        endReg = in.readString();
        rounds = in.readString();
        eventCategory = in.readString();
        maxTeamMembers = in.readString();
        minTeamMembers = in.readString();
        fees = in.readString();
        otherinfo = in.readString();
        isEvent = in.readByte() != 0;
        isWorkshop = in.readByte() !=0;
        requireTDP = in.readByte() !=0;
        paidEvent = in.readByte() !=0;
        limitteam = in.readByte() !=0;

    }

    public static final Creator<EventObject> CREATOR = new Creator<EventObject>() {
        @Override
        public EventObject createFromParcel(Parcel in) {
            return new EventObject(in);
        }

        @Override
        public EventObject[] newArray(int size) {
            return new EventObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(info);
        dest.writeString(venue);
        dest.writeString(contactemail);
        dest.writeString(contact);
        dest.writeString(registration);
        dest.writeString(prizedetails);
        dest.writeString(tdpdetails);
        dest.writeString(eventDate);
        dest.writeString(startReg);
        dest.writeString(endReg);
        dest.writeString(rounds);
        dest.writeString(eventCategory);
        dest.writeString(maxTeamMembers);
        dest.writeString(minTeamMembers);
        dest.writeString(fees);
        dest.writeString(otherinfo);
        if(isEvent!=null)
            dest.writeByte((byte) (isEvent ? 1 : 0));
        if(isWorkshop!=null)
            dest.writeByte((byte) (isWorkshop ? 1 : 0));
        if(requireTDP!=null)
            dest.writeByte((byte) (requireTDP ? 1 : 0));
        if(paidEvent!=null)
            dest.writeByte((byte) (paidEvent ? 1 : 0));
        if(limitteam!=null)
            dest.writeByte((byte) (limitteam ? 1 : 0));


    }


//    shaastraFellowship: { type: Boolean, default: false },
//    imageid: String,
//    imagename: String,
//    points: [{ type: Number }],
//    registeredTeams: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Team' }],
//    registeredMembers: [{ type: mongoose.Schema.Types.ObjectId, ref: 'User' }],
//    winners: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Team' }],
//    selectedTeams: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Team' }],
//    marqueeNotifs: [{ type: mongoose.Schema.Types.ObjectId, ref: 'MarqueeNotif' }],
//    workshopCategory:{ type: String, default: "" }
}
