package es.iesnervion.albertonavarro.a12_examen.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import es.iesnervion.albertonavarro.a12_examen.MainActivity;
import es.iesnervion.albertonavarro.a12_examen.R;
import es.iesnervion.albertonavarro.a12_examen.models.Contact;

public class AddFragment extends Fragment implements View.OnClickListener{

    public View view;
    private EditText etName;
    private EditText etAge;
    private EditText etPhone;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private Button btnAccept;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_fragment, container, false);


        etName = (EditText) view.findViewById(R.id.etAddName);
        etAge = (EditText) view.findViewById(R.id.etAddAge);
        etPhone = (EditText) view.findViewById(R.id.etAddPhone);
        rbFemale = (RadioButton) view.findViewById(R.id.rbAddFemale);
        rbMale = (RadioButton) view.findViewById(R.id.rbAddMale);
        btnAccept = (Button) view.findViewById(R.id.btnAddAccept);
        btnAccept.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddAccept:
                if(etName.getText().equals("") || etAge.getText().equals("") || etPhone.getText().equals("") || (!rbFemale.isChecked()&&!rbMale.isChecked()))
                    Toast.makeText(getContext(), "Por favor, rellena todos los campos", Toast.LENGTH_LONG).show();
                else if(Integer.parseInt(etAge.getText().toString())<0)
                        Toast.makeText(getContext(), "La edad no puede ser negativa", Toast.LENGTH_LONG).show();
                else{
                    String gender = rbMale.isChecked()?"Male":"Female";
                    ((MainActivity)getActivity()).addContact(new Contact(0,etName.getText().toString(), etPhone.getText().toString(), Integer.parseInt(etAge.getText().toString()), gender));

                    //Cleanup fields
                    etName.setText("");
                    etAge.setText("");
                    etPhone.setText("");
                    rbMale.setChecked(false);
                    rbFemale.setChecked(false);

                    Toast.makeText(getContext(), "Contacto guardado", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
