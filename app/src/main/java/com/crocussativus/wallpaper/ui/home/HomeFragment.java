package com.crocussativus.wallpaper.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.crocussativus.wallpaper.InternetConnection;
import com.crocussativus.wallpaper.MainActivity;
import com.crocussativus.wallpaper.R;
import com.crocussativus.wallpaper.SaveImage;
import com.crocussativus.wallpaper.SettingsActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import at.markushi.ui.CircleButton;
import dmax.dialog.SpotsDialog;

public class HomeFragment extends Fragment {

    public static final int PERMISSION_REQUEST_CODE = 1000;
    public static ArrayList<String> listImage;
    public int isSuffle = 1;
    private HomeViewModel homeViewModel;
    private ImageView imageView;
    private TextView tvID;
    private CircleButton buttonSet;
    private CircleButton buttonMore;
    private CircleButton buttonFullScreen;
    private CircleButton buttonDownload;
    private CircleButton buttonSuffle;
    private CircleButton buttonBack;
    private CircleButton buttonPlay;
    private InterstitialAd mInterstitialAd;
    private int position = 0;
    private int positionBack = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, PERMISSION_REQUEST_CODE);
        }
        isSuffle = 1;
        if (SettingsActivity.SyncFragment.getTimeDelay() != 0) {

        }

        imageView = root.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Click!", Toast.LENGTH_SHORT).show();

            }
        });

        tvID = root.findViewById(R.id.tv_id);
        tvID.setText("Welcome!");
        position = 0;
        positionBack = 1;
        listImage = new ArrayList<String>();
        listImage = MainActivity.imageArrayList;


        buttonSet = root.findViewById(R.id.buttonSet);
        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonSet();
            }
        });

        buttonFullScreen = root.findViewById(R.id.buttonFullScreen);
        buttonFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonFullScreen();
            }
        });

        buttonDownload = root.findViewById(R.id.buttonDownload);
        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonDownload();
            }
        });

        buttonSuffle = root.findViewById(R.id.buttonSuffle);
        buttonSuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonSuffle();
            }
        });

        buttonBack = root.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonBack();
            }
        });
        buttonPlay = root.findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonPlay();
            }
        });

//      Ad

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(getActivity());
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdUnitId("ca-app-pub-2521767413083911/2583020837");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Toast.makeText(getContext(), "AdLoaded!", Toast.LENGTH_LONG).show();
                Log.d("AD:", "AdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Toast.makeText(getContext(), "Error! AdFailedToLoad", Toast.LENGTH_LONG).show();
                Log.d("AD:", "AdFailedToLoad.");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Toast.makeText(getContext(), "AdOpened!", Toast.LENGTH_LONG).show();
                Log.d("AD:", "AdOpened");
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                Log.d("AD:", "AdClose");
            }
        });

        buttonMore = root.findViewById(R.id.buttonMore);
        buttonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonMore();
            }
        });

        return root;
    }


    private void onClickButtonPlay() {

        if (InternetConnection.checkConnection(getContext())) {

            if (isSuffle == 1) {
                Random r = new Random();
                int position1 = r.nextInt(MainActivity.imageArrayList.size() - 1) + 1;
                positionBack = position;
                position = position1;
                final Uri uri = Uri.parse(MainActivity.imageArrayList.get(position));
                Log.d("LinkImage: ",""+position+" || "+uri+"");

                Picasso.with(getContext()).load(uri).into(imageView);
                tvID.setText("ID:" + position);

            } else {
                if (position >= MainActivity.imageArrayList.size() - 1) {
                    position = 1;
                    positionBack = position;
                    position++;
                    final Uri uri = Uri.parse(MainActivity.imageArrayList.get(position));
                    Log.d("LinkImage: ",""+position+" || "+uri+"");

                    Picasso.with(getContext()).load(uri).into(imageView);
                    tvID.setText("ID:" + position);
                } else {
                    positionBack = position;
                    position++;
                    final Uri uri = Uri.parse(MainActivity.imageArrayList.get(position));
                    Log.d("LinkImage: ",""+position+" || "+uri+"");

                    Picasso.with(getContext()).load(uri).into(imageView);
                    tvID.setText("ID:" + position);
                }
            }


        } else {
            Toast.makeText(getContext(), "Internet Not Available.", Toast.LENGTH_LONG).show();
        }

    }

    private void onClickButtonBack() {
        if (InternetConnection.checkConnection(getContext())) {

            if (position == 0) {

                onClickButtonPlay();

            } else {
                int temp = position;
                position = positionBack - 1;
                positionBack = temp;
                onClickButtonPlay();
            }

        } else {
            Toast.makeText(getContext(), "Internet Not Available.", Toast.LENGTH_LONG).show();
        }


    }

    private void onClickButtonSuffle() {
        if (isSuffle == 1) {
            Toast.makeText(getActivity(), "Suffle Off", Toast.LENGTH_LONG).show();
            isSuffle = 0;
        } else {
            Toast.makeText(getActivity(), "Suffle On", Toast.LENGTH_LONG).show();
            isSuffle = 1;
        }

    }

    private void onClickButtonDownload() {
        if (position <= 0) {
            Toast.makeText(getActivity(), "Click Next Button Before", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Downloading...", Toast.LENGTH_SHORT).show();
            String linkImage;
            linkImage = MainActivity.imageArrayList.get(position);
            Uri uri = Uri.parse(linkImage);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Your should grant permission", Toast.LENGTH_SHORT).show();
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, PERMISSION_REQUEST_CODE);
                return;
            } else {
                final AlertDialog alertDialog = new SpotsDialog(getActivity());
                alertDialog.show();
                alertDialog.setMessage("Downloading...");
                String fileName = UUID.randomUUID().toString() + ".jpg";
                Picasso.with(getContext())
                        .load(uri)
                        .into(new SaveImage(getContext(), alertDialog, getContext().getContentResolver(), fileName, "Image description"));

            }
        }

    }


    private void onClickButtonFullScreen() {
        if (position < 1) {
            Toast.makeText(getActivity(), "Click Next Button Before!", Toast.LENGTH_SHORT).show();
        } else {
            String linkImage;
            linkImage = MainActivity.imageArrayList.get(position);
            Uri uri = Uri.parse(linkImage);
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
            likeIng.setPackage("com.instagram.android");
            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(linkImage)));
            }
        }


    }

    private void onClickButtonSet() {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getContext());
        try {
            myWallpaperManager.setBitmap(bitmap);
            Toast.makeText(getActivity(), "Wallpaper set", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Error setting wallpaper!", Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickButtonMore() {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            Log.d("ADLOG", "The AD Loaded!");
        } else {
            Log.d("ADLOG", "The interstitial wasn't loaded yet.");

            if (position < 1) {
                Toast.makeText(getActivity(), "Click Next Button Before!", Toast.LENGTH_SHORT).show();
            } else {
                String linkImage;
                linkImage = MainActivity.imageArrayList2.get(position);
                Uri uri = Uri.parse(linkImage);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                likeIng.setPackage("com.instagram.android");
                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(linkImage)));
                }
            }

        }

    }

}