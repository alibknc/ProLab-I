#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

typedef struct dugum {
    char *kelime;
    int adet;
    struct dugum *next;
} words;

words *kelimeEkle(words *kelimeListesi, char *kelime);

bool kontrolEt(words *kelimeListesi, char *kelime);

words *yenidenSirala(words *gelenListe, words *yeniListe);

words *basaekle(words *gelenListe, words *yeniListe);

words *sonElemanBul(words *liste);

words *sonaEkle(words *gelenListe, words *sonEleman);

words *arayaEkle(words *gelen, words *liste);

int main() {
    char text[500] = "";
    char temp[500];
    words *kelimeler;
    kelimeler = NULL;
    FILE *dosya;

    dosya = fopen("prolab1-3.txt", "r");
    if (dosya == 0) {
        printf("Dosyaya veri girisi yaptiginizdan emin olun.");
        fopen("prolab1-3.txt", "w+");
        exit(1);
    }

    while (fgets(temp, 500, dosya) != NULL){
        strcat(text, temp);
    }

    const char *ayrac = " \n";
    char *kelime = strtok(text, ayrac);
    words *adres;

    while (kelime != NULL) {
        kelimeler = kelimeEkle(kelimeler, kelime);
        if (kelimeler != NULL) adres = kelimeler;
        else kelimeler = adres;
        kelime = strtok(NULL, ayrac);
    }

    kelimeler = adres;
    words *yeniListe;
    yeniListe = NULL;

    kelimeler = yenidenSirala(kelimeler, yeniListe);

    while (kelimeler != NULL) {
        printf("%s %d\n", kelimeler->kelime, kelimeler->adet);
        kelimeler = kelimeler->next;
    }
    return 0;
}

words *yenidenSirala(words *gelenListe, words *yeniListe) {
    words *ilkEleman;
    words *sonEleman;

    while (1) {
        if (yeniListe == NULL) {
            yeniListe = (words *) malloc(sizeof(words));
            yeniListe->kelime = gelenListe->kelime;
            yeniListe->adet = gelenListe->adet;
            yeniListe->next = NULL;
            ilkEleman = yeniListe;
            sonEleman = yeniListe;
            gelenListe = gelenListe->next;
        } else {
            if (gelenListe->adet >= ilkEleman->adet) {
                yeniListe = basaekle(gelenListe, yeniListe);
                ilkEleman = yeniListe;
                gelenListe = gelenListe->next;
                sonEleman = sonElemanBul(yeniListe);
            } else if (gelenListe->adet <= sonEleman->adet) {
                sonEleman = sonaEkle(gelenListe, sonEleman);
                gelenListe = gelenListe->next;
            } else {
                yeniListe = arayaEkle(gelenListe, yeniListe);
                gelenListe = gelenListe->next;
            }
        }
        if(gelenListe == NULL) break;
    }
    return yeniListe;
}

words *arayaEkle(words *gelen, words *liste) {
    words *tempListe = liste;
    while (1) {
        if (tempListe->adet >= gelen->adet && tempListe->next->adet <= gelen->adet) {
            words *yeni = malloc(sizeof(words));
            yeni->adet = gelen->adet;
            yeni->kelime = gelen->kelime;
            yeni->next = tempListe->next;
            tempListe->next = yeni;
            break;
        }
        tempListe = tempListe->next;
    }
    return tempListe;
}

words *sonaEkle(words *gelenListe, words *sonEleman) {
    words *temp = (words *) malloc(sizeof(words));
    temp->adet = gelenListe->adet;
    temp->kelime = gelenListe->kelime;
    temp->next = NULL;
    sonEleman->next = temp;
    return temp;
}

words *sonElemanBul(words *liste) {
    words *temp = liste;
    while (temp->next != NULL)
        temp = temp->next;
    return temp;
}

words *basaekle(words *gelenListe, words *yeniListe) {
    words *temp = malloc(sizeof(words));
    temp->adet = gelenListe->adet;
    temp->kelime = gelenListe->kelime;
    temp->next = yeniListe;
    return temp;
}

words *kelimeEkle(words *kelimeListesi, char *kelime) {
    if (kelimeListesi != NULL) {
        bool sonuc = kontrolEt(kelimeListesi, kelime);
        if (!sonuc) {
            words *yeniKelime = malloc(sizeof(words));
            yeniKelime->kelime = kelime;
            yeniKelime->adet = 1;
            yeniKelime->next = NULL;
            while (kelimeListesi->next != NULL)
                kelimeListesi = kelimeListesi->next;
            kelimeListesi->next = yeniKelime;
            return NULL;
        }
        return NULL;
    } else {
        kelimeListesi = (words *) malloc(sizeof(words));
        kelimeListesi->kelime = kelime;
        kelimeListesi->adet = 1;
        kelimeListesi->next = NULL;
        return kelimeListesi;
    }
}

bool kontrolEt(words *kelimeListesi, char *kelime) {
    words *temp = kelimeListesi;
    while (1) {
        if (strcmp(temp->kelime, kelime) == 0) {
            temp->adet = temp->adet + 1;
            return true;
        }
        temp = temp->next;
        if (temp == NULL) break;
    }
    return false;
}
