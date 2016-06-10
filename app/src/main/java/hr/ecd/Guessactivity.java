package hr.ecd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class GuessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        String text = ((Ecd)getApplication()).getSpeechText();

        String[] words = text.replaceAll("[^A-Za-z]", "").toLowerCase().split(" ");
        Arrays.sort(words);

        Map<String, Integer> wordWorth = new HashMap<String, Integer>();
        if(words.length > 1) {
            for (int i = 1; i < words.length; i++) {
                if(words[i] == words[i-1]) {
                    if(!wordWorth.containsKey(words[i])) {
                        wordWorth.put(words[i], 1);
                    }
                    else {
                        Integer previousValue = wordWorth.get(words[i]);
                        wordWorth.remove(words[i]);
                        if(words[i].length() > 3)
                            wordWorth.put(words[i], (previousValue+5)); // Words longer than 3 characters are worth way more
                        else
                            wordWorth.put(words[i], (previousValue+1)); // Words less than 3 characters (The, it, at, on, in, and etc) will be only worth 1 point
                    }
                }
            }
        }

    }
}
