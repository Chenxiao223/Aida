package com.example.aida.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.aida.MyApplication;
import com.example.aida.R;
import com.example.aida.activity.CollocationActivity;
import com.example.aida.adapter.WardrobeAdapter;
import com.example.aida.been.Collocation;
import com.example.aida.greendao.CollocationDao;
import com.example.aida.view.SlideRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 搜索
 */
public class SearchFragment extends BaseFragment {
    private final int FLAG = 111;

    @BindView(R.id.view)
    View mView;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.recyclerView)
    SlideRecyclerView recyclerView;

    private CollocationDao collocationDao;
    private WardrobeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initImmersionBar(mView);

        initView();
        initListener();
    }

    private void initView() {
        collocationDao = MyApplication.getInstance().getDaoSession().getCollocationDao();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WardrobeAdapter(getContext(), R.layout.item_wardrobe);
        recyclerView.setAdapter(adapter);
    }

    public void initListener() {
        //点击搜索键的监听
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    getActivity()
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    //实现自己的搜索逻辑
                    getSearch();
                    return true;
                }
                return false;
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Collocation collocation = (Collocation) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.tv_alter://修改
                        Intent intent = new Intent(getContext(), CollocationActivity.class);
                        intent.putExtra("id", collocation.getId());
                        startActivityForResult(intent, FLAG);
                        break;
                    case R.id.tv_delete://删除
                        collocation.setIsdelete(1);
                        collocationDao.update(collocation);
                        getSearch();
                        break;
                }
            }
        });
    }

    public void getSearch() {
        try {
            List<Collocation> list = collocationDao.queryBuilder().where(CollocationDao.Properties.Isdelete.eq(0), CollocationDao.Properties.Name.like(et_search.getText().toString().trim() + "%")).list();
            adapter.setNewData(list);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}