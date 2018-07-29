package cred.io.sdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.TxnAdapter;
import network.ChatAPI;
import pojos.Transaction;

public class TxnHistory extends AppCompatActivity {

    RecyclerView rv;

    LinearLayoutManager llm;

    List<Transaction> txnList;

    TxnAdapter txnAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txn_history);

        rv = findViewById(R.id.rv);

        txnList = new ArrayList<>();

        initCardview();

        getTxnhistory();

        setTxnAdapter();
    }

    private void initCardview() {
        rv.setHasFixedSize(true);

        llm = new LinearLayoutManager(getApplicationContext());

        rv.setLayoutManager(llm);

    }

    private void getTxnhistory() {
        ChatAPI chatAPI = new ChatAPI(TxnHistory.this);

        String txnhistory = chatAPI.getTxnHistory();

        JSONObject txnJson = null;

        try {
            txnJson = new JSONObject(txnhistory);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray txnArray = txnJson.getJSONArray("data");
            for (int i = 0; i < txnArray.length(); i++) {
                JSONObject row = txnArray.getJSONObject(i);
                Transaction transaction = new Transaction();
                transaction.setAmount(row.getString("amount"));
                transaction.setInstrument(row.getString("instrument"));
                transaction.setCurrency(row.getString("currency"));
                transaction.setMerchant_name(row.getString("merchant_name"));
                transaction.setNature(row.getString("nature"));
                transaction.setType(row.getString("type"));
                transaction.setTimeStamp(row.getString("timestamp"));
                transaction.setStatus(row.getString("status"));
                transaction.setTxnId(row.getString("txnId"));
                transaction.setReply(txnJson.getString("reply"));
                txnList.add(transaction);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void setTxnAdapter() {
        txnAdapter = new TxnAdapter(txnList, getApplicationContext());

        rv.setAdapter(txnAdapter);
    }
}
