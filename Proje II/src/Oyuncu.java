public class Oyuncu {
    int oyuncuID;
    String oyuncuAdi;
    int skor = 0;
    Sporcu[] kartListesi = new Sporcu[8];

    public Oyuncu() {
    }

    public Oyuncu(int oyuncuID, String oyuncuAdi, int skor) {
        this.oyuncuID = oyuncuID;
        this.oyuncuAdi = oyuncuAdi;
        this.skor = skor;
    }

    String skorGoster(){
        return Integer.toString(skor);
    }

    int kartSec(Sporcu[] liste, int index){
        return 0;
    }

}
