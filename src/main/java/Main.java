import me.brennan.detector.Detector;

import java.io.File;

/**
 * @author Brennan
 * @since 12/8/2020
 **/
public class Main {

    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Invalid!");
            return;
        }
        final File file = new File(args[0]);

        if(!file.exists()) {
            System.out.println("File does not exist!");
            return;
        }

        new Detector(file);
    }

}
