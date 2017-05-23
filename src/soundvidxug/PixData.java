package soundvidxug;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;


public class PixData {
    
    int height, width, xdiv, ydiv, div, totalpix;  
    int num = 350;  //<<approx, numcomes out higher.
    float coefs[];
    Raster raster;
    Object pixel = null;
    int outnum;

    PixData(BufferedImage in){
       width = in.getWidth();
       height = in.getHeight();
       totalpix = width*height;
       /*   //not using rectangular div...
       xdiv = (int)((width/(double)num*Math.sqrt(num)));
       ydiv = (int)((height/(double)num*Math.sqrt(num)));
       */
       // just square div shape
       div = (int)Math.sqrt(totalpix/num);
       outnum = getTileCount();
        System.out.println("oscillator count: "+outnum);
       coefs = new float[outnum];      
    }
    
    void read(BufferedImage in){
      //  System.out.println("reading");
     /*
          coefs.clear();
           for (int y = 0; y < height; y+= div) {
             for (int x = 0; x < width; x+= div){ 
                 coefs.add(getL(in.getRGB(x, y)));
               }
            } 
      */

        //or: faster?
        raster = in.getRaster();
        int n = 0;
        for (int y = 0; y < height; y+= div) {
             for (int x = 0; x < width; x+= div){      
              //  coefs.add(getL((byte[])raster.getDataElements(x, y, null))/255.0);  
          //    in.setRGB(x, y, 0x00FFFFFFFF);
              coefs[n] = condition(getL((byte[])raster.getDataElements(x, y, null)));
              n++;
             //    System.out.println(coefs[n]);
             }
           } 
      //  System.out.println(coefs.toString());

    }
    
    int getTileCount(){
       int n = 0;
       for (int y = 0; y < height; y+= div) {
            for (int x = 0; x < width; x+= div){  
                n++;
            }   
       }
       return n;
    }
    
    int getL(int in){   //rgb int
    return ((in & 0xff) + (in >> 8 & 0xff) + (in >> 16 & 0xff)) ;
      
    }
    
    int getL(byte[] in){  //3 index byte 

        return ((in[0] & 0x000000ff) + (in[1] & 0x000000ff) + (in[2] & 0x000000ff) );

    }

    float condition(int in){
        return (float)Math.pow((in/270.0),1.8);
    }


    
}
