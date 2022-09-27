package com.utopiaxc.utopiatts.tts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.util.concurrent.Future;

public class MsTts {
    private static final String TAG = "MsTts";
    //Here use getApplicationContext to avoid memory leak
    @SuppressLint("StaticFieldLeak")
    private static volatile MsTts mInstance;
    private Context mContext;
    private SpeechSynthesizer mSpeechSynthesizer;

    public MsTts(Context context) {
        mContext = context;
        initTts();
    }

    public static MsTts getInstance(Context context) {
        if (mInstance == null) {
            synchronized (MsTts.class) {
                if (mInstance == null) {
                    mInstance = new MsTts(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    public void doSpeak(String text, int pitch, int rate) {
        Ssml ssml = new Ssml(text, "zh-CN-XiaoxiaoNeural", pitch,
                rate, "", 0);
        Future<SpeechSynthesisResult> speechSynthesisResultFuture =
                mSpeechSynthesizer.SpeakSsmlAsync(ssml.toString());
        while (!speechSynthesisResultFuture.isDone()) {
            if (speechSynthesisResultFuture.isCancelled()) {
                Log.d(TAG, "speechSynthesisResultFuture.isCancelled");
                break;
            }
        }
    }

    public void stopSpeak() {
        if (mSpeechSynthesizer != null) {
            mSpeechSynthesizer.StopSpeakingAsync();
        }
    }

    public SpeechSynthesizer getSpeechSynthesizer() {
        return mSpeechSynthesizer;
    }

    public void initTts() {
        Log.i(TAG, "initTts");
        SpeechConfig mSpeechConfig = SpeechConfig.fromSubscription(
                "", Regions.EAST_ASIA.getId());
        mSpeechConfig.setSpeechSynthesisOutputFormat(
                OutputFormat.RAW_48K_HZ_16_BIT_MONO_PCM.getSpeechSynthesisOutputFormat());
        AudioConfig audioConfig = AudioConfig.fromDefaultSpeakerOutput();
        mSpeechSynthesizer = new SpeechSynthesizer(mSpeechConfig, audioConfig);
    }
}
