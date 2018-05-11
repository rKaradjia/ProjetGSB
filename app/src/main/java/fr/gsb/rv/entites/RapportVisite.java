package fr.gsb.rv.entites;
import android.os.Parcel;
import android.os.Parcelable;

import fr.gsb.rv.entites.*;


import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


public class RapportVisite implements Parcelable{

  /*  private String nom;
    private String prenom;
*/

    private int numero ;
    private String bilan ;
    private int coefConfiance ;
    private GregorianCalendar dateVisite ;
    private GregorianCalendar dateRedaction ;
    private boolean lu ;

    private Praticien lePraticien ;
    private Visiteur leVisiteur ;
    private Motif leMotif ;
    private Map<Medicament,Integer> lesEchantillons = new HashMap<Medicament,Integer>() ;

    public RapportVisite() {
        super();
    }



    public RapportVisite(int numero, String bilan, int coefConfiance,
                         GregorianCalendar dateVisite, GregorianCalendar dateRedaction,
                         boolean lu) {
        super();
        this.numero = numero;
        this.bilan = bilan;
        this.coefConfiance = coefConfiance;
        this.dateVisite = dateVisite;
        this.dateRedaction = dateRedaction;
        this.lu = lu;
    }

    public RapportVisite(int numero, String bilan, int coefConfiance,
                         GregorianCalendar dateVisite, GregorianCalendar dateRedaction,
                         boolean lu, Praticien lePraticien, Visiteur leVisiteur,
                         Motif leMotif) {
        super();
        this.numero = numero;
        this.bilan = bilan;
        this.coefConfiance = coefConfiance;
        this.dateVisite = dateVisite;
        this.dateRedaction = dateRedaction;
        this.lu = lu;
        this.lePraticien = lePraticien;
        this.leVisiteur = leVisiteur;
        this.leMotif = leMotif;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBilan() {
        return bilan;
    }

    public void setBilan(String bilan) {
        this.bilan = bilan;
    }

    public int getCoefConfiance() {
        return coefConfiance;
    }

    public void setCoefConfiance(int coefConfiance) {
        this.coefConfiance = coefConfiance;
    }

    public GregorianCalendar getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(GregorianCalendar dateVisite) {
        this.dateVisite = dateVisite;
    }

    public GregorianCalendar getDateRedaction() {
        return dateRedaction;
    }

    public void setDateRedaction(GregorianCalendar dateRedaction) {
        this.dateRedaction = dateRedaction;
    }

    public boolean isLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }

    public Praticien getLePraticien() {
        return lePraticien;
    }

    public void setLePraticien(Praticien lePraticien) {
        this.lePraticien = lePraticien;
    }

    public Visiteur getLeVisiteur() {
        return leVisiteur;
    }

    public void setLeVisiteur(Visiteur leVisiteur) {
        this.leVisiteur = leVisiteur;
    }

    public Motif getLeMotif() {
        return leMotif;
    }

    public void setLeMotif(Motif leMotif) {
        this.leMotif = leMotif;
    }

    public Map<Medicament, Integer> getLesEchantillons() {
        return lesEchantillons;
    }



    public void setLesEchantillons(Map<Medicament, Integer> lesEchantillons) {
        this.lesEchantillons = lesEchantillons;
    }








    public int describeContents(){

        return 0;

    }

    public void writeToParcel(Parcel dest,int flags){


        dest.writeInt(this.numero);
        dest.writeString(this.bilan);
        dest.writeInt(this.coefConfiance);
        dest.writeByte((byte) (lu ? 1 : 0));

       // dest.writeSerializable(this.leMotif,flags);
        //dest.writeTypedObject(this.lePraticien,flags);
        dest.writeSerializable(dateVisite);
        dest.writeSerializable(dateRedaction);
        dest.writeParcelable((Parcelable) leMotif, flags);
        dest.writeParcelable((Parcelable) lePraticien, flags);
        dest.writeParcelable((Parcelable) leVisiteur,flags);
//dest.writeList(this.leMotif);
    }



    public RapportVisite (Parcel in){

        this.numero=in.readInt();
        this.bilan=in.readString();
        this.coefConfiance=in.readInt();
        this.lu = in.readByte() != 0;
        this.dateVisite = (GregorianCalendar) in.readSerializable();
        this.dateRedaction = (GregorianCalendar) in.readSerializable();


        this.leMotif = in.readParcelable(Motif.class.getClassLoader());
        this.lePraticien = in.readParcelable(Praticien.class.getClassLoader());
        this.leVisiteur = in.readParcelable(Praticien.class.getClassLoader());


    }


    public static final Parcelable.Creator<RapportVisite> CREATOR= new Parcelable.Creator<RapportVisite>(){

        public RapportVisite createFromParcel (Parcel source){

            return new RapportVisite(source);

        }

        public RapportVisite[] newArray (int size){
            return new RapportVisite[size];
        }

    };

   /* @Override
    public String toString() {
        return "RapportVisite [numero=" + numero + ", bilan=" + bilan
                + ", coefConfiance=" + coefConfiance + ", dateVisite="
                + dateVisite + ", dateRedaction=" + dateRedaction + ", lu="
                + lu + ", lePraticien=" + lePraticien + ", leVisiteur="
                + leVisiteur + ", leMotif=" + leMotif + ", lesEchantillons="
                + lesEchantillons + "]";
    }*/
}