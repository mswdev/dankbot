package client.api.util.enums;

/**
 * Created by Sphiinx on 11/11/2016.
 */
public enum DirectoryName {

    BIN("bin"),
    DEPENDANCIES("dependencies"),
    SETTINGS("settings");

    private final String DIRECTORY_NAME;

    DirectoryName(String directory_name) {
        this.DIRECTORY_NAME = directory_name;
    }

    public String getDirectoryName() {
        return DIRECTORY_NAME;
    }

}

