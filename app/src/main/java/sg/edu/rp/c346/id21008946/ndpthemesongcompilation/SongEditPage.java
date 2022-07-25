package sg.edu.rp.c346.id21008946.ndpthemesongcompilation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class SongEditPage extends AppCompatActivity {

    EditText etSongIDEDIT,etSongTitleEDIT,etSingersNameEDIT,etYearOfReleaseEDIT;
    RadioGroup rgRatingEDIT;
    RadioButton rb,rb1,rb2,rb3,rb4,rb5;
    Button btnUpdateEDIT,btnDeleteEDIT,btnCancelEDIT;
    Song data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_edit_page);

        etSongIDEDIT = findViewById(R.id.etSongIDEdit);
        etSongTitleEDIT = findViewById(R.id.etSongTitleEdit);
        etSingersNameEDIT = findViewById(R.id.etSingersNameEdit);
        etYearOfReleaseEDIT = findViewById(R.id.etYearOfReleaseEdit);
        rgRatingEDIT = findViewById(R.id.rgRatingStarsEdit);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);
        btnUpdateEDIT = findViewById(R.id.bnUpdate);
        btnDeleteEDIT = findViewById(R.id.bnDelete);
        btnCancelEDIT = findViewById(R.id.bnCancel);



        Intent i = getIntent();
        data =(Song)i.getSerializableExtra("data");

        etSongIDEDIT.setText(data.getId()+"");
        etSongTitleEDIT.setText(data.getTitle());
        etSingersNameEDIT.setText(data.getSingers());
        etYearOfReleaseEDIT.setText(data.getYear()+"");

        int stars = data.getStars();
        switch(stars)
        {
            case 1:
                rb1.setChecked(true);
                break;
            case 2:
                rb2.setChecked(true);
                break;
            case 3:
                rb3.setChecked(true);
                break;
            case 4:
                rb4.setChecked(true);
                break;
            case 5:
                rb5.setChecked(true);
                break;
        }

        btnUpdateEDIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBhelper dbh = new DBhelper(SongEditPage.this);
                data.setTitle(etSongTitleEDIT.getText().toString());
                data.setSingers(etSingersNameEDIT.getText().toString());
                String yeartextEDIT = etYearOfReleaseEDIT.getText().toString();
                int year = Integer.parseInt(yeartextEDIT);
                int radioID = rgRatingEDIT.getCheckedRadioButtonId();
                rb = findViewById(radioID);
                data.setYear(year);
                data.setStars(Integer.parseInt(rb.getText().toString()));
                dbh.updateNote(data);
                dbh.close();

                finish();
            }
        });

        btnDeleteEDIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBhelper dbh = new DBhelper(SongEditPage.this);
                int result = dbh.deleteNote(data.getId());
                Log.d("Result",result+"");
                finish();
            }
        });

        btnCancelEDIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        etSongIDEDIT.addTextChangedListener(songEditTextWatcher);
        etSingersNameEDIT.addTextChangedListener(songEditTextWatcher);
        etYearOfReleaseEDIT.addTextChangedListener(songEditTextWatcher);


    }

    private TextWatcher songEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String title = etSongTitleEDIT.getText().toString();
            String singers = etSingersNameEDIT.getText().toString();
            String year = etYearOfReleaseEDIT.getText().toString();

            if(!title.isEmpty() && !singers.isEmpty() && !year.isEmpty() && year.length()==4)
            {
                btnUpdateEDIT.setEnabled(true);
                btnDeleteEDIT.setEnabled(true);
            }
            else
            {
                btnUpdateEDIT.setEnabled(false);
                btnDeleteEDIT.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}