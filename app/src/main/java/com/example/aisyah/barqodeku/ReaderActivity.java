package com.example.aisyah.barqodeku;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ReaderActivity extends AppCompatActivity {
    final Activity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
    }

    public void onButtonClicked(View view){
        IntentIntegrator integrator = new IntentIntegrator(activity);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Silahkan Scan Barcode yang Terdapat pada Buku Anda.");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "Anda Membatalkan Scan",Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(this, BookDetails.class);
                intent.putExtra("BOOK_ID", result.getContents());
                startActivity(intent);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

