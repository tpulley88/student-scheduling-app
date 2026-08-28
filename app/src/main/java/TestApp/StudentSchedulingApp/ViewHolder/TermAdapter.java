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

import TestApp.StudentSchedulingApp.Entities.Term;
import TestApp.StudentSchedulingApp.R;
import TestApp.StudentSchedulingApp.UI.ViewTermDetail;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermVH> {

    private List<Term> termList;
    private final Context context;
    private final LayoutInflater aInflate;

    class TermVH extends RecyclerView.ViewHolder{

        private final TextView termTitle;
        private final TextView termStartDate;
        private final TextView termEndDate;

        private TermVH(@NonNull View itemView) {
            super(itemView);

            termTitle = itemView.findViewById(R.id.termTitle);
            termStartDate = itemView.findViewById(R.id.termStartDate);
            termEndDate = itemView.findViewById(R.id.termEndDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Term current = termList.get(position);
                    Intent intent = new Intent(context, ViewTermDetail.class);
                    intent.putExtra("Term ID", current.getTermID());
                    intent.putExtra("Term Title", current.getTermTitle());
                    intent.putExtra("Start Date", current.getStartDate().toEpochDay());
                    intent.putExtra("End Date", current.getEndDate().toEpochDay());
                    context.startActivity(intent);
                }
            });
        }
    }

    public TermAdapter(Context context) {
        aInflate = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = aInflate.inflate(R.layout.term_list_item, parent, false);
        return new TermAdapter.TermVH(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermVH holder, int position) {

        if (termList != null) {
            Term current = termList.get(position);

            holder.termTitle.setText(current.getTermTitle());
            holder.termStartDate.setText(current.getStartDate().toString());
            holder.termEndDate.setText(current.getEndDate().toString());

        } else {
            // Covers the case of data not being ready yet.
            holder.termTitle.setText("No Terms");
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setTerms(List<Term> term) {
        termList = term;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        try {
            return termList.size();
        } catch (Exception e) {
            return 0;
        }    }
}
