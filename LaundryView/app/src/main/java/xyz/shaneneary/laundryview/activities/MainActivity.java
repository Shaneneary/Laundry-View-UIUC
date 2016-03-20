package xyz.shaneneary.laundryview.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xyz.shaneneary.laundryview.R;
import xyz.shaneneary.laundryview.adapters.BaseAdapterDorm;
import xyz.shaneneary.laundryview.objects.DormMachines;

public class MainActivity extends AppCompatActivity {

	Button btnGetData;
	final String URL = "https://www.laundryalert.com/cgi-bin/urba7723/LMPage?Login=True";

//	ArrayList<String> dorms;
	List<DormMachines> dorms;
//	ArrayAdapter<DormMachines> dormsAdapter;
	BaseAdapterDorm dormsAdapter;
	ListView dormsList;

//	Elements tableNewData = new Elements();


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

		dorms = new ArrayList<DormMachines>();

		dormsList = (ListView) findViewById(R.id.myListView);
		dormsAdapter = new BaseAdapterDorm(dorms, this);


//		dormsList = (ListView) findViewById(R.id.myListView);
//		dorms = new ArrayList<String>();
//		dormsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, dorms);

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
				Elements rows = table.getElementsByTag("tr");

				// Removes table headers and whitespace at end
				rows.remove(0);
				rows.remove(0);
				rows.remove(30);
				for(Element row : rows) {
					int index = 0;
					DormMachines dorm = new DormMachines();
					for (Element rowData : row.getElementsByTag("td")) {
						switch (index) {
							case 0:

								break;
							case 1:
								// Dorm Name
								dorm.dormName = rowData.text();

								break;
							case 2:
								// Washers Free
								dorm.washersFree = Integer.parseInt(rowData.text());
								break;
							case 3:
								// Dryers Free
								dorm.dryersFree = Integer.parseInt(rowData.text());
								break;
							case 4:

								break;
							case 5:
								// Washers in Use
								dorm.washersInUse = Integer.parseInt(rowData.text());
								break;
							case 6:

								break;
							case 7:
								// Dryers In Use
								dorm.dryersInUse = Integer.parseInt(rowData.text());
								break;
							case 8:

								break;
							case 9:

								break;
						}
//						switch (index) {
//							case 0:
//
//								break;
//							case 1:
//								// Dorm Name
//								dorm.dormName = rowData.text();
//
//								break;
//							case 2:
//								// Washers Free
//								dorm.washersFree = rowData.text();
//								break;
//							case 3:
//								// Dryers Free
//								dorm.dryersFree = rowData.text();
//								break;
//							case 4:
//
//								break;
//							case 5:
//								// Washers in Use
//								dorm.washersInUse = rowData.text();
//								break;
//							case 6:
//
//								break;
//							case 7:
//								// Dryers In Use
//								dorm.dryersInUse = rowData.text();
//								break;
//							case 8:
//
//								break;
//							case 9:
//
//								break;
//						}
//						switch (index) {
//							case 0:
//								dorm.dormName = rowData.text();
//								break;
//							case 1:
//								dorm.washersFree = Integer.parseInt(rowData.text());
//								break;
//							case 2:
//								dorm.dryersFree = Integer.parseInt(rowData.text());
//								break;
//							case 3:
//								// Whitespace
//								break;
//							case 4:
//								dorm.washersInUse = Integer.parseInt(rowData.text());
//								break;
//							case 6:
//								dorm.dryersInUse = Integer.parseInt(rowData.text());
//								break;
//							case 7:
//								// Whitespace
//								break;
//						}
						index++;
					}
					dorms.add(dorm);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
//			if( ! tableNewData.isEmpty() ) {
//				dorms.clear();
//				for (Element row : tableNewData) {
////					if( row.text().equals("") == false) {
//						dorms.add(row.text());
////					}
//				}
//				tableNewData.clear();
//			}
			dormsAdapter.notifyDataSetChanged();
			mProgressDialog.dismiss();
		}
	}
}
