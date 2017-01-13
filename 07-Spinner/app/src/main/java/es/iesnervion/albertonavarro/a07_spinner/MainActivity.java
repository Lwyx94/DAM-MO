package es.iesnervion.albertonavarro.a07_spinner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TextWatcher, View.OnClickListener {

    private TextView selection;
    private ImageView image;
    private AutoCompleteTextView txtAuto;
    private static ArrayList<Band> bands;
    private static String[] bandsNames;
    private Spinner spin;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        selection = (TextView) findViewById(R.id.text);
        image = (ImageView) findViewById(R.id.image);
        image.setOnClickListener(this);
        bands = createBands();bandsNames = createBandsNames();
        Collections.sort(bands);



        //AutoCompleteText
        txtAuto = (AutoCompleteTextView) findViewById(R.id.txtAuto);
        txtAuto.addTextChangedListener(this);
        txtAuto.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, bandsNames));


        spin = (Spinner) findViewById(R.id.spinr);
        spin.setOnItemSelectedListener(this);



        MyArrayAdapter aa = new MyArrayAdapter(this, R.layout.row_layout, bands);
        spin.setAdapter(aa);

    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        selection.setText("You have selected "+bands.get(position).getName()+".");
        image.setImageResource(bands.get(position).getLogo());
        txtAuto.setText("");
    }

    public void onNothingSelected(AdapterView<?> parent) {
        selection.setText("");
    }


    private static ArrayList<Band> createBands(){
       ArrayList<Band> bands = new ArrayList<>();

        bands.add(new Band("Muse", R.drawable.muse));
        bands.add(new Band("Artic Monkeys", R.drawable.artic_monkeys));
        bands.add(new Band("Queen", R.drawable.queen));
        bands.add(new Band("Weezer", R.drawable.weezer));
        bands.add(new Band("Pink Floyd", R.drawable.pink_floyd));
        bands.add(new Band("Nirvana", R.drawable.nirvana));
        bands.add(new Band("System of a Down", R.drawable.soad));
        bands.add(new Band("AC/DC", R.drawable.ac_dc));
        bands.add(new Band("The Beatles", R.drawable.the_beatles));
        bands.add(new Band("The Doors", R.drawable.the_doors));

        return bands;
    }

    private static String[] createBandsNames(){
        String[] res = {"Muse","Artic Monkeys", "Queen", "Weezer", "Pink Floyd", "Nirvana",
                "System of a Down", "AC/DC", "The Beatles","The Doors"};
        Arrays.sort(res);
        return res;
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int pos = -1;

        for (int i = 0; i<bands.size() && pos==-1; i++){
            if(bands.get(i).getName().equals(s.toString()))
            {
                pos = i;
                spin.setSelection(i);
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image:
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 5);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }
    }

}
