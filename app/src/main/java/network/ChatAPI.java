package network;

import android.app.Activity;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;

public class ChatAPI {

    Activity activity;

    public ChatAPI(Activity activity) {
        this.activity = activity;
    }


    public String getTxnHistory() {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("getTxn.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String getProblemList(String problemType) {
        if (TextUtils.equals(problemType, "Checkout on")) {
            return getCheckout_Problems();
        }

        return null;
    }

    public String getCheckout_Problems() {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("checkout_problems.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
