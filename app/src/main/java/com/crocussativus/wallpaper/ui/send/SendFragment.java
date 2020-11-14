package com.crocussativus.wallpaper.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.crocussativus.wallpaper.R;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;
    private Button buttonSendEmail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);


        buttonSendEmail = root.findViewById(R.id.buttonSendEmail);

//        final TextView textView = root.findViewById(R.id.text_send);
//        sendViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        buttonSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonSenEmail();
            }
        });
        return root;
    }

    private void onClickButtonSenEmail() {
        Intent Email = new Intent(Intent.ACTION_SEND);
        Email.setType("text/email");
        Email.putExtra(Intent.EXTRA_EMAIL, new String[]{"tuan98.tran@gmail.com"});
        Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback Image App");
        Email.putExtra(Intent.EXTRA_TEXT, "Dear ...," + "");
        startActivity(Intent.createChooser(Email, "Send Feedback:"));
    }

}