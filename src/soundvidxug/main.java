package soundvidxug;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import java.io.File;

public class main {


    public static void main(String[] args) {
  
    File inputFile = new File(args[0]);
    File outputFile = new File(args[1]);
        
    IMediaReader audioreader = ToolFactory.makeReader(inputFile.toString());
    IMediaReader indexreader = ToolFactory.makeReader(inputFile.toString());
    
    
    FrameReader frameReader = new FrameReader(indexreader);
    AudioReader audioReader = new AudioReader(audioreader);
    frameReader.setAudioReader(audioReader);
    audioReader.setFrameReader(frameReader);
    
    audioreader.addListener(audioReader);
    IMediaWriter writer = ToolFactory.makeWriter(outputFile.toString(), audioreader); 
    audioReader.addListener(writer);
     
     indexreader.addListener(frameReader);
     frameReader.start();
            
    }
    
}
