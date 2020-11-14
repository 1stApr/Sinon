package com.crocussativus.wallpaper.ui.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.crocussativus.wallpaper.InternetConnection;
import com.crocussativus.wallpaper.MainActivity;
import com.crocussativus.wallpaper.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GalleryFragment extends Fragment {

    Bitmap[] bitmaps;
    int size = 0;
    private GalleryViewModel galleryViewModel;
    private RecyclerView recyclerView;
    private CardView cardView1;
    private CardView cardView2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        size = MainActivity.imageArrayList.size() - 1;
        try {
            bitmaps = getBitmap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cardView1 = root.findViewById(R.id.card1);
        cardView1.setVisibility(View.VISIBLE);
        cardView2 = root.findViewById(R.id.card2);
        cardView2.setVisibility(View.INVISIBLE);

        recyclerView = root.findViewById(R.id.stagger);
        MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter(bitmaps);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(myRecyclerAdapter);
        recyclerView.smoothScrollToPosition(size);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InternetConnection.checkConnection(getContext())) {
                    cardView1.setVisibility(View.INVISIBLE);
                    cardView2.setVisibility(View.VISIBLE);
                    recyclerView.smoothScrollToPosition(0);

                } else {
                    Toast.makeText(getContext(), "Internet Not Available.", Toast.LENGTH_LONG).show();
                }

            }
        });

        return root;
    }

    public void initCountDownTimer(int time) {
        new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                //textView.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                //textView.setText("done!");
            }
        }.start();
    }

    private Bitmap[] getBitmap() throws IOException {

        final Bitmap[] tempBitmap = new Bitmap[size];
//        tempBitmap[0] = BitmapFactory.decodeResource(getResources(),R.drawable.one);

        for (int i = 0; i < size; i++) {
            final int position = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final String name = MainActivity.imageArrayList.get(position);
                        URL url = new URL(name);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        Bitmap myBitmap = BitmapFactory.decodeStream(input);
                        tempBitmap[position] = myBitmap;
                    } catch (IOException e) {
                        // Null
                    }
                }
            }).start();
        }

        return tempBitmap;
    }

    private class MyRecyclerAdapter extends RecyclerView.Adapter<GridHolder> {
        Bitmap[] bitmaps;


        public MyRecyclerAdapter(Bitmap[] bitmaps) {
            this.bitmaps = bitmaps;
        }

        @NonNull
        @Override
        public GridHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.row_item, viewGroup, false);

            return new GridHolder(view);

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void onBindViewHolder(@NonNull GridHolder gridHolder, int position) {
            gridHolder.imageView.setImageBitmap(bitmaps[position]);
            gridHolder.position = position;

        }

        @Override
        public int getItemCount() {
            return bitmaps.length;
        }

    }

    private class GridHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        int position;
        TextView textView;
        String path;
        String useLink;

        public GridHolder(@NonNull final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.row_img);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "ID: " + position + "", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}