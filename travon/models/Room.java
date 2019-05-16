package com.thealienobserver.nikhil.travon.models;

public class Room {
    String shortdescription, rent, location, currency, Description, Bathrooms, Bedrooms, Furnished,PetFriendly,PostingDate,img1,img2,img3,sellername,sellerlocation,sellermailId,sellerphone;

    public Room(String shortdescription, String rent, String location, String currency, String Description, String Bathrooms, String Bedrooms, String Furnished, String PetFriendly, String PostingDate, String img1, String img2, String img3, String sellername, String sellerlocation, String sellermailId, String sellerphone) {
        this.shortdescription = shortdescription;
        this.rent = rent;
        this.location = location;
        this.currency = currency;
        this.Description = Description;
        this.Bathrooms = Bathrooms;
        this.Bedrooms = Bedrooms;
        this.Furnished = Furnished;
        this.PetFriendly = PetFriendly;
        this.PostingDate = PostingDate;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.sellername = sellername;
        this.sellermailId = sellermailId;
        this.sellerlocation = sellerlocation;
        this.sellerphone = sellerphone;
    }

    public String getShortdescription() { return shortdescription; }
    public String getRent() {
        return rent;
    }
    public String getLocation() {
        return location;
    }
    public String getCurrency() { return currency; }
    public String getDescription() { return Description; }
    public String getBathrooms() { return Bathrooms; }
    public String getBedrooms() { return Bedrooms; }
    public String getFurnished() { return Furnished; }
    public String getPetFriendly() { return PetFriendly; }
    public String getPostingDate() { return PostingDate; }
    public String getImg1() { return img1; }
    public String getImg2() { return img2; }
    public String getImg3() { return img3; }
    public String getSellername() { return sellername; }
    public String getSellermailId() { return sellermailId; }
    public String getSellerlocation() { return sellerlocation; }
    public String getSellerphone() { return sellerphone; }
}
