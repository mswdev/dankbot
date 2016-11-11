package client;

import javafx.scene.Group;
import javafx.scene.Scene;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Spencer on 11/6/2016.
 */
public class RSFrame extends JFrame {

    private ClientStub stub;
    //private JFXPanel JFX_PANEL;

    public RSFrame() {
        this.setTitle("DankBot v0.0.2");
        this.getContentPane().setLayout(null);
        this.setSize(new Dimension(Main.getInstance().getWidth() - 29, Main.getInstance().getHeight() - 69));
        this.setPreferredSize(new Dimension(Main.getInstance().getWidth() - 29, Main.getInstance().getHeight() - 69));

        stub = new ClientStub();
        stub.setBounds(0, -5, Main.getInstance().getWidth(), Main.getInstance().getHeight());

        //JFX_PANEL = new JFXPanel();
        //JFX_PANEL.setBounds(0, 0, client.Main.getInstance().getWidth(), client.Main.getInstance().getHeight());

        //Platform.runLater(() -> JFX_PANEL.setScene(createScene()));

        this.getContentPane().add(stub);
        //this.getContentPane().add(JFX_PANEL);
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
        this.show();
    }

    public ClientStub getStub() {
        return stub;
    }

    private Scene createScene() {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 30);

        javafx.scene.control.Button btn = new javafx.scene.control.Button("Start Dank Controls");
        btn.setLayoutX(2);
        btn.setLayoutY(2);
        root.getChildren().add(btn);

        return scene;
    }
}
