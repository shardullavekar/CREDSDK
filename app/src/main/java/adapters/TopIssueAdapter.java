package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import pojos.Issues;

public class TopIssueAdapter extends ArrayAdapter<Issues> {
    public TopIssueAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
