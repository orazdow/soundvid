package soundvidxug;


public class SoundGen {

    int numOscs = 192;
    double out = 0;
    double baseFreq = 50;
    double[] sineTable = new double[4096];
    Osc oscs[];
    double srate = 44100;
    
    SoundGen(int num){
        initOscs(num);
        initTable(sineTable);
    }
    
    void initTable(double[] table){
        for(int i = 0; i < table.length; i++){
            table[i] = Math.sin(2*Math.PI*(i/(double)table.length));
        }
    }

    void initOscs(int num){ 
        numOscs = num;
        oscs = new Osc[numOscs];
        double freq = baseFreq; 
        for(int i = 0; i < numOscs; i++){
          //  freq = baseFreq*(float)Math.pow(2,i/(float)12);
            freq = baseFreq*Math.pow(2,i/(double)60);
            oscs[i] = new Osc(freq, srate);
        }
        printOscs();
    }
    
    void printOscs(){
        double f1 = oscs[0].freq;
        double f2 = oscs[(int)Math.round(oscs.length*0.25)].freq;
        double f3 = oscs[(int)Math.round(oscs.length*0.5)].freq;
        double f4 = oscs[(int)Math.round(oscs.length*0.75)].freq;
        double f5 = oscs[oscs.length-1].freq;
        System.out.println("freqs:\nosc 1: "+f1+"\nosc 0.25: "+f2+"\nosc 0.5: "+f3+"\nosc 0.75: "+f4+"\nosc "+oscs.length+": "+f5);
    }
    
    short incOut(){
        out = 0;
        for (int i = 0; i <oscs.length; i++) {     
          out +=  oscs[i].incOut();     
        }
        return (short)(out*(10000/numOscs));

    }
    
    void randomCoefs(){
    for(int i = 0; i < numOscs; i++){
     oscs[i].amp = (float) Math.pow(Math.random(), 2); 
     }    
    }
    
    void setCoeffs(float[] in){
    for(int i = 0; i < in.length; i++){
        if(i < oscs.length){
            oscs[i].amp = in[i];
        }
    }
}
    
class Osc{
    
   double phase = 0;
   double step;
   double bstep;
   double srate = 44100;
   double amp = 1;
   double freq;
   
   int len = sineTable.length;
   
   Osc(double freq){
       bstep = 6.28318 * (freq/srate);
       step = len * (freq/srate);
       this.freq = freq;
   }
   Osc(double freq, double srate){
       this.srate = srate;
     bstep = 6.28318 * (freq/srate);
     step = len * (freq/srate);
       this.freq = freq;

   } 
   void setFreq(double freq){
     bstep = 6.28318 * (freq/srate);
     step = len * (freq/srate);
       this.freq = freq;

   }
   void setAmp(double amp){
       this.amp = amp;
   }
   
   double incOut(){
       phase += step;
       if(phase >= len){ phase = 0;}
       return sineTable[(int)phase]*amp;
   }
   
   double incOut2(){
    phase += bstep;
    return Math.sin(phase)*amp;
   }
   
}
    
}
