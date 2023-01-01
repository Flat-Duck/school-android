package ly.smarthive.school.activity;

import static ly.smarthive.school.COMMON.USER_TOKEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import ly.smarthive.school.AppController;
import ly.smarthive.school.COMMON;
import ly.smarthive.school.MyDividerItemDecoration;
import ly.smarthive.school.R;
import ly.smarthive.school.SessionManager;
import ly.smarthive.school.adapter.TimeTableDataAdapter;
import ly.smarthive.school.models.TimeTable;

public class TimeTableActivity extends AppCompatActivity {

    private static final String TAG = TimeTableActivity.class.getSimpleName();
    private final List<TimeTable> time_tablesList = new ArrayList<>();
    private TimeTableDataAdapter mAdapter;
    SessionManager sessionManager;
    Context context;
    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        mAdapter = new TimeTableDataAdapter(time_tablesList, this);
        sessionManager = new SessionManager(this);
        int SId = sessionManager.getStudentId();
        URL = COMMON.getUrl("time_tables",SId);
        context = this;

        RecyclerView recyclerView = findViewById(R.id.time_tables_rv);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);

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
            GrabAllRequests();
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void GrabAllRequests() {
        JsonObjectRequest jsonReq = new JsonObjectRequest(com.android.volley.Request.Method.GET,   URL, null, response -> {
            VolleyLog.e(TAG, "Response: " + response.toString());
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
        time_tablesList.clear();
        try {
            JSONArray feedArray = response.getJSONArray("data");
            JSONObject room = (JSONObject) feedArray.get(0);
            JSONObject detail = room.getJSONObject("details");
            Iterator<String> iterator = detail.keys();
            while(iterator.hasNext()){
                String day_name = iterator.next();
                TimeTable time_table = new TimeTable();
                time_table.setDay(day_name);
                JSONObject day_classes = detail.getJSONObject(day_name);
                Iterator<String> iterator2 = day_classes.keys();
                while(iterator2.hasNext()) {
                    String class_name =  iterator2.next();
                    JSONObject clas = day_classes.getJSONObject(class_name);
                    time_table.setSubs(clas.getString("subject"));
                }
                time_tablesList.add(time_table);
                mAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}