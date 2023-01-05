package ly.smarthive.school.activity;

import static ly.smarthive.school.COMMON.USER_TOKEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ly.smarthive.school.AppController;
import ly.smarthive.school.COMMON;
import ly.smarthive.school.R;
import ly.smarthive.school.SessionManager;
import ly.smarthive.school.Util;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    CardView subjectBtn,examsBtn,timeTableBtn,marksBtn,attendanceBtn,noteBtn;
    TextView name,grade,room, number;
    SessionManager sessionManager;
    Context context;
    String URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);
        int SId = sessionManager.getStudentId();
        URL = COMMON.getUrl("student",SId);
        context = this;

        name = findViewById(R.id.student_name);
        grade = findViewById(R.id.student_grade);
        room = findViewById(R.id.student_room);
        number = findViewById(R.id.student_number);

        subjectBtn = findViewById(R.id.cvSubject);
        examsBtn = findViewById(R.id.cvExam);
        timeTableBtn = findViewById(R.id.cvTimeTable);
        marksBtn = findViewById(R.id.cvMarks);
        attendanceBtn = findViewById(R.id.cvAttendance);
        noteBtn = findViewById(R.id.cvNotes);


        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL);
        if (entry != null) {
            String data = new String(entry.data, StandardCharsets.UTF_8);
            try {
                parseJsonFeed(new JSONObject(data));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            getStudentData();
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        timeTableBtn.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this,TimeTableActivity.class);
            startActivity(intent);
        });
        subjectBtn.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this,SubjectsActivity.class);
            startActivity(intent);

        });
        marksBtn.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this,MarksActivity.class);
            startActivity(intent);

        });
        examsBtn.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this,ExamsActivity.class);
            startActivity(intent);

        });
        attendanceBtn.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this,AttendancesActivity.class);
            startActivity(intent);

        });

        noteBtn.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this,NotesActivity.class);
            startActivity(intent);

        });

    }

    private void getStudentData() {

            JsonObjectRequest jsonReq = new JsonObjectRequest(com.android.volley.Request.Method.GET,   URL, null, response -> {
                VolleyLog.d(TAG, "Response: " + response.toString());
                Log.e("RE", response.toString());
                parseJsonFeed(response);
            }, error -> Log.d("VOLLEY ERROR", error.toString())) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headerMap = new HashMap<>();
                    headerMap.put("Content-Type", "application/json");
                    headerMap.put("Authorization", "Bearer " + USER_TOKEN);
                    return headerMap;
                }
            };
            AppController.getInstance().addToRequestQueue(jsonReq);
        }

        /**
         * Parsing json response and passing the data to feed view list adapter
         **/
        private void parseJsonFeed(JSONObject response) {


            try {
                JSONObject obj = response.getJSONObject("data");

                name.setText(obj.getString("name"));
                grade.setText(obj.getString("grade_name"));
                room.setText(obj.getString("room_name"));
                number.setText("765318726");
        } catch (JSONException e) {
                e.printStackTrace();
            }

        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            sessionManager.setLogin(false);
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finishAndRemoveTask();
            //MainActivity.this.finish(); // if the activity running has it's own context
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}