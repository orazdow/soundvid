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
            freq = baseFreq*Math.pow(2,i/(double)25);
            oscs[i] = new Osc(freq, srate);
        }
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
   int len = sineTable.length;
   
   Osc(double freq){
       bstep = 6.28318 * (freq/srate);
       step = len * (freq/srate);
   }
   Osc(double freq, double srate){
       this.srate = srate;
     bstep = 6.28318 * (freq/srate);
     step = len * (freq/srate);
   } 
   void setFreq(double freq){
     bstep = 6.28318 * (freq/srate);
     step = len * (freq/srate);
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
