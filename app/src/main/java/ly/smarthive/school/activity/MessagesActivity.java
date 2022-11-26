package ly.smarthive.school.activity;

import static ly.smarthive.school.COMMON.CHATS_URL;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ly.smarthive.school.AppController;
import ly.smarthive.school.R;
import ly.smarthive.school.SessionManager;
import ly.smarthive.school.adapter.MessagesDataAdapter;
import ly.smarthive.school.models.Message;
public class MessagesActivity extends AppCompatActivity {
    private static final String TAG = MessagesActivity.class.getSimpleName();
    private EditText editText;
    private List<Message> chatMessages = new ArrayList<>();
    private MessagesDataAdapter adapter;
    SessionManager session;

    String token;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        adapter = new MessagesDataAdapter( chatMessages,this);
        session = new SessionManager(this);
        token = session.getToken();
        editText = findViewById(R.id.edit_message);

        RecyclerView recyclerView = findViewById(R.id.recycler_chat);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        ImageButton btnSend = findViewById(R.id.btn_chat_send);
        btnSend.setOnClickListener(v -> sendMessage());

        getChats();
    }

    private void getChats() {
        chatMessages.clear();
        adapter.notifyDataSetChanged();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, CHATS_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray chats = response.getJSONObject("data").getJSONArray("chats");
                    parseChats(chats);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> Log.d("VOLLEY ERROR", error.toString())) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("Authorization", "Bearer " + token);
                return headerMap;

            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "chats");
    }

    private void parseChats(JSONArray chats) {
        for (int i = 0; i < chats.length(); i++) {
            try {
                JSONObject chat = chats.getJSONObject(i);
                makeChatObj(chat);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        adapter.notifyDataSetChanged();
    }

    private Message makeChatObj(JSONObject chat) {
        Message chatMessage = new Message();
        try {
            chatMessage.setId(chat.getInt("id"));
            chatMessage.setMessage(chat.getString("message"));
            chatMessage.setDate(chat.getString("date"));
            chatMessage.setMine(chat.getBoolean("ismine"));
            chatMessages.add(chatMessage);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void sendMessage() {
        if (editText.getText().toString().trim().equals("")) {
            Toast.makeText(MessagesActivity.this, "please_enter_message", Toast.LENGTH_SHORT).show();
        } else {
            String message = editText.getText().toString();
            syncMessage(message);
            getChats();
        }
    }

    private void syncMessage(final String message) {
        JSONObject params = new JSONObject();
        try {
            params.put("message", message);
            params.put("admin_id", 1);
            params.put("Content-Type", "application/json");
            params.put("Accept", "application/json");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, CHATS_URL, params, response -> {



        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("Accept", "application/json");
                headerMap.put("Authorization", "Bearer " + token);
                return headerMap;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "chat");
    }
}