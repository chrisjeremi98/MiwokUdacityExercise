    package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
    private AudioManager mAudioManager;
    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mCompletionListener= new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener=
            new AudioManager.OnAudioFocusChangeListener(){
                @Override
                public void onAudioFocusChange(int focusChange){
                    if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if(focusChange==AudioManager.AUDIOFOCUS_GAIN){
                        mMediaPlayer.start();
                    }else if(focusChange==AudioManager.AUDIOFOCUS_LOSS){
                        mMediaPlayer.release();
                    }

                }

            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> phrase=new ArrayList<Words>();
        phrase.add(new Words("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        phrase.add(new Words("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        phrase.add(new Words("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        phrase.add(new Words("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        phrase.add(new Words("I’m feeling good","kuchi achit",R.raw.phrase_im_feeling_good));
        phrase.add(new Words("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        phrase.add(new Words("Yes, I’m coming","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        phrase.add(new Words("I’m coming","әәnәm",R.raw.phrase_im_coming));
        phrase.add(new Words("Let’s go","yoowutis",R.raw.phrase_lets_go));
        phrase.add(new Words("Come here","әnni'nem",R.raw.phrase_come_here));


        WordAdapter adapter = new WordAdapter(this,phrase,R.color.category_phrases);
        ListView listView=(ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words word=phrase.get(position);
                //sebelum audio selesai
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getmMiwokSound());
                    mMediaPlayer.start();
                    //setelah audio seleasi
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

    }
    /**
     * Clean up the media player by releasing its resources.
     */
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
