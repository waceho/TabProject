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
    private final List<DocumentInfo> mDocuments;
    private final LayoutInflater mLayoutInflater;

    public DocumentRecyclerAdapter(Context context, List<DocumentInfo> documents) {
        mContext = context;
        mDocuments = documents;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_document_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DocumentInfo document = mDocuments.get(position);
        holder.mTextdocument.setText(document.getTitle());
        holder.mCurrentPosition = position;
    }

    @Override
    public int getItemCount() {
        return mDocuments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mTextdocument;
        public int mCurrentPosition;

        public ViewHolder(final View itemView) {
            super(itemView);
            mTextdocument = (TextView) itemView.findViewById(R.id.content);


            final DocumentInfo item = (DocumentInfo) itemView.getTag();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, mDocuments.get(mCurrentPosition).getTitle());
                    context.startActivity(intent);

                    Snackbar.make(v, mDocuments.get(mCurrentPosition).getTitle(),
                            Snackbar.LENGTH_LONG).show();


                }
            });
        }



    }
}







