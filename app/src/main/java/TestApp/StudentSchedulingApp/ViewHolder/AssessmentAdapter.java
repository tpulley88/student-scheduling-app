package TestApp.StudentSchedulingApp.ViewHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import TestApp.StudentSchedulingApp.Entities.Assessment;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.UI.ViewAssessmentDetail;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentVH> {

    private List<Assessment> assessmentList;
    private final Context context;
    private final LayoutInflater aInflate;

    class AssessmentVH extends RecyclerView.ViewHolder {

        private final TextView assessmentTitle;
        private final TextView assessmentStart;
        private final TextView assessmentEnd;
        private final CheckBox objectiveCheck;
        private final CheckBox performanceCheck;

        private AssessmentVH(View itemView) {
            super(itemView);
            assessmentTitle = itemView.findViewById(R.id.assessmentTitle);
            assessmentStart = itemView.findViewById(R.id.assessmentStartDate);
            assessmentEnd = itemView.findViewById(R.id.assessmentEndDate);
            objectiveCheck = itemView.findViewById(R.id.objCheck);
            performanceCheck = itemView.findViewById(R.id.perfCheck);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment current = assessmentList.get(position);
                    Intent intent = new Intent(context, ViewAssessmentDetail.class);
                    intent.putExtra("Assessment ID", current.getAssessID());
                    intent.putExtra("Assessment Title", current.getAssessTitle());
                    intent.putExtra("Start Date", current.getStartDate().toEpochDay());
                    intent.putExtra("End Date", current.getEndDate().toEpochDay());
                    intent.putExtra("Type", current.isTypeObjective());
                    intent.putExtra("Term ID", current.getTermID());
                    intent.putExtra("Course ID", current.getCourseID());
                    intent.putExtra("Instructor ID", current.getInstructorID());
                    context.startActivity(intent);
                }
            });
        }

    }

    public AssessmentAdapter(Context context) {
        aInflate = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = aInflate.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentVH holder, int position) {

        if (assessmentList != null) {
            Assessment current = assessmentList.get(position);

            holder.assessmentTitle.setText(current.getAssessTitle());
            holder.assessmentStart.setText(current.getStartDate().toString());
            holder.assessmentEnd.setText(current.getEndDate().toString());

            if (current.isTypeObjective()) {

                holder.objectiveCheck.toggle();

            } else {

                holder.performanceCheck.toggle();
            }


        } else {
            // Covers the case of data not being ready yet.
            holder.assessmentTitle.setText("No Assessments");
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setAssessments(List<Assessment> assess) {
        assessmentList = assess;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        try {
            return assessmentList.size();
        } catch (Exception e) {
            return 0;

        }
    }




}