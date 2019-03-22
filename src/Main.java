import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    //Loome Listi, milles on Piletid tüüpi isendid, et hoida meeles kõiki bussipileteid ja
    //nende staatuseid kohanumbritega 1-30.
    private static List<Pilet> piletid = new ArrayList<Pilet>();

    public static void main(String[] args){

        //Loome tsükliga 30 piletit kohanumbritega 1-30
        for (int i = 0; i < 30; i++) {
            //Lisame nad piletid Listi
            piletid.add(new Pilet(i+1));//Bussi kohanumbrid algavad 1-st. (0+1=1 ... 29+1=30)
        }


        while(true) {
            //Loome muutuja selle jaoks, mitu piletit tahetakse osta. Initsialiseerime selle esialgu väärtusega 0.
            int vaja = 0;

            try{

                if (müüdudArv() >= 30) {
                    JOptionPane.showConfirmDialog(null,
                            "Kõik piletid on müüdud!",
                            "Piletiost lõppenud!", 2);
                    System.exit(0);

                }

                StringBuilder müüdudPiletid = new StringBuilder(50);

                for (Pilet element:piletid) {
                    if (element.isMyydud()) {
                        if (müüdudPiletid.length() == 0) {//Et esimele müüdud piletile koma ja tühikut ei tuleks
                            müüdudPiletid.append(element.getKoha_nr());
                        }
                        else müüdudPiletid.append(", " + element.getKoha_nr());
                    }
                }


                String teadaanne;
                if (müüdudPiletid.length() == 0) {
                    teadaanne = "Selle väljumisele on hetkel kõik kohad vabad!" +
                            "\nPiletiostu jätkamiseks vajutage 'OK'.";
                }
                else {
                    teadaanne = "Sellele väljumisele on müüdud järgnevad piletid: " + müüdudPiletid.toString() +
                            ".\nVabade piletite ostmisega jätkamiseks vajutage 'OK'.";
                }

                int jätkamisValik = JOptionPane.showConfirmDialog(null,
                        teadaanne,
                        "Teretulemast piletiostu keskkonda!", 2);
                if (jätkamisValik != 0) System.exit(0);

                //Eelnevalt loodud vaja muutuja väärtus muudetakse lähtuvalt JOptionPane InputDialog vastusest.
                //Vastuselahtri vastus parsitakse int tüüpi väärtuseks, et seda vaja muutujasse sisestada.
                //InputDialog küsimuse ja pealkirja tegime identse.
                vaja = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "Mitut piletit soovid osta?", "Mitut piletit soovid osta?",
                        JOptionPane.INFORMATION_MESSAGE));

                //Kontrollitakse üle, kas eelnevasse JOptionPane lahtrisse sisestati 0. Kui sisestati, korratakse
                //eelnevat küsimust.
                if(vaja == 0) {
                    continue;
                }



                //Enne, kui hakatakse ostja soovitud arvu pileteid ostjale reaalselt pakkuda, tuleb kontrollida, kas
                //nii palju pileteid jätkub.
                //Juhul, kui järgijäänud piletite arv (piletite koguarv - müüdud arv) on väiksem, kui ostja soovitud
                //(vaja) piletite arv, siis nii palju pole võimalik müüa. Kuvatakse vastav teade ja väljutakse programmist.
                //See toimib ka juhul, kui müüdud arv on veel 0 ja kohe soovitakse üle 30 pileti osta.
                if(piletid.size() - müüdudArv() < vaja) {
                    JOptionPane.showMessageDialog(null, "Nii palju pileteid ei ole saada!");
                    System.exit(0);
                }

            } catch (Exception e) {
                //Võimaldab programmi töö sulgeda 'Cancel' või 'X' nupu abil.
                System.exit(0);
            }

            //Loome Listi, et hoida ainult neid pileteid, mida pakume ostjatele müügiks.
            //Paneme all oleva meetodi abil sinna just nii palju pileteid, kui soovitakse antud korral osta.
            List<Pilet> myydavad = pakuMyygiks(vaja);
            //Pakutavate piletite on ostja jaoks oluline vaid kohanumber, teeme selle Stringiks
            StringBuilder kohanumbrid = new StringBuilder(50);

            for (Pilet pilet : myydavad) {
                if (kohanumbrid.length() == 0) {//Et esimele müüdud piletile koma ja tühikut ei tuleks
                    kohanumbrid.append(pilet.getKoha_nr());
                }
                else kohanumbrid.append(", " + pilet.getKoha_nr());
            }

            //Lisame ostja vastuse küsimusele int muutujasse, millel saavad olla väärtused 0 või 1.
            int on_ok = JOptionPane.showConfirmDialog(null,
                    "Müügiks on pakkuda piletid kohanumbritega: " + kohanumbrid +
                            ". Kas olete nõus?","Tehingu kinnitamine", JOptionPane.OK_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            //Kui soovitakse osta pileteid, siis
            if(on_ok == 0) { // Miks yes == 0 ?!?

                //käime läbi kõik piletid ja kui müügiks pakutavate piletite Listis (myydavad) on samad piletid
                //muudame nad staatusesse müüdud.
                for (Pilet pilet : piletid) {
                    if(myydavad.contains(pilet)) {
                        pilet.setMyydud(true);
                    }
                }

                JOptionPane.showConfirmDialog(null,
                        "Ostsite piletid kohanumbritega " +
                                kohanumbrid + ".",
                        "Piletiost sooritatud!", JOptionPane.DEFAULT_OPTION);
            }

        }



    }

    //Meetod, mis tagastab Listi, milles on Pilet tüüpi isendid, mida pakutakse antud korral müügiks.
    private static List<Pilet> pakuMyygiks(int arv) {
        Random rand = new Random();
        List<Pilet> result = new ArrayList<Pilet>();
        while(result.size() < arv) {
            //Loome indeksi väärtuse, mis on vahemikus 0 - 30 (piletid suurus) välja arvatud. Ehk siis 0-29, mis meile
            //indeksina hästi sobib.
            int indeks = rand.nextInt(piletid.size());
            //Võtame piletid listist selle juhusliku indeksiga pileti
            Pilet pilet = piletid.get(indeks);
            //Kui pilet on juba mingil varasemal tehingul müüdud, siis me seda uuesti müügiks ei paku või kui
            //sel korral oleme selle pileti juba pakutavate hulka lisanud, siis jällegi topelt me seda ei tee.
            if(!result.contains(pilet) && !pilet.isMyydud()) {
                result.add(pilet); //Lisame juhusliku pileti, mis öeldud reeglite järgi ei kordu, pakutavate piletite Listi.
            }
        }
        //Sorteerime pakutavad piletid kasvavasse järjekorda.
        Collections.sort(result);
        return result;
    }

    public static int müüdudArv() {
        //Loome muutuja selle jaoks, mitu piletit on hetkeseisuga müüdud. Algväärtustame selle 0-ga.
        //Iga kord, kui nö uus inimene pileteid ostab, siis algväärtustatakse see uuesti ja kontrollitakse
        //piletid Listi iga isendi boolean staatuse abil, mitu piletit täpselt on hetkeseisuga müüdud.
        int myydud_arv = 0; //Loendur müüdud piletite arvu jaoks
        for (Pilet pilet : piletid) {
            if(pilet.isMyydud()) {
                myydud_arv++; //Lisatakse 1 juurde, kui pilet on müüdud
            }
        }
        return myydud_arv;
    }
}
