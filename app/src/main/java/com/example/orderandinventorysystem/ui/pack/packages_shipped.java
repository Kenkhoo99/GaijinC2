package com.example.orderandinventorysystem.ui.pack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.orderandinventorysystem.R;

public class packages_shipped extends Fragment {


 public packages_shipped() {

 }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_packages_shipped, container, false);
    }

}
