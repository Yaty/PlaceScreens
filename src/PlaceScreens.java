
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.stream.Stream;
import javax.media.MediaLocator;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class PlaceScreens {
  
    public static void startShooting(int rate, int time, String url, String folderPath) {
        // first we create the folder
        new File(folderPath).mkdir();
        
        // then we fill it
        int counter = 0;
        long start = System.currentTimeMillis();
        while(System.currentTimeMillis() - start < time * 1000) {
            try {
                InputStream in = new URL(url).openStream();
                Files.copy(in, Paths.get(folderPath + "/" + counter++ + ".jpg"));
                Thread.sleep(rate);
            } catch (IOException | InterruptedException ex) {
                System.out.println("Ohhhh .... something failed : " + ex.getMessage());
            }
        }
        
        System.out.println("The shooting is over :)");
    }
    
    @Deprecated
    public static void makeVideos(String imgsPath, String fileName, int frameRates) {
        // Getting images
        Vector<String> imgLst = new Vector<>();
        try(Stream<Path> paths = Files.walk(Paths.get(imgsPath))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    imgLst.add(filePath.toString());
                }
            });
        } catch (IOException ex) { 
            System.out.println("Failed while loading images : " + ex.getMessage());
        }

        JpegImagesToMovie imageToMovie = new JpegImagesToMovie();
        MediaLocator oml;
        if ((oml = JpegImagesToMovie.createMediaLocator(fileName)) == null) {
            System.err.println("Cannot build media locator from: " + fileName);
            System.exit(0);
        }
        try {
            imageToMovie.doIt(1000, 1000, frameRates, imgLst, oml);
        } catch (MalformedURLException ex) {
            System.out.println("Video making failed : " + ex.getMessage());
        }
        
        System.out.println("Video created !");
    }
    
    public static void main(String[] args) {
        /*Format formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date now = new Date();
        String folderPath = "screens/" + formatter.format(now);
        PlaceScreens.startShooting(60000, 3600, "https://abra.me/place-snaps/recent.png", folderPath);
        PlaceScreens.makeVideos(folderPath, "video.mp4", 5);*/
        PlaceScreens.makeVideos("./screens/2017-04-01-17-13-28", "./screens/2017-04-01-17-13-28/video.mp4", 5);
    }
}
