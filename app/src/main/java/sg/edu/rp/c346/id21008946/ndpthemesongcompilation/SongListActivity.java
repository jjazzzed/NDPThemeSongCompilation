package sg.edu.rp.c346.id21008946.ndpthemesongcompilation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class SongListActivity extends AppCompatActivity {

    Button btnsShow5star;
    ArrayList<Song> als;
    ArrayList<Song> aly;
    ListView lv;
//    ArrayAdapter<Song> aas;
    ArrayAdapter<String> aaY;
    CustomAdapter caSong;
    Boolean pressed;
    Spinner spDynamic;
    View myView;
    Boolean pressedspinner;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        pressed = false;
        lv = findViewById(R.id.lvSongs);
        als = new ArrayList<Song>();
        caSong = new CustomAdapter(this, R.layout.row, als);
        lv.setAdapter(caSong);
        String[] arraySpinner = new String[] {};
        btnsShow5star = findViewById(R.id.btnShow5Star);
        spDynamic = findViewById(R.id.sYear);
        DBhelper dbh = new DBhelper(SongListActivity.this);

        SpinnerStuff();

        als.clear();
        als.addAll(dbh.getAllSongs());
        caSong.notifyDataSetChanged();

        pressedspinner = false;

        spDynamic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    String selectedYear = spDynamic.getSelectedItem().toString();
                    if(selectedYear != "Select Year")
                    {
                        int year = Integer.parseInt(selectedYear);
                        als.clear();
                        als.addAll(dbh.getAllSongsBasedOnYear(year));
                        caSong.notifyDataSetChanged();
                    }//test
                   else
                    {
                        als.clear();
                        als.addAll(dbh.getAllSongs());
                        caSong.notifyDataSetChanged();
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Song data = als.get(position);
                Intent i = new Intent(SongListActivity.this,
                        SongEditPage.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        btnsShow5star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pressed == false)
                {
                    als.clear();
                    als.addAll(dbh.getAllSongsWith5Stars());
                    caSong.notifyDataSetChanged();
                    btnsShow5star.setText("SHOW ALL SONGS");
                    pressed = true;
                }
                else
                {
                    als.clear();
                    als.addAll(dbh.getAllSongs());
                    caSong.notifyDataSetChanged();
                    btnsShow5star.setText("SHOW ALL SONGS WITH 5 STARS");
                    pressed = false;

                }

            }
        });


        

    }

    @Override
    protected void onResume() {
        super.onResume();
        DBhelper dbh = new DBhelper(SongListActivity.this);
        als.clear();
        als.addAll(dbh.getAllSongs());
        caSong.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    void SpinnerStuff(){
        DBhelper dbh = new DBhelper(SongListActivity.this);
        als.addAll(dbh.getAllYears());
        ArrayList<String> yearList = new ArrayList<String>();
//        String[] test = {"1","2"};
        yearList.add("Select Year");
        for(int i = 0; i < als.size();i++)
        {
            String item = als.get(i).toStringYear();
            yearList.add(item);
            Log.d("result",als.get(i).toStringYear());
        }
        aaY = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,yearList );
        aaY.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDynamic.setAdapter(aaY);
    }
}