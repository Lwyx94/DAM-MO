package es.iesnervion.albertonavarro.a12_examen.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import es.iesnervion.albertonavarro.a12_examen.MainActivity;
import es.iesnervion.albertonavarro.a12_examen.R;
import es.iesnervion.albertonavarro.a12_examen.models.Contact;

public class EditFragment extends Fragment implements View.OnClickListener {

    public View view;
    public int id;
    private Switch swtEdit;
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
        view = inflater.inflate(R.layout.edit_fragment, container, false);

        swtEdit = (Switch) view.findViewById(R.id.swtEdit);
        swtEdit.setOnClickListener(this);
        etName = (EditText) view.findViewById(R.id.etEditName);
        etAge = (EditText) view.findViewById(R.id.etEditAge);
        etPhone = (EditText) view.findViewById(R.id.etEditPhone);
        rbFemale = (RadioButton) view.findViewById(R.id.rbEditFemale);
        rbMale = (RadioButton) view.findViewById(R.id.rbEditMale);
        btnAccept = (Button) view.findViewById(R.id.btnEditAccept);
        btnAccept.setOnClickListener(this);

        if (savedInstanceState != null) {
            id = savedInstanceState.getInt("id");
            Contact c = ((MainActivity) getActivity()).getContact(id);
            etName.setText(c.getName());
            etAge.setText("" + c.getAge());
            etPhone.setText(c.getPhone());
            if (c.getGender() == "Male")
                rbMale.setChecked(true);
            else
                rbFemale.setChecked(true);
        } else
            Toast.makeText(getContext(), "Se ha producido un error", Toast.LENGTH_SHORT).show();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.swtEdit:
                etName.setEnabled(swtEdit.isChecked());
                etAge.setEnabled(swtEdit.isChecked());
                etPhone.setEnabled(swtEdit.isChecked());
                rbMale.setEnabled(swtEdit.isChecked());
                rbFemale.setEnabled(swtEdit.isChecked());
                btnAccept.setEnabled(swtEdit.isChecked());
                break;
            case R.id.btnEditAccept:
                if (etName.getText().equals("") || etAge.getText().equals("") || etPhone.getText().equals("") || (!rbFemale.isChecked() && !rbMale.isChecked()))
                    Toast.makeText(getContext(), "Por favor, rellena todos los campos", Toast.LENGTH_LONG).show();
                else if (Integer.parseInt(etAge.getText().toString()) < 0)
                    Toast.makeText(getContext(), "La edad no puede ser negativa", Toast.LENGTH_LONG).show();
                else {
                    String gender = rbMale.isChecked() ? "Male" : "Female";
                    ((MainActivity) getActivity()).editContact(new Contact(id, etName.getText().toString(), etPhone.getText().toString(), Integer.parseInt(etAge.getText().toString()), gender));
                    Toast.makeText(getContext(), "Contacto guardado", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
