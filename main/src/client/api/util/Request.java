package client.api.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.Map;

public class Request {

    private static final int BUFFER_SIZE = 4096;

    /**
     * Downloads the specified file from the specified URL. Saves the file in the specified directory.
     *
     * @param url_name       The URL to download the file from.
     * @param save_directory The directory to save the downloaded file.
     * @param file_name The name to save the file as.
     * @return True if the file was successfully downloaded; false otherwise.
     */
    public static boolean downloadFile(String url_name, String save_directory, String file_name) {
        try {
            final URL URL = new URL(url_name);
            final HttpURLConnection CONNECTION = (HttpURLConnection) URL.openConnection();
            final int RESPONSE = CONNECTION.getResponseCode();

            if (RESPONSE == HttpURLConnection.HTTP_OK) {
                final InputStream INPUT_STREAM = CONNECTION.getInputStream();
                final FileOutputStream OUTPUT_STREAM = new FileOutputStream(Paths.get(save_directory, file_name).toFile());

                int bytes_read;
                final byte[] BUFFER = new byte[BUFFER_SIZE];
                while ((bytes_read = INPUT_STREAM.read(BUFFER)) != -1) {
                    OUTPUT_STREAM.write(BUFFER, 0, bytes_read);
                }

                OUTPUT_STREAM.close();
                INPUT_STREAM.close();
            }

            CONNECTION.disconnect();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns a String that contains the data response to the requested page
     *
     * @param url_name Web URL
     * @return a String with the data response.
     */
    public static String requestUrl(String url_name) {
        try {
            URL url = new URL(url_name);
            final URLConnection CONNECTION = url.openConnection();
            CONNECTION.setRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729");

            final BufferedReader READER = new BufferedReader(new InputStreamReader(CONNECTION.getInputStream()));
            String input_line;
            final StringBuilder OUTPUT = new StringBuilder();

            while ((input_line = READER.readLine()) != null)
                OUTPUT.append(input_line + "\n");

            READER.close();

            return OUTPUT.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Sends a post request to the specified url with the hash map.
     * Converts the data from the hash map to a byte[] before sending it.
     *
     * @param url_name The url to send the post request to.
     * @param hash_map The hash map containing the parameters.
     * @return True if the data was sent successfully; false otherwise.
     */
    public static boolean sendPostRequest(String url_name, Map<String, Object> hash_map) {
        try {
            final StringBuilder POST_DATA = new StringBuilder();
            for (Map.Entry<String, Object> param : hash_map.entrySet()) {
                if (POST_DATA.length() != 0)
                    POST_DATA.append('&');

                POST_DATA.append('&');
                POST_DATA.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                POST_DATA.append('=');
                POST_DATA.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }

            final byte[] POST_DATA_BYTES = POST_DATA.toString().getBytes("UTF-8");

            final URL URL = new URL(url_name);
            final HttpURLConnection CONNECTION = (HttpURLConnection) URL.openConnection();
            CONNECTION.setRequestMethod("POST");
            CONNECTION.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            CONNECTION.setRequestProperty("Content-Length", String.valueOf(POST_DATA_BYTES.length));
            CONNECTION.setDoOutput(true);
            CONNECTION.getOutputStream().write(POST_DATA_BYTES);
            final Reader READER = new BufferedReader(new InputStreamReader(CONNECTION.getInputStream(), "UTF-8"));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}