package client;

import client.api.util.FileManagment;
import client.api.util.enums.DirectoryName;
import client.api.util.enums.FileName;
import client.updater.Updater;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by Spencer on 11/12/2016.
 */
public class UpdaterMain {

    public Updater updater;
    private static UpdaterMain instance;

    public UpdaterMain() {
        final File WORKING_DIRECTORY = FileManagment.getWorkingDirectory();
        if (WORKING_DIRECTORY == null)
            return;

        updater = new Updater(Paths.get(WORKING_DIRECTORY.getAbsolutePath(), DirectoryName.DEPENDANCIES.getDirectoryName(), FileName.GAMEPACK.getFileName()).toString());
    }

    public static void main(String... args) {
        instance = new UpdaterMain();
        instance.getUpdater().run();
    }

    public static Updater getUpdater() {
        return instance.updater;
    }
}
