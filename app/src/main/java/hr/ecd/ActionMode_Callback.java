package hr.ecd;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import hr.ecd.R;

/**
 * Callback van het contect menu als je text wilt editen
 */
public class ActionMode_Callback implements ActionMode.Callback {

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        // Remove the "select all" option
        menu.removeItem(android.R.id.selectAll);
        menu.removeItem(android.R.id.shareText);
        // Remove the "cut" option
        menu.removeItem(android.R.id.cut);
        // Remove the "copy all" option
        menu.removeItem(android.R.id.copy);

        return true;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // Called when action mode is first created. The menu supplied
        // will be used to generate action buttons for the action mode

        // Here is an example MenuItem
        menu.add(0, 1, 0, "Definition").setIcon(R.drawable.ic_border_color_black_24dp);

        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        // Called when an action mode is about to be exited and
        // destroyed
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
       return false;
    }

}
