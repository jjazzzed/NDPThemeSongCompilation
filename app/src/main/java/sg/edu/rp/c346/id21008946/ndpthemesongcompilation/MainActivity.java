package sg.edu.rp.c346.id21008946.ndpthemesongcompilation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etSongTitle, etSingersName, etYearOfRelease;
    RadioGroup rgRating;
    RadioButton rb,rb1,rb2,rb3,rb4,rb5;
    Button btnInsert, btnShowList;
    ArrayList<Song> als;
    ListView lv;
    ArrayAdapter<Song> aas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSongTitle = findViewById(R.id.etSongTitle);
        etSingersName = findViewById(R.id.etSingersName);
        etYearOfRelease = findViewById(R.id.etYearOfRelease);
        rgRating = findViewById(R.id.rgRatingStars);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);
        btnInsert = findViewById(R.id.bnInsert);
        btnShowList = findViewById(R.id.bnShowList);
        lv = findViewById(R.id.lvResults);

        als = new ArrayList<Song>();
        aas = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1, als);
        lv.setAdapter(aas);


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String songtitle = etSongTitle.getText().toString();
                String singersname = etSingersName.getText().toString();
                String yearText = etYearOfRelease.getText().toString();
                int year = Integer.parseInt(yearText);
                int radioId = rgRating.getCheckedRadioButtonId();
                rb = findViewById(radioId);
                int rating = (Integer.parseInt(rb.getText().toString()));
                DBhelper dbh = new DBhelper(MainActivity.this);
                long inserted_id = dbh.insertSong(songtitle, singersname, year, rating);


                if (inserted_id != -1) {
//                    als.clear();
//                    als.addAll(dbh.getAllSongs());
//                    aas.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Insert not successful",
                            Toast.LENGTH_SHORT).show();
                }

                etSongTitle.setText("");
                etSingersName.setText("");
                etYearOfRelease.setText("");
                rb1.setChecked(true);
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SongListActivity.class);
                startActivity(i);
            }
        });

        etSongTitle.addTextChangedListener(songInsertTextWatcher);
        etSingersName.addTextChangedListener(songInsertTextWatcher);
        etYearOfRelease.addTextChangedListener(songInsertTextWatcher);
    }

    private TextWatcher songInsertTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String title = etSongTitle.getText().toString().trim();
            String singers = etSingersName.getText().toString().trim();
            String year = etYearOfRelease.getText().toString().trim();

            if(!title.isEmpty() && !singers.isEmpty() && !year.isEmpty() && year.length()==4)
            {
                btnInsert.setEnabled(true);
            }
            else
            {
                btnInsert.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}