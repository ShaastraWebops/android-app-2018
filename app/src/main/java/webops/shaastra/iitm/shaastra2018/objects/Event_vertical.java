package webops.shaastra.iitm.shaastra2018.objects;

import java.io.Serializable;

/**
 * Created by rohithram on 15/12/17.
 */

public class Event_vertical implements Serializable{
    public String id;
    public String title;
    public Event_vertical(String id,String title){
        this.id = id;
        this.title = title;
    }
}
