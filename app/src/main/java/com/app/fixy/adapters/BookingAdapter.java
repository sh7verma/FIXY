package com.app.fixy.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.app.fixy.R;
import com.app.fixy.interfaces.InterfacesCall;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private final Context mContext;
    Bitmap bitmap = null;

    private Handler handler = new Handler();
    Runnable runnable;
    private int progressStatus = 0, count = 25;
    int start = 0,end = 75,next=0;
    InterfacesCall.IndexClick click;

    public BookingAdapter(Context con, InterfacesCall.IndexClick click) {
        mContext = con;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booked, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        setView(holder,start);

//        holder.btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////              progressStatus = progressStatus+30;
//                start+=25;
//                progressStatus=start;
////              handler.postDelayed(runnable, 10);
//                setView(holder, start);
//
//            }
//        });

        holder.viewBooking.llMain.setOnClickListener(new View.OnClickListener() {
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
            case 25:
                showAccepted(holder);
                break;
            case 50:
                showOnWay(holder);
                break;
            case 75:
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
        holder.viewBooking.llRequest.setVisibility(View.VISIBLE);
        holder.viewBooking.llAccepted.setVisibility(View.GONE);
        holder.viewBooking.llOnWay.setVisibility(View.GONE);
        holder.viewBooking.llConfirmed.setVisibility(View.GONE);
    }

    private void showAccepted(ViewHolder holder) {
        int w = (int) mContext.getResources().getDimension(R.dimen._3sdp);
        int h = (int) mContext.getResources().getDimension(R.dimen._51sdp);
        RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(w, h);
        int l = (int) mContext.getResources().getDimension(R.dimen._10sdp);
        int t = (int) mContext.getResources().getDimension(R.dimen._5sdp);
        relativeLayout.setMargins(l, t, 0, 0);
        holder.vprogressbar.setLayoutParams(relativeLayout);
        holder.viewBooking.llRequest.setVisibility(View.VISIBLE);
        holder.viewBooking.llAccepted.setVisibility(View.VISIBLE);
        holder.viewBooking.llOnWay.setVisibility(View.GONE);
        holder.viewBooking.llConfirmed.setVisibility(View.GONE);
    }

    private void showOnWay(ViewHolder holder) {
        int w = (int) mContext.getResources().getDimension(R.dimen._3sdp);
        int h = (int) mContext.getResources().getDimension(R.dimen._102sdp);
        RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(w, h);
        int l = (int) mContext.getResources().getDimension(R.dimen._10sdp);
        int t = (int) mContext.getResources().getDimension(R.dimen._5sdp);
        relativeLayout.setMargins(l, t, 0, 0);
        holder.vprogressbar.setLayoutParams(relativeLayout);
        holder.viewBooking.llRequest.setVisibility(View.VISIBLE);
        holder.viewBooking.llAccepted.setVisibility(View.VISIBLE);
        holder.viewBooking.llOnWay.setVisibility(View.VISIBLE);
        holder.viewBooking.llConfirmed.setVisibility(View.GONE);
    }

    private void showConfirm(ViewHolder holder) {
        int w = (int) mContext.getResources().getDimension(R.dimen._3sdp);
        int h = (int) mContext.getResources().getDimension(R.dimen._155sdp);
        RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(w, h);
        int l = (int) mContext.getResources().getDimension(R.dimen._10sdp);
        int t = (int) mContext.getResources().getDimension(R.dimen._5sdp);
        relativeLayout.setMargins(l, t, 0, 0);
        holder.vprogressbar.setLayoutParams(relativeLayout);
        holder.viewBooking.llRequest.setVisibility(View.VISIBLE);
        holder.viewBooking.llAccepted.setVisibility(View.VISIBLE);
        holder.viewBooking.llOnWay.setVisibility(View.VISIBLE);
        holder.viewBooking.llConfirmed.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewBooking {
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
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View viewCircle1, viewCircle2, viewCircle3, viewCircle4;
        ProgressBar vprogressbar;
        ViewBooking viewBooking = new ViewBooking();

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(viewBooking, itemView);
            viewCircle1 = itemView.findViewById(R.id.view_circle1);
            viewCircle2 = itemView.findViewById(R.id.view_circle2);
            viewCircle3 = itemView.findViewById(R.id.view_circle3);
            viewCircle4 = itemView.findViewById(R.id.view_circle4);
            vprogressbar = itemView.findViewById(R.id.vprogressbar);

            viewBooking.llRequest.setVisibility(View.VISIBLE);
            viewBooking.llAccepted.setVisibility(View.GONE);
            viewBooking.llOnWay.setVisibility(View.GONE);
            viewBooking.llConfirmed.setVisibility(View.GONE);
//            runProgressbar();


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
