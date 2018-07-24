package cred.io.sdk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProblemAdapter extends ArrayAdapter<Problem> {

    private Context context;
    private List<Problem> problemList;

    public ProblemAdapter(@NonNull Context context, ArrayList<Problem> problemList) {
        super(context, 0, problemList);
        this.context = context;
        this.problemList = problemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);

        Problem current_Problem = problemList.get(position);

        TextView problem_description = (TextView) listItem.findViewById(R.id.problem_description);

        problem_description.setText(current_Problem.getProblem_description());

        return listItem;
    }
}
