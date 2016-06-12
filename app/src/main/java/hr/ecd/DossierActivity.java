package hr.ecd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.BlockingQueue;

/**
 * Het clienten dossier
 */
public class DossierActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ProgressDialog progressDialog;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private Button nieuwJournaalButton;
    private String userId;

    // Settings
    private Integer maxDossiersPerView = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dossier);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setTitle(((Ecd) this.getApplication()).getEmployeeJSON().getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        mDrawerList = (ListView)findViewById(R.id.navList);

        addDrawerItems();
        setupDrawer();
        drawerListener();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Doe normaal man");
        progressDialog.show();

        nieuwJournaalButton = (Button) findViewById(R.id.nieuwJournaal);
        nieuwJournaalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DossierActivity.this,SpeechActivity.class));
            }
        });

        Intent intent = getIntent();

        userId = intent.getStringExtra("userId");

        updateRecentJournal();
        getClientData();

    }

    private void getClientData() {
        Api api = new Api();
        try {
            api.request(this, "/client/" + userId, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    fillClientData(response);
                }
            });
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

    }

    private void fillClientData(JSONObject response) {

        TextView voornaamText = (TextView)findViewById(R.id.voornaamText),
                 achternaamText = (TextView)findViewById(R.id.achternaamText),
                 geboorteDatumText = (TextView)findViewById(R.id.geboortedatumText),
                 kamerText = (TextView)findViewById(R.id.kamernrText),
                 attentieText = (TextView)findViewById(R.id.attentieText),
                 telefoonNummerText = (TextView)findViewById(R.id.telnrText),
                 emailText = (TextView)findViewById(R.id.emailText),
                 burgerlijkestaatText = (TextView)findViewById(R.id.burgelijkestaatText),
                 gewichtText = (TextView)findViewById(R.id.gewichtText),
                 geslachtText = (TextView)findViewById(R.id.geslachtText),
                 leeftijdText = (TextView)findViewById(R.id.leeftijdText);

        try {

            voornaamText.append(response.getString("name"));
            achternaamText.append(response.getString("surname"));
            geboorteDatumText.append(response.getString("birthdate"));
            kamerText.append(response.getString("room"));
            telefoonNummerText.append(response.getString("phonenumber"));
            emailText.append(response.getString("email"));
            burgerlijkestaatText.append(response.getString("martial"));
            gewichtText.append(response.getString("weight") + "kg");
            geslachtText.append((response.getString("sex").equals("1")) ? "Man" : "Vrouw");
            String[] age = response.getString("birthdate").split("-");
            leeftijdText.append("" + getAge(Integer.parseInt(age[0]),Integer.parseInt(age[1]), 1));

            if(!response.isNull("detail"))
                attentieText.setText(response.getString("detail"));

        }
        catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public int getAge (int _year, int _month, int _day) {
        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if(a < 0)
            throw new IllegalArgumentException("Age < 0");
        return a;
    }

    private void drawerListener(){
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawer(mDrawerList);
                switch (position){
                    case 0:
                        startActivity(new Intent(DossierActivity.this,SpeechActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(DossierActivity.this, GuessActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void addDrawerItems() {
        String[] osArray = { "Record activities", "Relevant information" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }

    private void setupDrawer() {
        mActivityTitle = getTitle().toString();
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList.bringToFront();
        mDrawerLayout.requestLayout();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigatie");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void updateRecentJournal() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStart() {

        progressDialog.hide();
        progressDialog.dismiss();
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Hier moet de activity opnieuw alle updatebare data ophalen. Bijvoorbeeld: recenteJournaal data
        //get recenteJournaal from server
        //updateRecentJournal();
    }

}
