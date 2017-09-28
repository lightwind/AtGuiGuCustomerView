package com.lightwind.a03_popupwindow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_input)
    EditText mEtInput;
    @BindView(R.id.iv_down_arrow)
    ImageView mIvDownArrow;

    private PopupWindow popupWindow;

    private ListView listView;

    private ArrayList<String> msgs;

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mEtInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    popupWindow = new PopupWindow(MainActivity.this);
                    popupWindow.setWidth(mEtInput.getWidth());
                    // 为了能适配不同像素的手机，需要使用dp，这里将200dp转成像素，这样就能适配不同的手机屏幕
                    int height = DensityUtil.dip2px(MainActivity.this, 200);
                    popupWindow.setHeight(height);

                    popupWindow.setContentView(listView);
                    popupWindow.setFocusable(true);// 设置焦点，否则点击没有作用
                }

                popupWindow.showAsDropDown(mEtInput, 0, 0);
            }
        });


        // 准备数据，设置适配器
        msgs = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            msgs.add(i + "--aaaaaaaaa--" + 1);
        }
        listView = new ListView(this);
        listView.setBackgroundResource(R.drawable.listview_background);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = msgs.get(position);
                mEtInput.setText(msg);
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return msgs.size();
        }

        @Override
        public Object getItem(int position) {
            return msgs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item_main, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            // 根据位置得到数据
            final String msg = msgs.get(position);
            viewHolder.mTvMsg.setText(msg);

            viewHolder.mIvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 从集合中删除
                    msgs.remove(msg);
                    // 刷新UI，就是刷新适配器
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }

    static class ViewHolder {
        @BindView(R.id.tv_msg)
        TextView mTvMsg;
        @BindView(R.id.iv_delete)
        ImageView mIvDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
