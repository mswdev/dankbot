package client;

import client.api.util.FileManagment;
import client.api.util.enums.DirectoryName;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sphiinx on 11/11/2016.
 */
public class ClientDirectory {

    public static boolean needsDirectory() {
        return FileManagment.getWorkingDirectory() == null;
    }

    public static boolean createDirectory() {
        return FileManagment.createDirectory(FileManagment.DIRECTORY_LOCATION, FileManagment.DIRECTORY_NAME);
    }

    public static boolean needsSubDirectories() {
        final File WORKING_DIRECTORY = FileManagment.getWorkingDirectory();
        if (WORKING_DIRECTORY == null)
            return false;

        final String[] FILE_NAMES = FileManagment.getFileNamesInDirectory(WORKING_DIRECTORY.getAbsolutePath());
        for (DirectoryName directory_name : DirectoryName.values()) {
            if (Arrays.asList(FILE_NAMES).contains(directory_name.getDirectoryName()))
                continue;

            return true;
        }

        return false;
    }

    public static boolean createSubDirectories() {
        final File WORKING_DIRECTORY = FileManagment.getWorkingDirectory();
        if (WORKING_DIRECTORY == null)
            return false;

        List<String> DIRECTORIES_TO_CREATE = new ArrayList<>();
        final String[] FILE_NAMES = FileManagment.getFileNamesInDirectory(WORKING_DIRECTORY.getAbsolutePath());
        for (DirectoryName directory_name : DirectoryName.values()) {
            if (Arrays.asList(FILE_NAMES).contains(directory_name.getDirectoryName()))
                continue;

            DIRECTORIES_TO_CREATE.add(directory_name.getDirectoryName());
        }

        for (String directory_name : DIRECTORIES_TO_CREATE) {
            FileManagment.createDirectory(WORKING_DIRECTORY.getAbsolutePath(), directory_name);
        }

        return true;
    }

}

