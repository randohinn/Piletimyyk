public class Pilet implements Comparable<Pilet> {
    private int koha_nr;
    private boolean myydud = false;

    public int getKoha_nr() {
        return koha_nr;
    }

    public void setKoha_nr(int koha_nr) {
        this.koha_nr = koha_nr;
    }

    //Selle meetodi abil saab küsida, kas pilet on müüdud staatuses või mitte.
    public boolean isMyydud() {
        return myydud;
    }

    //Kui pilet on müüdud, siis set meetodi abil saab selle müüduks muuta.
    public void setMyydud(boolean myydud) {
        this.myydud = myydud;
    }

    //Konstruktor, milles kasutatakse kohanumbri parameetrit. Vaikimisi on pilet mitte müüdud staatuses.
    public Pilet(int koht) {
        this.koha_nr = koht;
    }

    @Override
    public String toString() {
        return "Pilet{" +
                "koha_nr=" + koha_nr +
                ", myydud=" + myydud +
                '}';
    }

    //Määrame sorteerimisaluseks kohanumbrid. Siis pakutakse bussikohti sorteeritult.
    @Override
    public int compareTo(Pilet o) {
        if(koha_nr < o.getKoha_nr()) {
            return -1;
        }
        if(koha_nr > o.getKoha_nr()) {
            return 1;
        }
        return 0;
    }
}
