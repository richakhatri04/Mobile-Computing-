package com.thealienobserver.nikhil.travon.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thealienobserver.nikhil.travon.R;
import com.thealienobserver.nikhil.travon.models.CategoryScore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CategoryScoresAdapter extends RecyclerView.Adapter<CategoryScoresAdapter.ViewHolder> {
    private static final String TAG = "CategoryScoresAdapter";
    private NumberFormat numberFormat = new DecimalFormat("##.###");

    private Context callerContext;
    ArrayList<CategoryScore> categoryScores;

    /**
     * Adapter for setting values for Welcoming score item
     * @param context
     * @param categoryScores
     */

    public CategoryScoresAdapter(Context context, ArrayList<CategoryScore> categoryScores) {
        this.callerContext = context;
        this.categoryScores = categoryScores;
    }

    @NonNull
    @Override
    public CategoryScoresAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_category_score_items, parent, false);
        CategoryScoresAdapter.ViewHolder holder = new CategoryScoresAdapter.ViewHolder(view);
        return holder;
    }

    /**
     * Sets the values on the layout elements
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull CategoryScoresAdapter.ViewHolder viewHolder, int i) {
        Log.d(TAG, "bind view called");
        final CategoryScore currentScore = this.categoryScores.get(i);
        viewHolder.categoryNameTv.setText(currentScore.getName());
        double score = Math.round(currentScore.getScoreOutOf10());
        viewHolder.scoreTv.setText(numberFormat.format(score) + "/10");
        viewHolder.progressBarScore.setProgress((int) score);
    }

    /**
     * Gets the elements of the view for further manipulation
     * @return
     */
    @Override
    public int getItemCount() {
        return this.categoryScores.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryNameTv, scoreTv;
        ProgressBar progressBarScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameTv = itemView.findViewById(R.id.categoryNameTv);
            scoreTv = itemView.findViewById(R.id.scoreTv);
            progressBarScore = itemView.findViewById(R.id.progressBarScore);
        }
    }

}
