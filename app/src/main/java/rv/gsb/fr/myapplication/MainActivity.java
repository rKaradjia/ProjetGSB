package rv.gsb.fr.myapplication;

import fr.gsb.rv.technique.*;

import java.lang.String;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.text.*;
import android.content.*;
import android.view.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
public class MainActivity extends Activity {
    EditText var1;
    EditText var2;
    TextView var3;
    TextView var4;
    Button lebouton;
    Button annuler;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mRootView = (RelativeLayout) findViewById(R.id.activity_main);

        var1=(EditText)findViewById(R.id.editText);
        var2=(EditText)findViewById(R.id.editText2);
        var3=(TextView)findViewById(R.id.textView6);
        var4=(TextView)findViewById(R.id.textView7);
        lebouton=(Button)findViewById(R.id.button);

        lebouton.setOnClickListener(
                new View.OnClickListener() {
                    @Override

                            public void onClick(View vue){
                        try {
                            seConnecter(vue);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                    }

                }
        );


        annuler=(Button)findViewById(R.id.button2);

        annuler.setOnClickListener(
                new View.OnClickListener() {
                    @Override

                    public void onClick(View vue){
                        annuler(vue);

                    }

                }
        );

    }

    public void seConnecter(View vue) throws UnsupportedEncodingException{


     //   Session session = new Session();


        EditText Login = this.var1;
        EditText Mdp = this.var2;

        String TfLogin = Login.getText().toString();
        String Pfmdp = Mdp.getText().toString();
        System.out.println(Pfmdp);
        System.out.println(TfLogin);
        Boolean estOuvert=session.ouvrir(TfLogin,Pfmdp);


        //192.168.74.129
        String id = URLEncoder.encode(TfLogin, "UTF-8");
        String mdp = URLEncoder.encode(Pfmdp, "UTF-8");
        String url = String.format( "http://10.0.2.2:5000/login/%s/%s" , id , mdp );
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

                        Log.i("GSB", response.getJSONObject(i).getString("VIS_MATRICULE") + " " + response.getJSONObject(i).getString("VIS_MDP"));
                        String nomVisiteur = response.getJSONObject(i).getString("VIS_NOM") + " " + response.getJSONObject(i).getString("VIS_PRENOM");
                        String matVisiteur = response.getJSONObject(i).getString("VIS_MATRICULE");
                        System.out.println(matVisiteur);

                        Bundle paquet = new Bundle();
                        paquet.putString("nb", nomVisiteur);
                        paquet.putString("id", matVisiteur);
                        Intent intentionEnvoyer = new Intent(MainActivity.this, MenuRvActivity.class);
                        intentionEnvoyer.putExtras(paquet);
                        startActivity(intentionEnvoyer);

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

       // }else {


            System.out.println("Resultat du login");
            System.out.println("fail");

            var1.setText("");
            var2.setText("");


        JsonArrayRequest requete = new JsonArrayRequest(Request.Method.GET, url , null, ecouteurReponse, ecouteurErreur);

        RequestQueue fileReq = Volley.newRequestQueue(this);
        fileReq.add(requete);


       // }




   }

    public void annuler(View vue){


        this.var1.setText("");
        this.var2.setText("");

    }

}



