package ProiectPOO;
import javax.swing.*;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        ArrayList <Aspirator> aspiratoare= new ArrayList<>();
        aspiratoare.add(new Aspirator());
        aspiratoare.add(new Aspirator("Karcher SE 4 1.081-150.0",1,5,4,499.99));
        aspiratoare.add(new Aspirator("Rowenta Clean It IN5012F0",0.4f,1.5f,2,399.99));
        aspiratoare.add(new Aspirator("Karcher DS 6",0.650f,2,1,200));
        aspiratoare.add(new Aspirator("Electrolux EB31C1UG",0.75f,3,1,150));
        aspiratoare.add(new Aspirator("Paxton NSAC101MA-18-1200A",1.2f,18,3,549.99));
        aspiratoare.add(new Aspirator("Studio Casa Hepatech Aqua",2.4f,2,2,300));
        aspiratoare.add(new Aspirator("Karcher WD 3 S V-15/4/20",1,15,3,459.99));
        aspiratoare.add(new Aspirator("Hikoki RP18DAW4Z",0.08f,6,2,250));
        aspiratoare.add(new Aspirator("Flex VC 6 L MC",1.2f,6,2,300));
        aspiratoare.add(aspiratoare.get(4));
        ArrayList<Masina_de_fum> masiniDeFum=new ArrayList<>();
        masiniDeFum.add(new Masina_de_fum());
        masiniDeFum.add(new Masina_de_fum("lynker",1.2f,new float[]{1,1,1,1,1,1},1,2,500));
        masiniDeFum.add(new Masina_de_fum("LED-600",0.6f,new float[]{1,1,1,1,1,1,1,1},1,1,499.99f));//link-ul daca nu ma credeti https://www.emag.ro/masina-de-ceata-de-mare-putere-de-600w-cu-glob-disco-si-lumini-led-de-petrecere-in-13-culori-telecomanda-wireless-2500cfm-perfecta-pentru-scene-de-dj-nunti-si-halloween-8714022122160/pd/DCB90S3BM/?ref=sponsored_products_p_r_ov_pt_5_2&provider=rec-ads&recid=recads_3_5f4905fad467cff7b6b6cec5134196d5323145a2d449d54ff169aeaa6c485201_1763622157&scenario_ID=3&aid=36fd0cf3-bba3-11f0-a709-06adb8bd5265&oid=278064597
        masiniDeFum.add(new Masina_de_fum("Party 400W",0.4f,null,1,1,699.99f));
        masiniDeFum.add(new Masina_de_fum("Masina de fum",1.5f,new float[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},1,1,349.99f));
        masiniDeFum.add(new Masina_de_fum("Masina de fum cu lumini",0.5f,new float[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},2,2,899.99f));
        masiniDeFum.add(new Masina_de_fum("Masina fum greu cu gheata carbonica",2,null,4,2,499.99f));
        masiniDeFum.add(new Masina_de_fum("Masina de fum si baloane",2,new float[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},1.35f,2,549.99f));
        masiniDeFum.add(new Masina_de_fum("Masina de fum",1,new float[]{1},2,2,449.99f));
        masiniDeFum.add(masiniDeFum.get(3));
        for(int i=0;i<aspiratoare.size();i++) {
            System.out.println(aspiratoare.get(i));
        }
        for(int i=0;i<masiniDeFum.size();i++) {
            System.out.println(masiniDeFum.get(i));
        }
        Aspirator.saveList(aspiratoare,"fisierout.txt");
        Masina_de_fum.saveList(masiniDeFum,"fisierout1.txt");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Interfata(aspiratoare,masiniDeFum); // Instanțiază și construiește fereastra GUI
            }
        });

    }}
