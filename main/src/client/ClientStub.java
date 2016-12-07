package client;

import client.api.client.Client;
import client.api.util.FileManagment;
import client.api.util.Logging;
import client.api.util.Request;
import client.api.util.enums.DirectoryName;
import client.api.util.enums.FileName;
import client.reflection.Reflection;
import client.updater.Updater;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Spencer on 11/5/2016.
 */
public class ClientStub extends JPanel implements AppletStub {

    private static final String JAV_CONFIG_REGEX = "\\'jagex\\-jav:\\/\\/(.*?)'";

    private HashMap<String, String> params = new HashMap<>();

    private boolean wait_for_client = true;
    private Applet applet;

    private boolean loadParamaters(final String HTML) {
        final Pattern PATTERN = Pattern.compile(JAV_CONFIG_REGEX);
        final Matcher MATCHER = PATTERN.matcher(HTML);

        if (MATCHER.find()) {
            final String JAV_CONFIG = MATCHER.group(1);
            Logging.debug("JAV_CONFIG = " + JAV_CONFIG);
            final String JAV_CONFIG_RESPONSE = Request.requestUrl("http://" + JAV_CONFIG);
            Logging.debug("JAV_CONFIG_RESPONSE = " + JAV_CONFIG_RESPONSE);

            for (String line : JAV_CONFIG_RESPONSE.split("\n")) {
                if (line.contains("=")) {
                    line = line.replaceAll("param=", "");
                    String[] parts = line.split("=");
                    if (parts.length == 1) {
                        params.put(parts[0], "");
                    } else if (parts.length == 2) {
                        params.put(parts[0], parts[1]);
                    } else if (parts.length == 3) {
                        params.put(parts[0], parts[1] + "=" + parts[2]);
                    } else if (parts.length == 4) {
                        params.put(parts[0], parts[1] + "=" + parts[2] + "=" + parts[3]);
                    }
                }
            }

            return true;
        }

        return false;
    }

    private boolean downloadGamepack(final String GAMEPACK_URL) {
        final File WORKING_DIRECTORY = FileManagment.getWorkingDirectory();
        if (WORKING_DIRECTORY == null)
            return false;

        final Path GAME_PACK_PATH = Paths.get(WORKING_DIRECTORY.getAbsolutePath(), DirectoryName.DEPENDANCIES.getDirectoryName());
        final File GAME_PACK = FileManagment.getFileInDirectory(GAME_PACK_PATH.toString(), FileName.GAMEPACK.getFileName());
        if (GAME_PACK == null)
            return Request.downloadFile(GAMEPACK_URL, GAME_PACK_PATH.toString(), FileName.GAMEPACK.getFileName());

        return true;
    }

    public ClientStub() {
        new Thread(() -> {
            if (ClientDirectory.needsDirectory())
                ClientDirectory.createDirectory();

            if (ClientDirectory.createSubDirectories())
                ClientDirectory.createSubDirectories();

            setSize(new Dimension(Main.getInstance().getWidth(), Main.getInstance().getHeight()));

            final String GAME_PAGE = Request.requestUrl("http://oldschool.runescape.com/game?world=370");

            if (loadParamaters(GAME_PAGE)) {
                final String GAMEPACK_URL = params.get("codebase") + params.get("initial_jar");
                if (downloadGamepack(GAMEPACK_URL)) {
                    try {
                        final File WORKING_DIRECTORY = FileManagment.getWorkingDirectory();
                        if (WORKING_DIRECTORY == null)
                            return;

                        Reflection.setLoader(Paths.get(WORKING_DIRECTORY.getAbsolutePath(), DirectoryName.DEPENDANCIES.getDirectoryName(), FileName.GAMEPACK.getFileName()).toString());

                        applet = (Applet) Reflection.loadClass("client").newInstance();
                        applet.setBounds(0, 0, Main.getInstance().getWidth(), Main.getInstance().getHeight());
                        applet.setPreferredSize(new Dimension(Main.getInstance().getWidth(), Main.getInstance().getHeight()));
                        applet.setStub(this);
                        applet.init();
                        applet.start();

                        this.add(applet, BorderLayout.CENTER);
                        this.revalidate();

                        while (applet.getComponents().length == 0) {
                            Logging.debug("No applet components loaded.");
                            Thread.sleep(1000);
                        }

                        Logging.debug(applet.getComponents().length + " loaded.");

                        wait_for_client = false;

                        Reflection.init();

                        final RSCanvas canvas = new RSCanvas(Client.getCanvas());

                        Client.setCanvas(canvas);
                        Main.getInstance().setCanvas(canvas);

                    } catch (IllegalAccessException | InterruptedException | InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public URL getDocumentBase() {
        return getCodeBase();
    }

    @Override
    public URL getCodeBase() {
        try {
            return new URL(params.get("codebase"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getParameter(String name) {
        return params.get(name);
    }

    @Override
    public AppletContext getAppletContext() {
        return null;
    }

    @Override
    public void appletResize(int width, int height) {

    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (wait_for_client) {
            final Graphics2D graphics2D = (Graphics2D) graphics;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graphics2D.setColor(Color.BLACK);
            graphics2D.fillRect(0, 0, getWidth(), getHeight());

            graphics.setColor(Color.GREEN.darker());
            graphics.setFont(new Font("Verdana", 0, 30));
            graphics2D.drawString("DankBot is loading!", 240, 250);

            repaint(600);
        }
    }
}