package kz.growit.altynorda.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jean on 9/30/2015.
 */
public class Agencies {
    private int Id;
    private String Login, Agencyname, Address, City, Phone, Mobile, ImageFileName;

    public Agencies(JSONObject jsonObject) {
        try {
            this.Id = jsonObject.getInt("Id");
            this.Login = jsonObject.getString("Login");
            this.Agencyname= jsonObject.getString("Agencyname");
            this.Address = jsonObject.getString("Address");
            this.City = jsonObject.getString("City");
            this.Phone = jsonObject.getString("Phone");
            this.Mobile = jsonObject.getString("Mobile");
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

    public String getAgencyname() {
        return Agencyname;
    }

    public String getAddress() {
        return Address;
    }

    public String getCity() {
        return City;
    }

    public String getPhone() {
        return Phone;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getImageFileName() {
        return ImageFileName;
    }
}
