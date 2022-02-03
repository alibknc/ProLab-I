public class Futbolcu extends Sporcu {
    String futbolcuIsim;
    String futbolcuTakim;
    int penalti;
    int serbestVurus;
    int kaleciKarsiKarsiya;
    boolean kartKullanildiMi = false;
    static Futbolcu[] oyuncuListe = new Futbolcu[8];

    public Futbolcu() {
    }

    public Futbolcu(int penalti, int serbestVurus, int kaleciKarsiKarsiya) {
        this.futbolcuIsim = super.sporcuIsim;
        this.futbolcuTakim = super.sporcuTakim;
        setPenalti(penalti);
        setSerbestVurus(serbestVurus);
        setKaleciKarsiKarsiya(kaleciKarsiKarsiya);
    }

    @Override
    int sporcuPuaniGoster() {
        super.sporcuPuaniGoster();
        return penalti + serbestVurus + kaleciKarsiKarsiya;
    }

    public static Futbolcu[] oyuncuOlustur() {
        for (int i = 0; i < 8; i++) {
            Futbolcu temp = new Futbolcu(Test.oyuncuPenalti[i], Test.oyuncuSerbestF[i], Test.oyuncuKKarsiya[i]);
            temp.sporcuIsim = Test.oyuncuIsimF[i];
            oyuncuListe[i] = temp;
        }
        return oyuncuListe;
    }

    public int getPenalti() {
        return penalti;
    }

    public int getSerbestVurus() {
        return serbestVurus;
    }

    public int getKaleciKarsiKarsiya() {
        return kaleciKarsiKarsiya;
    }

    public void setPenalti(int penalti) {
        this.penalti = penalti;
    }

    public void setSerbestVurus(int serbestVurus) {
        this.serbestVurus = serbestVurus;
    }

    public void setKaleciKarsiKarsiya(int kaleciKarsiKarsiya) {
        this.kaleciKarsiKarsiya = kaleciKarsiKarsiya;
    }

}
