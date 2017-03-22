package com.example.caojian.myapplication.recyclerView;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.caojian.myapplication.R;

import java.util.List;

/**
 * Created by caojian on 2017/1/11.
 */
public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.FruitViewHoid> {


    private List<Fruit> mList_data;
    public FruitAdapter(List<Fruit> pList){
        mList_data = pList;
    }
    @Override
    public FruitViewHoid onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(),R.layout.fruit_item,null);
        FruitViewHoid hoid =  new FruitViewHoid(itemView);
        hoid.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FtuitActivity.toFruitActivity(parent.getContext(),"aaaa");
            }
        });
        return hoid;
    }

    @Override
    public void onBindViewHolder(FruitViewHoid holder, int position) {
        Fruit fruit = mList_data.get(position);
        holder.mTv_msg.setText(fruit.getText_msg());
    }



    @Override
    public int getItemCount() {
        return mList_data.size();
    }

    public class FruitViewHoid extends RecyclerView.ViewHolder{
        public TextView mTv_msg;
        public ImageView mImg_show;
        public CardView cardView;
        public FruitViewHoid(View itemView) {
            super(itemView);
            mTv_msg = (TextView) itemView.findViewById(R.id.tv_item);
            mImg_show = (ImageView) itemView.findViewById(R.id.img_item);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
        }
    }
}
