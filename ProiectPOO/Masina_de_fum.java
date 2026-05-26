package ProiectPOO;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

class Masina_de_fum extends Aparat implements Serializable {
    private String denumire;
    private float productivitate;
    private float putere;
//    private float capacitate_baterie;
//    private float nivel_baterie;
//    private float sanatate_baterie;
//    private int numar_leduri;
    private float[] stare_leduri;
    private float capacitate_rezervor;
    private float volum_curent;
    private int garantie_de_functionare;
    private float functionare_ramasa;
    private float pret;

    //float putere_totala;
    //float rotatie_perie;
    //loat viteza_deplasare;
    //float[] dimemsiuni;
    public void schimba_starea(Rezultat a) {
        Rezultat_masina_fum b= (Rezultat_masina_fum) a;


        //JOptionPane.showMessageDialog(null,b);
        JOptionPane.showMessageDialog(null,String.format(
                "In urma utilizarii aparatului %s, aparatul a produs %.5f metri  cubi de fum\nIn consecinta s-a consumat %.5f kW \nIar in rezervor au ramas %.5f l de lichid",
                denumire,
                b.produs,
                b.curent_consumat,
                b.volum_ramas));

        curent -= b.curent_consumat;
        volum_curent = b.volum_ramas;
        stare_leduri = b.stare_leduri;
        functionare_ramasa = b.functionare_ramasa;
        if(b.defectiune) cost_mentenanta(b);
    }
    public Masina_de_fum(Masina_de_fum a) {
        this.denumire = a.denumire;
        this.putere = a.putere;
        this.productivitate = a.productivitate;
//        this.capacitate_baterie = a.capacitate_baterie;
//        this.nivel_baterie = a.nivel_baterie;
//        this.sanatate_baterie = a.sanatate_baterie;
        this.stare_leduri = a.stare_leduri;
        this.volum_curent = a.volum_curent;
        this.garantie_de_functionare = a.garantie_de_functionare;
        this.functionare_ramasa = a.functionare_ramasa;
        this.pret=a.pret;
    }

    public Rezultat pornire(float cerinta) {//neterminata

        double moment_oprire = 1;
        double consum = putere * (cerinta / productivitate);
        if (consum < curent) moment_oprire = 1;
        else {
            moment_oprire = rotunjire(curent / consum, 9);
            if (JOptionPane.showConfirmDialog(null, "Oprirea a fost cauzata de energia insuficienta, doriti sa incarcati energia?", "oprire fortata", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                cost_mentenanta(new Rezultat_masina_fum(true, 1, curent,null , 1, 1));
                moment_oprire = 1;
            }
        }
        if (cerinta < functionare_ramasa) moment_oprire = 1;
        else {
            moment_oprire = rotunjire(functionare_ramasa / cerinta, 9);
            //JOptionPane.showMessageDialog(null,"oprire fortata cauzata de nivelul de uzura prea mare asupra pieselor");
            if (JOptionPane.showConfirmDialog(null, "Oprirea a fost cauzata de nivelul de uzura prea mare asupra pieselor, doriti sa duceti masina de fum la reparat?", "oprire fortata ", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                cost_mentenanta(new Rezultat_masina_fum(true, 1, 0, null, 1, 0));
                moment_oprire = 1;
            }
        }
        //if(stare_filtru> (cerinta/productivitate)) moment_oprire=1;
        if (stare_leduri != null) {
            String mesaj="";
            Random rand = new Random();
            float[] defectiuni = new float[stare_leduri.length];
            for (int i = 0; i < stare_leduri.length; i++) {
                defectiuni[i] = (rand.nextFloat(10)/100)*(cerinta/productivitate);
                stare_leduri[i] -= defectiuni[i];}
            for(int i=0;i<stare_leduri.length;i++){

        if(stare_leduri[i] < 0) {
            stare_leduri[i] = 0;
        }}
                if (JOptionPane.showConfirmDialog(null, "Masinii de fum i s-au ars niste leduri\nDoriti sa schimbati ledul?", "Defectiune detectata", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    cost_mentenanta(new Rezultat_masina_fum(false, 1,0 ,stare_leduri , 1, 1));
                }
            }
            if (volum_curent > (cerinta / 4000)) moment_oprire = 1;//la o ora de functionare sacul se umple cam 3 litri
            else {
                moment_oprire = rotunjire((volum_curent * 4000) / cerinta, 9);
                //JOptionPane.showMessageDialog(null,"oprire fortata cauzata unui sac prea plin");
                if (JOptionPane.showConfirmDialog(null, "Oprirea a fost cauzata de insuficienta solutie din rezervor, doriti sa-l umpleti?", "oprire fortata ", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    cost_mentenanta(new Rezultat_masina_fum(true, 1, 0, null, 0, 1));
                    moment_oprire = 1;
                }
            }
        return new Rezultat_masina_fum(moment_oprire != 1, (float) (((int) ((moment_oprire * cerinta) * Math.pow(10, 5))) / Math.pow(10, 5)), (float) (((int) ((moment_oprire * consum) * Math.pow(10, 5))) / Math.pow(10, 5)),stare_leduri , (float) (volum_curent - ((int) (((moment_oprire * (cerinta / 4000))) * Math.pow(10, 5))) / Math.pow(10, 5)), (float) (functionare_ramasa - ((int) (((moment_oprire * cerinta / productivitate)) * Math.pow(10, 5))) / Math.pow(10, 5)));
        }

    void cost_mentenanta(Rezultat c){
        Rezultat_masina_fum a=(Rezultat_masina_fum) c;
        if(curent-a.curent_consumat<=0.001) {
            curent+=Double.parseDouble(JOptionPane.showInputDialog("Introduceti valoarea de curent pe care doriti sa o adaugati"));
            JOptionPane.showMessageDialog(null,"Pretul pentru aprovizionarea facuta este de "+curent+" pret de 1 leu per kW");
        }
        if(a.volum_ramas<=0.001) {
            JOptionPane.showMessageDialog(null,"Costul pentru a achizitiona un un litru de lichid este de 35 lei");
            volum_curent +=capacitate_rezervor;
        }
        if(a.stare_leduri!=null){
            int pret_led=0;
            int n=0;
            String mesaj="";
            for(int i=0;i<stare_leduri.length;i++){
        if(a.stare_leduri[i]<=0.0001) {
            pret_led += 5;
            n++;
            stare_leduri[i] += 1;

        }}
        JOptionPane.showMessageDialog(null,"S-au ars " + n + " leduri, costul pentru a le inlocui este de " + pret_led + " lei");}
        if(a.functionare_ramasa<=0.001) {
            JOptionPane.showMessageDialog(null,"Costul estimativ pentru reparatii este de "+pret/4);
            functionare_ramasa+=garantie_de_functionare*365/8;
        }
    }
    Masina_de_fum() {
        denumire = "";
        productivitate = 0;
        putere = 0;
//        capacitate_baterie = 0;
//        nivel_baterie = 0;
//        sanatate_baterie = 0;
        stare_leduri = new float[0];
        capacitate_rezervor = 0;
        volum_curent = 0;
        garantie_de_functionare = 0;
        functionare_ramasa = 0;
        pret = 0;

    }

    public Masina_de_fum(String denumire, float putere , float[] stare_leduri, float capacitate_rezervor, int garantie_de_functionare,float pret) {
        this.denumire = denumire;
        this.putere=putere;
        this.productivitate =putere*100;
//        this.capacitate_baterie = capacitate_baterie;
//        this.nivel_baterie = capacitate_baterie;
//        this.sanatate_baterie = 600;
        this.stare_leduri = stare_leduri;
        this.capacitate_rezervor = capacitate_rezervor;
        this.volum_curent =capacitate_rezervor;
        this.garantie_de_functionare = garantie_de_functionare;
        this.functionare_ramasa=garantie_de_functionare*365*productivitate;
        this.pret = pret;
    }

    public String detalii() {
        String a = "";
        int numarObiecte=0;
        if(stare_leduri!=null) {
            for (int i = 0; i < stare_leduri.length; i++) {
                a += (stare_leduri[i]*100) + "% ";
            }
            numarObiecte=stare_leduri.length;
        }
        else a="nu are leduri";
        return "Masina de fum se numeste "+denumire+" are puterea de " + putere + "kW \nAre "+ numarObiecte+ "leduri  iar starea ledurilor in procente este " + a  + " "
                + "\nMasina are un rezervor de "+capacitate_rezervor+"l, iar volumul de solutie ramas este de " + volum_curent + "l ";
    }

    public String toString() {
        return denumire;
    }

    public float getProductivitate() {
        return productivitate;
    }
        public static float rotunjire(double numar,int zecimala){
            int nr=(int)(numar*Math.pow(10,zecimala));
            float rez=(float)(nr/Math.pow(10,zecimala));
            return rez;
        }

    public float getPutere() {
        return putere;
    }

    public float[] getStare_leduri() {
        return stare_leduri;
    }

//    public float getNivel_baterie() {
//        return nivel_baterie;
//    }
//
//    public float getSanatate_baterie() {
//        return sanatate_baterie;
//    }


    public float getVolum_curent() {
        return volum_curent;
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

    public void setPutere(float putere) {
        this.putere = putere;
    }

//    public void setNivel_baterie(int nivel_baterie) {
//        this.nivel_baterie = nivel_baterie;
//    }
//
//    public void setSanatate_baterie(float sanatate_baterie) {
//        this.sanatate_baterie = sanatate_baterie;
//    }

//    public void setNumar_leduri(int numar_leduri) {
//        this.numar_leduri = numar_leduri;
//    }

    public void setStare_leduri(float[] stare_leduri) {
        this.stare_leduri = new float[stare_leduri.length];
        int cont = 0;
        for (float i : stare_leduri) {
            stare_leduri[cont++] = i;
        }
    }

    public void setVolum_curent(float volum_curent) {
        this.volum_curent = volum_curent;
    }

    public void setGarantie_de_functionare(int garantie_de_functionare) {
        this.garantie_de_functionare = garantie_de_functionare;

    }
    public static void cautaDupaNume(Masina_de_fum[] masini,String nume) {
        String mesaj="";
        for (int i=0;i<masini.length;i++){
            if(masini[i].denumire.contains(nume)) {
                mesaj = mesaj + masini[i]+"\n";
            }
        }
        if(mesaj.equals("")) JOptionPane.showMessageDialog(null,"nu s-a gasit aspiratorul cautat");
        else JOptionPane.showMessageDialog(null,mesaj);
    }


    public static void cautaDupaPret(Masina_de_fum[] masini,float pretmin,float pretmax) {
        String mesaj="";
        for (int i=0;i<masini.length;i++){
            if(masini[i].pret<pretmax && masini[i].pret>pretmin) {
                mesaj = mesaj +masini[i]+"\n";
            }
        }
        if(mesaj.equals("")) JOptionPane.showMessageDialog(null,"nu s-a gasi un aspirator care sa respecte cerintele");
        else JOptionPane.showMessageDialog(null,mesaj);
    }

    public static void saveList(ArrayList<Masina_de_fum> lista, String fileName) {

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(fileName))) {
            oos.writeObject(lista);
            System.out.println("Lista a fost salvată cu succes în " + fileName);
        } catch (IOException e) {
            System.err.println("Eroare la salvarea datelor în " + fileName + ": " + e.getMessage());
        }
    }
}
