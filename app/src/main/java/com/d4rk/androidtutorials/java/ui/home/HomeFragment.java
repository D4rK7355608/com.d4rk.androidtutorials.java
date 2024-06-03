package com.d4rk.androidtutorials.java.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.d4rk.androidtutorials.java.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.d4rk.androidtutorials.java.databinding.FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}