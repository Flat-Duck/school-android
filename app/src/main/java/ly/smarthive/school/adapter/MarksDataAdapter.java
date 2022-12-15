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
import ly.smarthive.school.models.Mark;

public class MarksDataAdapter extends RecyclerView.Adapter<MarksDataAdapter.MyViewHolder> {
    private final Context context;
    private final List<Mark> markList;

    public MarksDataAdapter(List<Mark> markList, Context context) {
        this.context = context;
        this.markList = markList;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,val_1,val_2,total;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.subject_text);
            val_1 = view.findViewById(R.id.val_1_text);
            val_2 = view.findViewById(R.id.val_2_text);
            total = view.findViewById(R.id.total_text);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mark, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Mark mark = markList.get(position);
        holder.name.setText(mark.getName());
        holder.val_1.setText(""+mark.getVal_one());
        holder.val_2.setText(""+mark.getVal_two());
        int x = mark.getVal_one() + mark.getVal_two();
        holder.total.setText(""+x+"");
    }

    @Override
    public int getItemCount() {

        return markList.size();
    }
}