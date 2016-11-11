package client.api.oldschool.wrappers;

import client.api.client.*;
import client.api.oldschool.interfaces.Positionable;

/**
 * Created by Sphiinx on 11/6/2016.
 */
public class RSPlayer extends RSCharacter implements Positionable {

    private Player player;
    private RSPlayerDefinition definition;
    private Model model;

    public RSPlayer(Player player) {
        super(player);
        this.player = player;
    }

    /**
     * Gets the RSPlayer's combat level.
     *
     * @return The RSPlayer's combat level.
     * */
    public int getCombatLevel() {
        return player.getCombatLevel();
    }

    /**
     * Gets the skull state of the RSPlayer.
     *
     * @return 0 if the player is skulled; -1 otherwise.
     * */
    public int getSkullIcon() {
        return player.getSkullIcon();
    }


    //APIDOC
    public boolean isValid() {
        return player != null && player.isValid();
    }

    public int getOrientation() {
        return player.getOrientation();
    }

    public RSPlayerDefinition getDefinition() {
        return definition = definition != null && definition.isValid() ? definition : new RSPlayerDefinition(player.getDefinition());
    }

    public Model getModel() {
        /*if (model != null && model.isValid()) {
            RSTile animatedTile = getPosition().getAnimatedTile();
            model.updatePosition(animatedTile.getX(), animatedTile.getY(), animatedTile.getPlane(), getOrientation());
            return model;
        }*/

        RSPlayerDefinition def = getDefinition();
        if (!def.isValid()) return null;

        Cache cache = Client.getPlayerModelCache();
        HashTable table = cache.getHashTable();
        Object node;

        long modelId = def.getAnimatedModelId();
        while ((node = table.getNext()) != null) {
            Node n = new Node(node);
            if (n.getId() == modelId) {
                RSTile animatedTile = getPosition().getAnimatedTile();
                model = new Model(node, getOrientation(), animatedTile.getX(), animatedTile.getY(), animatedTile.getPlane());
                break;
            }
        }

        return model;
    }

}

