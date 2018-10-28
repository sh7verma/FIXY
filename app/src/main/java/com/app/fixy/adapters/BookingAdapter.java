package com.app.fixy.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.interfaces.InterfacesCall;
import com.app.fixy.models.RequestModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private final Context mContext;
    Runnable runnable;
    int start = 0, end = 75;
    InterfacesCall.IndexClick click;
    ArrayList<RequestModel.ResponseBean> mData;
    private Handler handler = new Handler();

    public BookingAdapter(Context con, ArrayList<RequestModel.ResponseBean> data, InterfacesCall.IndexClick click) {
        mContext = con;
        this.click = click;
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booked, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        setView(holder, Integer.parseInt(mData.get(holder.getAdapterPosition()).getRequest_status()));

        holder.txtName.setText(mData.get(holder.getAdapterPosition()).getFullname());
        holder.txtId.setText("ID: " + mData.get(holder.getAdapterPosition()).getId());
        holder.txtCoins.setText(mData.get(holder.getAdapterPosition()).getOriginal_price() + " Coins");
        holder.txtWorkerName.setText(mData.get(holder.getAdapterPosition()).getFullname());

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.clickIndex(position);
            }
        });

    }

    private void setView(@NonNull ViewHolder holder, int start) {
        switch (start) {
            case 0:
                showPending(holder);
                break;
            case 1:
                showAccepted(holder);
                break;
            case 2:
                showOnWay(holder);
                break;
            case 3:
                showConfirm(holder);
                break;
        }
    }


    private void showPending(ViewHolder holder) {
        RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(R.dimen._3sdp, 0);
        int l = (int) mContext.getResources().getDimension(R.dimen._10sdp);
        int t = (int) mContext.getResources().getDimension(R.dimen._5sdp);
        relativeLayout.setMargins(l, t, 0, 0);
        holder.vprogressbar.setLayoutParams(relativeLayout);
        holder.llRequest.setVisibility(View.VISIBLE);
        holder.llAccepted.setVisibility(View.GONE);
        holder.llOnWay.setVisibility(View.GONE);
        holder.llConfirmed.setVisibility(View.GONE);

    }

    private void showAccepted(ViewHolder holder) {
        int w = (int) mContext.getResources().getDimension(R.dimen._3sdp);
        int h = (int) mContext.getResources().getDimension(R.dimen._51sdp);
        RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(w, h);
        int l = (int) mContext.getResources().getDimension(R.dimen._10sdp);
        int t = (int) mContext.getResources().getDimension(R.dimen._5sdp);
        relativeLayout.setMargins(l, t, 0, 0);
        holder.vprogressbar.setLayoutParams(relativeLayout);
        holder.llRequest.setVisibility(View.VISIBLE);
        holder.llAccepted.setVisibility(View.VISIBLE);
        holder.llOnWay.setVisibility(View.GONE);
        holder.llConfirmed.setVisibility(View.GONE);
        holder.txtAcceptedTime.setText(mData.get(holder.getAdapterPosition()).getAccepted_time());

    }

    private void showOnWay(ViewHolder holder) {
        int w = (int) mContext.getResources().getDimension(R.dimen._3sdp);
        int h = (int) mContext.getResources().getDimension(R.dimen._102sdp);
        RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(w, h);
        int l = (int) mContext.getResources().getDimension(R.dimen._10sdp);
        int t = (int) mContext.getResources().getDimension(R.dimen._5sdp);
        relativeLayout.setMargins(l, t, 0, 0);
        holder.vprogressbar.setLayoutParams(relativeLayout);
        holder.llRequest.setVisibility(View.VISIBLE);
        holder.llAccepted.setVisibility(View.VISIBLE);
        holder.llOnWay.setVisibility(View.VISIBLE);
        holder.llConfirmed.setVisibility(View.GONE);
        holder.txt_on_the_way_time.setText(mData.get(holder.getAdapterPosition()).getOntheway_time());

    }

    private void showConfirm(ViewHolder holder) {
        int w = (int) mContext.getResources().getDimension(R.dimen._3sdp);
        int h = (int) mContext.getResources().getDimension(R.dimen._155sdp);
        RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(w, h);
        int l = (int) mContext.getResources().getDimension(R.dimen._10sdp);
        int t = (int) mContext.getResources().getDimension(R.dimen._5sdp);
        relativeLayout.setMargins(l, t, 0, 0);
        holder.vprogressbar.setLayoutParams(relativeLayout);
        holder.llRequest.setVisibility(View.VISIBLE);
        holder.llAccepted.setVisibility(View.VISIBLE);
        holder.llOnWay.setVisibility(View.VISIBLE);
        holder.llConfirmed.setVisibility(View.VISIBLE);
        holder.txt_confirmed_time.setText(mData.get(holder.getAdapterPosition()).getConfirm_time());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        View viewCircle1, viewCircle2, viewCircle3, viewCircle4;
        ProgressBar vprogressbar;

        @BindView(R.id.ll_request)
        LinearLayout llRequest;
        @BindView(R.id.ll_accepted)
        LinearLayout llAccepted;
        @BindView(R.id.ll_on_way)
        LinearLayout llOnWay;
        @BindView(R.id.ll_confirmed)
        LinearLayout llConfirmed;
        @BindView(R.id.ll_main)
        LinearLayout llMain;

        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_coins)
        TextView txtCoins;
        @BindView(R.id.txt_id)
        TextView txtId;
        @BindView(R.id.txt_worker_name)
        TextView txtWorkerName;
        @BindView(R.id.txt_accepted_time)
        TextView txtAcceptedTime;
        @BindView(R.id.txt_requested_time)
        TextView txt_requested_time;
        @BindView(R.id.txt_on_the_way_time)
        TextView txt_on_the_way_time;
        @BindView(R.id.txt_confirmed_time)
        TextView txt_confirmed_time;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            viewCircle1 = itemView.findViewById(R.id.view_circle1);
            viewCircle2 = itemView.findViewById(R.id.view_circle2);
            viewCircle3 = itemView.findViewById(R.id.view_circle3);
            viewCircle4 = itemView.findViewById(R.id.view_circle4);
            vprogressbar = itemView.findViewById(R.id.vprogressbar);

            llRequest.setVisibility(View.VISIBLE);
            llAccepted.setVisibility(View.GONE);
            llOnWay.setVisibility(View.GONE);
            llConfirmed.setVisibility(View.GONE);
        }

        private void runProgressbar() {
            runnable = new Runnable() {
                @Override
                public void run() {
                    start++;
                    if (start > 0 && start < 2) {
                        viewCircle1.setBackground(mContext.getResources().getDrawable(R.drawable.grey_oval));
                    } else if (start > 24 && start < end) {

                        viewCircle1.setBackground(mContext.getResources().getDrawable(R.drawable.circle_green));
                        viewCircle2.setBackground(mContext.getResources().getDrawable(R.drawable.circle_green));
                    } else if (start > 49 && start < end) {

                        viewCircle1.setBackground(mContext.getResources().getDrawable(R.drawable.circle_green));
                        viewCircle2.setBackground(mContext.getResources().getDrawable(R.drawable.circle_green));
                        viewCircle3.setBackground(mContext.getResources().getDrawable(R.drawable.circle_green));
                    } else if (start > 73 && start < end) {

                        viewCircle1.setBackground(mContext.getResources().getDrawable(R.drawable.circle_green));
                        viewCircle2.setBackground(mContext.getResources().getDrawable(R.drawable.circle_green));
                        viewCircle3.setBackground(mContext.getResources().getDrawable(R.drawable.circle_green));
                        viewCircle4.setBackground(mContext.getResources().getDrawable(R.drawable.circle_green));
                    }
                    vprogressbar.setProgress(start);

                    if (start < end) {

                        handler.postDelayed(runnable, 10);
                    } else {
                        handler.removeCallbacks(runnable);
                    }
                }
            };
        }
    }
}
