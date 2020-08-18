package com.example.aida.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.aida.R;
import com.example.aida.fragment.FashionFragment;
import com.example.aida.fragment.MyFragment;
import com.example.aida.fragment.SearchFragment;
import com.example.aida.fragment.WardrobeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.fragment_nrs)
    FrameLayout fragmentNrs;
    @BindView(R.id.bottom_ngs)
    BottomNavigationView bottomNgs;
    private ArrayList<String> fragmentNames;
    private Fragment mCurrentFragment;
    private String fragmentTag;
    private int index = 0;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        fragmentNames = new ArrayList<>();
        fragmentNames.add(WardrobeFragment.class.getName());
        fragmentNames.add(FashionFragment.class.getName());
        fragmentNames.add(SearchFragment.class.getName());
        fragmentNames.add(MyFragment.class.getName());

        bottomNgs.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.wardrobefragment) {
                    index = 0;
                } else if (menuItem.getItemId() == R.id.fashionfragment) {
                    index = 1;
                } else if (menuItem.getItemId() == R.id.searchfragment) {
                    index = 2;
                } else {
                    index = 3;
                }

                fragmentTag = fragmentNames.get(index);
                Fragment fragment = getFragmentByTag(fragmentTag);
                showFragment(mCurrentFragment, fragment, fragmentTag);

                return true;
            }
        });

        fragmentTag = fragmentNames.get(index);
        Fragment fragment = getFragmentByTag(fragmentTag);
        showFragment(mCurrentFragment, fragment, fragmentTag);
    }

    private Fragment getFragmentByTag(String name) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(name);
        if (fragment != null) {
            return fragment;
        } else {
            try {
                fragment = (Fragment) Class.forName(name).newInstance();
            } catch (Exception e) {
                fragment = new WardrobeFragment();
            }
        }
        return fragment;
    }

    private void showFragment(Fragment from, Fragment to, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (from == null) {
            if (to.isAdded()) {
                transaction.show(to);
            } else {
                transaction.add(R.id.fragment_nrs, to, tag);
            }
        } else {
            if (to.isAdded()) {
                transaction.hide(from).show(to);
            } else {
                transaction.hide(from).add(R.id.fragment_nrs, to, tag);
            }
        }
        transaction.commit();
        mCurrentFragment = to;
    }

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, R.string.exit, Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}