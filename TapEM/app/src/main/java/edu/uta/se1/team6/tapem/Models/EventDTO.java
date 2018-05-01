package edu.uta.se1.team6.tapem.Models;

import java.io.Serializable;

import edu.uta.se1.team6.tapem.Helpers.RequestStatus;

/**
 * Created by yashodhan on 3/23/18.
 */

public class EventDTO extends RequestStatus implements Serializable {

    private String event;
    private String title;
    private String description;
    private String status;
    private String created_by_user;
    private String created_by_user_name;
    private String caterer;
    private String caterer_name;
    private String caterer_email;
    private String from_t_stamp;
    private String to_t_stamp;
    private String location;
    private String meals;
    private String meal_type;
    private String meal_formality;
    private boolean serving_alcohol;
    private int cost;

    public EventDTO() {
    }


    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_by_user() {
        return created_by_user;
    }

    public void setCreated_by_user(String created_by_user) {
        this.created_by_user = created_by_user;
    }

    public String getCreated_by_user_name() {
        return created_by_user_name;
    }

    public void setCreated_by_user_name(String created_by_user_name) {
        this.created_by_user_name = created_by_user_name;
    }

    public String getCaterer() {
        return caterer;
    }

    public void setCaterer(String caterer) {
        this.caterer = caterer;
    }

    public String getCaterer_name() {
        return caterer_name;
    }

    public void setCaterer_name(String caterer_name) {
        this.caterer_name = caterer_name;
    }

    public String getFrom_t_stamp() {
        return from_t_stamp;
    }

    public void setFrom_t_stamp(String from_t_stamp) {
        this.from_t_stamp = from_t_stamp;
    }

    public String getTo_t_stamp() {
        return to_t_stamp;
    }

    public void setTo_t_stamp(String to_t_stamp) {
        this.to_t_stamp = to_t_stamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMeals() {
        return meals;
    }

    public void setMeals(String meals) {
        this.meals = meals;
    }

    public String getMeal_type() {
        return meal_type;
    }

    public void setMeal_type(String meal_type) {
        this.meal_type = meal_type;
    }

    public String getMeal_formality() {
        return meal_formality;
    }

    public void setMeal_formality(String meal_formality) {
        this.meal_formality = meal_formality;
    }

    public boolean isServing_alcohol() {
        return serving_alcohol;
    }

    public void setServing_alcohol(boolean serving_alcohol) {
        this.serving_alcohol = serving_alcohol;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCaterer_email() {
        return caterer_email;
    }

    public void setCaterer_email(String caterer_email) {
        this.caterer_email = caterer_email;
    }
}
