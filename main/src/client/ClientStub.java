package client;

import client.api.client.Client;
import client.api.util.Logging;
import client.api.util.Request;
import client.reflection.Reflection;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Spencer on 11/5/2016.
 */
public class ClientStub extends JPanel implements AppletStub {

    private static final String JAR_FILE = "gamepack.jar";
    private static final String JAV_CONFIG_REGEX = "\\'jagex\\-jav:\\/\\/(.*?)'";

    private HashMap<String, String> params = new HashMap<>();

    private boolean wait_for_client = true;
    private Applet applet;

    public ClientStub() {
        setSize(new Dimension(Main.getInstance().getWidth(), Main.getInstance().getHeight()));

        final String GAME_PAGE = Request.requestUrl("http://oldschool.runescape.com/game?world=370");
        final Pattern PATTERN = Pattern.compile(JAV_CONFIG_REGEX);
        final Matcher MATCHER = PATTERN.matcher(GAME_PAGE);

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

            if (params.containsKey("codebase") && params.containsKey("initial_jar")) {
                final String GAMEPACK_URL = params.get("codebase") + params.get("initial_jar");
                Logging.debug("GAMEPACK_URL = " + GAMEPACK_URL);

                try {
                    if (!Files.exists(Paths.get(JAR_FILE))) {
                        if (!Request.downloadFile(GAMEPACK_URL, JAR_FILE))
                            return;
                    }

                    if (Files.exists(Paths.get(JAR_FILE))) {

                        Reflection.setLoader(JAR_FILE);

                        applet = Client.getInstance();
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

                        wait_for_client = false;


                        final RSCanvas canvas = new RSCanvas(Client.getCanvas());

                        Client.setCanvas(canvas);
                        Main.getInstance().setCanvas(canvas);

                        Logging.debug(applet.getComponents().length + " loaded.");

                    }
                } catch (InterruptedException e) {
                    Logging.error(e.getMessage());
                }
            }
        }
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
            graphics2D.drawString("DankBot is loading!", 285, 480);

            repaint(600);
        }
    }
}