package kz.growit.altynorda.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Талгат on 14.10.2015.
 */
public class Comments {
    private int Id, ListingId;
    private String Text, Username, PendingDate, ApprovedDate, RejectedDate, LastUpdate, CreatedDate;

//            Enabled: true,
//            Pending: false,
//            Approved: true,
//            Rejected: false,
//            ChildCommentList: null
//    }

    public Comments(JSONObject resp) {
        try {
            this.Id = resp.getInt("Id");
            this.ListingId = resp.getInt("ListingId");

            this.Text = resp.getString("Text");
            this.Username = resp.getString("Username");
            this.PendingDate = resp.getString("PendingDate");
            this.ApprovedDate = resp.getString("ApprovedDate");
            this.RejectedDate = resp.getString("RejectedDate");
            this.LastUpdate = resp.getString("LastUpdate");
            this.CreatedDate = resp.getString("CreatedDate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return Id;
    }

    public int getListingId() {
        return ListingId;
    }

    public String getText() {
        return Text;
    }

    public String getUsername() {
        return Username;
    }

    public String getPendingDate() {
        return PendingDate;
    }

    public String getApprovedDate() {
        return ApprovedDate;
    }

    public String getRejectedDate() {
        return RejectedDate;
    }

    public String getLastUpdate() {
        return LastUpdate;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }
}
