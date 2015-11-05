package kz.growit.altynorda.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by jean on 10/13/2015.
 */
public class AllPictures {
//    private String ImageUrl;

    private int Id, ListingID, PictureType, PictureSize, ParentPictureId;
    private Date Dins, LastUpdate, CreatedDate;

    private String ImageUrl;


    public AllPictures(JSONObject jsonObject) {
        try {

            this.Id = jsonObject.getInt("Id");
            this.ListingID = jsonObject.getInt("ListingID");
            this.PictureType = jsonObject.getInt("PictureType");
            this.PictureSize = jsonObject.getInt("PictureSize");
            this.ParentPictureId = jsonObject.getInt("ParentPictureId");
            this.ImageUrl = jsonObject.getString("ImageUrl");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getListingID() {
        return ListingID;
    }

    public void setListingID(int listingID) {
        ListingID = listingID;
    }

    public int getPictureType() {
        return PictureType;
    }

    public void setPictureType(int pictureType) {
        PictureType = pictureType;
    }

    public int getPictureSize() {
        return PictureSize;
    }

    public void setPictureSize(int pictureSize) {
        PictureSize = pictureSize;
    }

    public int getParentPictureId() {
        return ParentPictureId;
    }

    public void setParentPictureId(int parentPictureId) {
        ParentPictureId = parentPictureId;
    }

    public Date getDins() {
        return Dins;
    }

    public void setDins(Date dins) {
        Dins = dins;
    }

    public Date getLastUpdate() {
        return LastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        LastUpdate = lastUpdate;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
