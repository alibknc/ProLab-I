import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Test extends Application {
    Dimension ekranBoyutu = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    int yukseklik = ekranBoyutu.height - 80;
    int genislik = ekranBoyutu.width;
    int kartW = (genislik - 180) / 8;
    int kartH = (kartW / 18) * 22;
    ArrayList<Rectangle> pcKartList = new ArrayList<>();
    ArrayList<Rectangle> kullaniciKartList = new ArrayList<>();
    ArrayList<ImageView> kullaniciResimList = new ArrayList<>();
    ArrayList<ImageView> pcResimList = new ArrayList<>();
    Text kazanilanPuan, toplamPuan, secilenOzellik, text, acilis;
    String ozellik, kartSirasi;
    Button geriAl = new Button("Geri Al");
    ImageView sonAtilan1, sonAtilan2;
    boolean geriAlActive;
    int hamleSirasi = 0, x, y, i, sonuc, index0 = -1;

    static int[] oyuncuPenalti = {85, 95, 85, 80, 75, 70, 75, 80};
    static int[] oyuncuSerbestF = {85, 90, 75, 80, 85, 75, 80, 90};
    static int[] oyuncuKKarsiya = {95, 90, 90, 85, 80, 80, 75, 85};
    static String[] oyuncuIsimF = {
            "Lionel Messi",
            "Cristiano Ronaldo",
            "Kylian Mbappe",
            "Robert Lewandowski",
            "Edinson Cavani",
            "Gheorghe Hagi",
            "Alex de Souza",
            "Mohamed Salah"
    };

    static int[] oyuncuIkilik = {90, 80, 75, 80, 85, 75, 90, 85};
    static int[] oyuncuUcluk = {85, 75, 80, 80, 90, 85, 95, 75};
    static int[] oyuncuSerbestB = {85, 85, 80, 90, 80, 75, 90, 75};
    static String[] oyuncuIsimB = {
            "LeBron James",
            "Kevin Durant",
            "Stephen Curry",
            "James Harden",
            "Kobe Bryant",
            "Bobby Dixon",
            "Michael Jordan",
            "Tim Duncan"
    };

    static Kullanici kullanici = new Kullanici();
    static Bilgisayar pc = new Bilgisayar();

    static Rectangle kartOlustur(int x, int y) {
        Rectangle rect = new Rectangle();
        rect.setX(x);
        rect.setY(y);
        rect.setWidth(180);
        rect.setHeight(220);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(3);
        rect.setSmooth(true);
        rect.setEffect(new DropShadow());
        return rect;
    }

    static Text yaziOlustur(int x, int y, String metin) {
        Text text = new Text();
        text.setFont(Font.font("Liberation Serif", FontWeight.BOLD, FontPosture.REGULAR, 24));
        text.setFill(Color.BLACK);
        text.setText(metin);
        text.setX(x);
        text.setY(y);
        return text;
    }

    ImageView resimOlustur(int x, int y, Group group) {
        index0++;
        int index = index0;
        String dosyaIsmi = "images/" + kullanici.kartListesi[index].sporcuIsim + ".png";
        Image img = new Image(dosyaIsmi);
        ImageView imgview = new ImageView(img);
        imgview.setX(x);
        imgview.setY(y);
        imgview.setFitHeight(220);
        imgview.setFitWidth(180);

        EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!geriAlActive) {
                    if (kartSirasi == null) {
                        kartAt(group, index);
                        group.getChildren().removeAll(imgview, kullaniciKartList.get(index));
                    } else if (index < 4 && kartSirasi.equals("F") || index >= 4 && kartSirasi.equals("B") || index < 4 && pc.fkartBittiMi() || index > 4 && pc.bkartBittiMi()) {
                        kartAt(group, index);
                        group.getChildren().removeAll(imgview, kullaniciKartList.get(index));
                    }
                }
            }
        };
        imgview.setOnMouseClicked(handler);
        return imgview;
    }

    void puanYazdir(Group group, int sonuc) {
        group.getChildren().remove(acilis);
        secilenOzellik = yaziOlustur(20, yukseklik / 2, "Seçilen Özellik: " + ozellik);
        if (sonuc == 1) {
            kazanilanPuan = yaziOlustur(20, (yukseklik / 2) + 20, "Oyuncu +10 Puan");
        } else if (sonuc == 2) {
            kazanilanPuan = yaziOlustur(20, (yukseklik / 2) + 20, "Tur Berabere!\n" + "Oyuncu +0 Puan\n" + "Bilgisayar +0 Puan");
        } else {
            kazanilanPuan = yaziOlustur(20, (yukseklik / 2) + 20, "Bilgisayar +10 Puan");
        }
        toplamPuan = yaziOlustur(genislik - 250, yukseklik / 2, "Bilgisayar: " + pc.skorGoster() + "\nKullanıcı: " + kullanici.skorGoster());
        group.getChildren().addAll(kazanilanPuan, secilenOzellik, toplamPuan);
    }

    void kartAt(Group group, int index) {
        int boslukY = (yukseklik - (2 * (kartH + 20)) - kartH) / 2;
        x = (genislik - (2 * kartW) - 20) / 2;
        y = 20 + kartH + boslukY;

        if (index < 4) {
            kartSirasi = "B";
        } else {
            kartSirasi = "F";
        }

        String dosyaIsmi = "images/" + kullanici.kartListesi[index].sporcuIsim + ".png";
        Image img = new Image(dosyaIsmi);
        ImageView imgview = new ImageView(img);
        imgview.setX(x);
        imgview.setY(y);
        imgview.setFitHeight(220);
        imgview.setFitWidth(180);
        group.getChildren().add(imgview);
        sonAtilan1 = imgview;

        i = pc.kartSec(pc.kartListesi, index);

        dosyaIsmi = "images/" + pc.kartListesi[i].sporcuIsim + ".png";
        Image img2 = new Image(dosyaIsmi);
        ImageView imgview2 = new ImageView(img2);
        imgview2.setX(x + kartW + 20);
        imgview2.setY(y);
        imgview2.setFitHeight(220);
        imgview2.setFitWidth(180);
        group.getChildren().add(imgview2);
        sonAtilan2 = imgview2;

        group.getChildren().removeAll(pcKartList.get(i), pcResimList.get(i), kazanilanPuan, secilenOzellik, toplamPuan, text);

        karsilastir(group, index, i, index);
    }

    void karsilastir(Group group, int kIndex, int pcIndex, int index) {
        hamleSirasi++;
        Random r = new Random();
        int ozellikNo = r.nextInt(3);
        int deger1;
        int deger2;

        if (index < 4) {
            Basketbolcu bp1 = (Basketbolcu) kullanici.kartListesi[kIndex];
            Basketbolcu bp2 = (Basketbolcu) pc.kartListesi[pcIndex];
            bp1.kartKullanildiMi = true;
            bp2.kartKullanildiMi = true;
            if (ozellikNo == 0) {
                deger1 = bp1.getIkilikAtis();
                deger2 = bp2.getIkilikAtis();
                ozellik = "İkilik Atış";
            } else if (ozellikNo == 1) {
                deger1 = bp1.getUclukAtis();
                deger2 = bp2.getUclukAtis();
                ozellik = "Üçlük Atış";
            } else {
                deger1 = bp1.getSerbestAtis();
                deger2 = bp2.getSerbestAtis();
                ozellik = "Serbest Atış";
            }
        } else {
            Futbolcu fp1 = (Futbolcu) kullanici.kartListesi[kIndex];
            Futbolcu fp2 = (Futbolcu) pc.kartListesi[pcIndex];
            fp1.kartKullanildiMi = true;
            fp2.kartKullanildiMi = true;
            if (ozellikNo == 0) {
                deger1 = fp1.getPenalti();
                deger2 = fp2.getPenalti();
                ozellik = "Penaltı";
            } else if (ozellikNo == 1) {
                deger1 = fp1.getSerbestVurus();
                deger2 = fp2.getSerbestVurus();
                ozellik = "Serbest Vuruş";
            } else {
                deger1 = fp1.getKaleciKarsiKarsiya();
                deger2 = fp2.getKaleciKarsiKarsiya();
                ozellik = "Kaleciyle Karşı Karşıya";
            }
        }

        int kullaniciSkor = Integer.parseInt(kullanici.skorGoster());
        int pcSkor = Integer.parseInt(pc.skorGoster());

        if (pc.kartBittiMi()) {
            if (ozellik.equals("İkilik Atış") || ozellik.equals("Üçlük Atış") || ozellik.equals("Serbest Atış")) {
                text = yaziOlustur(815, 650, "Bir Basketbolcu Seçiniz");
            } else {
                text = yaziOlustur(815, 650, "Bir Futbolcu Seçiniz");
            }
            group.getChildren().add(text);
        } else if (pc.bkartBittiMi()) {
            text = yaziOlustur(815, 650, "Bir Futbolcu Seçiniz");
            group.getChildren().add(text);
        } else if (pc.fkartBittiMi()) {
            text = yaziOlustur(815, 650, "Bir Basketbolcu Seçiniz");
            group.getChildren().add(text);
        } else if (ozellik.equals("İkilik Atış") || ozellik.equals("Üçlük Atış") || ozellik.equals("Serbest Atış")) {
            text = yaziOlustur(815, 650, "Bir Futbolcu Seçiniz");
            group.getChildren().add(text);
        } else if (ozellik.equals("Serbest Vuruş") || ozellik.equals("Penaltı") || ozellik.equals("Kaleciyle Karşı Karşıya")) {
            text = yaziOlustur(815, 650, "Bir Basketbolcu Seçiniz");
            group.getChildren().add(text);
        }

        if (deger1 > deger2) {
            sonuc = 1;
            kullanici.skor = kullanici.skor + 10;
        } else if (deger1 == deger2) {
            hamleSirasi--;
            sonuc = 2;
            geriAlActive = true;
            geriAl.setLayoutX(910);
            geriAl.setLayoutY(700);
            group.getChildren().add(geriAl);
            geriAl.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (index < 4) {
                        kartSirasi = "B";
                        ((Basketbolcu) pc.kartListesi[pcIndex]).kartKullanildiMi = false;
                    } else {
                        kartSirasi = "F";
                        ((Futbolcu) pc.kartListesi[pcIndex]).kartKullanildiMi = false;
                    }
                    group.getChildren().addAll(pcKartList.get(pcIndex), pcResimList.get(pcIndex), kullaniciKartList.get(kIndex), kullaniciResimList.get(kIndex));
                    group.getChildren().removeAll(sonAtilan1, sonAtilan2, geriAl);
                    geriAlActive = false;
                }
            });
        } else {
            sonuc = 0;
            pc.skor = pc.skor + 10;
        }

        if (hamleSirasi == 8 && deger1 > deger2)
            kullaniciSkor += 10;
        if (hamleSirasi == 8 && deger2 > deger1)
            pcSkor += 10;
        if (sonuc != 2 && pc.kartBittiMi()) {
            group.getChildren().remove(text);
            if (kullaniciSkor > pcSkor) {
                text = yaziOlustur(780, 650, "Oyun bitti. Kazanan: Kullanıcı\n                 Tebrikler!");
                group.getChildren().addAll(text);
            } else if (kullaniciSkor < pcSkor) {
                text = yaziOlustur(800, 650, "Oyun bitti. Kazanan: Bilgisayar");
                group.getChildren().addAll(text);
            } else {
                text = yaziOlustur(800, 650, "Oyun bitti. Sonuç: Berabere");
                group.getChildren().addAll(text);
            }
        }

        puanYazdir(group, sonuc);

    }

    @Override
    public void start(Stage primaryStage) {
        Group group = new Group();
        Scene scene = new Scene(group, genislik, yukseklik, Color.GRAY);

        Button btn = new Button("Oyuna Başla");
        btn.setLayoutX(genislik / 2.0);
        btn.setLayoutY(yukseklik / 2.0);
        group.getChildren().add(btn);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                acilis = yaziOlustur(770, 650, "Bir oyuncu seçimi bekleniyor...");
                group.getChildren().add(acilis);

                x = 20;
                y = 20;

                for (int i = 0; i < 8; i++) {
                    Rectangle kart = kartOlustur(x, y);
                    pcKartList.add(kart);

                    if (i < 4) {
                        Image img = new Image("images/Basketbolcu.png");
                        ImageView imgview = new ImageView(img);
                        imgview.setX(x);
                        imgview.setY(y);
                        imgview.setFitHeight(220);
                        imgview.setFitWidth(180);
                        pcResimList.add(imgview);
                        group.getChildren().addAll(kart, imgview);
                    } else {
                        Image img = new Image("images/Futbolcu.png");
                        ImageView imgview = new ImageView(img);
                        imgview.setX(x);
                        imgview.setY(y);
                        imgview.setFitHeight(220);
                        imgview.setFitWidth(180);
                        pcResimList.add(imgview);
                        group.getChildren().addAll(kart, imgview);
                    }
                    x += kartW + 20;
                }

                x = 20;
                y = yukseklik - 20 - kartH;

                for (int i = 0; i < 8; i++) {
                    Rectangle kart = kartOlustur(x, y);
                    ImageView imgview = resimOlustur(x, y, group);
                    group.getChildren().addAll(kart, imgview);
                    kullaniciKartList.add(kart);
                    kullaniciResimList.add(imgview);
                    x += kartW + 20;
                }

                int boslukY = (yukseklik - (2 * (kartH + 20)) - kartH) / 2;
                x = (genislik - (2 * kartW) - 20) / 2;
                y = 20 + kartH + boslukY;

                for (int i = 0; i < 2; i++) {
                    Rectangle rect = kartOlustur(x, y);
                    group.getChildren().addAll(rect);
                    x += kartW + 20;
                }

                group.getChildren().remove(btn);
            }
        });
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("ProLab-II");
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Basketbolcu[] basketbolcular;
        basketbolcular = Basketbolcu.oyuncuOlustur();

        Futbolcu[] futbolcular;
        futbolcular = Futbolcu.oyuncuOlustur();

        kartDagit(basketbolcular, futbolcular);

        for (int i = 0; i < 8; i++) {
            System.out.println(kullanici.kartListesi[i].sporcuIsim);
            System.out.println(pc.kartListesi[i].sporcuIsim);
        }
        launch(args);
    }

    public static void kartDagit(Basketbolcu[] b, Futbolcu[] f) {
        int i = 0;
        int j = 0;

        while (i != 4) {
            List<Sporcu> kList = Arrays.asList(kullanici.kartListesi);
            Random r = new Random();
            int index = r.nextInt(8);
            if (!kList.contains(b[index])) {
                kullanici.kartListesi[i] = b[index];
                i++;
            }
        }

        while (i != 8) {
            List<Sporcu> kList = Arrays.asList(kullanici.kartListesi);
            Random r = new Random();
            int index = r.nextInt(8);
            if (!kList.contains(f[index])) {
                kullanici.kartListesi[i] = f[index];
                i++;
            }
        }

        List<Sporcu> kList = Arrays.asList(kullanici.kartListesi);

        for (i = 0; i < 8; i++) {
            if (!kList.contains(b[i])) {
                pc.kartListesi[j] = b[i];
                j++;
            }
        }

        for (i = 0; i < 8; i++) {
            if (!kList.contains(f[i])) {
                pc.kartListesi[j] = f[i];
                j++;
            }
        }

    }
}
