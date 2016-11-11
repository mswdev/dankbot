package client.api.oldschool.wrappers;

import client.api.client.Character;
import client.api.client.Client;
import client.api.oldschool.interfaces.Positionable;

/**
 * Created by Sphiinx on 11/6/2016.
 */
public class RSCharacter implements Positionable {

    private Character character;

    public RSCharacter(Character character) {
        this.character = character;
    }

    /**
     * Gets the RSCharacter's animation.
     *
     * @return The RSCharacter's animation.
     */
    public int getAnmiation() {
        return character.getAnimiation();
    }

    /**
     * Gets the RSCharacter's position.
     *
     * @return The RSTile containing the RSCharacter's position.
     */
    @Override
    public RSTile getPosition() {
        return new RSTile(character.getX() + Client.getBaseX(), character.getY() + Client.getBaseY());
    }

    /**
     * Checks if the RSCharacter is valid.
     *
     * @return True if the RSCharacter is valid; false otherwise.
     * */
    public boolean isValid() {
        return character.isValid();
    }

}

