package rv.gsb.fr.myapplication;

import android.app.Activity;
import android.app.ListActivity;

import java.util.ArrayList;
import java.util.List;
import android.view.View.OnClickListener;
import android.view.View.OnClickListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;
import org.w3c.dom.Text;

import java.util.GregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import fr.gsb.rv.entites.RapportVisite;
import fr.gsb.rv.entites.Visiteur;
import fr.gsb.rv.modeles.ModeleGsb;
import fr.gsb.rv.technique.Session;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;



public class ListeRvActivity extends /*ListActivity*/AppCompatActivity implements AdapterView.OnItemClickListener {
    //Liste recuperant tout les rapports de visite recupere par la requete

    //signification differentes
    List<String> lesRapports = new ArrayList<String>();//afficher ce que tu veut dans item
    List<String> lesRapportsDescJSON = new ArrayList<String>();//afficher plus de val  -->Vue suivante

    //private String[] rapports= new String[];
    Button btn;
    TextView id;
    TextView Annee;
    TextView mois;

    TextView tv;
    ListView lvRapports;
    //Session session;
    //ModeleGsb modele;

    String info;
    String idSend;
    String moisSend;
    String anneeSend;
    Integer moisInt;
    Integer anneeInt;

    Integer idRapport;//POur la reucperation des echantillons dans la table offir inner join medicament
    // Integer id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_rv);

        id = (TextView) findViewById(R.id.id);

        lvRapports = (ListView) findViewById(R.id.lv);
        tv = (TextView) findViewById(R.id.test);

        Bundle paquet = this.getIntent().getExtras();
        moisSend = paquet.getString("mois");
        anneeSend = paquet.getString("annee");
        idSend = paquet.getString("id");

        System.out.println("afficher le mois et l'année saisit par l'utilisateur " +moisSend + anneeSend);

        moisInt = Integer.parseInt(moisSend);
        anneeInt = Integer.parseInt(anneeSend);




       /* ItemCommandeAdaptateur adaptateur = new ItemCommandeAdaptateur();
        lvRapports.setAdapter(adaptateur);
        lvRapports.setOnItemClickListener(this);//ecouteur de la liste*/





        System.out.println("nous sommes avant les ecouteurs");
        String url = String.format( "http://10.0.2.2:5000/rv/%s/%d/%d" , idSend ,moisInt , anneeInt );
        System.out.println(url);



        Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                try {
                    System.out.println("ca passe dans le OnResponse");
                    for (int i =0 ; i < response.length(); i++){
                        Log.i("GSB", response.getJSONObject(i).getString("VISITEUR"));
                        String matVisiteur = response.getJSONObject(i).getString("VISITEUR");
                        String nomVisiteur = response.getJSONObject(i).getString("VIS_NOM");
                        String moisRapport = response.getJSONObject(i).getString("MONTH(RAP_DATE)");
                        String anneeRapport = response.getJSONObject(i).getString("YEAR(RAP_DATE)");
                        String jourRapport = response.getJSONObject(i).getString("DAY(RAP_DATE)");
                        idRapport = response.getJSONObject(i).getInt("RAP_NUM");
                        String bilanRapport = response.getJSONObject(i).getString("RAP_BILAN");
                        String confRapport = response.getJSONObject(i).getString("RAP_CONF");

                        String leRapportFinalJSON = matVisiteur + " " + jourRapport + "/" +moisRapport + "/" + anneeRapport;
                        String leRapportDescJSON = "Visiteur : " + matVisiteur + " " + nomVisiteur + "\n" + "Date : " +jourRapport + "/" +moisRapport + "/" + anneeRapport + "\n" + "Bilan : " + bilanRapport + "\n" + "Coefficient de confiance : " + confRapport;
                        System.out.println("Dans le OnResponse reponse valeurs à récuperer leRapportFinalJSON : " + matVisiteur + " " + nomVisiteur + " " +jourRapport + " " +  moisRapport + " " + anneeRapport );
                        System.out.println("Dans le OnResponse reponse valeurs à récuperer leRapportDescJSON : " + matVisiteur + " " + nomVisiteur + " " +jourRapport + " " +  moisRapport + " " + anneeRapport + " " + bilanRapport + " " + confRapport );


                        lesRapports.add(leRapportFinalJSON);
                        lesRapportsDescJSON.add(leRapportDescJSON);
                        System.out.println("Liste des rapport ajouter à lesRapportsJSON : " + lesRapports.get(i));
                        System.out.println("La liste entiere dans lesRapportsJSON : " + lesRapports);
                        System.out.println("Liste des rapport ajouter à lesRapportsDescJSON : " + lesRapportsDescJSON.get(i));
                        System.out.println("La liste entiere dans lesRapportsDescJSON : " + lesRapportsDescJSON);


                        String leRapportJSON = lesRapports.get(i);
                        String rvDescJson = lesRapportsDescJSON.get(i);
                        System.out.println("Dans le OnResponse leRapportJSON : " + leRapportJSON);
                        System.out.println("Dans le OnResponse rvDescJson : " + rvDescJson);

                        //tvRapport.setText(leRapportFinalJSON);
                     /*   ItemRapportAdapter adaptateur = new ItemRapportAdapter();
                        lvRapport.setAdapter(adaptateur);
                     */

                        lvRapports = (ListView) findViewById(R.id.lv);
                        tv = (TextView) findViewById(R.id.test);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListeRvActivity.this , android.R.layout.simple_list_item_1, lesRapports);
                        lvRapports.setAdapter(adapter);
                        lvRapports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                                Bundle paquet = new Bundle();
                                paquet.putInt("idrapport",idRapport);
                                paquet.putInt("mois",moisInt);
                                paquet.putInt("annee",anneeInt);
                                paquet.putString("info", lesRapportsDescJSON.get(i).toString());
                                Intent intentionEnvoyer = new Intent(getApplication(), VisuActivity.class);
                                intentionEnvoyer.putExtras(paquet);
                                startActivity(intentionEnvoyer);
                            }
                        });


                    }

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






    }


    @Override

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        //List<RapportVisite> lesRapportVisites = (List<RapportVisite>) ModeleGsb.getInstance().getRapportsVisites(Session.getSession().getLeVisiteur(),mois,annee).get(position);


    }


}
