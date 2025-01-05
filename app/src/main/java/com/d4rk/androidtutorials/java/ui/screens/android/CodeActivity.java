package com.d4rk.androidtutorials.java.ui.screens.android;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.d4rk.androidtutorials.java.R;
import com.d4rk.androidtutorials.java.databinding.ActivityTabLayoutBinding;
import com.d4rk.androidtutorials.java.ui.screens.android.repository.LessonRepository;
import com.d4rk.androidtutorials.java.utils.EdgeToEdgeDelegate;
import com.google.android.material.tabs.TabLayoutMediator;

public class CodeActivity extends AppCompatActivity {
    private ActivityTabLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTabLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdgeDelegate edgeToEdgeDelegate = new EdgeToEdgeDelegate(this);
        edgeToEdgeDelegate.applyEdgeToEdge(binding.tabLayout);

        CodeViewModel viewModel = new ViewModelProvider(this).get(CodeViewModel.class);

        String lessonName = getIntent().getStringExtra("lesson_name");
        if (lessonName == null) {
            finish();
            return;
        }
        viewModel.setLessonName(lessonName);

        viewModel.getLesson().observe(this, lesson -> {
            setupViewPager(lesson);
            setTitle(getString(lesson.titleResId()));
        });
    }

    private void setupViewPager(LessonRepository.Lesson lesson) {
        CodePagerAdapter adapter = new CodePagerAdapter(this, lesson);
        binding.viewpager.setAdapter(adapter);

        new TabLayoutMediator(binding.tabs, binding.viewpager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText(R.string.code_java);
                    } else if (position == 1) {
                        tab.setText(R.string.layout_xml);
                    }
                }
        ).attach();
    }

    private static class CodePagerAdapter extends FragmentStateAdapter {
        private final LessonRepository.Lesson lesson;

        public CodePagerAdapter(@NonNull AppCompatActivity fragmentActivity, LessonRepository.Lesson lesson) {
            super(fragmentActivity);
            this.lesson = lesson;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return position == 0
                    ? CodeFragment.newInstance(lesson.codeResId())
                    : LayoutFragment.newInstance(lesson.layoutResId());
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}