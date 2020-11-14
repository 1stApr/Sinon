package com.crocussativus.wallpaper.ui.infor;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.crocussativus.wallpaper.R;

public class InforFragment extends Fragment {

    private InforViewModel inforViewModel;
    private TextView textView15;
    private TextView textView16;
    private TextView textView40;
    private TextView textView41;
    private TextView textView42;
    private TextView textView43;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inforViewModel =
                ViewModelProviders.of(this).get(InforViewModel.class);
        View root = inflater.inflate(R.layout.fragment_infor, container, false);

//        final TextView textView = root.findViewById(R.id.text_share);
//        inforViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        textView15 = root.findViewById(R.id.textView15);
        if (textView15 != null) {
            textView15.setMovementMethod(LinkMovementMethod.getInstance());
        }
        textView16 = root.findViewById(R.id.textView16);
        if (textView16 != null) {
            textView16.setMovementMethod(LinkMovementMethod.getInstance());
        }
        textView40 = root.findViewById(R.id.textView40);
        if (textView40 != null) {
            textView40.setMovementMethod(LinkMovementMethod.getInstance());
        }
        textView41 = root.findViewById(R.id.textView41);
        if (textView41 != null) {
            textView41.setMovementMethod(LinkMovementMethod.getInstance());
        }
        textView42 = root.findViewById(R.id.textView42);
        if (textView42 != null) {
            textView42.setMovementMethod(LinkMovementMethod.getInstance());
        }
        textView43 = root.findViewById(R.id.textView43);
        if (textView43 != null) {
            textView43.setMovementMethod(LinkMovementMethod.getInstance());
        }
        return root;
    }
}