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
import ly.smarthive.school.models.TimeTable;

public class TimeTableDataAdapter  extends RecyclerView.Adapter<TimeTableDataAdapter.MyViewHolder> {
    private final Context context;
    private final List<TimeTable> time_tableList;

    public TimeTableDataAdapter(List<TimeTable> time_tableList, Context context) {
        this.context = context;
        this.time_tableList = time_tableList;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView day, class_1,class_2, class_3,class_4, class_5,class_6;

        public MyViewHolder(View view) {
            super(view);
            day = view.findViewById(R.id.day_text);
            class_1 = view.findViewById(R.id.class_1_text);
            class_2 = view.findViewById(R.id.class_2_text);
            class_3 = view.findViewById(R.id.class_3_text);
            class_4 = view.findViewById(R.id.class_4_text);
            class_5 = view.findViewById(R.id.class_5_text);
            class_6 = view.findViewById(R.id.class_6_text);

        }
    }

    @NonNull
    @Override
    public TimeTableDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_table, parent, false);
        return new TimeTableDataAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TimeTableDataAdapter.MyViewHolder holder, int position) {
        TimeTable time_table = time_tableList.get(position);
        holder.day.setText(time_table.getDay());
        holder.class_1.setText(time_table.getSubs(0));
        holder.class_2.setText(time_table.getSubs(1));
        holder.class_3.setText(time_table.getSubs(2));
        holder.class_4.setText(time_table.getSubs(3));
        holder.class_5.setText(time_table.getSubs(4));
        holder.class_6.setText(time_table.getSubs(5));

    }

    @Override
    public int getItemCount() {

        return time_tableList.size();
    }
}