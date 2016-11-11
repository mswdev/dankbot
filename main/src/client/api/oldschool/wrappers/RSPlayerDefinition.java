package client.api.oldschool.wrappers;

import client.api.client.PlayerDefinition;

/**
 * Created by Spencer on 11/9/2016.
 */
public class RSPlayerDefinition {

    private PlayerDefinition definition;

    public RSPlayerDefinition(PlayerDefinition definition) {
        this.definition = definition;
    }

    public long getAnimatedModelId() {
        return definition.getAnimatedModelId();
    }

    public long getStaticModelId() {
        return definition.getStaticModelId();
    }

    public boolean isValid() {
        return definition.isValid();
    }

}
