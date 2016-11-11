package client.api.oldschool.wrappers;

import client.api.client.NPC;
import client.api.client.NPCDefinition;
import client.api.oldschool.interfaces.Positionable;

/**
 * Created by Sphiinx on 11/7/2016.
 */
public class RSNPC extends RSCharacter implements Positionable {

    private NPC npc;

    public RSNPC(NPC npc) {
        super(npc);
        this.npc = npc;
    }

    /**
     * Gets the definition of the RSNPC.
     *
     * @return The definition of the RSNPC.
     */
    public NPCDefinition getDefinition() {
        return npc.getDefinition();
    }

    /**
     * Gets the animation ID of the RSNPC.
     *
     * @return The animation ID of the RSNPC.
     */
    public int getAnimation() {
        return npc.getAnimiation();
    }

    /**
     * Checks if the RSNPC is valid.
     *
     * @return True if the RSNPC is valid; false otherwise.
     */
    public boolean isValid() {
        return npc.isValid();
    }

    /**
     * Gets the interacting index of the RSNPC.
     *
     * @return The interacting index of the RSNPC.
     * */
    public int getInteractingIndex() {
        return npc.getInteractingIndex();
    }

    /**
     * Gets X position of the RSNPC.
     *
     * @return The X position of the RSNPC.
     * */
    public int getX() {
        return npc.getX();
    }

    /**
     * Gets the Y position of the RSNPC.
     *
     * @return The Y position of the RSNPC.
     * */
    public int getY() {
        return npc.getY();
    }

}

