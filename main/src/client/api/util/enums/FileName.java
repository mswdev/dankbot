package client.api.util.enums;

/**
 * Created by Sphiinx on 11/11/2016.
 */
public enum FileName {

    GAMEPACK("gamepack.jar");

    private final String FILE_NAME;

    FileName(String file_name) {
        this.FILE_NAME = file_name;
    }

    public String getFileName() {
        return FILE_NAME;
    }

}
