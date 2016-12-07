package client.api.oldschool.wrappers;

import client.api.client.*;
import client.api.oldschool.interfaces.Positionable;
import client.api.oldschool.interfaces.Validatable;

/**
 * Created by Sphiinx on 11/6/2016.
 */
public class RSPlayer extends RSCharacter implements Positionable, Validatable {

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
     */
    public int getCombatLevel() {
        if (!isValid())
            return -1;

        return player.getCombatLevel();
    }

    /**
     * Gets the skull state of the RSPlayer.
     *
     * @return 0 if the RSPlayer is skulled; -1 otherwise.
     */
    public int getSkullIcon() {
        if (!isValid())
            return -1;

        return player.getSkullIcon();
    }

    /**
     * Checks if the RSPlayer is valid.
     *
     * @return True if the RSPlayer is valid; false otherwise.
     */
    public boolean isValid() {
        return player != null && player.isValid();
    }

    /**
     * Gets the orientation of the RSPlayer.
     *
     * @return The orientation of the RSPlayer.
     */
    public int getOrientation() {
        if (!isValid())
            return -1;

        return player.getOrientation();
    }

    /**
     * Gets the RSPlayerDefinition of the RSPlayer.
     *
     * @return The RSPlayerDefinition.
     */
    public RSPlayerDefinition getDefinition() {
        if (!isValid())
            return null;

        return definition = definition != null && definition.isValid() ? definition : new RSPlayerDefinition(player.getDefinition());
    }

    /**
     * Gets the model of the RSPlayer.
     *
     * @return The model of the RSPlayer.
     */
    public Model getModel() {
        if (!isValid())
            return null;

        final RSPlayerDefinition DEFINITION = getDefinition();
        if (!DEFINITION.isValid())
            return null;

        final Cache CACHE = Client.getPlayerModelCache();
        final HashTable TABLE = CACHE.getHashTable();
        final long MODEL_ID = DEFINITION.getAnimatedModelId();

        Object node;
        while ((node = TABLE.getNext()) != null) {
            final Node NODE = new Node(node);
            if (NODE.getId() != MODEL_ID)
                continue;

            final RSTile ANIMATED_TILE = getPosition().getAnimatedTile();
            model = new Model(node, getOrientation(), ANIMATED_TILE.getX(), ANIMATED_TILE.getY(), ANIMATED_TILE.getPlane());
            break;
        }

        return model;
    }

}

