package com.example.aida.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.aida.R;
import com.example.aida.been.ImageList;

public class CollocationAdapter extends BaseQuickAdapter<ImageList, BaseViewHolder> {
    private Context context;

    public CollocationAdapter(Context context, int LayoutResId) {
        super(LayoutResId);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageList item) {
        ImageView imageView = helper.getView(R.id.image);
        Glide.with(context).asBitmap().load(item.getImagePath()).thumbnail(0.5f).into(imageView);
        helper.addOnClickListener(R.id.image);
    }
}
