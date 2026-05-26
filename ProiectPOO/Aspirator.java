package ProiectPOO;
import javax.swing.JOptionPane;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

class Aspirator extends Aparat implements Serializable{
    private String denumire;
    private float productivitate;
    private float alimentare;
    private int calitate_filtru;
    private float stare_filtru;
    private float capacitate_sac;
    private float volum_curent;
    private int garantie_de_functionare;
    private float functionare_ramasa;
    private double pret;
    //float putere_totala;
    //float rotatie_perie;
    //loat viteza_deplasare;
    //float[] dimemsiuni;
    public void schimba_starea(Rezultat a) {
        Rezultat_aspirator b= (Rezultat_aspirator) a;


        //JOptionPane.showMessageDialog(null,b);
        JOptionPane.showMessageDialog(null,String.format(
                "In urma utilizarii aparatului %s, aparatul a curatat %.5f metri patrati de suprafata\nIn consecinta s-a consumat %.5f kW \nIn sac au ramas liberi %.5f l",
                denumire,
                b.produs,
                b.curent_consumat,
                b.volum_liber));

//        "In urma utilizarii aparatului " + denumire + " aparatul a curatat " + a.produs + " metri patrati de suprafata\n" +
//                " in consecinta s-a consumat " + a.curent_consumat + "kW \nIn sac au ramas liberi " + b.volum_liber + "l"
        curent -= b.curent_consumat;
        volum_curent = b.volum_liber;
        stare_filtru = b.stare_filtru;
        functionare_ramasa = b.functionare_ramasa;
        //JOptionPane.showMessageDialog(b.defectiune);
        if(b.defectiune) cost_mentenanta(b);
    }

    public void cost_mentenanta(Rezultat c) {
        Rezultat_aspirator a=(Rezultat_aspirator)c;
        if(curent-a.curent_consumat<=0.001) {
            curent+=Double.parseDouble(JOptionPane.showInputDialog("Introduceti valoarea de curent pe care doriti sa o adaugati"));
            JOptionPane.showMessageDialog(null,"Pretul pentru aprovizionarea facuta este de "+curent+" pret de 1 leu per kW");
        }
        if(a.volum_liber<=0.001) {
            JOptionPane.showMessageDialog(null,"Costul pentru a achizitiona un sac nou este de 10 lei");
            volum_curent +=capacitate_sac;
        }
        if(a.stare_filtru<=0.0001) {
            JOptionPane.showMessageDialog(null,"Costul pentru a achizitiona un filtru nou este de 65 lei");
        stare_filtru+=calitate_filtru;
        }
        if(a.functionare_ramasa<=0.001) {
            JOptionPane.showMessageDialog(null,"Costul estimativ pentru reparatii este de "+pret/4);
            functionare_ramasa+=garantie_de_functionare*365/8;
        }
    }

    /*
    public double calcul_curent_consumat(float cerinta){
        double consum=alimentare*(cerinta/productivitate);
        return consum;
    }
     */
    public Aspirator(Aspirator a) {
        this.pret=a.pret;
        this.functionare_ramasa=a.functionare_ramasa;
        this.calitate_filtru = a.calitate_filtru;
        this.capacitate_sac = a.capacitate_sac;
        this.denumire = a.denumire;
        this.productivitate = a.productivitate;
        this.alimentare = a.alimentare;
        this.stare_filtru = a.stare_filtru;
        this.volum_curent = a.volum_curent;
        this.garantie_de_functionare = a.garantie_de_functionare;
    }

    public Rezultat pornire(float cerinta) {
        double moment_oprire=1;
        double consum = alimentare * (cerinta / productivitate);
        if (consum < curent) moment_oprire=1;
        else {
            moment_oprire=rotunjire(curent/consum,9);
            if(JOptionPane.showConfirmDialog(null,"Oprirea a fost cauzata de energia insuficienta, doriti sa incarcati energia?","oprire fortata",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                cost_mentenanta(new Rezultat_aspirator(true,1,curent,1,1,1));
                moment_oprire=1;
            }
        }
        if(cerinta < functionare_ramasa) moment_oprire=1;
        else {
            moment_oprire=rotunjire(functionare_ramasa/cerinta,9);
            //JOptionPane.showMessageDialog(null,"oprire fortata cauzata de nivelul de uzura prea mare asupra pieselor");
            if(JOptionPane.showConfirmDialog(null,"Oprirea a fost cauzata de nivelul de uzura prea mare asupra pieselor, doriti sa duceti aspiratorul la reparat?","oprire fortata ",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                cost_mentenanta(new Rezultat_aspirator(true,1,0,1,1,0));
                moment_oprire=1;
            }
        }
        if(stare_filtru> (cerinta/productivitate)) moment_oprire=1;
        else {
            //JOptionPane.showMessageDialog(null,"oprire fortata cauzata de energia insuficienta");
            moment_oprire=rotunjire(stare_filtru*productivitate/cerinta,9);
            if(JOptionPane.showConfirmDialog(null,"Oprirea a fost cauzata de un filtru care a ajuns la limita.\nPentru a pastra garantia de functionare a aspiratorului este necesara schimbarea acestuia\nDoriti sa schimbati filtrul?","oprire fortata ",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                cost_mentenanta(new Rezultat_aspirator(true,1,0,0,1,1));
                moment_oprire=1;
            }
        }
        if(volum_curent>(cerinta/30)) moment_oprire=1;//la o ora de functionare sacul se umple cam 3 litri
        else {
            moment_oprire = rotunjire((volum_curent * 30) / cerinta,9);
            //JOptionPane.showMessageDialog(null,"oprire fortata cauzata unui sac prea plin");
            if(JOptionPane.showConfirmDialog(null,"Oprirea a fost cauzata de sacul care a ajuns la limita, doriti sa schimbati sacul?","oprire fortata ",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                cost_mentenanta(new Rezultat_aspirator(true,1,0,1,0,1));
                moment_oprire=1;
            }
        }

        Rezultat_aspirator rez=new Rezultat_aspirator(moment_oprire!=1,(float)(((int)((moment_oprire*cerinta)*Math.pow(10,5)))/Math.pow(10,5)),(float)(((int)((moment_oprire*consum)*Math.pow(10,5)))/Math.pow(10,5)),(float)(stare_filtru-((int)(((moment_oprire*cerinta)/150)*Math.pow(10,5)))/Math.pow(10,5)), (float)(volum_curent-((int)(((moment_oprire*(cerinta/30)))*Math.pow(10,5)))/Math.pow(10,5)),(float)(functionare_ramasa-((int)(((moment_oprire*cerinta/productivitate))*Math.pow(10,5)))/Math.pow(10,5)));
        return rez;

    }

    Aspirator() {//neterminata (constructor implicit)
        denumire = "";
        productivitate = 0;
        alimentare = 0;
        stare_filtru = 0;
        volum_curent = 0;
        garantie_de_functionare = 0;
        functionare_ramasa=0;
        calitate_filtru=0;
        capacitate_sac=0;



    }

    Aspirator(String denumire, float alimentare, float capacitate_sac, int garantie_de_functionare,double pret) {
        this.denumire = denumire;
        this.alimentare = alimentare;
        this.calitate_filtru = garantie_de_functionare*100;
        this.stare_filtru = calitate_filtru;
        this.capacitate_sac = capacitate_sac;
        this.volum_curent = capacitate_sac;
        this.garantie_de_functionare = garantie_de_functionare;
        this.productivitate =alimentare*30;
        this.functionare_ramasa=garantie_de_functionare*365*productivitate;
        this.pret=pret;
    }

    public String detalii() {
//        return String.format(
//                "Aspiratorul se numeste %s, puterea aspiratorului este de %.0f W\nsacul aspiratorului are volum de %.5f l dintre care" +
//                        " disponibili %.5f l \niar pretul aspiratorului este de %.5f lei ",
//                denumire,
//                alimentare,
//                capacitate_sac,
//                volum_curent,
//                pret);
        return "Aspiratorul se numeste "+denumire + "\nputerea aspiratorului este de " + alimentare + "W\nsacul aspiratorului are volum de "
                + capacitate_sac + "l dintre care disponibili " + volum_curent + "l \niar pretul aspiratorului este de "+pret+" lei";
    }

    public String toString() {
        return denumire;
    }

    public float getProductivitate() {
        return productivitate;
    }

    public float getAlimentare() {
        return alimentare;
    }

    public int getCalitate_filtru() {
        return calitate_filtru;
    }

    public float getStare_filtru() {
        return stare_filtru;
    }

    public float getCapacitate_sac() {
        return capacitate_sac;
    }

    public float getVolum_curent() {
        return volum_curent;
    }
    public double getPret() {
        return pret;
    }

    public double getGarantie_de_functionare() {
        return garantie_de_functionare;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public void setProductivitate(float productivitate) {
        this.productivitate = productivitate;
    }

    public void setAlimentare(float alimentare) {
        this.alimentare = alimentare;
    }

    public void setCalitate_filtru(int calitate_filtru) {
        this.calitate_filtru = calitate_filtru;
    }

    public void setStare_filtru(float stare_filtru) {
        this.stare_filtru = stare_filtru;
    }

    public void setCapacitate_sac(float capacitate_sac) {
        this.capacitate_sac = capacitate_sac;
    }

    public void setVolum_curent(float volum_curent) {
        this.volum_curent = volum_curent;
    }

    public void setGarantie_de_functionare(int garantie_de_functionare) {
        this.garantie_de_functionare = garantie_de_functionare;

    }
    public void setPret(double pret) {
        this.pret = pret;

    }
    public static void cautaDupaNume(ArrayList<Aspirator> aspiratoare, String nume) {
        String mesaj="";
        for (int i=0;i<aspiratoare.size();i++){
            if(aspiratoare.get(i).denumire.contains(nume)) {
                mesaj = mesaj + aspiratoare.get(i)+"\n";
            }
        }
        if(mesaj.equals("")) JOptionPane.showMessageDialog(null,"nu s-a gasit aspiratorul cautat");
        else JOptionPane.showMessageDialog(null,mesaj);
    }
    public static float rotunjire(double numar,int zecimala){
        int nr=(int)(numar*Math.pow(10,zecimala));
        float rez=(float)(nr/Math.pow(10,zecimala));
        return rez;
    }

    public static void cautaDupaPret(ArrayList<Aspirator> aspiratoare,float pretmin,float pretmax) {
    String mesaj="";
    for (int i=0;i<aspiratoare.size();i++){
        if(aspiratoare.get(i).pret<pretmax && aspiratoare.get(i).pret>pretmin) {
            mesaj = mesaj + aspiratoare.get(i)+"\n";
        }
    }
    if(mesaj.equals("")) JOptionPane.showMessageDialog(null,"nu s-a gasi un aspirator care sa respecte cerintele");
    else JOptionPane.showMessageDialog(null,mesaj);
}
    public static void saveList(ArrayList<Aspirator> lista, String fileName) {

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(fileName))) {
            oos.writeObject(lista);
            System.out.println("Lista a fost salvată cu succes în " + fileName);
        } catch (IOException e) {
            System.err.println("Eroare la salvarea datelor în " + fileName + ": " + e.getMessage());
        }
    }

}


