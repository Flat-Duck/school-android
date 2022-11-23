package ly.smarthive.school.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import ly.smarthive.school.R;
import ly.smarthive.school.models.Subject;

public class SubjectsDataAdapter extends RecyclerView.Adapter<SubjectsDataAdapter.MyViewHolder> {
    private final Context context;
    private final List<Subject> subjectList;

    public SubjectsDataAdapter(List<Subject> subjectList, Context context) {
        this.context = context;
        this.subjectList = subjectList;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Subject subject = subjectList.get(position);
        holder.name.setText(subject.getName());
    }

    @Override
    public int getItemCount() {

        return subjectList.size();
    }
}