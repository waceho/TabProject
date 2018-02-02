package com.estiam.amanciodossou.tabproject.dummy;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.estiam.amanciodossou.tabproject.ItemDetailActivity;
import com.estiam.amanciodossou.tabproject.ItemDetailFragment;
import com.estiam.amanciodossou.tabproject.R;

import java.util.List;

/**
 * Created by Jim.
 */

public class DocumentRecyclerAdapter extends RecyclerView.Adapter<DocumentRecyclerAdapter.ViewHolder> {

    private final Context mContext;
    private final List<DocumentInfo> mCourses;
    private final LayoutInflater mLayoutInflater;

    public DocumentRecyclerAdapter(Context context, List<DocumentInfo> courses) {
        mContext = context;
        mCourses = courses;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_course_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DocumentInfo course = mCourses.get(position);
        holder.mTextCourse.setText(course.getTitle());
        holder.mCurrentPosition = position;
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mTextCourse;
        public int mCurrentPosition;

        public ViewHolder(final View itemView) {
            super(itemView);
            mTextCourse = (TextView) itemView.findViewById(R.id.content);

            final DocumentInfo item = (DocumentInfo) itemView.getTag();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                   // intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.getCourseId());
                    context.startActivity(intent);

                    Snackbar.make(v, mCourses.get(mCurrentPosition).getTitle(),
                            Snackbar.LENGTH_LONG).show();


                }
            });
        }



    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}







