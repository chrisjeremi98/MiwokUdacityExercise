package com.example.android.miwok;

/**
 * Created by asus on 11-Oct-17.
 */

public class Words {

    private String mDefaultTranslation;

    private String mMiwokTranslation;

    private int mMiwokImage = NO_IMAGE;

    private int mMiwokSound;

    private static final int NO_IMAGE = -1;

    public Words(String defaultTranslation, String miwokTranslation, int miwokSound) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mMiwokSound=miwokSound;
    }

    public Words(String defaultTranslation, String miwokTranslation, int miwokSound, int miwokImage){
        mDefaultTranslation=defaultTranslation;
        mMiwokTranslation=miwokTranslation;
        mMiwokImage=miwokImage;
        mMiwokSound=miwokSound;
    }

    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    public String getmMiwokTranslation(){
        return mMiwokTranslation;
    }

    public int getmMiwokImage(){return mMiwokImage;}

    public int getmMiwokSound(){return mMiwokSound;}

    public boolean checkImage(){return mMiwokImage != NO_IMAGE; }
}
