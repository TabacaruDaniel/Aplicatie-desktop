package ProiectPOO;

class Rezultat{
    protected boolean defectiune;
    protected double produs;
    protected double curent_consumat;
    protected float functionare_ramasa;
    public Rezultat(boolean defectiune,double produs,double curent_consumat,float functionare_ramasa){
        this.defectiune = defectiune;
        this.produs=produs;
        this.curent_consumat=curent_consumat;
        this.functionare_ramasa=functionare_ramasa;
    }
}
