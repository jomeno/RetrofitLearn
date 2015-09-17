package com.example.android.retrofitlearn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.retrofitlearn.api.MedaceService;

import java.util.ArrayList;

import data.CourseAdapter;
import data.Operation;
import data.PackModel;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class MainActivity extends AppCompatActivity {

    String API_BASE_URL;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        API_BASE_URL = getResources().getString(R.string.api_base_url);

        final ArrayList<PackModel> packs = new ArrayList<>();

        //final ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
                //android.R.layout.simple_list_item_1, android.R.id.text1, packs);

        final CourseAdapter mAdapter2 = new CourseAdapter(this, 0, packs);

        ListView list2 = (ListView) findViewById(R.id.list2);

        //list.setAdapter(mAdapter);
        list2.setAdapter(mAdapter2);
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(view.getContext(), "Test", Toast.LENGTH_SHORT).show();
            }
        });

        retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        MedaceService api = retrofit.create(MedaceService.class);
        Call<Operation<ArrayList<PackModel>>> getPacks = api.getPacks("true");

        getPacks.enqueue(new Callback<Operation<ArrayList<PackModel>>>() {
            @Override
            public void onResponse(Response<Operation<ArrayList<PackModel>>> response) {
                Operation<ArrayList<PackModel>> operation = response.body();
                for (PackModel pack : operation.Result) {
                    Log.d("PACK", pack.Name);
                    packs.add(pack);
                }

                mAdapter2.groupItems(packs);

            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("TEST", t.getMessage());

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }
}
