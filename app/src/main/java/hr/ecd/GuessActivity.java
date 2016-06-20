package hr.ecd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GuessActivity extends AppCompatActivity {

    public static <K, V extends Comparable<? super V>> Map<K, V>
    sortByValue( Map<K, V> map )
    {
        List<Map.Entry<K, V>> list =
                new LinkedList<>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return -( o1.getValue() ).compareTo( o2.getValue() );
            }
        } );

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);
        if(((Ecd)getApplication()).getSpeechText() == null) {
            TextView tv = (TextView)findViewById(R.id.guessText);
            tv.setText("Geen relevantie informatie gevonden.");
        }
        else {
            checkWordValues();
        }

    }

    protected void checkWordValues() {
        String text = ((Ecd)getApplication()).getSpeechText()[0];

        String[] words = text.replaceAll("[^A-Za-z ]", "").toLowerCase().split(" ");
        Arrays.sort(words);

        Map<String, Integer> wordWorth = new HashMap<String, Integer>();
        if(words.length > 1) {
            for (int i = 0; i < words.length; i++) {
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

        TextView tv = (TextView)findViewById(R.id.guessText);
        Map<String, Integer> wordWorthSorted = GuessActivity.sortByValue(wordWorth);
        Iterator it = wordWorthSorted.entrySet().iterator();
        tv.setText("");
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            tv.append(pair.getKey() + " " + pair.getValue() + "\n");
        }
    }
}
