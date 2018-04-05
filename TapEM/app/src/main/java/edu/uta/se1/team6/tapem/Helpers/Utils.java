package edu.uta.se1.team6.tapem.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import edu.uta.se1.team6.tapem.Core.AppDetails;
import edu.uta.se1.team6.tapem.Models.EventDTO;
import edu.uta.se1.team6.tapem.Models.UserDTO;
import edu.uta.se1.team6.tapem.Models.VenueModel;
import edu.uta.se1.team6.tapem.R;

/**
 * Created by yashodhan on 3/23/18.
 */

public class Utils {

    public static int getRandomColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        color = Color.parseColor("#00B282");
        return color;
    }

    public static void saveSharedPrefs(Activity activity, String key, String update_data) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, update_data);
        Log.d("User", "saveSharedPrefs: " + key + " : " + update_data);
        editor.commit();
    }

    public static  String readSharedPrefs(Activity activity, String key) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "";
        String result= sharedPref.getString(key, defaultValue);
        return result;
    }

    public static void removeKeySharedPrefs(Activity activity, String key){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(key);
        editor.apply();
    }

    public static UserDTO initiateDummy(String MavID, String type){
        UserDTO user = new UserDTO(MavID, "Steve", "Rogers", "ACTIVE",
                new java.util.Date().toString(), type, "2018-03-24T20:16:08+00:00", "Male");
        return user;
    }

    public static String generateRandTime(){
        long offset = Timestamp.valueOf("2018-31-03 00:00:00").getTime();
        long end = Timestamp.valueOf("2018-01-05 00:00:00").getTime();
        long diff = end - offset + 1;
        Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
        return rand.toString();
    }

    public static List<EventDTO> getDummyEvents(){
        List<EventDTO> eventsList = new ArrayList<>();
        eventsList.add(new EventDTO("Android Workshop", AppDetails.getActivity().getString(R.string.lorem_ipsum), "ACTIVE", "1001544391", "Steve Rogers", "Maverick Hall", ""));
        eventsList.add(new EventDTO("Campus Music Festival", AppDetails.getActivity().getString(R.string.lorem_ipsum), "ACTIVE", "1001544391", "Steve Rogers", "KC Hall", ""));
        eventsList.add(new EventDTO("UTA MOBI JS Workshop", AppDetails.getActivity().getString(R.string.lorem_ipsum), "ACTIVE", "1001544391", "Steve Rogers", "Arlington Hall", ""));
        return eventsList;
    }

    public static List<EventDTO> getDummyRequests(){
        List<EventDTO> eventsList = new ArrayList<>();
        eventsList.add(new EventDTO("React Web Development", AppDetails.getActivity().getString(R.string.lorem_ipsum), "REQUESTED", "1001544391", "Steve Rogers", "KC Hall", ""));
        eventsList.add(new EventDTO("Charity Event", AppDetails.getActivity().getString(R.string.lorem_ipsum), "REQUESTED", "1001544391", "Steve Rogers", "KC Hall", ""));
        eventsList.add(new EventDTO("Weekend Party", AppDetails.getActivity().getString(R.string.lorem_ipsum), "REQUESTED", "1001544391", "Steve Rogers", "KC Hall", ""));
        return eventsList;
    }



    public  static List<VenueModel> getVenues() {
        List<VenueModel> venuesList = new ArrayList<>();
        venuesList.add(new VenueModel("Select Hall", 0));
        venuesList.add(new VenueModel("Maverick, hall", 100));
        venuesList.add(new VenueModel("KC hall", 25));
        venuesList.add(new VenueModel("Arlington hall", 50));
        venuesList.add(new VenueModel("Shard, hall", 25));
        venuesList.add(new VenueModel("Liberty. hall", 75));
        return venuesList;
    }

    public static List<UserDTO> getDummyRegs(){
        List<UserDTO> userDTOS = new ArrayList<>();
        userDTOS.add(new UserDTO("1122334455", "Alex", "Chan", "PENDING", new Date().toString(), "GENERAL", new Date().toString(), "Male"));
        userDTOS.add(new UserDTO("2233445566", "Rupert", "Grint", "PENDING", new Date().toString(), "CATERER", new Date().toString(), "Male"));
        userDTOS.add(new UserDTO("3344556677", "Ashley", "Greene", "PENDING", new Date().toString(), "STAFF", new Date().toString(), "Female"));
        userDTOS.add(new UserDTO("4455667788", "Max", "Ramon", "PENDING", new Date().toString(), "GENERAL", new Date().toString(), "Female"));
        return userDTOS;
    }

    public static List<UserDTO> getDummyUsers(){
        List<UserDTO> userDTOS = new ArrayList<>();
        userDTOS.add(new UserDTO("1122334455", "Alex", "Chan", "ACTIVE", new Date().toString(), "GENERAL", new Date().toString(), "Male"));
        userDTOS.add(new UserDTO("2233445566", "Rupert", "Grint", "ACTIVE", new Date().toString(), "CATERER", new Date().toString(), "Male"));
        userDTOS.add(new UserDTO("3344556677", "Ashley", "Greene", "ACTIVE", new Date().toString(), "STAFF", new Date().toString(), "Female"));
        userDTOS.add(new UserDTO("4455667788", "Max", "Ramon", "ACTIVE", new Date().toString(), "GENERAL", new Date().toString(), "Female"));
        return userDTOS;
    }

    public static List<String> getUserTypes(){
        List<String> userTypes = new ArrayList<>();
        userTypes.add("General");
        userTypes.add("Caterer");
        userTypes.add("Caterer staff");
        userTypes.add("Admin");
        return userTypes;
    }

    public static List<String> getStaff() {
        List<String> dummyStaff = new ArrayList<>();
        dummyStaff.add("Tim");
        dummyStaff.add("Martha");
        dummyStaff.add("Bob");
        dummyStaff.add("Jerry");
        dummyStaff.add("Jack");
        return dummyStaff;
    }

    public static List<String> getFoodTypes() {
        List<String> foodTypes = new ArrayList<>();
        foodTypes.add("Please select the food type");
        foodTypes.add("American");
        foodTypes.add("Chinese");
        foodTypes.add("French");
        foodTypes.add("Greek");
        foodTypes.add("Indian");
        foodTypes.add("Italian");
        foodTypes.add("Japanese");
        foodTypes.add("Mexican");
        foodTypes.add("Pizza");
        return foodTypes;
    }
}
