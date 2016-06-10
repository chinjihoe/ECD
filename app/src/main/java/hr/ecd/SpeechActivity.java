package hr.ecd;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.media.AudioManager;

public class SpeechActivity extends Activity implements RecognitionListener {

    private TextView returnedText;
    private TextView subjectiefText;
    private TextView objectiefText;
    private TextView evaluatieText;
    private TextView planText;

    private TextView subjectiefKopText;
    private TextView objectiefKopText;
    private TextView evaluatieKopText;
    private TextView planKopText;
    private TextView SOEPText;

    private Button afrondenButton;
    private Button vorigeButton;
    private Button volgendeButton;
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
        SOEPText = (TextView) findViewById(R.id.subjectiefText);
        subjectiefKopText = (TextView) findViewById(R.id.subjectiefKopText);
        objectiefKopText = (TextView) findViewById(R.id.objectiefKopText);
        evaluatieKopText = (TextView) findViewById(R.id.evaluatieKopText);
        planKopText = (TextView) findViewById(R.id.planKopText);
        vorigeButton = (Button) findViewById(R.id.vorigeButton);
        volgendeButton = (Button) findViewById(R.id.volgendeButton);
        afrondenButton = (Button) findViewById(R.id.afrondenButton);
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
        vorigeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int previous = SOEP.valueOf(SOEPStatus.toString()).ordinal()-1;
                if(previous == -1)
                    previous = 3;
                SOEPStatus = SOEP.values()[previous];
                changeSOEPStatus();
            }
        });
        volgendeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int next = SOEP.valueOf(SOEPStatus.toString()).ordinal()+1;
                if(next == 4)
                    next = 0;
                SOEPStatus = SOEP.values()[next];
                changeSOEPStatus();
            }
        });
        afrondenButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                alertDialog();
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
    public void onBackPressed() {
        alertDialog();
    }

    public void alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Afronden?");

        // Set up the buttons
        builder.setPositiveButton("Nee", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toggleButton.setChecked(false);
                dialog.cancel();
                finish();
            }
        });
        builder.show();

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
        ArrayList<String> matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        partialText = matches.get(0);
        Log.i(LOG_TAG,"onPartialResults: "+partialText);

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
            changeSOEPStatus();
            Log.i("SOEPStatus",SOEPStatus.toString());

        }
        else if(commands.contains("objectief")){
            SOEPStatus = SOEP.OBJECTIEF;
            partialText = partialText.replaceAll("objectief", "");
            changeSOEPStatus();
            Log.i("SOEPStatus",SOEPStatus.toString());

        }
        else if(commands.contains("evaluatie")){
            SOEPStatus = SOEP.EVALUATIE;
            partialText = partialText.replaceAll("evaluatie", "");
            changeSOEPStatus();
            Log.i("SOEPStatus",SOEPStatus.toString());

        }
        else if(commands.contains("plan")){
            SOEPStatus = SOEP.PLAN;
            partialText = partialText.replaceAll("plan", "");
            changeSOEPStatus();
            Log.i("SOEPStatus",SOEPStatus.toString());

        }
        switch (SOEPStatus){
            case SUBJECTIEF:
                SOEPText.setText(replaceSymbols(subjectiefTotalText+partialText));
                break;
            case OBJECTIEF:
                SOEPText.setText(replaceSymbols(objectiefTotalText+partialText));
                break;
            case EVALUATIE:
                SOEPText.setText(replaceSymbols(evaluatieTotalText+partialText));
                break;
            case PLAN:
                SOEPText.setText(replaceSymbols(planTotalText+partialText));
                break;
            default:
                break;
        }
        //SOEPText.setText((SOEPText.getText().toString().trim()).replaceAll("\\s+"," "));
        //totalText.replaceAll(" punt",".").replaceAll(" vraagteken","?").replaceAll(" uitroepteken","!").replaceAll(" komma",",");
        returnedText.setText(totalText+partialText);
    }

    public String replaceSymbols(String totalText){
        totalText = totalText.replaceAll(" punt",".").replaceAll(" vraagteken","?").replaceAll(" uitroepteken","!").replaceAll(" komma",",");
        return totalText;
    }

    public void changeSOEPStatus(){
        subjectiefKopText.setText("Subjectief: ");
        objectiefKopText.setText("Objectief: ");
        evaluatieKopText.setText("Evaluatie: ");
        planKopText.setText("Plan: ");
        switch (SOEPStatus){
            case SUBJECTIEF:
                SOEPText = subjectiefText;
                subjectiefKopText.append("Actief");
                //subjectiefText.setText(subjectiefTotalText + partialText);
                break;
            case OBJECTIEF:
                SOEPText = objectiefText;
                objectiefKopText.append("Actief");
                //objectiefText.setText(objectiefTotalText + partialText);
                break;
            case EVALUATIE:
                SOEPText = evaluatieText;
                evaluatieKopText.append("Actief");
                //evaluatieText.setText(evaluatieTotalText + partialText);
                break;
            case PLAN:
                SOEPText = planText;
                planKopText.append("Actief");
                //planText.setText(planTotalText + partialText);
                break;
            default:
                break;
        }
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults: "+results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0));
        totalText = returnedText.getText()+" ";
        switch (SOEPStatus){
            case SUBJECTIEF:
                subjectiefTotalText = SOEPText.getText()+" ";
                break;
            case OBJECTIEF:
                objectiefTotalText = SOEPText.getText()+" ";
                break;
            case EVALUATIE:
                evaluatieTotalText = SOEPText.getText()+" ";
                break;
            case PLAN:
                planTotalText = SOEPText.getText()+" ";
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
