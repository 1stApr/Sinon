package com.crocussativus.wallpaper.ui.featured;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.crocussativus.wallpaper.R;

public class FeaturedFragment extends Fragment {

    private FeaturedViewModel featuredViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        featuredViewModel =
                ViewModelProviders.of(this).get(FeaturedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_featured_image, container, false);

//        final TextView textView = root.findViewById(R.id.text_slideshow);
//        featuredViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        return root;
    }
}