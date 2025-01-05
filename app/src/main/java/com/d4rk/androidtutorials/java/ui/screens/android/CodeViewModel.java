package com.d4rk.androidtutorials.java.ui.screens.android;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.d4rk.androidtutorials.java.ui.screens.android.repository.LessonRepository;

public class CodeViewModel extends ViewModel {
    private final MutableLiveData<LessonRepository.Lesson> lesson = new MutableLiveData<>();
    private final LessonRepository repository = new LessonRepository();

    public void setLessonName(String lessonName) {
        lesson.setValue(repository.getLesson(lessonName));
    }

    public LiveData<LessonRepository.Lesson> getLesson() {
        return lesson;
    }
}
