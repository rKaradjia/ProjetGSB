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

import java.util.List;

import fr.gsb.rv.entites.RapportVisite;
import fr.gsb.rv.modeles.ModeleGsb;
import fr.gsb.rv.technique.Session;

public class RechercheRvActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
        TextView id;
        TextView afficher;
        TextView mois;
        TextView annee;

        String idSend;
        String moisSend;
        String anneeSend;
        Button btn;
        ModeleGsb modele;
        Session session;

        private static final String [] lesMois = {"1","2","3","4","5",
        "6","7","8","9","10","11","12"};

        private static final String [] lesAnnees = {"2010","2011","2012","2013","2014","2015",
            "2016","2017","2018"};

        Spinner spMois;
        Spinner spAnnee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_rv);
        id = (TextView)findViewById(R.id.id);
        afficher = (TextView)findViewById(R.id.textView3);
        mois = (TextView)findViewById(R.id.textView);
        annee = (TextView)findViewById(R.id.textView2);

        Bundle paquet = this.getIntent().getExtras();
        String paquetRecu=paquet.getString("nb");
        String idRecu = paquet.getString("id");
        afficher.setText(paquetRecu);
        id.setText(idRecu);

        idSend = idRecu;//Passage dans la nouvelle vue

        spMois = ( Spinner )findViewById(R.id.spinner);
        spMois.setOnItemSelectedListener(this);

        spAnnee = ( Spinner )findViewById(R.id.spinner2);
        spAnnee.setOnItemSelectedListener(this);


        /*MOIS*/

        ArrayAdapter<String> aaMois = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                lesMois
        );

        aaMois.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

       spMois.setAdapter(aaMois);

       /*Annee*/

        ArrayAdapter<String> aaAnnee = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                lesAnnees
        );

        aaAnnee.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spAnnee.setAdapter(aaAnnee);





        btn = (Button)findViewById(R.id.button5);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override

                    public void onClick(View vue){
                        Rechercher(vue);

                    }

                }
        );

    }


    public void Rechercher(View vue){

        Integer anneeInt = Integer.parseInt(anneeSend);
        Integer moisInt = Integer.parseInt(moisSend);


            Bundle paquet=new Bundle();
            paquet.putString("mois",moisSend);//1 er variable
            paquet.putString("annee",anneeSend);//2 nd variable
            paquet.putString("id",idSend);


            Intent intentionEnvoyer = new Intent (this , ListeRvActivity.class);
            intentionEnvoyer.putExtras(paquet);

            startActivity(intentionEnvoyer);
       // }

    }

    public void onNothingSelected(AdapterView<?> parent) {
    }



    public void onItemSelected (AdapterView<?> parent, View vue, int position , long id){

        String annee = spAnnee.getSelectedItem().toString();
        String mois =spMois.getSelectedItem().toString();

        this.moisSend=mois;
        this.anneeSend=annee;

    }
}
