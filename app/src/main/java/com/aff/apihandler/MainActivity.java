package com.aff.apihandler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private TextView tvShow;
    private Button btnRefresh;

    private String apiUrl = "https://api.kawalcorona.com/indonesia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initListener() {
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getDataHome();
            }
        });
    }

    private void initView(){
        tvShow = findViewByID(R.id.tv_show);
        btnRefresh = findViewByID(R.id.tv_show);
    }

    public class getHomeAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog (context: MainActivity.this);
            progressDialog.setMessage("Please wait....");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpUrlConnection urlConnection=null;
                try {
                    url = new URL(apiUrl);
                    urlConnection = (HttpUrlConnection) url.openConnection();

                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isw = new InputStreamReader(in);

                    int data = isw.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isw.read();
                    }
                    return current;

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (urlConnection != null) {
                    urlConnection.disconnect()
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
        }
    }
}