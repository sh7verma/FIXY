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
import com.app.fixy.utils.Animations;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.fixy.activities.SearchServiceActivity.mSelected;

public class SearchCategoryAdapter extends RecyclerView.Adapter<SearchCategoryAdapter.ViewHolder> {

    InterfacesCall.IndexClick mClick;
    ArrayList<ServicesModel.ResponseBean.CategoriesBean> mData;
    private Context mContext;
    private int mHeight;

    public SearchCategoryAdapter(Context context, int height, InterfacesCall.IndexClick click, ArrayList<ServicesModel.ResponseBean.CategoriesBean> categoriesList) {
        mContext = context;
        mHeight = height;
        mClick = click;
        this.mData = categoriesList;
    }

    @NonNull
    @Override
    public SearchCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_single, parent, false);
        return new SearchCategoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchCategoryAdapter.ViewHolder holder, int position) {

        if (mSelected == position) {
            holder.llMain.setBackground(mContext.getResources().getDrawable(R.drawable.black_oval));
            Animations.AnimatedClick(mContext, holder.llMain);
        } else {
            holder.llMain.setBackground(mContext.getResources().getDrawable(R.drawable.white_oval));
        }
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
        holder.txtCategoryName.setText(mData.get(position).getCategory_name());
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelected = holder.getAdapterPosition();
                mClick.clickIndex(holder.getAdapterPosition());
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
        @BindView(R.id.txt_category_name)
        TextView txtCategoryName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}


