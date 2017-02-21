package es.iesnervion.albertonavarro.a11_preparatoriaexamen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SQLFragmentControles extends Fragment implements View.OnClickListener{

    public View view;

    Button btnInsertar;
    Button btnBorrar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sqlfragment_controles, container, false);
        btnBorrar =  (Button) view.findViewById(R.id.btnBorrar);
        btnBorrar.setOnClickListener(this);
        btnInsertar = (Button) view.findViewById(R.id.btnInsertar);
        btnInsertar.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBorrar:
                ((ActivitySQLite)getActivity()).msqlite.borrarTodo();
                break;
            case R.id.btnInsertar:
                ((ActivitySQLite)getActivity()).msqlite.insertarUsuario("Hey", "Que pasa");
                break;
        }

        ((ActivitySQLite)getActivity()).actualizarLista();

    }


}





