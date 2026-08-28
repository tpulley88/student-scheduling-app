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

import TestApp.StudentSchedulingApp.Entities.Instructor;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.UI.ModifyInstructor;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.InstructorVH> {

    private List<Instructor> instructorList;
    private final Context context;
    private final LayoutInflater aInflate;

    class InstructorVH extends RecyclerView.ViewHolder{

        private final TextView instructorName;
        private final TextView instructorPhone;
        private final TextView instructorEmail;

        private InstructorVH(View itemView){
            super(itemView);

            instructorName = itemView.findViewById(R.id.instructorName);
            instructorPhone = itemView.findViewById(R.id.instructorPhone);
            instructorEmail = itemView.findViewById(R.id.instructorEmail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Instructor current = instructorList.get(position);
                    Intent intent = new Intent(context, ModifyInstructor.class);
                    intent.putExtra("Instructor ID", current.getInstructorID());
                    intent.putExtra("Instructor Name", current.getInstructorName());
                    intent.putExtra("Instructor Phone", current.getInstructorPhone());
                    intent.putExtra("Instructor Email", current.getInstructorEmail());
                    context.startActivity(intent);
                }
            });

        }

    }

    public InstructorAdapter(Context context) {
        aInflate = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public InstructorVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = aInflate.inflate(R.layout.instructor_list_item, parent, false);
        return new InstructorVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorVH holder, int position) {

        if (instructorList != null) {
            Instructor current = instructorList.get(position);

            holder.instructorName.setText(current.getInstructorName());
            holder.instructorPhone.setText(current.getInstructorPhone());
            holder.instructorEmail.setText(current.getInstructorEmail());


        } else {
            // Covers the case of data not being ready yet.
            holder.instructorName.setText("No Assessments");
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInstructors(List<Instructor> instructor) {
        instructorList = instructor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        try {
            return instructorList.size();
        } catch (Exception e) {
            return 0;

        }
    }
}

