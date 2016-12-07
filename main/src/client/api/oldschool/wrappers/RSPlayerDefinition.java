package client.api.oldschool.wrappers;

import client.api.client.PlayerDefinition;
import client.api.oldschool.interfaces.Validatable;

/**
 * Created by Spencer on 11/9/2016.
 */
public class RSPlayerDefinition implements Validatable {

    private PlayerDefinition definition;

    public RSPlayerDefinition(PlayerDefinition definition) {
        this.definition = definition;
    }

    /**
     * Gets the animated model ID from the RSPlayerDefinition.
     *
     * @return The animated model ID.
     */
    public long getAnimatedModelId() {
        return definition.getAnimatedModelId();
    }

    /**
     * Gets an int array of the appearance from the RSPlayerDefinition.
     *
     * @return An int array containing the RSPlayerDefinition's appearance.
     */
    public int[] getAppearances() {
        return definition.getAppearance();
    }

    /**
     * Gets an int array the body colors from the RSPlayerDefinition.
     *
     * @return An int array containing the body colors from the RSPlayerDefinition.
     */
    public int[] getBodyColors() {
        return definition.getBodyColors();
    }

    /**
     * Checks if the RSPlayerDefinition is a female.
     *
     * @return True if the RSPlayerDefinition is a female; false otherwise.
     */
    public boolean isFemale() {
        return definition.isFemale();
    }

    /**
     * Gets the NPC ID of the RSPlayerDefinition.
     *
     * @return The NPC ID of the RSPlayerDefintion.
     */
    public int getNPCId() {
        return definition.getNPCId();
    }

    /**
     * Gets the static model ID of the RSPlayerDefinition.
     *
     * @return The static model ID of the RSPlayerDefinition.
     */
    public long getStaticModelId() {
        return definition.getStaticModelId();
    }

    /**
     * Checks if the RSPlayerDefinition is valid.
     *
     * @return True if the RSPlayerDefinition is valid; false otherwise.
     */
    public boolean isValid() {
        return definition != null && definition.isValid();
    }

}
