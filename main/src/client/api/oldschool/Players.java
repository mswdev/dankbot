package client.api.oldschool;

import client.api.client.Client;
import client.api.oldschool.wrappers.RSPlayer;

import java.util.stream.Stream;

/**
 * Created by Spencer on 11/6/2016.
 */
public class Players {

    /**
     * Gets your RSPlayer.
     *
     * @return Returns your RSPlayer.
     */
    public static RSPlayer getLocalPlayer() {
        return new RSPlayer(Client.getLocalPlayer());
    }

    /**
     * Gets all of the RSPlayer's in the area.
     *
     * @return An RSPlayer array of all the RSPlayer's in the area.
     */
    public static RSPlayer[] getAll() {
        return Stream.of(Client.getPlayers()).map(RSPlayer::new).toArray(RSPlayer[]::new);
    }

}
