package edu.uta.se1.team6.tapem.Models;

import java.io.Serializable;

import edu.uta.se1.team6.tapem.Helpers.RequestStatus;

/**
 * Created by yashodhan on 3/23/18.
 */

public class UserDTO extends RequestStatus implements Serializable {

    private String user_id;
    private String mav_id;
    private String first_name;
    private String last_name;
    private String role;
    private String sex;
    private String password;
    private String email_id;
    private String status;
    private String birth_date;
    private String error;

//    public UserDTO(String mavID, String firstName, String lastName, String status, String approvedOn, String type, String birthDate, String sex) {
//        this.mavID = mavID;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.status = status;
//        this.approvedOn = approvedOn;
//        this.type = type;
//        this.birthDate = birthDate;
//        this.sex = sex;
//    }


    public UserDTO() {
    }


    public String getId() {
        return user_id;
    }

    public void setId(String id) {
        this.user_id = id;
    }

    public String getMavID() {
        return mav_id;
    }

    public void setMavID(String mavID) {
        this.mav_id = mavID;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}




