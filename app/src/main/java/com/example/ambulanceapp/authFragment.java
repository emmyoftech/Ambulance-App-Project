package com.example.ambulanceapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ambulanceapp.authpkg.authMain;

public class authFragment extends Fragment {

    private static final String AppDataFromMain = "AppData";

    private String stringData;
    public authFragment() {
    }
    public static authFragment newInstance(String data) {
        authFragment fragment = new authFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stringData = getArguments().getString(AppDataFromMain);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragRootView = inflater.inflate(R.layout.fragment_auth, container, false);
        new authMain(fragRootView, requireContext());
        return fragRootView;
    }
}