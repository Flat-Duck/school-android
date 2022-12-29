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
import ly.smarthive.school.models.Note;



public class NotesDataAdapter extends RecyclerView.Adapter<NotesDataAdapter.MyViewHolder> {
    private final Context context;
    private final List<Note> noteList;

    public NotesDataAdapter(List<Note> noteList, Context context) {
        this.context = context;
        this.noteList = noteList;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView content;

        public MyViewHolder(View view) {
            super(view);
            content = view.findViewById(R.id.content);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.content.setText(note.getContent());
    }

    @Override
    public int getItemCount() {

        return noteList.size();
    }
}