package cred.io.sdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapters.ProblemAdapter;
import adapters.ThreadAdapter;
import chat.Message;
import network.ChatAPI;
import pojos.Problem;

public class ChatActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private ArrayList<Message> messages;

    private ImageButton buttonSend;

    private EditText editTextMessage, suggestionText;

    private ListView listView;

    private ImageButton yes, no;

    private Button suggestionButton, ratingSubmit;

    private RatingBar ratingBar;

    private ChatAPI chatAPI;

    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String context = getIntent().getStringExtra("transaction");

        chatAPI = new ChatAPI(ChatActivity.this);

        gson = new Gson();

        initViews();
        initRating();
        initSuggestionForm();
        initYesorno();

        routeTxn(context);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        buttonSend = findViewById(R.id.buttonSend);
        editTextMessage = findViewById(R.id.editTextMessage);
        viewFlipper = findViewById(R.id.myViewFlipper);
        listView = findViewById(R.id.listview);

        yes = findViewById(R.id.yesbutton);

        no = findViewById(R.id.nobutton);

        suggestionButton = findViewById(R.id.submitSuggestion);

        ratingSubmit = findViewById(R.id.submitrating);

        ratingBar = findViewById(R.id.ratingbar);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        messages = new ArrayList<>();

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(editTextMessage.getText().toString(), "", "");
            }
        });
    }

    private void processMessage(String name, String message, String id) {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        Message message1 = new Message(Integer.valueOf(id), message, date, "CRED");
        messages.add(message1);
        adapter.notifyDataSetChanged();
        scrollToBottom();

    }

    private void scrollToBottom() {
        adapter.notifyDataSetChanged();
        if (adapter.getItemCount() > 1)
            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, adapter.getItemCount() - 1);
    }

    private void sendMessage(String message, String next_status, String reply) {
        //final String message = editTextMessage.getText().toString().trim();
        if (message.equalsIgnoreCase(""))
            return;


        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        Message message1 = new Message(101, message, date,"");
        messages.add(message1);
        initAdapter();
        adapter.notifyDataSetChanged();

        scrollToBottom();

        editTextMessage.setText("");

        if (TextUtils.isEmpty(reply)) {
            return;
        }

        next_stage(next_status, reply);

        //processMessage("CRED", "Hey!, thanks for reaching out! What issue are you facing?", "102");

    }

    private void initAdapter() {
        adapter = new ThreadAdapter(this, messages, 101);
        recyclerView.setAdapter(adapter);
    }


    private void showChatview() {
        viewFlipper.setDisplayedChild(0);
    }

    private void showListView(String problemType) {
        viewFlipper.setDisplayedChild(1);

        initListViewAdapter(problemType);

    }

    private void showLikeorNot() {
        viewFlipper.setDisplayedChild(2);
    }

    private void showRatingBar() {
        viewFlipper.setDisplayedChild(3);
    }

    private void showSuggestioninput() {
        viewFlipper.setDisplayedChild(4);
    }

    private void initListViewAdapter(String problemType) {
        ArrayList<Problem> problems = new ArrayList<>();

        JSONObject problemJson = null;

        String next_state = null;

        try {
            Type listType = new TypeToken<List<Problem>>(){}.getType();
            problemJson = new JSONObject(chatAPI.getProblemList(problemType));
            next_state = problemJson.getString("next_state");
            problems = gson.fromJson(problemJson.getJSONArray("data").toString(), listType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ProblemAdapter adapter = new ProblemAdapter(this, problems);
        listView.setAdapter(adapter);

        final ArrayList<Problem> finalProblems = problems;
        final String finalNext_state = next_state;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sendMessage(finalProblems.get(i).getProblem_description(), finalNext_state, "Ok, we see that the transaction failed because the bank page timed out.\nYou should get your money back within 8-10 working days.");
            }
        });
    }

    private void initRating() {
        ratingSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rating = String.valueOf(ratingBar.getRating());
                Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initYesorno() {
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Like", Toast.LENGTH_LONG).show();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Dislike", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initSuggestionForm() {
        suggestionText = findViewById(R.id.suggestionText);
        suggestionButton = findViewById(R.id.submitSuggestion);

        suggestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(suggestionText.getText().toString())) {
                    return;
                }

                Toast.makeText(getApplicationContext(), suggestionText.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void routeTxn(String context) {
        JSONObject contextJson = null;
        try {
            contextJson = new JSONObject(context);
            if (TextUtils.equals(contextJson.getString("type"), "Checkout on")) {
                String message = "TxnId=" + contextJson.getString("txnId") + "\n" + "Status=" + contextJson.getString("status");
                sendMessage(message, "", "Hey, thanks for reaching out!\nWhat issue are you facing?");
                showListView(contextJson.getString("type"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void next_stage(String next_state, String reply) {
        if (TextUtils.equals(next_state, "showfeedback")) {
            showLikeorNot();
        }

        if (TextUtils.equals(next_state, "showRating")) {
            showRatingBar();
        }

        if (!TextUtils.isEmpty(reply)) {
            processMessage("CRED", reply, "102");
        }

    }

}
