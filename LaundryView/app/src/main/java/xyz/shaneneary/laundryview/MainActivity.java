package xyz.shaneneary.laundryview;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	Button btnGetData;
	final String URL = "https://www.laundryalert.com/cgi-bin/urba7723/LMPage?Login=True";

	ArrayList<String> dorms;
	ArrayAdapter<String> dormsAdapter;
	ListView dormsList;
	Elements tableNewData;


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

		dormsList = (ListView) findViewById(R.id.myListView);
		dorms = new ArrayList<String>();
		dormsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, dorms);

		dormsList.setAdapter(dormsAdapter);

		setupListViewListener();
	}

	private void setupListViewListener() {
		dormsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dorms.remove(position);
				dormsAdapter.notifyDataSetChanged();
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
				Element table = doc.getElementById("tableb");
				tableNewData = table.getElementsByTag("tr");
				tableNewData.remove(0);
				tableNewData.remove(0);
				tableNewData.remove(30);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if( ! tableNewData.isEmpty() ) {
				for (Element row : tableNewData) {
					dorms.add(row.text());
				}
			}
			dormsAdapter.notifyDataSetChanged();
			mProgressDialog.dismiss();
		}
	}
}
