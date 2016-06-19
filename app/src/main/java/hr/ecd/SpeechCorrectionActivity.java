package hr.ecd;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by Chinji on 16-6-2016.
 */
public class SpeechCorrectionActivity extends SpeechActivity {/*
    private static final int TRANSLATE = 1;

    protected void main(){
        super.subjectiefText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                menu.add(0,TRANSLATE,0,"Translate"); //.setIcon(R.drawable.ic_translate); //choose any icon
                // Remove the other options
                menu.removeItem(android.R.id.selectAll);
                menu.removeItem(android.R.id.cut);
                menu.removeItem(android.R.id.copy);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()){
                    case TRANSLATE:
                        int min = 0;
                        int max = subjectiefText.getText().length();
                        if (subjectiefText.isFocused()) {
                            final int selStart = subjectiefText.getSelectionStart();
                            final int selEnd = subjectiefText.getSelectionEnd();

                            min = Math.max(0, Math.min(selStart, selEnd));
                            max = Math.max(0, Math.max(selStart, selEnd));
                        }

                        final CharSequence selectedText = subjectiefText.getText().subSequence(min, max); //this is your desired string
                        Toast.makeText(getApplicationContext(),selectedText,Toast.LENGTH_SHORT).show();

                        //Here put your code for translation

                        mode.finish();
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }*/
}
