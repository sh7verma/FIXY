package com.app.fixy.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.interfaces.InterfacesCall;
import com.app.fixy.models.ServicesModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchServiceAdapter extends RecyclerView.Adapter<SearchServiceAdapter.ViewHolder> {

    InterfacesCall.IndexClick mcClickService;
    List<ServicesModel.ResponseBean.CategoriesBean.SubcategoriesBean> mData;
    private Context mContext;
    private int mHeight;

    public SearchServiceAdapter(Context context, int height, InterfacesCall.IndexClick clickSer, List<ServicesModel.ResponseBean.CategoriesBean.SubcategoriesBean> subcategories) {
        mContext = context;
        mHeight = height;
        mcClickService = clickSer;
        mData = subcategories;
    }

    @NonNull
    @Override
    public SearchServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_single, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchServiceAdapter.ViewHolder holder, final int position) {
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
        holder.txtServiceName.setText(mData.get(position).getCategory_name());
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcClickService.clickIndex(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_main)
        LinearLayout llMain;
        @BindView(R.id.txt_service_name)
        TextView txtServiceName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}

