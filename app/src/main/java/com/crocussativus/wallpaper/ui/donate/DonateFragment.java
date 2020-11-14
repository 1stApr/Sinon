package com.crocussativus.wallpaper.ui.donate;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.crocussativus.wallpaper.R;

public class DonateFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    private Button buttonPalpal;
    private ToggleButton toggleButtonDonate;
    private ImageView imageView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_donate, container, false);

//        final TextView textView = root.findViewById(R.id.text_tools);
////        toolsViewModel.getText().observe(this, new Observer<String>() {
////            @Override
////            public void onChanged(@Nullable String s) {
////                textView.setText(s);
////            }
////        });
        buttonPalpal = root.findViewById(R.id.button);
        buttonPalpal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickButtonPalpal(v);
            }
        });
        imageView = root.findViewById(R.id.imageView);

        return root;
    }

    public void onclickButtonPalpal(View view) {
        Toast.makeText(getContext(), "Please wait... ", Toast.LENGTH_LONG).show();
        String payPalLink;
        payPalLink = "https://www.paypal.me/meeooooow/";
        Uri uri = Uri.parse(payPalLink);
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
        likeIng.setPackage("com.palpal.android");
        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(payPalLink)));
        }
    }

}