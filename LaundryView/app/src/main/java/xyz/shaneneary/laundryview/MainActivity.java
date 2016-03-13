package xyz.shaneneary.laundryview;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnGetData;
    final String URL = "https://www.laundryalert.com/cgi-bin/urba7723/LMPage?Login=True";
    String content;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetData = (Button) findViewById(R.id.btnContent);
        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetContent().execute();
            }
        });

    }

    private class GetContent extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Getting Content");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(URL).get();
                String rawData = doc.body().text();
                content = trimData( rawData );

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            TextView contentTextView = (TextView) findViewById(R.id.myTextView);
            contentTextView.setText(content);
            mProgressDialog.dismiss();
        }
    }

    public String trimData(String data) {
        int start = data.indexOf("Allen");
        int end = data.indexOf("On site operation");
        return data.substring(start, end);
    }
}
