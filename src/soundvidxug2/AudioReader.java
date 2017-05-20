package soundvidxug2;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import java.nio.ShortBuffer;

public class AudioReader extends MediaToolAdapter{
    
    
    IMediaReader reader;
    long timestamp = 0;
    FrameReader frameReader;
    double phase = 0;
    double freq = 200;
    double inc = (freq/(double)44100)* 6.28318;
    
    
    AudioReader(IMediaReader reader){
        
        this.reader = reader;
        
    }
    
    void setFrameReader(FrameReader in){
        frameReader = in;
    }
    
    @Override
    public void onAudioSamples(IAudioSamplesEvent event){
        timestamp = event.getTimeStamp();
        
        ShortBuffer buffer = event.getAudioSamples().getByteBuffer().asShortBuffer();
        for(int i = 0; i < buffer.limit(); i++){
            buffer.put(i, (short)(Math.sin(phase)*5000));
            phase += inc;
        }       
        
        super.onAudioSamples(event);
    }
    
    void setFreq(double freq){
        this.freq = freq;
        inc = (freq/(double)44100)* 6.28318;
    }
    
    @Override
    public void onVideoPicture(IVideoPictureEvent event){
        super.onVideoPicture(event);
    }
    
    
    void readPackets(long time){
        while(timestamp < time){
            reader.readPacket();
        }
    }
    
    void finish(){
        
        while(reader.readPacket() == null){
           // System.out.println("ok");
        }
        
    }

    
}
