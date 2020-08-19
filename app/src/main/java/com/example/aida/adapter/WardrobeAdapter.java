package com.example.aida.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.aida.R;
import com.example.aida.been.Collocation;

public class WardrobeAdapter extends BaseQuickAdapter<Collocation, BaseViewHolder> {
    private Context context;

    public WardrobeAdapter(Context context, int LayoutResId) {
        super(LayoutResId);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Collocation item) {
        helper.setText(R.id.iv_title, item.getName());
        Glide.with(context).asBitmap().load(item.getClothes()).thumbnail(0.5f).into((ImageView) helper.getView(R.id.image1));
        Glide.with(context).asBitmap().load(item.getBottoms()).thumbnail(0.5f).into((ImageView) helper.getView(R.id.image2));
        Glide.with(context).asBitmap().load(item.getShoes()).thumbnail(0.5f).into((ImageView) helper.getView(R.id.image3));
        Glide.with(context).asBitmap().load(item.getAccessories()).thumbnail(0.5f).into((ImageView) helper.getView(R.id.image4));

        helper.addOnClickListener(R.id.tv_alter).addOnClickListener(R.id.tv_delete);
    }
}
