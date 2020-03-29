package odev;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;


public class YSA {
	
	private static final File egitimDosya = new File(YSA.class.getResource("ysaodev.txt").getPath());
	private static final File testDosya = new File(YSA.class.getResource("ysaodevtest.txt").getPath());    
	double[] maksimumlar,minimumlar;
    private DataSet egitimVeriSeti;
    private DataSet testVeriSeti;
    MomentumBackpropagation bp;
    private int araKatmanNoron;

	double maksEpoch;
	double []eldeEdilenHatalar;
    
    
	public YSA(int araKatmanNoron, double momentum, double ok, double error, int epoch)throws FileNotFoundException{
		eldeEdilenHatalar = new double[(int) maksEpoch];
		maksimumlar = new double[10];
		minimumlar = new double[10];
		for(int  i=0;i<10;i++){
			
			maksimumlar[i] = Integer.MIN_VALUE;
			minimumlar[i] = Integer.MAX_VALUE;
		
		}
		EgitimVeriSetiMaksAyarla();
		TestVeriSetiMaksAyarla();

        egitimVeriSeti = EgitimVeriSeti();
        testVeriSeti = TestVeriSeti();
		
        bp = new MomentumBackpropagation();
        bp.setMomentum(momentum);
        bp.setLearningRate(ok);
        bp.setMaxError(error);
        bp.setMaxIterations(epoch);
        this.araKatmanNoron = araKatmanNoron;
	}
	
	double mse(double[] beklenen , double[] cikti){
        double satirHata = 0;
        for (int i = 0; i < 1; i++) {
            satirHata += Math.pow((beklenen[i] - cikti[i]) , 2);
        }
        return satirHata;
    }

    public double test(){
        NeuralNetwork sinirselAg = new NeuralNetwork().createFromFile("ag.nnet");
        double toplamHata = 0;
        for (DataSetRow r : testVeriSeti) {
            sinirselAg.setInput(r.getInput());
            sinirselAg.calculate();
            toplamHata += mse(r.getDesiredOutput(), sinirselAg.getOutput());
        }
        return toplamHata / testVeriSeti.size();
    }
    
	public DataSet TestVeriSeti() throws FileNotFoundException {
		Scanner oku = new Scanner(testDosya);
        DataSet test = new DataSet(9, 1);
        while(oku.hasNextDouble()){
            double[] inputs = new double[9];
            for (int i = 0; i < 9; i++) {
                double d = oku.nextDouble();
                inputs[i] = min_max(maksimumlar[i], minimumlar[i], d);
            }
            DataSetRow satir = new DataSetRow(inputs, new double[] {oku.nextDouble()});
            test.add(satir);
        }
        oku.close();
        return test;
	}

	public DataSet EgitimVeriSeti() throws FileNotFoundException {
		Scanner oku = new Scanner(egitimDosya);
        DataSet egitim = new DataSet(9, 1);
        while(oku.hasNextDouble()){
            double[] inputs = new double[9];
            for (int i = 0; i < 9; i++) {
            	double d = oku.nextDouble();
                inputs[i] = min_max(maksimumlar[i], minimumlar[i], d);
            	
            }
            DataSetRow satir = new DataSetRow(inputs, new double[] {oku.nextDouble()});
            egitim.add(satir);
        }
        oku.close();
        
        
        return egitim;
	}

	private void TestVeriSetiMaksAyarla() throws FileNotFoundException {
		int s=0;
		Scanner oku = new Scanner(testDosya);
        while(oku.hasNextDouble()){
            for (int i = 0; i < 9; i++) {
                double d = oku.nextDouble();
                if(d > maksimumlar[i])  maksimumlar[i] = d;
                if(d < minimumlar[i])  minimumlar[i] = d;
            }
            oku.nextDouble();
        }
        oku.close();
	
		
	}

	private void EgitimVeriSetiMaksAyarla() throws FileNotFoundException {

		Scanner oku = new Scanner(egitimDosya);
        while(oku.hasNextDouble()){
            for (int i = 0; i < 9; i++) {
                double d = oku.nextDouble();
                if(d > maksimumlar[i])  maksimumlar[i] = d;
                if(d < minimumlar[i])  minimumlar[i] = d;
            }
            oku.nextDouble();
        }
        oku.close();
	
		
	}

	
	public void egit() throws FileNotFoundException  {
		
		MultiLayerPerceptron sinirselAg = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,9,araKatmanNoron,1);
		sinirselAg.setLearningRule(bp);
        sinirselAg.learn(egitimVeriSeti);
        sinirselAg.save("ag.nnet");
        System.out.println("Eğitim Tamamlandı.");
        
        /*
        BackPropagation bb = new BackPropagation();
        bb.setNeuralNetwork(sinirselAg);
        sinirselAg.setLearningRule(bb);
        bb.setLearningRate(15);
        //bb.setMaxError(0.000000000001);
		for(int i = 1; i<maksEpoch; i++){
			bb.doOneLearningIteration(egitimVeriSeti);
			//System.out.println("Epoch " + i + ", error=" + bb.getTotalNetworkError());
			//if(sinirselAg.getLearningRule().getTotalNetworkError() < 0.001)
			if(i==0) eldeEdilenHatalar[i]=1;
			else {
				float sonuc = (float) ((sinirselAg.getLearningRule().getPreviousEpochError()-0.8295000000000000)*1000000000);
			}
				//eldeEdilenHatalar[i]=sinirselAg.getLearningRule().getPreviousEpochError();
		}
		sinirselAg.save("ogrenenAg.nnet");
		System.out.println("EÄŸitim TamamlandÄ±.");
		*/
	}

	 public String tekTest(double[] inputs){
	        for (int i = 0; i < 9; i++) {
	            inputs[i] = min_max(maksimumlar[i], minimumlar[i], inputs[i]);
	        }
	        NeuralNetwork sinirselAg = new NeuralNetwork().createFromFile("ag.nnet");
	        sinirselAg.setInput(inputs);
	        sinirselAg.calculate();
	        return sonuc(sinirselAg.getOutput());
	    }
	 
	 public String sonuc(double[] outputs){
		 
	        double maks = outputs[0];
	        
	        if(maks < 1.5) return "Hiç Kullanılmamış";
	        if(maks < 2.5) return "Uzun Süreli Kullanım ";
	        if(maks < 3.5) return "Kısa Süreli Kullanım";
	        return "";
	    
	 }

	public double egitiHata(){
        return bp.getTotalNetworkError();
    }
	 private double min_max (double max, double min, double x){
	        return  ((x-min)/(max-min));
	}
}
