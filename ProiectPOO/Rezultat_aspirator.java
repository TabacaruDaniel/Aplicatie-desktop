package ProiectPOO;

import javax.swing.JOptionPane;

class Rezultat_aspirator extends Rezultat{
    protected float stare_filtru;
    protected float volum_liber;
    public Rezultat_aspirator(boolean defectiune,double produs,double curent_consumat,float stare_filtru, float volum_liber,float functionare_ramasa){
        super(defectiune,produs,curent_consumat,functionare_ramasa);
        this.stare_filtru=stare_filtru;
        this.volum_liber=volum_liber;
    }
public String toString(){
    return ""+produs+" "+curent_consumat+" "+stare_filtru+" "+volum_liber+" "+functionare_ramasa;
}
}
