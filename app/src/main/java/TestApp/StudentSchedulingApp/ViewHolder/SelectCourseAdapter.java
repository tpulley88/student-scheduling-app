package TestApp.StudentSchedulingApp.ViewHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import TestApp.StudentSchedulingApp.Entities.Course;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.UI.AssessmentsByCourse;

public class SelectCourseAdapter extends RecyclerView.Adapter<SelectCourseAdapter.SelectCourseVH> {

    private List<Course> courseList;
    private final Context context;
    private final LayoutInflater aInflate;

    class SelectCourseVH extends RecyclerView.ViewHolder{

        private final TextView courseTitle;
        private final TextView courseStartDate;
        private final TextView courseEndDate;
        private final TextView courseProgress;


        private SelectCourseVH(@NonNull View itemView) {
            super(itemView);

            courseTitle = itemView.findViewById(R.id.courseTitle);
            courseStartDate = itemView.findViewById(R.id.courseStartDate);
            courseEndDate = itemView.findViewById(R.id.courseEndDate);
            courseProgress = itemView.findViewById(R.id.courseStatus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course current = courseList.get(position);
                    Intent intent = new Intent(context, AssessmentsByCourse.class);
                    intent.putExtra("Course ID", current.getCourseID());
                    intent.putExtra("Course Title", current.getCourseTitle());
                    context.startActivity(intent);
                }
            });
        }
    }

    public SelectCourseAdapter(Context context) {
        aInflate = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public SelectCourseAdapter.SelectCourseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = aInflate.inflate(R.layout.course_list_item, parent, false);
        return new SelectCourseAdapter.SelectCourseVH(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull SelectCourseAdapter.SelectCourseVH holder, int position) {

        if (courseList != null) {
            Course current = courseList.get(position);

            holder.courseTitle.setText(current.getCourseTitle());
            holder.courseStartDate.setText(current.getStartDate().toString());
            holder.courseEndDate.setText(current.getEndDate().toString());
            holder.courseProgress.setText(current.getCourseStatus());

        } else {
            // Covers the case of data not being ready yet.
            holder.courseTitle.setText("No Terms");
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setTerms(List<Course> term) {
        courseList = term;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        try {
            return courseList.size();
        } catch (Exception e) {
            return 0;
        }    }

}
