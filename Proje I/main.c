#include <stdio.h>
#include <graphics.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

int dosyaOku(char &konsol, char &inputtxt);

void koordinat_cizdir(int);

void noktalari_yaz(double px[], double py[], int xn[], int yn[], int);

int atama_ve_nokta_sayisi(char inputtxt[], int xn[], int yn[]);

void ortalama(double &xort, double &yort, int, int xn[], int yn[]);

void cemberi_hesapla_ve_ciz(double px[], double py[], float, float, double &xort, double &yort,int);

void b_spline(double px[],double py[], int xn[],int yn[],int);

int main() {
	int i;
	char konsolgirisi[30];   //kullanici not defterine veriyi konsoldan girmek isterse etkinlesecek char dizisi
	
	FILE *dosya= fopen("prolab1.txt","r");
	if (dosya==NULL) {
		i=0;
		dosya= fopen("prolab1.txt","w");
		printf("Dosya okunamadi! Lutfen prolab1.txt dosyasina veri girisi yapiniz.\nKonsol uzerinden veri yazdirmak isterseniz 1 tusuna basiniz.");
		scanf("%d",&i);
		if(i!=1)
			return 1;
		else {
			printf("Nokta verilerini giriniz:");
			scanf("%s",konsolgirisi);
			fprintf(dosya,konsolgirisi);
		}
	}
	
	char inputtxt[50];      //dosyadan veri okuma	
	fgets(inputtxt,50,dosya);
	fclose(dosya);
	
	int xn[20];
	int yn[20];

	int noktasayisi=atama_ve_nokta_sayisi(inputtxt,xn,yn);  //nokta verisini alma

	int window1= initwindow(1000,750);  // pencereyi tanýmlýyoruz.

	koordinat_cizdir(0);  //Grafik penceresine koordinar düzleminin çizilmesi

	double px[noktasayisi],py[noktasayisi];

	noktalari_yaz(px,py,xn,yn,noktasayisi);  //noktalarýn korrdinat düzleminde gösterimi

	double xort,yort;
	double *pxort=&xort;
	double *pyort=&yort;

	ortalama(*pxort,*pyort,noktasayisi,xn,yn);  //noktalarýn ortalama x ve y deðerlerinin hesabý
	
	float x1=175,y1=700;
	float *px1=&x1;
	float *py1=&y1;

	cemberi_hesapla_ve_ciz(px,py,x1,y1,*pxort,*pyort,noktasayisi);  // minimum enclosing circle hesabý ve grafik ekranýna çizilmesi

	b_spline(px,py,xn,yn,noktasayisi); //b-spline eðrilerinin çizimi

	getch();
	closegraph();
	return 0;
}

int faktor(int x) { //faktoriyel fonksiyonu
	int z,faktor=1;
	for(z=1; z<=x; z++) {
		faktor = faktor * z;
	}
	return faktor;
}

double kom(int x, int y) { //kombinasyon fonksiyonu
	int z,b,k;
	double kom;
	if(x>y) {
		b=x;
		k=y;
	} else {
		b=y;
		k=x;
	}//büyük küçük sayý hangisi belirleniyor
	kom = faktor(b)/(faktor(k)*faktor(b-k));
	return kom;
}

void koordinat_cizdir(int d) {
	int i,z,x,y;
	
	setcolor(5);
	line(500,50,500,700);    //Koordinat düzlemini çiziyoruz.
	line(175,375,825,375);
	setcolor(3);
	char kn[90];  //koordinat noktalarý

	for(i=-13,z=0,x=170; i<14; i++,x+=25) { //bu döngüde piksel kaymasýný göz önüne alarak iþaretleri ve sayýlarý yerleþtirdik.
		sprintf(kn,"%d",i);
		if(x==495)
			outtextxy(496,367,"•");
		else
			outtextxy(x,367,"•");

		if(i<0)
			outtextxy(x-10,380,kn);
		else
			outtextxy(x,380,kn);
	}

	for(z=13,y=60; z>-14; z--,y+=25) {     //bu döngüde piksel kaymasýný göz önüne alarak iþaretleri ve sayýlarý yerleþtirdik.
		sprintf(kn,"%d",z);
		if(y!=385) {
			if(y==60)
				outtextxy(496,45,"•");
			else
				outtextxy(504,y,"•");
			settextstyle(0,1,1);

			if(y<160)
				outtextxy(525,y,kn);
			else if(y<=255)
				outtextxy(515,y,kn);
			else if(y<385)
				outtextxy(515,y,kn);
			else if(y<=610)
				outtextxy(520,y,kn);
			else
				outtextxy(530,y,kn);
		}
	}
}

void noktalari_yaz(double px[], double py[], int xn[], int yn[], int noktasayisi) {
	int i,z,x,y;
	char koordinat[50];
	
	for(i=0,x=175,y=700; i<noktasayisi; i++) { //Noktalarin yerleþtirilmesi

		for(z=-13; z<xn[i]; z++)
			x+=25;

		for(z=-13; z<yn[i]; z++)
			y-=25;

		px[i]=x+2; //nokta verilerinin alýnmasý
		py[i]=y+5;

		setcolor(4);
		outtextxy(x+3,y+10,"•");
		setcolor(YELLOW);
		line(x+5,y+10,x+25,y+30);
		sprintf(koordinat,"(%d,%d)",xn[i],yn[i]);
		setcolor(10);
		outtextxy(x+65,y+44,koordinat);
		x=175;
		y=700;
	}
}

int atama_ve_nokta_sayisi(char inputtxt[50], int xn[], int yn[]) {   // x ve y noktalarý atanýyor. kaç nokta olduðu döndürülüyor.
	int i,v,l;
	const char *ayrac=" {,}";
	char *kelime=strtok(inputtxt,ayrac);
	i=0,v=0,l=0;
	
	while (kelime != NULL) {
		if(i%2==0) {
			xn[l]=atoi(kelime);
			l++;
		} else {
			yn[v]=atoi(kelime);
			v++;
		}
		kelime = strtok(NULL, ayrac);
		i++;
	}
	
	return l;
}

void ortalama(double &xort, double &yort, int noktasayisi, int xn[], int yn[]) { //ortalama hesaplama
	double topx=0,topy=0;

	for(int i=0; i<noktasayisi; i++) {
		topx+=xn[i];
		topy+=yn[i];
	}
	
	xort=topx/noktasayisi;
	yort=topy/noktasayisi;
}

void cemberi_hesapla_ve_ciz(double px[], double py[], float x1, float y1, double &xort, double &yort, int noktasayisi) {

	float h,j;
	int i;

	for(h=-13,x1=175; h<xort; h+=0.01)
		x1+=0.25;

	for(j=-13,y1=700; j<yort; j+=0.01)
		y1-=0.25;

	double yaricap=0;
	double pow1,pow2;
	int enUzak;
	
	setcolor(14);
	
	for(i=0; i<noktasayisi; i++) {
		pow1=pow((x1-px[i]),2.0);
		pow2=pow((y1-py[i]),2.0);

		if((float)sqrt(pow1+pow2)>=yaricap) {
			yaricap=(float)sqrt(pow1+pow2);
			enUzak=i;
		}
	}

	double mx=x1-px[enUzak];
	double my=y1-py[enUzak];
	double m=mx/my;

	if(m<0)
		m*=-1;

	int enUzak2=101;
	double deger=1.0;

	while(enUzak2==101) {
		if(px[enUzak]<x1 && py[enUzak]>y1) {
			x1-=0.01;
			y1+=(1/m)*0.01;
		} else if(px[enUzak]>x1 && py[enUzak]>y1) {
			x1+=0.01;
			y1+=(1/m)*0.01;
		} else if(px[enUzak]>x1 && py[enUzak]<y1) {
			x1+=0.01;
			y1-=(1/m)*0.01;
		} else if(px[enUzak]<x1 && py[enUzak]<y1) {
			x1-=0.01;
			y1-=(1/m)*0.01;
		}

		pow1=pow((x1-px[enUzak]),2);
		pow2=pow((y1-py[enUzak]),2);
		yaricap=(double)sqrt(pow1+pow2);

		for(i=0; i<noktasayisi; i++) {
			if(i!=enUzak) {
				pow1=pow((x1-px[i]),2.0);
				pow2=pow((y1-py[i]),2.0);
				deger = (double)sqrt(pow1+pow2);

				if((yaricap-deger)<=1.7) {
					enUzak2=i;
					break;
				}
			}
		}
	}

	double ax = (px[enUzak2]+px[enUzak])/2;
	double ay = (py[enUzak2]+py[enUzak])/2;

	mx=x1-ax;
	my=y1-ay;
	m=mx/my;

	if(m<0)
		m*=-1;

	double enUzak3=101;

	while(enUzak3==101) {
		if(ax<x1 && ay>y1) {
			x1-=0.01;
			y1+=(1/m)*0.01;
		} else if(ax>x1 && ay>y1) {
			x1+=0.01;
			y1+=(1/m)*0.01;
		} else if(ax>x1 && ay<y1) {
			x1+=0.01;
			y1-=(1/m)*0.01;
		} else if(ax<x1 && ay<y1) {
			x1-=0.01;
			y1-=(1/m)*0.01;
		}

		pow1=pow((x1-px[enUzak]),2.0);
		pow2=pow((y1-py[enUzak]),2.0);
		yaricap=(double)sqrt(pow1+pow2);

		for(i=0; i<noktasayisi; i++) {
			if(i!=enUzak && i!=enUzak2) {
				pow1=pow((x1-px[i]),2.0);
				pow2=pow((y1-py[i]),2.0);
				deger = (double)sqrt(pow1+pow2);

				if((yaricap-deger)<=1.7) {
					enUzak3=i;
					break;
				}
			}
		}
		if(fabs(x1-ax)<0.01 && fabs(y1-ay)<0.01)
			break;
	}

	h=-13,j=-13;
	outtextxy(x1+4,y1+5,"•");
	float x2, y2;

	for (x2=175; x2<x1; x2+=0.01)
		h+=0.0004;

	for (y2=700; y2>y1; y2-=0.01)
		j+=0.0004;

	char merkez[20];
	sprintf(merkez,"(%.1f,%.1f)",h,j);
	line(x1,y1,x1+20,y1+20);
	outtextxy(x1+100,y1+20,merkez);
	
	circle(x1,y1,yaricap);

	char yaricaps[]= "Çemberin yarýçapý: ";
	char merkeznoktas[]= " Çemberin Merkez noktasý: ";
	char string[150];
	sprintf(string,"%s %.4f %s {%.1f,%.1f}",yaricaps,yaricap/25,merkeznoktas,h,j);
	setcolor(BLUE);
	outtextxy(750,20,string);
	printf("Yaricap=%.4f\nMekrez Noktasi=(%.1f,%.1f)",yaricap/25,h,j);
}

void b_spline(double px[], double py[], int xn[], int yn[], int noktasayisi) {

	float s, toplamx=0, toplamy=0;
	int i, tempx, tempy;

	for(i=0; i<noktasayisi-1; i++)
		for(i=0; i<noktasayisi-1; i++) {
			if((xn[i]+yn[i])/2<(xn[i+1]+yn[i+1])/2) {
				tempx=px[i],tempy=py[i];
				px[i]=px[i+1];
				px[i+1]=tempx;
				py[i]=py[i+1];
				py[i+1]=tempy;
			}
		}

	for(s=0.0; s<=1.0; s=s+(0.001/5)) {

		for(i=0; i<noktasayisi; i++) {
			toplamx=toplamx+(kom(noktasayisi-1,i)*pow(s,i)*pow((1-s),(noktasayisi-1-i))*px[i]);
		}

		for(i=0; i<noktasayisi; i++) {
			toplamy=toplamy+(kom(noktasayisi-1,i)*pow(s,i)*pow((1-s),(noktasayisi-1-i))*py[i]);
		}
		
		putpixel(toplamx,toplamy,15);
		toplamx=0;
		toplamy=0;
	}
}
