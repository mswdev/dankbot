package client.api.oldschool.wrappers;

import client.api.client.Character;
import client.api.client.Client;
import client.api.oldschool.interfaces.Positionable;
import client.api.oldschool.interfaces.Validatable;

/**
 * Created by Sphiinx on 11/6/2016.
 */
public class RSCharacter implements Positionable, Validatable {

    private Character character;

    public RSCharacter(Character character) {
        this.character = character;
    }

    /**
     * Gets the RSCharacters animation.
     *
     * @return The RSCharacters animation.
     */
    public int getAnmiation() {
        return character.getAnimation();
    }

    public int getCombatTime() {
        return character.getCombatTime();
    }

    public int getFrameOne() {
        return character.getFrameOne();
    }

    public int getFrameTwo() {
        return character.getFrameTwo();
    }

    public Object getHitCycles() {
        return character.getHitCycles();
    }

    public Object getHitDamages() {
        return character.getHitDamages();
    }

    public Object getHitTypes() {
        return character.getHitTypes();
    }

    public Object getHealthBars() {
        return character.getHealthBars();
    }

    public int getInteractingIndex() {
        return character.getInteractingIndex();
    }

    public int getLocalX() {
        return character.getX();
    }

    public int getLocalY() {
        return character.getY();
    }

    public String getMessage() {
        return character.getMessage();
    }

    public int getOrientation() {
        return character.getOrientation();
    }

    public int getQueueSize() {
        return character.getQueueSize();
    }

    public Object getQueueTraversed() {
        return character.getQueueTraversed();
    }

    public int[] getQueueX() {
        return character.getQueueX();
    }

    public int[] getQueueY() {
        return character.getQueueY();
    }

    public int getRuntimeAnimation() {
        return character.getRuntimeAnimation();
    }

    public int getStandAnimation() {
        return character.getStandAnimation();
    }

    /**
     * Gets the RSCharacter's position.
     *
     * @return The RSTile containing the RSCharacter's position.
     */
    @Override
    public RSTile getPosition() {
        return new RSTile(getLocalX() + Client.getBaseX(), getLocalY() + Client.getBaseY());
    }

    /**
     * Checks if the RSCharacter is valid.
     *
     * @return True if the RSCharacter is valid; false otherwise.
     */
    public boolean isValid() {
        return character != null && character.isValid();
    }

}

