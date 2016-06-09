package hr.ecd;

import java.util.ArrayList;
import android.content.Context;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.media.AudioManager;

import org.w3c.dom.Text;

public class SpeechActivity extends Activity implements RecognitionListener {

    private TextView returnedText;
    private TextView subjectiefText;
    private TextView objectiefText;
    private TextView evaluatieText;
    private TextView subjectiefKopText;
    private TextView objectiefKopText;
    private TextView evaluatieKopText;
    private TextView planKopText;
    private TextView planText;
    private ToggleButton toggleButton;
    private ProgressBar progressBar;
    private AudioManager audioManager;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";
    private int streamVolume = 0;
    private String totalText = "";
    private String partialText = "";
    private String subjectiefTotalText = "";
    private String objectiefTotalText = "";
    private String evaluatieTotalText = "";
    private String planTotalText = "";
    private String[] allText = new String[4];

    private SOEP SOEPStatus;
    public enum SOEP{
        SUBJECTIEF,
        OBJECTIEF,
        EVALUATIE,
        PLAN;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speech);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        returnedText = (TextView) findViewById(R.id.speechTextView);
        subjectiefText = (TextView) findViewById(R.id.subjectiefText);
        objectiefText = (TextView) findViewById(R.id.objectiefText);
        evaluatieText = (TextView) findViewById(R.id.evaluatieText);
        planText = (TextView) findViewById(R.id.planText);
        subjectiefKopText = (TextView) findViewById(R.id.subjectiefKopText);
        objectiefKopText = (TextView) findViewById(R.id.objectiefKopText);
        evaluatieKopText = (TextView) findViewById(R.id.evaluatieKopText);
        planKopText = (TextView) findViewById(R.id.planKopText);

        progressBar = (ProgressBar) findViewById(R.id.speechProgressBar);
        toggleButton = (ToggleButton) findViewById(R.id.speechToggleButton);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        listen();
        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    listen();
                } else {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    speech.stopListening();
                    speech.destroy();
                    allText[0] = subjectiefText.getText().toString();
                    allText[1] = objectiefText.getText().toString();
                    allText[2] = evaluatieText.getText().toString();
                    allText[3] = planText.getText().toString();
                    setSpeechText(allText);
                }
            }
        });
        SOEPStatus = SOEP.SUBJECTIEF;
    }


    public void listen(){
        streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);

        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "nl");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        speech.startListening(recognizerIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        progressBar.setIndeterminate(false);
        progressBar.setMax(10);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        //progressBar.setIndeterminate(true);
        //toggleButton.setChecked(false);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, streamVolume, 0);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        switch(errorCode) {
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
            case SpeechRecognizer.ERROR_NO_MATCH:
                speech.destroy();

                listen();
                break;
        }
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.i(LOG_TAG, "onPartialResults");
        ArrayList<String> matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        partialText = matches.get(0);
        partialText = partialText.replaceAll(" punt",".").replaceAll("punt",".");
        partialText = partialText.replaceAll(" vraagteken","?").replaceAll("vraagteken","?");
        partialText = partialText.replaceAll(" uitroepteken","!").replaceAll("uitroepteken","!");
        partialText = partialText.replaceAll(" komma",", ").replaceAll("komma",", ");

        String commands = partialText.toLowerCase().replaceAll(" ","");
        if (commands.contains("stopopnemen")) {
            speech.stopListening();
            speech.destroy();
            partialText = partialText.replaceAll("stop opnemen", "");
            toggleButton.setChecked(false);
        }
        else if(commands.contains("subjectief")){
            SOEPStatus = SOEP.SUBJECTIEF;
            partialText = partialText.replaceAll("subjectief", "");
        }
        else if(commands.contains("objectief")){
            SOEPStatus = SOEP.OBJECTIEF;
            partialText = partialText.replaceAll("objectief", "");
        }
        else if(commands.contains("evaluatie")){
            SOEPStatus = SOEP.EVALUATIE;
            partialText = partialText.replaceAll("evaluatie", "");
        }
        else if(commands.contains("plan")){
            SOEPStatus = SOEP.PLAN;
            partialText = partialText.replaceAll("plan", "");
        }
        setActiveSOEP();
        switch (SOEPStatus){
            case SUBJECTIEF:
                subjectiefKopText.append("Actief");
                subjectiefText.setText(subjectiefTotalText + partialText);
                break;
            case OBJECTIEF:
                objectiefKopText.append("Actief");
                objectiefText.setText(objectiefTotalText + partialText);
                break;
            case EVALUATIE:
                evaluatieKopText.append("Actief");
                evaluatieText.setText(evaluatieTotalText + partialText);
                break;
            case PLAN:
                planKopText.append("Actief");
                planText.setText(planTotalText + partialText);
                break;
            default:
                break;
        }
        returnedText.setText(totalText + partialText);
    }

    public void setActiveSOEP(){
        subjectiefKopText.setText("Subjectief: ");
        objectiefKopText.setText("Objectief: ");
        evaluatieKopText.setText("Evaluatie: ");
        planKopText.setText("Plan: ");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        totalText = returnedText.getText()+" ";
        switch (SOEPStatus){
            case SUBJECTIEF:
                subjectiefTotalText = subjectiefText.getText()+" ";
                break;
            case OBJECTIEF:
                objectiefTotalText = objectiefText.getText()+" ";
                break;
            case EVALUATIE:
                evaluatieTotalText = evaluatieText.getText()+" ";
                break;
            case PLAN:
                planTotalText = planText.getText()+" ";
                break;
            default:
                break;
        }
        speech.destroy();
        listen();
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        progressBar.setProgress((int) rmsdB);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    public void setSpeechText(String[] speechText){
        ((Ecd)this.getApplication()).setSpeechText(speechText);
        for(String s:allText){
            Log.i("speechText",s);
        }
    }
}
