public class Basketbolcu extends Sporcu {
    String basketbolcuIsim;
    String basketbolcuTakim;
    int ikilikAtis;
    int uclukAtis;
    int serbestAtis;
    boolean kartKullanildiMi = false;
    static Basketbolcu[] oyuncuListe = new Basketbolcu[8];

    public Basketbolcu() {
    }

    public Basketbolcu(int ikilikAtis, int uclukAtis, int serbestAtis) {
        this.basketbolcuIsim = super.sporcuIsim;
        this.basketbolcuTakim = super.sporcuTakim;
        setIkilikAtis(ikilikAtis);
        setUclukAtis(uclukAtis);
        setSerbestAtis(serbestAtis);
    }

    public static Basketbolcu[] oyuncuOlustur() {
        for (int i = 0; i < 8; i++) {
            Basketbolcu temp = new Basketbolcu(Test.oyuncuIkilik[i], Test.oyuncuUcluk[i], Test.oyuncuSerbestB[i]);
            temp.sporcuIsim = Test.oyuncuIsimB[i];
            oyuncuListe[i] = temp;
        }
        return oyuncuListe;
    }

    @Override
    int sporcuPuaniGoster() {
        super.sporcuPuaniGoster();
        return serbestAtis + uclukAtis + ikilikAtis;
    }

    public int getIkilikAtis() {
        return ikilikAtis;
    }

    public int getUclukAtis() {
        return uclukAtis;
    }

    public int getSerbestAtis() {
        return serbestAtis;
    }

    public void setIkilikAtis(int ikilikAtis) {
        this.ikilikAtis = ikilikAtis;
    }

    public void setUclukAtis(int uclukAtis) {
        this.uclukAtis = uclukAtis;
    }

    public void setSerbestAtis(int serbestAtis) {
        this.serbestAtis = serbestAtis;
    }

}
