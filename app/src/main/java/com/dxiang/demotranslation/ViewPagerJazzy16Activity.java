package com.dxiang.demotranslation;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.dxiang.demotranslation.view.JazzyViewPager;
import com.dxiang.demotranslation.view.OutlineContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewPagerJazzy16Activity extends AppCompatActivity {
    private JazzyViewPager mJazzy;
    private MainAdapter mainAdapter;
    private String[] effects ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_jazzy16);
        effects=getResources().getStringArray(R.array.jazzy_effects);
        setupJazziness(JazzyViewPager.TransitionEffect.Tablet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        for (String effect : effects)
            menu.add(effect);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals("Toggle Fade")) {//设置透明度
            mJazzy.setFadeEnabled(!mJazzy.getFadeEnabled());
        } else {
            JazzyViewPager.TransitionEffect effect = JazzyViewPager.TransitionEffect.valueOf(item.getTitle().toString());
            setupJazziness(effect);
        }
        return true;
    }

    private void setupJazziness(JazzyViewPager.TransitionEffect effect) {
        if (mainAdapter==null){
            mainAdapter=new MainAdapter();
            mJazzy=(JazzyViewPager) findViewById(R.id.jazzy_pager);
            mJazzy.setAdapter(mainAdapter);
            mJazzy.setPageMargin(30);
        }
        mJazzy.setTransitionEffect(effect);
    }

    private class MainAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            TextView text = new TextView(ViewPagerJazzy16Activity.this);
            text.setGravity(Gravity.CENTER);
            text.setTextSize(30);
            text.setTextColor(Color.WHITE);
            text.setText("Page " + position);
            text.setPadding(30, 30, 30, 30);
            int bg = Color.rgb((int) Math.floor(Math.random()*128)+64,
                    (int) Math.floor(Math.random()*128)+64,
                    (int) Math.floor(Math.random()*128)+64);
            text.setBackgroundColor(bg);
            container.addView(text, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mJazzy.setObjectForPosition(text, position);
            return text;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object obj) {
            container.removeView(mJazzy.findViewFromObject(position));
        }
        @Override
        public int getCount() {
            return 10;
        }
        @Override
        public boolean isViewFromObject(View view, Object obj) {
            if (view instanceof OutlineContainer) {
                return ((OutlineContainer) view).getChildAt(0) == obj;
            } else {
                return view == obj;
            }
        }
    }
}
