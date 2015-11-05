package kz.growit.altynorda.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jean on 10/15/2015.
 */
public class Cities {
    private int Id;
    private String Name, State;
    private Double Latitude, Longtitude;
    private Boolean Enabled;
    private String  LastUpdate, CreatedDate;  //need to change type to Date

    public Cities(JSONObject jsonObject) {
        try {
            this.Id = jsonObject.getInt("Id");
            this.Name = jsonObject.getString("Name");
            this.State = jsonObject.getString("State");
            this.Enabled = jsonObject.getBoolean("Enabled");
            this.Latitude = jsonObject.getDouble("Latitude");
            this.Longtitude = jsonObject.getDouble("Longitude");
//            this.LastUpdate = jsonObject.getString("LastUpdate");
//            this.CreatedDate = jsonObject.getString("CreatedDate");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getState() {
        return State;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public Double getLongtitude() {
        return Longtitude;
    }

    public Boolean getEnabled() {
        return Enabled;
    }

    public String getLastUpdate() {
        return LastUpdate;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }
}