package kz.growit.altynorda.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Талгат on 14.10.2015.
 */
public class Listings {
    private int Id, AgencyId, AgentId, CityId, ExchangeId, PropertyTypeId,
            Floor, TotalFloors, RoomCount, RentTypeId, BedCount,
            WallMaterialId, ConditionId, ListingStatusId;
    private String Title, Address, Price, Description, Username,
            Latitude, Longitude, TotalArea, KitchenArea, ConstructionYear, CeilingHeight,
            MaxNumberPeopleForReservation;
    private boolean IsZalog, IsDorm, IsMicrowave, IsSmokingAllowed, IsPool, IsToaster, IsCoffeePot,
            IsCableTV, IsParquet, IsRoofTerrace, IsTerrace, IsBalcony, IsGarage, IsPharmacy,
            IsKindergarten, IsBarbers, IsSchool, IsShoppingMall, IsRestaurant, IsBusStop, IsPark,
            IsAtm, IsParking, IsTrc, IsInternet, IsWifi, IsTv, IsPhone, IsWashingMachine, IsIron,
            IsHairDryer, IsAc, IsTeaPot, IsDishes, IsSheets, IsTowel, IsRefrigerator, IsPrepaid,
            IsReservable, IsShower, IsBath, IsPetAllowed, IsDocumentProvided, IsTransfer,
            IsHygieneItems, isFeatured, isPriceDropped, Enabled, Pending, Approved, Rejected;
    private String PendingDate, ApprovedDate, RejectedDate, LastUpdate, CreatedDate;
    private ArrayList<AllPictures> allPictures = new ArrayList<>();
    private ArrayList<Comments> allComments = new ArrayList<>();
//        allReserves: []

    public Listings(JSONObject resp) {
        try {
            JSONArray pics = resp.getJSONArray("allPictures");
            for (int i = 0; i < pics.length(); i++) {
                AllPictures item = new AllPictures(pics.getJSONObject(i));
                this.allPictures.add(item);
            }

            JSONArray comments = resp.getJSONArray("allComments");
            for (int i = 0; i < comments.length(); i++) {
                Comments item = new Comments(comments.getJSONObject(i));
                allComments.add(item);
            }

            this.PendingDate = resp.getString("PendingDate");
            this.ApprovedDate = resp.getString("ApprovedDate");
            this.RejectedDate = resp.getString("RejectedDate");
            this.LastUpdate = resp.getString("LastUpdate");
            this.CreatedDate = resp.getString("CreatedDate");

            this.Id = resp.getInt("Id");
//            this.AgencyId = resp.getInt("AgencyId");
//            this.AgentId = resp.getInt("AgentId");
            this.CityId = resp.getInt("CityId");
//            this.ExchangeId = resp.getInt("ExchangeId");
//            this.PropertyTypeId = resp.getInt("PropertyTypeId");
//            this.Floor = resp.getInt("Floor");
//            this.TotalFloors = resp.getInt("TotalFloors");
         //   this.RoomCount = resp.getInt("RoomCount");
//            this.RentTypeId = resp.getInt("RentTypeId");
//            this.BedCount = resp.getInt("BedCount");
//            this.WallMaterialId = resp.getInt("WallMaterialId");
//            this.ConditionId = resp.getInt("ConditionId");
//            this.ListingStatusId = resp.getInt("ListingStatusId");

            this.Title = resp.getString("Title");
            this.Address = resp.getString("Address");
            this.Price = resp.getString("Price");
            this.Description = resp.getString("Description");
            this.Username = resp.getString("Username");
            this.Latitude = resp.getString("Latitude");
            this.Longitude = resp.getString("Longitude");
            this.TotalArea = resp.getString("TotalArea");
            this.KitchenArea = resp.getString("KitchenArea");
            this.ConstructionYear = resp.getString("ConstructionYear");
            this.CeilingHeight = resp.getString("CeilingHeight");
            this.MaxNumberPeopleForReservation = resp.getString("MaxNumberPeopleForReservation");

//            this.IsZalog = resp.getBoolean("IsZalog");
//            this.IsDorm = resp.getBoolean("IsDorm");
//            this.IsMicrowave = resp.getBoolean("IsMicrowave");
//            this.IsSmokingAllowed = resp.getBoolean("IsSmokingAllowed");
//            this.IsPool = resp.getBoolean("IsPool");
//            this.IsToaster = resp.getBoolean("IsToaster");
//            this.IsCoffeePot = resp.getBoolean("IsCoffeePot");
//            this.IsCableTV = resp.getBoolean("IsCableTV");
//            this.IsParquet = resp.getBoolean("IsParquet");
//            this.IsRoofTerrace = resp.getBoolean("IsRoofTerrace");
//            this.IsTerrace = resp.getBoolean("IsTerrace");
//            this.IsBalcony = resp.getBoolean("IsBalcony");
//            this.IsGarage = resp.getBoolean("IsGarage");
//            this.IsPharmacy = resp.getBoolean("IsPharmacy");
//            this.IsKindergarten = resp.getBoolean("IsKindergarten");
//            this.IsBarbers = resp.getBoolean("IsBarbers");
//            this.IsSchool = resp.getBoolean("IsSchool");
//            this.IsShoppingMall = resp.getBoolean("IsShoppingMall");
//            this.IsRestaurant = resp.getBoolean("IsRestaurant");
//            this.IsBusStop = resp.getBoolean("IsBusStop");
//            this.IsPark = resp.getBoolean("IsPark");
//            this.IsAtm = resp.getBoolean("IsAtm");
//            this.IsParking = resp.getBoolean("IsParking");
//            this.IsTrc = resp.getBoolean("IsTrc");
//            this.IsInternet = resp.getBoolean("IsInternet");
//            this.IsWifi = resp.getBoolean("IsWifi");
//            this.IsTv = resp.getBoolean("IsTv");
//            this.IsPhone = resp.getBoolean("IsPhone");
//            this.IsWashingMachine = resp.getBoolean("IsWashingMachine");
//            this.IsIron = resp.getBoolean("IsIron");
//            this.IsHairDryer = resp.getBoolean("IsHairDryer");
//            this.IsAc = resp.getBoolean("IsAc");
//            this.IsTeaPot = resp.getBoolean("IsTeaPot");
//            this.IsDishes = resp.getBoolean("IsDishes");
//            this.IsSheets = resp.getBoolean("IsSheets");
//            this.IsTowel = resp.getBoolean("IsTowel");
//            this.IsRefrigerator = resp.getBoolean("IsRefrigerator");
//            this.IsPrepaid = resp.getBoolean("IsPrepaid");
//            this.IsZalog = resp.getBoolean("IsZalog");
//            this.IsReservable = resp.getBoolean("IsReservable");
//            this.IsShower = resp.getBoolean("IsShower");
//            this.IsBath = resp.getBoolean("IsBath");
//            this.IsPetAllowed = resp.getBoolean("IsPetAllowed");
//            this.IsDocumentProvided = resp.getBoolean("IsDocumentProvided");
//            this.IsTransfer = resp.getBoolean("IsTransfer");
//            this.IsHygieneItems = resp.getBoolean("IsHygieneItems");
//            this.isFeatured = resp.getBoolean("isFeatured");
//            this.isPriceDropped = resp.getBoolean("isPriceDropped");
//            this.Enabled = resp.getBoolean("Enabled");
//            this.Pending = resp.getBoolean("Pending");
//            this.Approved = resp.getBoolean("Approved");
//            this.Rejected = resp.getBoolean("Rejected");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return Id;
    }

    public int getAgencyId() {
        return AgencyId;
    }

    public int getAgentId() {
        return AgentId;
    }

    public int getCityId() {
        return CityId;
    }

    public int getExchangeId() {
        return ExchangeId;
    }

    public int getPropertyTypeId() {
        return PropertyTypeId;
    }

    public int getFloor() {
        return Floor;
    }

    public int getTotalFloors() {
        return TotalFloors;
    }

    public int getRoomCount() {
        return RoomCount;
    }

    public int getRentTypeId() {
        return RentTypeId;
    }

    public int getBedCount() {
        return BedCount;
    }

    public int getWallMaterialId() {
        return WallMaterialId;
    }

    public int getConditionId() {
        return ConditionId;
    }

    public int getListingStatusId() {
        return ListingStatusId;
    }

    public String getTitle() {
        return Title;
    }

    public String getAddress() {
        return Address;
    }

    public String getPrice() {
        return Price;
    }

    public String getDescription() {
        return Description;
    }

    public String getUsername() {
        return Username;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getTotalArea() {
        return TotalArea;
    }

    public String getKitchenArea() {
        return KitchenArea;
    }

    public String getConstructionYear() {
        return ConstructionYear;
    }

    public String getCeilingHeight() {
        return CeilingHeight;
    }

    public String getMaxNumberPeopleForReservation() {
        return MaxNumberPeopleForReservation;
    }

    public boolean isZalog() {
        return IsZalog;
    }

    public boolean isDorm() {
        return IsDorm;
    }

    public boolean isMicrowave() {
        return IsMicrowave;
    }

    public boolean isSmokingAllowed() {
        return IsSmokingAllowed;
    }

    public boolean isPool() {
        return IsPool;
    }

    public boolean isToaster() {
        return IsToaster;
    }

    public boolean isCoffeePot() {
        return IsCoffeePot;
    }

    public boolean isCableTV() {
        return IsCableTV;
    }

    public boolean isParquet() {
        return IsParquet;
    }

    public boolean isRoofTerrace() {
        return IsRoofTerrace;
    }

    public boolean isTerrace() {
        return IsTerrace;
    }

    public boolean isBalcony() {
        return IsBalcony;
    }

    public boolean isGarage() {
        return IsGarage;
    }

    public boolean isPharmacy() {
        return IsPharmacy;
    }

    public boolean isKindergarten() {
        return IsKindergarten;
    }

    public boolean isBarbers() {
        return IsBarbers;
    }

    public boolean isSchool() {
        return IsSchool;
    }

    public boolean isShoppingMall() {
        return IsShoppingMall;
    }

    public boolean isRestaurant() {
        return IsRestaurant;
    }

    public boolean isBusStop() {
        return IsBusStop;
    }

    public boolean isPark() {
        return IsPark;
    }

    public boolean isAtm() {
        return IsAtm;
    }

    public boolean isParking() {
        return IsParking;
    }

    public boolean isTrc() {
        return IsTrc;
    }

    public boolean isInternet() {
        return IsInternet;
    }

    public boolean isWifi() {
        return IsWifi;
    }

    public boolean isTv() {
        return IsTv;
    }

    public boolean isPhone() {
        return IsPhone;
    }

    public boolean isWashingMachine() {
        return IsWashingMachine;
    }

    public boolean isIron() {
        return IsIron;
    }

    public boolean isHairDryer() {
        return IsHairDryer;
    }

    public boolean isAc() {
        return IsAc;
    }

    public boolean isTeaPot() {
        return IsTeaPot;
    }

    public boolean isDishes() {
        return IsDishes;
    }

    public boolean isSheets() {
        return IsSheets;
    }

    public boolean isTowel() {
        return IsTowel;
    }

    public boolean isRefrigerator() {
        return IsRefrigerator;
    }

    public boolean isPrepaid() {
        return IsPrepaid;
    }

    public boolean isReservable() {
        return IsReservable;
    }

    public boolean isShower() {
        return IsShower;
    }

    public boolean isBath() {
        return IsBath;
    }

    public boolean isPetAllowed() {
        return IsPetAllowed;
    }

    public boolean isDocumentProvided() {
        return IsDocumentProvided;
    }

    public boolean isTransfer() {
        return IsTransfer;
    }

    public boolean isHygieneItems() {
        return IsHygieneItems;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public boolean isPriceDropped() {
        return isPriceDropped;
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public boolean isPending() {
        return Pending;
    }

    public boolean isApproved() {
        return Approved;
    }

    public boolean isRejected() {
        return Rejected;
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

    public ArrayList<AllPictures> getAllPictures() {
        return allPictures;
    }

    public ArrayList<Comments> getAllComments() {
        return allComments;
    }
}
