package ly.smarthive.school;

public class COMMON {

    public static  String BASE_URL = "http://10.0.2.2/school/public/api/";
    //public static  String BASE_URL = "http://10.0.2.2/school/public/api/";
    public static  String LOGIN_URL = BASE_URL + "login";
    public static  int STUDENT_ID ;

    public static  String ATTENDANCE_URL = "/attendances";
    public static  String SUBJECTS_URL = "/subjects";
    public static  String EXAMS_URL = "/exams";
    public static  String CHATS_URL = BASE_URL + "chats";
    public static  String STUDENTS_URL = BASE_URL + "main";

    public static  String UPDATE_PASSWORD_URL = BASE_URL + "/password/";

    public static  String CURRENT_USER_EMAIL;
    public static  String CURRENT_USER_PASSWORD;
    public static  String USER_TOKEN ;
    // Shared pref mode
    public static final String PREF_NAME = "school_app";
    public static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    public static final String KEY_TOKEN = "accessToken";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_STATUS = "stat";
    public static final String KEY_STUDENT_ID = "student_id";
    public static final String KEY_EMAIL = "email";

    private static final String ATT = "attendances";
    public static final String SUB =  "subjects";
    public static final String EXA = "exams";
    public static final String STU = "student";

    public static String getUrl(String name,int id){

        switch (name){
            case ATT:
                return BASE_URL + id + ATTENDANCE_URL;
            case SUB:
                return BASE_URL + id + SUBJECTS_URL;
            case EXA:
                return BASE_URL + id + EXAMS_URL;
            case STU:
                return BASE_URL + id;
            //case TIT:
              //  return STUDENTS_URL;
            default:
                return "";
        }
    }
}
