package com.example.aida.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aida.MyApplication;
import com.example.aida.R;
import com.example.aida.activity.AddClothesActivity;
import com.example.aida.activity.CollocationActivity;
import com.example.aida.adapter.WardrobeAdapter;
import com.example.aida.been.Collocation;
import com.example.aida.been.ImageList;
import com.example.aida.greendao.CollocationDao;
import com.example.aida.greendao.ImageListDao;
import com.example.aida.view.SlideRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 衣橱
 */
public class WardrobeFragment extends BaseFragment {
    private final int FLAG = 111;

    @BindView(R.id.view)
    View mView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_text_right)
    TextView tv_text_right;
    @BindView(R.id.tv_jacket)
    TextView tv_jacket;
    @BindView(R.id.tv_bottoms)
    TextView tv_bottoms;
    @BindView(R.id.tv_shoes)
    TextView tv_shoes;
    @BindView(R.id.tv_accessories)
    TextView tv_accessories;
    @BindView(R.id.recyclerView)
    SlideRecyclerView recyclerView;

    private ImageListDao imageListDao;
    private CollocationDao collocationDao;
    private WardrobeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wardrobe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        initImmersionBar(mView);
        initView();
        initData();
    }

    private void initView() {
        imageListDao = MyApplication.getInstance().getDaoSession().getImageListDao();
        collocationDao = MyApplication.getInstance().getDaoSession().getCollocationDao();
        iv_back.setVisibility(View.GONE);
        tv_title.setText("我的衣橱");
        tv_text_right.setVisibility(View.VISIBLE);
        tv_text_right.setText("添加搭配");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WardrobeAdapter(getContext(), R.layout.item_wardrobe);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        List<ImageList> list1 = imageListDao.queryBuilder().where(ImageListDao.Properties.Flag.eq(1)).list();
        if (!list1.isEmpty()) {
            tv_jacket.setText("共" + list1.size() + "个");
        }
        List<ImageList> list2 = imageListDao.queryBuilder().where(ImageListDao.Properties.Flag.eq(2)).list();
        if (!list2.isEmpty()) {
            tv_bottoms.setText("共" + list2.size() + "个");
        }
        List<ImageList> list3 = imageListDao.queryBuilder().where(ImageListDao.Properties.Flag.eq(3)).list();
        if (!list3.isEmpty()) {
            tv_shoes.setText("共" + list3.size() + "个");
        }
        List<ImageList> list4 = imageListDao.queryBuilder().where(ImageListDao.Properties.Flag.eq(4)).list();
        if (!list4.isEmpty()) {
            tv_accessories.setText("共" + list4.size() + "个");
        }

        List<Collocation> list5 = collocationDao.queryBuilder().list();
        adapter.setNewData(list5);
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.ly_jacket, R.id.ly_bottoms, R.id.ly_shoes, R.id.ly_accessories, R.id.tv_text_right})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ly_jacket://上衣
                intent = new Intent(getActivity(), AddClothesActivity.class);
                intent.putExtra("title", "上衣");
                intent.putExtra("flag", 1);
                startActivityForResult(intent, FLAG);
                break;
            case R.id.ly_bottoms://下装
                intent = new Intent(getActivity(), AddClothesActivity.class);
                intent.putExtra("title", "下装");
                intent.putExtra("flag", 2);
                startActivityForResult(intent, FLAG);
                break;
            case R.id.ly_shoes://鞋子
                intent = new Intent(getActivity(), AddClothesActivity.class);
                intent.putExtra("title", "鞋子");
                intent.putExtra("flag", 3);
                startActivityForResult(intent, FLAG);
                break;
            case R.id.ly_accessories://配饰
                intent = new Intent(getActivity(), AddClothesActivity.class);
                intent.putExtra("title", "配饰");
                intent.putExtra("flag", 4);
                startActivityForResult(intent, FLAG);
                break;
            case R.id.tv_text_right://添加搭配
                intent = new Intent(getActivity(), CollocationActivity.class);
                startActivityForResult(intent, FLAG);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initData();
    }
}