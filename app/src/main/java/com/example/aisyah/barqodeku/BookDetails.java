package com.example.aisyah.barqodeku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class BookDetails extends AppCompatActivity {

    private static final String TAG = BookDetails.class.getSimpleName();
    private ProgressDialog pDialog;
    TextView title, summary, location;
    private static String url = "http://192.168.43.75/hawhaw/show.php?id=";
    private static String bookId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        title = findViewById(R.id.isi_judul);
        summary = findViewById(R.id.isi_ringkasan);
        location = findViewById(R.id.isi_posisi);

        Intent i = getIntent();
        bookId = i.getStringExtra("BOOK_ID");

        new GetDetails().execute();

    }

    private class GetDetails extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(BookDetails.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            final String jsonStr = sh.makeServiceCall(url + bookId);

            Log.e(TAG, "Response from url: " + jsonStr);

            if(jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String strTitle = jsonObj.getString("judul");
                    String strSummary = jsonObj.getString("ringkasan");
                    String strLocation = jsonObj.getString("posisi_barang");

                    title.setText(strTitle);
                    summary.setText(strSummary);
                    location.setText(strLocation);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(pDialog.isShowing()) pDialog.dismiss();
        }
    }

}