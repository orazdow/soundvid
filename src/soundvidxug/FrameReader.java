package soundvidxug;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import java.awt.image.BufferedImage;

public class FrameReader extends MediaToolAdapter{
    
    long count = 0;
    long next = 0;
    long timestamp = 0;
    IMediaReader reader;
    AudioReader audioreader;
    BufferedImage out;
    boolean image1st = false;
    PixData pix;
    
    FrameReader(IMediaReader reader){
        
        this.reader = reader;
        reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
    }
    
    void setAudioReader(AudioReader in){
        audioreader = in;
    }
    
    @Override
    public void onVideoPicture(IVideoPictureEvent event){
           next++;
           timestamp = event.getTimeStamp();
           out = event.getImage();
           if(!image1st){
               pix = new PixData(out);
               audioreader.initOscs(pix.outnum);
               image1st = true;
           }
           pix.read(out);
           
        //   audioreader.setFreq( 100.0+Math.random()*100 );
           audioreader.setCoefs(pix.coefs);
           audioreader.readPackets(timestamp);

      super.onVideoPicture(event);
    }
    
    boolean nextFrame(){
        boolean rtn = true;
        while(count == next){
            if(reader.readPacket() != null){
                rtn = false;
            }
        }
        count = next;
        return rtn;
    }
    
    void start(){
        while(nextFrame()){}    
        audioreader.finish();
    }
    
}
