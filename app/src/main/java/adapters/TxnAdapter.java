package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import cred.io.sdk.R;
import pojos.Transaction;

public class TxnAdapter extends RecyclerView.Adapter<TxnAdapter.TxnViewHolder> {

    List<Transaction> transactionList;

    Context context;

    @NonNull
    @Override
    public TxnViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.txnhistory, viewGroup, false);
        TxnViewHolder txnViewHolder = new TxnViewHolder(v);
        return txnViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TxnViewHolder txnViewHolder, int i) {

        setImages(transactionList.get(i).getType(), "someKey", txnViewHolder);

        txnViewHolder.merchant_name.setText(transactionList.get(i).getMerchant_name());
        txnViewHolder.txn_nature.setText(transactionList.get(i).getNature());
        txnViewHolder.txnamount.setText(transactionList.get(i).getCurrency() + " " + transactionList.get(i).getAmount());
        txnViewHolder.txnType.setText(transactionList.get(i).getType());
        txnViewHolder.txntimestamp.setText("2PM July 23, 2018");
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class TxnViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txntimestamp, merchant_name, txnType, txnamount, txn_nature;
        ImageView txn_photo, txn_status_image;

        TxnViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            txntimestamp = itemView.findViewById(R.id.txntimestamp);
            merchant_name = itemView.findViewById(R.id.merchant_name);
            txnType = itemView.findViewById(R.id.txnType);
            txnamount = itemView.findViewById(R.id.txnamount);
            txn_nature = itemView.findViewById(R.id.txn_nature);
            txn_photo = itemView.findViewById(R.id.txn_photo);
            txn_status_image = itemView.findViewById(R.id.txn_status_image);
        }
    }

    public TxnAdapter(List<Transaction> transactions, Context context) {
        this.context = context;
        this.transactionList = transactions;
    }

    private String getTimestamp(long time) {
        return null;
    }

    private String decodeNature(String nature) {
        JSONObject natureJson = null;
        String natureString = "";
        try {
             natureJson = new JSONObject(nature);
             for (String key : iterate(natureJson.keys())) {
                 natureString = key + " " + natureJson.getString(key);
             }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return natureString;
    }

    private void setImages(String type, String nature_key, TxnViewHolder txnViewHolder) {
        if (TextUtils.equals(type, "Credit Card Bill Paid")) {
            txnViewHolder.txn_photo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_credit_card_purple_500_36dp));
        }

        if (TextUtils.equals(type, "Payment Received from")) {
            txnViewHolder.txn_photo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_call_received_pink_a200_36dp));
        }

        if (TextUtils.equals(type, "Paid To")) {
            txnViewHolder.txn_photo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_call_made_pink_a200_36dp));
        }

    }

    private <T> Iterable<T> iterate(final Iterator<T> i){
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return i;
            }
        };
    }

}
