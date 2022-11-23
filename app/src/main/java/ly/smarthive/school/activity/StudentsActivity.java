package ly.smarthive.school.activity;

import static ly.smarthive.school.COMMON.STUDENTS_URL;

import static ly.smarthive.school.COMMON.STUDENT_ID;
import static ly.smarthive.school.COMMON.USER_TOKEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ly.smarthive.school.AppController;
import ly.smarthive.school.COMMON;
import ly.smarthive.school.MyDividerItemDecoration;
import ly.smarthive.school.R;
import ly.smarthive.school.SessionManager;
import ly.smarthive.school.adapter.StudentsDataAdapter;

import ly.smarthive.school.models.Student;


public class StudentsActivity extends AppCompatActivity implements StudentsDataAdapter.SelectedItem {

    private static final String TAG = StudentsActivity.class.getSimpleName();

    private final List<Student> studentsList = new ArrayList<>();
    private StudentsDataAdapter mAdapter;
    Context context;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        mAdapter = new StudentsDataAdapter(studentsList, this,this);
        context = this;
        session = new SessionManager(this);
        USER_TOKEN = session.getToken();
        RecyclerView recyclerView = findViewById(R.id.students_rv);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(STUDENTS_URL);
        if (entry != null) {
            String data = new String(entry.data, StandardCharsets.UTF_8);
            try {
                parseJsonFeed(new JSONObject(data));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            GetStudents();
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }




    private void GetStudents() {
        JsonObjectRequest jsonReq = new JsonObjectRequest(com.android.volley.Request.Method.GET, STUDENTS_URL, null, response -> {
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

    @SuppressLint("NotifyDataSetChanged")
    private void parseJsonFeed(JSONObject response) {
        studentsList.clear();
        try {
            JSONArray feedArray = response.getJSONArray("data");
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
                Student student = new Student();
                student.setId(feedObj.getInt("id"));
                student.setName(feedObj.getString("name"));
                student.setGrade(feedObj.getString("grade_name") + " / "+ feedObj.getString("room_name"));
                studentsList.add(student);
                mAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectedItem(Student student) {
        Log.e(TAG,student.getName());
        STUDENT_ID = student.getId();
        session.setStudentId(STUDENT_ID);
        STUDENT_ID = session.getStudentId();
        Intent intent = new Intent(StudentsActivity.this,MainActivity.class);
        startActivity(intent);
//        String ACTION_URL = REQUESTS_URL + request.getId() + (accept ? ACCEPT_REQUEST_URL : REFUSE_REQUEST_URL);
//        JsonObjectRequest jsonReq = new JsonObjectRequest(com.android.volley.Request.Method.POST, ACTION_URL, null, response -> {
//            VolleyLog.d(TAG, "Response: " + response.toString());
//            Log.e("RE", response.toString());
//            try {
//                if (response.getBoolean("success")) {
//                    Toast.makeText(context, "The request has been Updated", Toast.LENGTH_LONG).show();
//                    GrabAllRequests();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }, error -> Log.d("VOLLEY ERROR", error.toString())) {
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> headerMap = new HashMap<>();
//                headerMap.put("Content-Type", "application/json");
//                headerMap.put("Authorization", "Bearer " + USER_TOKEN);
//                return headerMap;
//            }
//        };
//        AppController.getInstance().addToRequestQueue(jsonReq);
    }
}