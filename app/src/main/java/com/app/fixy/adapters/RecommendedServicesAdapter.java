package com.app.fixy.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.fixy.R;
import com.app.fixy.activities.ServiceDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shubham verma on 17-08-2018.
 */

public class RecommendedServicesAdapter extends RecyclerView.Adapter<RecommendedServicesAdapter.ViewHolder> {

    private Context mContext;
    private int mHeight;

    public RecommendedServicesAdapter(Context context, int height) {
        mContext = context;
        mHeight = height;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommended_services, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
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
        GradientDrawable bgShape = (GradientDrawable) holder.llBackground.getBackground();
        bgShape.setColor(mContext.getResources().getColor(R.color.cleaner));

//        Picasso.get()
//                .load(R.mipmap.ic_beauty)
//                .transform(new CircleTransform())
//                .resize((int) (mHeight * 0.08), (int) (mHeight * 0.08))
//                .into(holder.imgService);
//            }


        holder.llBackground.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.in, R.anim.out);

            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_background)
        LinearLayout llBackground;

        @BindView(R.id.img_service)
        ImageView imgService;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
