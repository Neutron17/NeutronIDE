package neutron.io;

import java.io.*;

public class Writer {
    public static void bufferedWriter(String text, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            BufferedOutputStream bout = new BufferedOutputStream(fos);
            byte[] a = text.getBytes();
            bout.write(a);
            bout.flush();
            bout.close();
        } catch (IOException ex) {
            System.err.println("Error in bufferedWriter");
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            System.err.println("Path cannot be null");
            ex.printStackTrace();
        }
    }

    public static void withoutOverwrite(String text, String path) {
        File log = new File(path);
        try {
            if (!log.exists()) {
                log.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(log, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(text);
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("COULD NOT LOG!!");
        }
    }
}
