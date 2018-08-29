package com.app.fixy.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.fixy.R;
import com.app.fixy.adapters.ShowAddressAdapter;
import com.app.fixy.customviews.RecyclerItemTouchHelper;
import com.app.fixy.models.LocationModel;

import java.util.ArrayList;

import butterknife.BindView;

public class ShowAddressActivity extends BaseActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    @BindView(R.id.img_back)
    ImageView icBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_add)
    TextView txtAdd;
    @BindView(R.id.txt_no_card)
    TextView txtNoCard;
    @BindView(R.id.recycleview)
    RecyclerView recyclerView;
    ShowAddressAdapter adapter;
    private int showEdit = 0;
    int callBackStatus = 0;
    private ArrayList<LocationModel> locationModel;

    @Override
    protected int getContentView() {
        return R.layout.activity_show_address;
    }

    @Override
    protected void initUI() {
    }

    @Override
    protected void onCreateStuff() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        txtNoCard.setVisibility(View.GONE);
        // attaching the touch helper to recycler view
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();

    }

    public void setData() {

        locationModel = db.getLocations();

        if (locationModel != null && locationModel.size() > 0) {
            adapter = new ShowAddressAdapter(this, mWidth, mHeight, showEdit,locationModel);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);

        } else {
            recyclerView.setVisibility(View.GONE);

        }
    }
    @Override
    protected void initListener() {
        icBack.setOnClickListener(this);
        txtAdd.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.img_back:

                finish();
                overridePendingTransition(R.anim.slide_right,
                        R.anim.slide_out_right);
                break;
            case R.id.txt_add:
                intent = new Intent(this, AddAddressActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in, R.anim.out);
                break;
        }
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ShowAddressAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
//            String name = cartList.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
//            final Item deletedItem = cartList.get(viewHolder.getAdapterPosition());
//            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view


            db.deleteCustomAddress(locationModel.get(viewHolder.getAdapterPosition()));
            adapter.removeItem(viewHolder.getAdapterPosition());
            // showing snack bar with Undo option
            /*Snackbar snackbar = Snackbar
                    .make(txtAdd, getString(R.string.remove_add_warning), Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.ok_, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.deleteCustomAddress(locationModel.get(viewHolder.getAdapterPosition()));
                    adapter.removeItem(viewHolder.getAdapterPosition());
                    // undo is selected, restore the deleted item
//                    adapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();*/
        }
    }
}
