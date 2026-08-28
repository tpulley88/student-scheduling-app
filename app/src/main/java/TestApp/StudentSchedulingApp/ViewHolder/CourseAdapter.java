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
import TestApp.StudentSchedulingApp.UI.ViewCourseDetail;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseVH> {

    private List<Course> courseList;
    private final Context context;
    private final LayoutInflater aInflate;

    class CourseVH extends RecyclerView.ViewHolder {

        private final TextView courseTitle;
        private final TextView courseStartDate;
        private final TextView courseEndDate;
        private final TextView courseStatus;


        private CourseVH(@NonNull View itemView) {
            super(itemView);

            courseTitle = itemView.findViewById(R.id.courseTitle);
            courseStartDate = itemView.findViewById(R.id.courseStartDate);
            courseEndDate = itemView.findViewById(R.id.courseEndDate);
            courseStatus = itemView.findViewById(R.id.courseStatus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course current = courseList.get(position);
                    Intent intent = new Intent(context, ViewCourseDetail.class);
                    intent.putExtra("Course ID", current.getCourseID());
                    intent.putExtra("Course Title", current.getCourseTitle());
                    intent.putExtra("Start Date", current.getStartDate().toEpochDay());
                    intent.putExtra("End Date", current.getEndDate().toEpochDay());
                    intent.putExtra("Course Status", current.getCourseStatus());
                    intent.putExtra("Instructor ID", current.getInstrID());
                    intent.putExtra("Term ID", current.getTermID());
                    intent.putExtra("Course Notes", current.getCourseNotes());
                    context.startActivity(intent);
                }
            });
        }

    }

    public CourseAdapter(Context context) {
        aInflate = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = aInflate.inflate(R.layout.course_list_item, parent, false);
        return new CourseAdapter.CourseVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseVH holder, int position) {

        if (courseList != null) {
            Course current = courseList.get(position);

            holder.courseTitle.setText(current.getCourseTitle());
            holder.courseStartDate.setText(current.getStartDate().toString());
            holder.courseEndDate.setText(current.getEndDate().toString());
            holder.courseStatus.setText(current.getCourseStatus());

        } else {
            // Covers the case of data not being ready yet.
            holder.courseTitle.setText("No Courses");
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCourses(List<Course> course) {
        courseList = course;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        try {
            return courseList.size();
        } catch (Exception e) {
            return 0;
        }
    }
}
