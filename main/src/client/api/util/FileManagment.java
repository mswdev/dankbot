package client.api.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sphiinx on 11/5/2016.
 */
public class FileManagment {

    /**
     * The directory location of DankBot.
     * */
    public static String DIRECTORY_LOCATION = System.getProperty("user.home");

    /**
     * The directory name of DankBot.
     * */
    public static String DIRECTORY_NAME = ".dankbot";

    /**
     * Creates a directory at the specified path with the specified name.
     *
     * @param path The path to create the directory.
     * @param name The name to name the directory.
     * @return True if successful; false otherwise.
     */
    public static boolean createDirectory(String path, String name) {
        final File directory = new File(path, name);
        return directory.mkdir();
    }

    /**
     * Creates a file at the specified path with the specified name and extension.
     *
     * @param path      The path to create the file.
     * @param file_name The name to name the file.
     * @param extension The extension of the file.
     * @return True if successful; false otherwise.
     */
    public static boolean createFile(String path, String file_name, String extension) {
        final File file = new File(path, file_name + extension);
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Gets the specified file name is the specified directory.
     *
     * @param file_name The name of the file to get from the directory.
     * @param directory The directory to get the file from.
     * @return The specified file if found; null otherwise.
     */
    public static File getFileInDirectory(String directory, String file_name) {
        final File file = new File(directory);
        final File[] matching_files = file.listFiles((dir, name) -> name.equals(file_name));
        return matching_files.length > 0 ? matching_files[0] : null;
    }

    /**
     * Gets all of the Files in the specified directory.
     *
     * @param directory The directory to get the files from.
     * @return A File array if any files are present; null otherwise.
     */
    public static File[] getFilesInDirectory(String directory) {
        final File file = new File(directory);
        final File[] files = file.listFiles();
        if (files == null)
            return null;

        return files;
    }

    /**
     * Gets all of the Files in the specified directory.
     *
     * @param directory The directory to get the files from.
     * @return A File array if any files are present; null otherwise.
     */
    public static String[] getFileNamesInDirectory(String directory) {
        final List<String> file_names = new ArrayList<>();
        final File[] files = getFilesInDirectory(directory);
        if (files == null)
            return null;

        for (File file : files) {
            if (file == null)
                continue;

            file_names.add(file.getName());
        }

        return file_names.stream().toArray(String[]::new);
    }

    /**
     * Gets the working directory of the client.
     *
     * @return The working directory of the client.
     */
    public static File getWorkingDirectory() {
        final File WORKING_DIRECTORY = getFileInDirectory(DIRECTORY_LOCATION, DIRECTORY_NAME);
        if (WORKING_DIRECTORY == null)
            return null;

        return WORKING_DIRECTORY;
    }

}

