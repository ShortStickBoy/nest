package com.sunzn.nest.sample;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sunzn.nest.library.nested.QMUIContinuousNestedBottomAreaBehavior;
import com.sunzn.nest.library.nested.QMUIContinuousNestedBottomRecyclerView;
import com.sunzn.nest.library.nested.QMUIContinuousNestedScrollLayout;
import com.sunzn.nest.library.nested.QMUIContinuousNestedTopAreaBehavior;
import com.sunzn.nest.library.nested.QMUIContinuousNestedTopWebView;
import com.sunzn.nest.library.webview.QMUIWebView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NestedActivity1 extends AppCompatActivity {

    @BindView(R.id.coordinator)
    QMUIContinuousNestedScrollLayout mCoordinatorLayout;

    private QMUIWebView mNestedWebView;
    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter<String> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_one);
        ButterKnife.bind(this);
        initCoordinatorLayout();
    }

    protected void initCoordinatorLayout() {
        mNestedWebView = new QMUIContinuousNestedTopWebView(this);
        int matchParent = ViewGroup.LayoutParams.MATCH_PARENT;
        CoordinatorLayout.LayoutParams webViewLp = new CoordinatorLayout.LayoutParams(matchParent, matchParent);
        webViewLp.setBehavior(new QMUIContinuousNestedTopAreaBehavior(this));
        mCoordinatorLayout.setTopAreaView(mNestedWebView, webViewLp);

        mRecyclerView = new QMUIContinuousNestedBottomRecyclerView(this);
        CoordinatorLayout.LayoutParams recyclerViewLp = new CoordinatorLayout.LayoutParams(matchParent, matchParent);
        recyclerViewLp.setBehavior(new QMUIContinuousNestedBottomAreaBehavior());
        mCoordinatorLayout.setBottomAreaView(mRecyclerView, recyclerViewLp);

        mNestedWebView.loadUrl("https://mp.weixin.qq.com/s/zgfLOMD2JfZJKfHx-5BsBg");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        mAdapter = new BaseRecyclerAdapter<String>(this, null) {
            @Override
            public int getItemLayoutId(int viewType) {
                return android.R.layout.simple_list_item_1;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, int position, String item) {
                holder.setText(android.R.id.text1, item);
            }
        };
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                Toast.makeText(NestedActivity1.this, "click position=" + pos, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        onDataLoaded();
    }

    private void onDataLoaded() {
        List<String> data = new ArrayList<>(Arrays.asList("Helps", "Maintain", "Liver",
                "Health", "Function", "Supports", "Healthy", "Fat", "Metabolism",
                "Nuturally", "Bracket", "Refrigerator", "Bathtub", "Wardrobe", "Comb",
                "Apron", "Carpet", "Bolster", "Pillow", "Cushion"));
        Collections.shuffle(data);
        mAdapter.setData(data);
    }

}
