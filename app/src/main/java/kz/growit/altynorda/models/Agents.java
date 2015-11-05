package kz.growit.altynorda.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;


public class Agents {
    private int Id, UserId, AgencyID, Phone, Mobile;
    private ArrayList<String> allListings;
    private String Login, Firstname, Middlename, Lastname,Email, Skype,
            AboutMe, FacebookLink, TwitterLink, LinkedinLink,
            ImageFileName,ShortUrl;
    private boolean Enabled;
    private Date LastUpdate, CreatedDate;

    public Agents(JSONObject jsonObject) {
        try {
            this.Id = jsonObject.getInt("Id");
            this.Login = jsonObject.getString("Login");
            this.Email = jsonObject.getString("Email");
            this.ImageFileName = jsonObject.getString("ImageFileName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return Id;
    }

    public String getLogin() {
        return Login;
    }

    public String getEmail() {
        return Email;
    }

    public String getImageFileName() {
        return ImageFileName;
    }
}
