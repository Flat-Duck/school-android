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
import ly.smarthive.school.models.Exam;

public class ExamsDataAdapter extends RecyclerView.Adapter<ExamsDataAdapter.MyViewHolder> {
    private Context context;
    private List<Exam> examsList;

    public ExamsDataAdapter(List<Exam> ExamsList, Context context) {
        this.context = context;
        this.examsList = ExamsList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView subject, period, date;


        public MyViewHolder(View view) {
            super(view);
            subject = view.findViewById(R.id.subject_text);
            period = view.findViewById(R.id.period_text);
            date = view.findViewById(R.id.date_text);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exam, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Exam exam = examsList.get(position);
        holder.subject.setText(exam.getSubject());
        holder.period.setText(  "الاولى" );// + exam.getPeriod());
        holder.date.setText(exam.getDate());
    }


    @Override
    public int getItemCount() {
        return examsList.size();
    }

}