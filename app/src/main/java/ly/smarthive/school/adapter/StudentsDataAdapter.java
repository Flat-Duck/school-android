package ly.smarthive.school.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import ly.smarthive.school.R;
import ly.smarthive.school.models.Student;


public class StudentsDataAdapter extends RecyclerView.Adapter<StudentsDataAdapter.MyViewHolder> {
    private final Context context;
    private final List<Student> studentList;
    public SelectedItem selectedItem;
    public StudentsDataAdapter(List<Student> studentList,SelectedItem mSelectedItem,Context context) {
        this.context = context;
        this.studentList = studentList;
        this.selectedItem = mSelectedItem;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView grade;
        public CircularImageView studentImage;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            grade = view.findViewById(R.id.grade);
            studentImage = view.findViewById(R.id.select_student);
            studentImage.setOnClickListener(view1 -> selectedItem.selectedItem(studentList.get(getAdapterPosition())));
        }
    }

    @NonNull
    @Override
    public StudentsDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentsDataAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StudentsDataAdapter.MyViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.name.setText(student.getName());
        holder.grade.setText(student.getGrade());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
    public interface SelectedItem{
        void selectedItem(Student student);
    }
}