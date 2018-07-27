package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import pojos.Issues;

public class OtherIssueAdapter extends ArrayAdapter<Issues> {

    public OtherIssueAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
