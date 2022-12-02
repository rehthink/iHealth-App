package com.example.iHealth.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iHealth.R;
import com.example.iHealth.databinding.FragmentUserInfoBinding;

public class UserInfoFragment extends Fragment {

    FragmentUserInfoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUserInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
}