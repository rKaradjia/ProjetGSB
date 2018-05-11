package rv.gsb.fr.myapplication;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MenuRvActivity extends Activity {
    TextView idTextView;
    TextView identification;
    Button btnconsulter;
    Button btnsaisir;

    String idEnvoye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_rv);

        identification = (TextView)findViewById(R.id.textView4);
        idTextView = (TextView)findViewById(R.id.id);
        btnconsulter = (Button)findViewById(R.id.button3);
        btnsaisir = (Button)findViewById(R.id.button4);

        Bundle paquet = this.getIntent().getExtras();

        String paquetRecu=paquet.getString("nb");
        String id = paquet.getString("id");

        idEnvoye = id;//enregistrement pour passage vers la nouvelle vue

        identification.setText(paquetRecu);
        idTextView.setText(id);

        btnconsulter=(Button)findViewById(R.id.button3);


        btnconsulter.setOnClickListener(
                new View.OnClickListener() {
                    @Override

                    public void onClick(View vue){
                        Consulter(vue);

                    }

                }
        );

        btnsaisir.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View vue) {
                        Saisir(vue);
                    }
                }
        );

    }
    public void Consulter(View vue){


        //   Session session = new Session();




            String consulterAfficher = "Veuillez renseigner les champs";

            Bundle paquet=new Bundle();
            paquet.putString("nb",consulterAfficher);
            paquet.putString("id",idEnvoye);

            Intent intentionEnvoyer = new Intent (this , RechercheRvActivity.class);
            intentionEnvoyer.putExtras(paquet);

            startActivity(intentionEnvoyer);




        }
    public void Saisir(View vue){


        //   Session session = new Session();




        String consulterAfficher = "Veuillez renseigner les champs";

        Bundle paquet=new Bundle();
        paquet.putString("nb",consulterAfficher);

        Intent intentionEnvoyer = new Intent (this , Saisie_rv_activity.class);
        intentionEnvoyer.putExtras(paquet);

        startActivity(intentionEnvoyer);




    }



}
