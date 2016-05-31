package hr.ecd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Chinji on 31-5-2016.
 */
public class DossierActivity extends AppCompatActivity {

    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dossier);

        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loadings chi's bullsacknoodle");
        progressDialog.show();

    }
    private void addDrawerItems() {
        String[] osArray = { "Recent","Record"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        progressDialog.hide();
        progressDialog.dismiss();
        super.onStart();
    }

}
