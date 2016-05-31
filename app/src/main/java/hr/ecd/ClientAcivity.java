package hr.ecd;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ClientAcivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_acivity);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loadings chi's bullsacknoodle");
        progressDialog.show();

    }

    @Override
    protected void onStart() {
        progressDialog.hide();
        progressDialog.dismiss();
        super.onStart();
    }

}
