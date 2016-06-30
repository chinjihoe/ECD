package hr.ecd;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.nfc.NfcAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

/**
 * Na het inloggen kan de gebruiker een NFC chip scannen
 * Het ophalen van informatie van de chip gebeurd hier
 * Op de NFC chip staat een id van een client
 * Na het uitlezen van het id zal de app het dossier openen van dit id(client)
 */

public class NFCActivity extends AppCompatActivity {

    private NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);

        //Actionbar title veranderen naar gebruikersnaam
        try {
            toolbar.setTitle(((Ecd) this.getApplication()).getEmployeeJSON().getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mNfcAdapter = (NfcAdapter) NfcAdapter.getDefaultAdapter(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //get info of NFC chip
        if (intent.hasExtra(mNfcAdapter.EXTRA_TAG)) {
            Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if(parcelables != null && parcelables.length > 0) {
                readTextFromTag((NdefMessage)parcelables[0]);
            }
            else {
                Toast.makeText(this, "No extra messages", Toast.LENGTH_LONG);
            }
        }

        super.onNewIntent(intent);
    }

    //start dossierActivity
    protected void readTextFromTag(NdefMessage message) {
        NdefRecord[] ndefRecords = message.getRecords();
        if(ndefRecords != null && ndefRecords.length > 0 ) {
            NdefRecord ndefRecord = ndefRecords[0];
            String text = getTextFromNdefRecord(ndefRecord);
            try {
                Integer userId = Integer.parseInt(text);
                Intent intent = new Intent(this, DossierActivity.class);
                intent.putExtra("userId", userId.toString());
                ((Ecd)this.getApplication()).setClientId(userId);

                startActivityForResult(intent, 1);
            }
            catch(Exception e) {
                Toast.makeText(this, "This tag does not contain user information", Toast.LENGTH_LONG);
            }
        }
        else {
            Toast.makeText(this, "No extra messages", Toast.LENGTH_LONG);
        }
    }

    public String getTextFromNdefRecord(NdefRecord ndefRecord)
    {
        String tagContent = null;
        try {
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = ((payload[0] & 128) == 0 ? "UTF-8" : "UTF-16");
            int languageSize = payload[0] & 0063;
            tagContent = new String(payload, languageSize + 1,
                    payload.length - languageSize - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("getTextFromNdefRecord", e.getMessage(), e);
        }
        return tagContent;
    }

    @Override
    protected void onResume() {
        Intent intent = new Intent(this, NFCActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilter = new IntentFilter[]{};

        if(mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, null);

        super.onResume();
    }

    @Override
    protected void onPause() {
        if(mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
        super.onPause();
    }

}
