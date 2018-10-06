package com.app.fixy.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.models.ServicesModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shubham verma on 18-08-2018.
 */

public class AllServicesAdapter extends RecyclerView.Adapter<AllServicesAdapter.ViewHolder> {

    Context mContext;
    int mHeight;
    ArrayList<ServicesModel.ResponseBean.CategoriesBean> mData;

    public AllServicesAdapter(Context context, int height, ArrayList<ServicesModel.ResponseBean.CategoriesBean> data) {
        mContext = context;
        mHeight = height;
        mData = data;
    }

    @NonNull
    @Override
    public AllServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_services, parent, false);
        return new AllServicesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AllServicesAdapter.ViewHolder holder, final int position) {

//            if (!TextUtils.isEmpty(mData.getProfilePicURL().getOriginal())) {
//                Picasso.with(mContext)
//                        .load(mData.getProfilePicURL().getOriginal())
//                        .transform(new RoundCorners())
//                        .resize((int) (mHeight * 0.13), (int) (mHeight * 0.13))
//                        .placeholder(R.mipmap.ic_img_ph)
//                        .error(R.mipmap.ic_img_ph)
//                        .centerCrop()
//                        .into(imgProfile);
//            } else {

//        GradientDrawable bgShape = (GradientDrawable) holder.llBackground.getBackground();
//        bgShape.setColor(Color.BLACK);

//        Picasso.get()
//                .load(R.mipmap.ic_beauty)
//                .transform(new CircleTransform())
//                .resize((int) (mHeight * 0.08), (int) (mHeight * 0.08))
//                .into(holder.imgService);
//            }
        holder.txtName.setText(mData.get(position).getCategory_name());
        holder.txtCount.setText("(" + mData.get(position).getTotal() + ")");

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_count)
        TextView txtCount;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
