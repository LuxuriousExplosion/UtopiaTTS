package com.utopiaxc.utopiatts.engine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class CheckVoiceData extends Activity {
    private static final String TAG = CheckVoiceData.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int result = TextToSpeech.Engine.CHECK_VOICE_DATA_PASS;

        ArrayList<String> unavailable = new ArrayList<>();

        ArrayList<String> available = new ArrayList<>(Arrays.asList("zho-CHN", "zho-HKG", "zho-TWN"));


        Intent returnData = new Intent();
        Log.i(TAG, available.toString());

        returnData.putStringArrayListExtra(TextToSpeech.Engine.EXTRA_AVAILABLE_VOICES, available);
        returnData.putStringArrayListExtra(TextToSpeech.Engine.EXTRA_UNAVAILABLE_VOICES, unavailable);
        setResult(result, returnData);
        finish();
    }
}