package edu.uta.se1.team6.tapem.Core;

/**
 * Created by yashodhan on 3/23/18.
 */

public interface AppConstrants {

    //	String SERVER_BASE_URL = "";
    String SERVER_BASE_URL = "http://18.236.26.234:8000/";
    String SERVER_POST_URL = SERVER_BASE_URL+"api/";

//	public static final String SERVER_POST_URL = "http://192.168.43.83:8080/GoStickies-b2b/api/";

    boolean IS_MOCK_AUTH = false;
    String STATUS_BLOCKED = "BLOCKED";
    String STATUS_ACTIVE = "ACTIVE";

    String AMAZONAWS_SERVER_POST_URL = "";

    String DEVELOPMENT = "development";
    String PRODUCTION = "production";

}
