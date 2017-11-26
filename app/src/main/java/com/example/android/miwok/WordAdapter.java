package com.example.android.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by asus on 11-Oct-17.
 */

public class WordAdapter extends ArrayAdapter<Words> {
    private int mColorsAdapter;

    public WordAdapter(Activity context, ArrayList<Words> words, int ColorsResource){

        super(context,0,words);
        mColorsAdapter=ColorsResource;
    }


    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView=convertView;
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Words currentWords=getItem(position);

        TextView defautTextView=(TextView) listItemView.findViewById(R.id.english_text_view);
        defautTextView.setText(currentWords.getDefaultTranslation());

        TextView miwokTextView=(TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWords.getmMiwokTranslation());

        ImageView miwokImage=(ImageView) listItemView.findViewById(R.id.miwok_image);


        if(currentWords.checkImage()){
            miwokImage.setImageResource(currentWords.getmMiwokImage());
        } else{
            miwokImage.setVisibility(View.GONE);
        }

        View TextContainer = listItemView.findViewById(R.id.textcontainer);
        int color= ContextCompat.getColor(getContext(), mColorsAdapter);
        TextContainer.setBackgroundColor(color);



        return listItemView;
    }
}
