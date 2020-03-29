package odev;

import java.io.IOException;
import java.util.Scanner;


public class main {

	public static void main(String[] args) throws IOException {
		double[] inputs = new double[9];
		int secim;
		Scanner in = new Scanner(System.in);
		YSA YsinirAgi = null;
		do {
		System.out.println("1. Ağı Eğit");
        System.out.println("2. Ağı Test Et");
        System.out.println("3. Tek Veri ile Test Et");
        System.out.println("4. Çıkış");
        secim = in.nextInt();
        switch(secim){
        case 1:
        	
        	YsinirAgi = new YSA(25,0.1, 15, 0.9, 35000);
        	
        	YsinirAgi.egit();
            System.out.println("Eğitimdeki Hata Değeri : "+ YsinirAgi.egitiHata());
            
            break;
        case 2:
            if(YsinirAgi == null)
            {
                System.out.println("Egitim Yapilmamis");
                
            }
            else
            {
                System.out.println("Test Verisindeki Hata Değeri : "+ YsinirAgi.test());	
            }
            
            break;
        case 3:
        	 if(YsinirAgi == null)
             {
                 System.out.println("Egitim Yapilmamis");
                 
             }
        	 
             System.out.println("Kadının Yaşı : ");
             inputs[0] = in.nextDouble();
             System.out.println("Kadın Eğitim Durumu (0-4) : ");
             inputs[1] = in.nextDouble();
             System.out.println("Erkek Eğitim Durumu (0-4) : ");
             inputs[2] = in.nextDouble();
             System.out.println("Çocuk Sayısı : ");
             inputs[3] = in.nextDouble();
             System.out.println("Kadın Müslüman Mı 0=Hayır 1=Evet : ");
             inputs[4] = in.nextDouble();
             System.out.println("Kadın Çalısıyor Mu 0=Evet 1=Hayır : ");
             inputs[5] = in.nextDouble();
             System.out.println("Kocanın Mesleği Gelir Düzeyi (0-4) : ");
             inputs[6] = in.nextDouble();
             System.out.println("Standart Yaşam İndeksı (0-4) : ");
             inputs[7] = in.nextDouble();
             System.out.println("Medyaya Maruz Kalma 0=Var 1=Yok : ");
             inputs[8] = in.nextDouble();
             
             System.out.println("Sonuc  : "+YsinirAgi.tekTest(inputs));
             
        	break;
        	}
		} while (secim != 3);
		
	}

}
