package neutron.parser;

import lombok.Getter;
import lombok.Setter;
import neutron.io.Reader;
import neutron.io.Writer;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;

public class Parser {
    private @Getter @Setter File file;
    public Parser(File file) throws UnsupportedFileExtension,FileNotFoundException {
        if(!file.exists()) throw new FileNotFoundException("File: "+file+" does not exist");
        if(!FilenameUtils.getExtension(file.getName()).equals("json")) throw new UnsupportedFileExtension(file);
        this.file = file;
    }
    public Parser(String path) throws UnsupportedFileExtension,FileNotFoundException {
        File file = new File(path);
        if(!file.exists()) throw new FileNotFoundException("File: "+file+" does not exist");
        if(!FilenameUtils.getExtension(file.getName()).equals("json")) throw new UnsupportedFileExtension(file);
        this.file = file;
    }
    public void write(Object[] arr) {
        JSONObject jObject = new JSONObject();
        for(int i = 0;i < arr.length / 2;i+=2) {
            jObject.put((String) arr[i],arr[i+1]);
        }
        Writer.bufferedWriter(jObject.toJSONArray(),file);
    }
    public void read() {
        String value = Reader.read(file).toString();
        System.out.println(value);
    }
}
