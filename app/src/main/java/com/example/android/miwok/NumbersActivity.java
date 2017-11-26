package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

public class NumbersActivity extends AppCompatActivity {
private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT|| focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    }
                     else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                        mMediaPlayer.start();
                    }
                    else if(focusChange==AudioManager.AUDIOFOCUS_LOSS){
                        mMediaPlayer.release();

                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Words> words=new ArrayList<Words>();
//        words.add("One");
            words.add(new Words("One", "Lutti",R.raw.number_one,R.drawable.number_one));
//        words.add("Two");
        words.add(new Words("Two", "Otiiko",R.raw.number_two,R.drawable.number_two));
//        words.add("Three");
        words.add(new Words("Three", "Tolokosu",R.raw.number_three,R.drawable.number_three));
//        words.add("Four");
        words.add(new Words("Four", "Oyyisa",R.raw.number_four,R.drawable.number_four));
//        words.add("Five");
        words.add(new Words("Five", "Massioa",R.raw.number_five,R.drawable.number_five));
//        words.add("Six");
        words.add(new Words("Six", "Temmoka",R.raw.number_six,R.drawable.number_six));
//        words.add("Seven");
        words.add(new Words("Seven", "Kanekaku",R.raw.number_seven,R.drawable.number_seven));
//        words.add("Eight");
        words.add(new Words("Eight", "Kawinta",R.raw.number_eight,R.drawable.number_eight));
//        words.add("Nine");
        words.add(new Words("Nine", "Woe",R.raw.number_nine,R.drawable.number_nine));
//        words.add("Ten");
        words.add(new Words("Ten", "Na'aacha",R.raw.number_ten,R.drawable.number_ten));

        //TEST LOG
//        ArrayList<String> restaurant= new ArrayList<String>();
//        restaurant.add("Good Food");
//        restaurant.add("Bad Food");
//        restaurant.remove("Bad Food");
//        int number=restaurant.size();

        WordAdapter adapter = new WordAdapter(this, words,R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override

            public  void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //posisi adapter yang diklik, berbeda dengan di adapter pada activity harus dilakukan manual
                Words word=words.get(position);
                releaseMediaPlayer();
                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback

                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getmMiwokSound());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }
    /**
     * Clean up the media player by releasing its resources.
     */
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
