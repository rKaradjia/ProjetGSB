package rv.gsb.fr.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import fr.gsb.rv.modeles.ModeleGsb;
import fr.gsb.rv.entites.RapportVisite;
import fr.gsb.rv.technique.Session;


public class VisuActivity extends AppCompatActivity {

    String lesechantillons;

    ModeleGsb modeleGsb;

    TextView info;
    Button lebouton;

    Integer index;
    Integer mois;
    Integer annee;

    Integer idRapport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visu);



        Bundle paquet = this.getIntent().getExtras();
        String paquetRecu = paquet.getString("info");
        Integer positionrecu = paquet.getInt("position");
        mois=paquet.getInt("mois");
        annee=paquet.getInt("annee");
        info = (TextView) findViewById(R.id.info);
        info.setText(paquetRecu);
        index = positionrecu;

        idRapport=paquet.getInt("idrapport");//identifiant du rapport de visite

        lebouton = (Button) findViewById(R.id.btn);

        lebouton.setOnClickListener(
                new View.OnClickListener() {
                    @Override

                    public void onClick(View vue) {
                        echantillons(vue);

                    }

                }
        );

    }
        public void echantillons(View vue) {

        //preparation a l'envoie des données pour la consultation des echantillons


            /*String prepaPaquet=ModeleGsb.getInstance().getRapportsVisites(Session.getSession().getLeVisiteur(), mois, annee).get(index).getLesEchantillons().toString();

            Bundle paquet=new Bundle();
            paquet.putString("nb",prepaPaquet);


            Intent intentionEnvoyer = new Intent (this , VisuEchantActivity.class);
            intentionEnvoyer.putExtras(paquet);

            startActivity(intentionEnvoyer);*/

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /*String id = URLEncoder.encode(TfLogin, "UTF-8");
            String mdp = URLEncoder.encode(Pfmdp, "UTF-8");*/
            String url = String.format( "http://10.0.2.2:5000/rv/echantillons/%d" , idRapport );
            System.out.println(url);
//192.168.152.128          192.168.74.129(internet)
            // if(estOuvert==true) {

            //Modele
            /*System.out.println("Resultat du login");
            System.out.println("connection ok");
            String nom=session.getSession().getLeVisiteur().getNom();
            String prenom=session.getSession().getLeVisiteur().getPrenom();
            String matricule = TfLogin;
            System.out.println("Parametres recus"+nom + " "+prenom );*/

           /* String prepaPaquet=nom+ " " +prenom;

            Bundle paquet=new Bundle();
            paquet.putString("nb",prepaPaquet);
            paquet.putString("id",TfLogin);

            Intent intentionEnvoyer = new Intent (this , MenuRvActivity.class);
            intentionEnvoyer.putExtras(paquet);

            startActivity(intentionEnvoyer);*/


            Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {


                public void onResponse(JSONArray response) {
                    try {
                        System.out.println("ca passe dans lecouteur reponse");
                        for (int i =0 ; i < response.length(); i++){


                           // Log.i("GSB", response.getJSONObject(i).getString("VIS_MATRICULE") + " " + response.getJSONObject(i).getString("VIS_MDP"));
                            String echantillons = response.getJSONObject(i).getString("MED_NOMCOMMERCIAL");


                            lesechantillons = lesechantillons + "     "+echantillons;

                           /* Bundle paquet = new Bundle();
                            paquet.putString("nb", nomVisiteur);
                            paquet.putString("id", matVisiteur);
                            Intent intentionEnvoyer = new Intent(VisuActivity.this, VisuEchantActivity.class);
                            intentionEnvoyer.putExtras(paquet);
                            startActivity(intentionEnvoyer);*/

                        }

                        Bundle paquet = new Bundle();
                        paquet.putString("lesechantillons", lesechantillons);
                        Intent intentionEnvoyer = new Intent(VisuActivity.this, VisuEchantActivity.class);
                        intentionEnvoyer.putExtras(paquet);
                        startActivity(intentionEnvoyer);



                    }
                    catch( JSONException e ){
                        Log.e("GSB", "Erreur JSON : " + e.getMessage());
                        System.out.println("Erreur JSON : ");
                    }
                }
            };
            Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("GSB", "Erreur HTTP : " + error.getMessage());
                    System.out.println("ca passe dans ecouteur erreur");
                }
            };



            JsonArrayRequest requete = new JsonArrayRequest(Request.Method.GET, url , null, ecouteurReponse, ecouteurErreur);

            RequestQueue fileReq = Volley.newRequestQueue(this);
            fileReq.add(requete);

          /*  Bundle paquet = new Bundle();
            paquet.putString("lesechantillons", lesechantillons);
            Intent intentionEnvoyer = new Intent(VisuActivity.this, VisuEchantActivity.class);
            intentionEnvoyer.putExtras(paquet);
            startActivity(intentionEnvoyer);*/

        }

}

