package webops.shaastra.iitm.shaastra2018.objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by rohithram on 15/12/17.
 */

public class EventObject implements Serializable {

    public String name,info,venue;
    public String contactemail, registration,prizedetails,tdpdetails;
    public Boolean isEvent,isWorkshop;
    public Date eventDate,startReg,endReg;

    public EventObject(JSONObject jsonResult){

        try {

            name = jsonResult.getString("name");
            info = jsonResult.getString("info");
            venue = jsonResult.getString("venue");
            registration = jsonResult.getString("registration");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


//    rounds: {type: Number, default: 1},
//    requireTDP: { type: Boolean, default: false },
//    shaastraFellowship: { type: Boolean, default: false },
//    paidEvent: { type: Boolean, default: false },
//    fees: Number,
//    maxTeamMembers: Number,
//    minTeamMembers: Number,
//    limitteam: {type: Boolean, default: false},
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
