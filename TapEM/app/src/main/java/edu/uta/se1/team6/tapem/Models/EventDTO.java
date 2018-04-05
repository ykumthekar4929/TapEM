package edu.uta.se1.team6.tapem.Models;

import java.io.Serializable;

import edu.uta.se1.team6.tapem.Helpers.RequestStatus;

/**
 * Created by yashodhan on 3/23/18.
 */

public class EventDTO extends RequestStatus implements Serializable {

    private String id;
    private String name;
    private String address;
    private String area;
    private String status;
    private String createdById;
    private String createdByName;
    private String createdOn;
    private String catererUserID;
    private String catererFirstName;
    private String catererLastName;
    private String catererEmail;
    private String catererMobile;
    private String city;
    private String zipcode;
    private String lat;
    private String lon;
    private String imageURL;

    public EventDTO(String name, String address, String status, String createdById, String createdByName, String city, String imageURL) {
        this.name = name;
        this.address = address;
        this.status = status;
        this.createdById = createdById;
        this.createdByName = createdByName;
        this.city = city;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCatererUserID() {
        return catererUserID;
    }

    public void setCatererUserID(String catererUserID) {
        this.catererUserID = catererUserID;
    }

    public String getCatererFirstName() {
        return catererFirstName;
    }

    public void setCatererFirstName(String catererFirstName) {
        this.catererFirstName = catererFirstName;
    }

    public String getCatererLastName() {
        return catererLastName;
    }

    public void setCatererLastName(String catererLastName) {
        this.catererLastName = catererLastName;
    }

    public String getCatererEmail() {
        return catererEmail;
    }

    public void setCatererEmail(String catererEmail) {
        this.catererEmail = catererEmail;
    }

    public String getCatererMobile() {
        return catererMobile;
    }

    public void setCatererMobile(String catererMobile) {
        this.catererMobile = catererMobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
