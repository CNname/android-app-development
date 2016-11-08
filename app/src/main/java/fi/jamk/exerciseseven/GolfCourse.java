package fi.jamk.exerciseseven;

import android.media.Image;

import java.io.Serializable;

/**
 * Created by Ultoros on 2.11.2016.
 */
@SuppressWarnings("serial")
public class GolfCourse implements Serializable{
    private static final long serialVersionUID = 1L;
    String type;
    Double lat;
    Double lng;
    String field;
    String address;
    String phonenumber;
    String email;
    String imageSite = "http://ptm.fi/jamk/android/golfcourses/";
    String imageUrl;
    String description;
    String webSite;

    public GolfCourse( String type, Double lat, Double lng, String field, String address, String phonenumber, String email, String imageUrl, String description, String webSite){
        this.type = type;
        this.lat = lat;
        this.lng = lng;
        this.field = field;
        this.address = address;
        this.phonenumber = phonenumber;
        this.email = email;
        this.imageUrl = imageUrl;
        this.description = description;
        this.webSite = webSite;
    }
}
