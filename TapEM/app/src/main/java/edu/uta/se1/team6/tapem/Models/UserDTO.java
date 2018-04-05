package edu.uta.se1.team6.tapem.Models;

import java.io.Serializable;

import edu.uta.se1.team6.tapem.Helpers.RequestStatus;

/**
 * Created by yashodhan on 3/23/18.
 */

public class UserDTO extends RequestStatus implements Serializable {

    private String id;
    private String mavID;
    private String firstName;
    private String lastName;
    private String status;
    private String approvedOn;
    private String type;
    private String birthDate;
    private String sex;

    public UserDTO(String mavID, String firstName, String lastName, String status, String approvedOn, String type, String birthDate, String sex) {
        this.mavID = mavID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.approvedOn = approvedOn;
        this.type = type;
        this.birthDate = birthDate;
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMavID() {
        return mavID;
    }

    public void setMavID(String mavID) {
        this.mavID = mavID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApprovedOn() {
        return approvedOn;
    }

    public void setApprovedOn(String approvedOn) {
        this.approvedOn = approvedOn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
