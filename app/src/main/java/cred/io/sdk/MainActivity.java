package cred.io.sdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ViewFlipper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import chat.Message;
import chat.ThreadAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    //Recyclerview objects
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    //ArrayList of messages to store the thread messages
    private ArrayList<Message> messages;

    //Button to send new message on the thread
    private ImageButton buttonSend;

    //EditText to send new message on the thread
    private EditText editTextMessage;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setView();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        buttonSend = findViewById(R.id.buttonSend);
        editTextMessage = findViewById(R.id.editTextMessage);
        viewFlipper = findViewById(R.id.myViewFlipper);
        listView = findViewById(R.id.listview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        messages = new ArrayList<>();

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
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

        showListView();
    }

    private void scrollToBottom() {
        adapter.notifyDataSetChanged();
        if (adapter.getItemCount() > 1)
            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, adapter.getItemCount() - 1);
    }

    private void sendMessage() {
        final String message = editTextMessage.getText().toString().trim();
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

        processMessage("CRED", "Hey there, thanks for reaching out! We are looking into your issue.", "102");

    }

    private void initAdapter() {
        adapter = new ThreadAdapter(this, messages, 101);
        recyclerView.setAdapter(adapter);
    }

    private void setView(){
        showChatview();
    }

    private void showChatview() {
        viewFlipper.setDisplayedChild(0);
    }

    private void showListView() {
        viewFlipper.setDisplayedChild(1);

        initListViewAdapter();

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

    private void initListViewAdapter() {
        ArrayList<Problem> problems = new ArrayList<>();
        Problem problem = new Problem("1", "Account Debited but Bill Still pending");
        Problem problem2 = new Problem("2", "Money debited multiple times");
        Problem problem3 = new Problem("3", "Biller dashboard doesn't reflect");
        problems.add(problem);
        problems.add(problem2);
        problems.add(problem3);

        ProblemAdapter adapter = new ProblemAdapter(this, problems);
        listView.setAdapter(adapter);
    }

}
