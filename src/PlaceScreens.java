
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class PlaceScreens {
  
    public static void startShooting(int rate, int time, String url) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date now = new Date();
        String folderPath = "screens/" + formatter.format(now);
        
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
    }
    
    public static void main(String[] args) {
        PlaceScreens.startShooting(60000, 3600, "https://abra.me/place-snaps/recent.png");
    }
}
