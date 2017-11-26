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

public class FamilyMembersActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener=
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK||focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    }else if(focusChange==AudioManager.AUDIOFOCUS_GAIN){
                        mMediaPlayer.start();
                    } else if (focusChange==AudioManager.AUDIOFOCUS_LOSS){
                        mMediaPlayer.release();
                    }
                }
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> family=new ArrayList<Words>();
        family.add(new Words("father","әpә",R.raw.family_father,R.drawable.family_father));
        family.add(new Words("mother","әṭa",R.raw.family_mother,R.drawable.family_mother));
        family.add(new Words("son","angsi",R.raw.family_son,R.drawable.family_son));
        family.add(new Words("daughter","tune",R.raw.family_daughter,R.drawable.family_daughter));
        family.add(new Words("older brother","taachi",R.raw.family_older_brother,R.drawable.family_older_brother));
        family.add(new Words("younger brother","chalitti",R.raw.family_younger_brother,R.drawable.family_younger_brother));
        family.add(new Words("older sister","teṭe",R.raw.family_older_sister,R.drawable.family_older_sister));
        family.add(new Words("younger sister","kolliti",R.raw.family_younger_sister,R.drawable.family_younger_sister));
        family.add(new Words("grandmother","ama",R.raw.family_grandmother,R.drawable.family_grandmother));
        family.add(new Words("grandfather","paapa",R.raw.family_grandfather,R.drawable.family_grandfather));

        WordAdapter adapter=new WordAdapter(this,family,R.color.category_family);
        ListView listView=(ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words word= family.get(position);
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(FamilyMembersActivity.this, word.getmMiwokSound());
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
