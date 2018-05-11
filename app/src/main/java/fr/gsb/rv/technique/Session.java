package fr.gsb.rv.technique;
import fr.gsb.rv.entites.Visiteur;
import fr.gsb.rv.modeles.*;
/**
 * Created by rk on 10/12/17.
 */

public class Session {

    private static Session session = null ;
    private Visiteur leVisiteur ;

    private Session(Visiteur leVisiteur){
        super() ;
        this.leVisiteur = leVisiteur ;
    }

    public static boolean ouvrir(String matricule, String mdp){
        Visiteur visiteur = ModeleGsb.getInstance().seConnecter(matricule, mdp) ;
        if( visiteur != null ){
            Session.session = new Session( visiteur ) ;
            return true ;
        }
        else {
            return false ;
        }
    }

    public static Session getSession(){
        return Session.session ;
    }

    public static void fermer(){
        Session.session = null ;
    }

    public Visiteur getLeVisiteur(){
        return this.leVisiteur ;
    }


}
