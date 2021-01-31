package neutron.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
    public static ArrayList<String> read(String path) {
        try {
            File file = new File(path);
            Scanner sc = new Scanner(file);
            ArrayList<String> lines = new ArrayList<String>();
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                lines.add(data);
            }
            sc.close();
            return lines;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
