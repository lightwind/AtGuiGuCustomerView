package com.lightwind.a07_quickindex;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.lv_main)
    ListView mLvMain;
    @BindView(R.id.tv_word)
    TextView mTvWord;
    @BindView(R.id.iv_words)
    IndexView mIvWords;

    private Handler handler = new Handler();
    // 联系人的集合
    private ArrayList<Person> persons;
    private IndexAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 设置监听字母下标索引的变化
        mIvWords.setOnIndexChangerListener(new IndexView.OnIndexChangerListener() {
            @Override
            public void onIndexChanger(String word) {
                updateWord(word);
                updateListView(word);
            }
        });

        initData();

        // 设置适配器
        adapter = new IndexAdapter();
        mLvMain.setAdapter(adapter);
    }

    private void updateListView(String word) {
        for (int i = 0; i < persons.size(); i++) {
            String listWord = persons.get(i).getPingyin().substring(0, 1);
            if (word.equals(listWord)) {
                // i是listView中的位置
                mLvMain.setSelection(i);// 定位到listView中的位置
                return;// 只要满足条件，就立刻return，否则就会加到最后一个位置
            }
        }
    }

    private class IndexAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return persons.size();
        }

        @Override
        public Object getItem(int position) {
            return persons.get(position);
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

            String name = persons.get(position).getName();
            String word = persons.get(position).getPingyin().substring(0, 1);

            viewHolder.mTvName.setText(name);
            viewHolder.mTvWord.setText(word);

            if (position == 0) {
                viewHolder.mTvWord.setVisibility(View.VISIBLE);
            } else {
                // 得到前一个位置的字母，如果当前的和上一个相同就隐藏，否则就显示
                String preWord = persons.get(position - 1).getPingyin().substring(0, 1);
                if (word.equals(preWord)) {
                    viewHolder.mTvWord.setVisibility(View.GONE);
                } else {
                    viewHolder.mTvWord.setVisibility(View.VISIBLE);
                }
            }

            return convertView;
        }
    }

    static class ViewHolder {
        @BindView(R.id.tv_word)
        TextView mTvWord;
        @BindView(R.id.tv_name)
        TextView mTvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 显示中间的大的字母
     */
    private void updateWord(String word) {
        // 显示
        mTvWord.setVisibility(View.VISIBLE);
        mTvWord.setText(word);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 因为Handler是new在主线程，所以run()也是运行在主线程
                mTvWord.setVisibility(View.GONE);
            }
        }, 3000);
    }

    /**
     * 初始化数据
     */
    private void initData() {

        persons = new ArrayList<>();
        persons.add(new Person("张晓飞"));
        persons.add(new Person("杨光福"));
        persons.add(new Person("胡继群"));
        persons.add(new Person("刘畅"));

        persons.add(new Person("钟泽兴"));
        persons.add(new Person("尹革新"));
        persons.add(new Person("安传鑫"));
        persons.add(new Person("张骞壬"));

        persons.add(new Person("温松"));
        persons.add(new Person("李凤秋"));
        persons.add(new Person("刘甫"));
        persons.add(new Person("娄全超"));
        persons.add(new Person("张猛"));

        persons.add(new Person("王英杰"));
        persons.add(new Person("李振南"));
        persons.add(new Person("孙仁政"));
        persons.add(new Person("唐春雷"));
        persons.add(new Person("牛鹏伟"));
        persons.add(new Person("姜宇航"));

        persons.add(new Person("刘挺"));
        persons.add(new Person("张洪瑞"));
        persons.add(new Person("张建忠"));
        persons.add(new Person("侯亚帅"));
        persons.add(new Person("刘帅"));

        persons.add(new Person("乔竞飞"));
        persons.add(new Person("徐雨健"));
        persons.add(new Person("吴亮"));
        persons.add(new Person("王兆霖"));

        persons.add(new Person("阿三"));
        persons.add(new Person("李博俊"));

        //排序
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getPingyin().compareTo(o2.getPingyin());
            }
        });
    }
}
