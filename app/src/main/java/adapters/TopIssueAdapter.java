package adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cred.io.sdk.R;
import pojos.Issues;

public class TopIssueAdapter extends ArrayAdapter<Issues> {

    private Context context;

    private ArrayList<Issues> arrayList;

    public TopIssueAdapter(Context context, ArrayList<Issues> issuesArrayList ){
        super(context, 0, issuesArrayList);
        this.arrayList = issuesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.help_list_item,parent,false);

        Issues issue = arrayList.get(position);

        TextView problem_description = listItem.findViewById(R.id.problem_description);

        ImageView problem_image = listItem.findViewById(R.id.leftImage);

        problem_image.setImageURI(Uri.parse(issue.getUrl()));

        problem_description.setText(issue.getDescription());

        return listItem;
    }

    
}
