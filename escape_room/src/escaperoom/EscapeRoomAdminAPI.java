package escaperoom;

import escaperoom.room.EscapeRoom;
import exceptions.PlatformCapacityExceededException;
import exceptions.RoomAlreadyExistsException;
import exceptions.RoomNotFoundException;
import exceptions.TeamNotFoundException;

public interface EscapeRoomAdminAPI {

    /**
     * Adds a new escape room to the platform.
     *
     * @param room the escape room to be added.
     * @throws IllegalArgumentException          if room is null.
     * @throws PlatformCapacityExceededException if the maximum number of escape rooms has already been reached.
     * @throws RoomAlreadyExistsException        if the specified room already exists in the platform.
     */
    void addEscapeRoom(EscapeRoom room) throws RoomAlreadyExistsException;

    /**
     * Removes the escape room with the specified name from the platform.
     *
     * @param roomName the name of the escape room to be removed.
     * @throws IllegalArgumentException if the room name is null, empty or blank.
     * @throws RoomNotFoundException    if the platform does not contain an escape room with the specified name.
     */
    void removeEscapeRoom(String roomName) throws RoomNotFoundException;

    /**
     * Returns all escape rooms contained in the platform.
     */
    EscapeRoom[] getAllEscapeRooms();

    /**
     * Registers a team achievement: escaping a room for the specified time.
     *
     * @param roomName   the name of the escape room.
     * @param teamName   the name of the team.
     * @param escapeTime the escape time in minutes.
     * @throws IllegalArgumentException if the room name or the team name is null, empty or blank,
     *                                  or if the escape time is negative, zero or bigger than the maximum time
     *                                  to escape for the specified room.
     * @throws RoomNotFoundException    if the platform does not contain an escape room with the specified name.
     */
    void registerAchievement(String roomName, String teamName, int escapeTime)
            throws RoomNotFoundException, TeamNotFoundException;

}
