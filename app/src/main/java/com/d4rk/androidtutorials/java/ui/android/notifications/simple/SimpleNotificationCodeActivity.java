package com.d4rk.androidtutorials.java.ui.android.notifications.simple;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.ui.android.notifications.simple.tabs.SimpleNotificationTabCodeFragment;
import com.d4rk.androidtutorials.java.ui.android.notifications.simple.tabs.SimpleNotificationTabLayoutFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class SimpleNotificationCodeActivity extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        TabLayout tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SimpleNotificationTabCodeFragment(), getString(R.string.code_java));
        adapter.addFragment(new SimpleNotificationTabLayoutFragment(), getString(R.string.layout_xml));
        viewPager.setAdapter(adapter);
    }

    @SuppressWarnings("deprecation")
    private static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment> fragmentList = new ArrayList<>();
        private final ArrayList<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager) {
            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(@NonNull Fragment fragment, @NonNull String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}