package rv.gsb.fr.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
public class Saisie_rv_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView afficher;

    private static final String [] lesMotifs = {"NA","test","test2"};

    private static final String [] lesPraticiens = {"Karadjia","Lamberti","Bourneuf","Camoit","Goislard"};


    Spinner praticiensp;
    Spinner motifsp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie_rv_activity);

        afficher = (TextView)findViewById(R.id.afficher);

        praticiensp = ( Spinner )findViewById(R.id.spinner3);
        praticiensp.setOnItemSelectedListener(this);

        motifsp = ( Spinner )findViewById(R.id.spinner4);
        motifsp.setOnItemSelectedListener(this);

        Bundle paquet = this.getIntent().getExtras();
        String paquetRecu=paquet.getString("nb");
        String idRecu = paquet.getString("id");
        afficher.setText(paquetRecu);
         /*MOIS*/

        ArrayAdapter<String> praticien = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                lesMotifs
        );

        praticien.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        praticiensp.setAdapter(praticien);

       /*Annee*/

        ArrayAdapter<String> motif = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                lesPraticiens
        );

        motif.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        motifsp.setAdapter(motif);
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }



    public void onItemSelected (AdapterView<?> parent, View vue, int position , long id){

        String annee = lesMotifs[position];
        String mois = lesPraticiens[position];

      /*  this.moisSend=mois;
        this.anneeSend=annee;*/

    }
}
