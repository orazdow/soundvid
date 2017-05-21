package soundvidxug;


public class SoundGen {

    int numOscs = 192;
   // short out = 0;
    double baseFreq = 50;
   // short[] sineTable = new short[4096];
    Osc oscs[];
    double srate = 44100;
    Osc o = new Osc(200);
    
    SoundGen(int num){
        initOscs(num);
    }

    void initOscs(int num){ 
        numOscs = num;
        oscs = new Osc[numOscs];
        double freq = baseFreq; //int a = 0;
        for(int i = 0; i < numOscs; i++){
         //   freq += freq/25; 
          //  freq = baseFreq*(float)Math.pow(2,i/(float)12);
              freq = baseFreq*(float)Math.pow(2,i/(double)25);
          // System.out.println(a+" "+freq);  a++;  
            oscs[i] = new Osc(freq, srate);
          //  oscs[i].setFreq(freq);
          //  System.out.println(oscs[i].freq+" "+oscs[i].step);
        }// System.out.println("done: "+numOscs);
    }
    
    short incOut(){
        double out = 0;
        for (int i = 0; i <oscs.length; i++) {
          
          out +=  oscs[i].incOut();     
        }
        return (short)(out*(32000/numOscs));
     //   return (short)(oscs[100].incOut()*5000);
    // return out;
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
        }else{ System.out.println("ERROR: pix array: "+in.length+" larger than osc array: "+oscs.length); break; }
    }
}
    
class Osc{
    
   double phase = 0;
   double step;
   double srate = 44100;
   double amp = 1;
   
   Osc(double freq){
       step = 6.28318 * (freq/srate);
   }
   Osc(double freq, double srate){
       this.srate = srate;
       step = 6.28318 * (freq/srate);
   } 
   void setFreq(double freq){
       step = 6.28318 * (freq/srate);
   }
   void setAmp(double amp){
       this.amp = amp;
   }
   
   double incOut(){
       phase += step;
       return (Math.sin(phase)*amp);
   }
   
}
    
}
