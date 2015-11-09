package kz.growit.altynorda;


import android.app.Activity;

public class BaseActivity extends Activity{

    public static final String APP_PREFERENCES = "AltynOrdaPrefs";

    public static final String GAME_PREFERENCES_TOKEN= "Token"; // String
    public static final String GAME_PREFERENCES_EMAIL = "Email"; // String
    public static final String GAME_PREFERENCES_PASSWORD = "Password"; // String
    public static final String GAME_PREFERENCES_DOB = "DOB"; // Long
    public static final String GAME_PREFERENCES_GENDER = "Gender";  // Integer, in array order: Male (1), Female (2), and Undisclosed (0)
    public static final String GAME_PREFERENCES_SCORE = "Score"; // Integer
    public static final String GAME_PREFERENCES_CURRENT_QUESTION = "CurQuestion"; // Integer
    public static final String GAME_PREFERENCES_AVATAR = "Avatar"; // String URL to image
    public static final String GAME_PREFERENCES_FAV_PLACE_NAME = "FavPlaceName"; // String
    public static final String GAME_PREFERENCES_FAV_PLACE_LONG = "FavPlaceLong"; // float
    public static final String GAME_PREFERENCES_FAV_PLACE_LAT = "FavPlaceLat"; // float
    public static final String GAME_PREFERENCES_PLAYER_ID = "ServerId"; // Integer
}
