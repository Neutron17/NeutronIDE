package neutron.parser;

import java.io.File;
import java.io.IOException;

public class UnsupportedFileExtension extends IOException {
    public UnsupportedFileExtension() {}
    public UnsupportedFileExtension(File file) {
        super("File "+file+" has unsupported file extension");
    }
    public UnsupportedFileExtension(String str) {
        super(str);
    }
}
