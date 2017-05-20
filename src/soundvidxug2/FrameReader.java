package soundvidxug2;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.event.IVideoPictureEvent;

public class FrameReader extends MediaToolAdapter{
    
    long count = 0;
    long next = 0;
    long timestamp = 0;
    IMediaReader reader;
    AudioReader audioreader;
    
    FrameReader(IMediaReader reader){
        
        this.reader = reader;
        
    }
    
    void setAudioReader(AudioReader in){
        audioreader = in;
    }
    
    @Override
    public void onVideoPicture(IVideoPictureEvent event){
           next++;
           timestamp = event.getTimeStamp();
                  
           audioreader.setFreq( 100.0+Math.random()*100 );
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
