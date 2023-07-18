package escaperoom;

import escaperoom.room.EscapeRoom;
import escaperoom.room.Review;
import escaperoom.team.Team;
import exceptions.PlatformCapacityExceededException;
import exceptions.RoomAlreadyExistsException;
import exceptions.RoomNotFoundException;
import exceptions.TeamNotFoundException;

public class EscapeRoomPlatform implements EscapeRoomAdminAPI,EscapeRoomPortalAPI{

    private Team[] teams;
    private final int maxCapacity;
    private EscapeRoom[] rooms;
    private int roomsCount=0;

    public EscapeRoomPlatform(Team[] teams, int maxCapacity) {
        this.teams = teams;
        this.maxCapacity = maxCapacity;
        rooms=new EscapeRoom[maxCapacity];
    }

    @Override
    public void addEscapeRoom(EscapeRoom room) throws RoomAlreadyExistsException {
        if (room==null)
            throw new IllegalArgumentException("Invalid room");
        if (roomsCount==maxCapacity)
            throw new PlatformCapacityExceededException("Max capacity exceided");
        for (EscapeRoom current:rooms)
        {
            if (current!=null&&current.equals(room))
                throw new RoomAlreadyExistsException("Room already exists");
        }
        int placeToReplace=0;
        while (rooms[placeToReplace]!=null)
            placeToReplace++;

        rooms[placeToReplace]=room;
        roomsCount++;
    }

    @Override
    public void removeEscapeRoom(String roomName) throws RoomNotFoundException {
        if (roomName==null|| roomName.isEmpty() || roomName.isBlank())
            throw new IllegalArgumentException("Invalid name");

        for (int i = 0; i < roomsCount; i++) {
            if (rooms[i]!=null&&rooms[i].getName().equals(roomName)){
                rooms[i]=rooms[roomsCount-1];
                rooms[roomsCount-1]=null;
                roomsCount--;
                return;
            }
        }
        throw new RoomNotFoundException("Room not found");
    }

    @Override
    public EscapeRoom[] getAllEscapeRooms() {
        EscapeRoom[] result=new EscapeRoom[roomsCount];
        int current=0;
        for (int i = 0; i < roomsCount; i++) {
            result[current++]=rooms[i];
        }
        return result;
    }

    private Team getTeamByName(String teamName) throws TeamNotFoundException {
        if (teamName==null||teamName.isBlank()||teamName.isEmpty())
            throw new IllegalArgumentException("Invalid team name");

        for (Team team:teams) {
            if (team!=null && team.getName().equals(teamName))
                return team;
        }
        throw new TeamNotFoundException("Team not found");
    }
    @Override
    public void registerAchievement(String roomName, String teamName, int escapeTime) throws RoomNotFoundException, TeamNotFoundException {
        EscapeRoom room=getEscapeRoomByName(roomName);
        if (escapeTime<=0||escapeTime>room.getMaxTimeToEscape())
            throw new IllegalArgumentException("Invalid escape time");
        Team team=getTeamByName(teamName);

        int bonus = 0;
        double escapeSpeed = escapeTime / (double) room.getMaxTimeToEscape();
        if (escapeSpeed <= 0.5) {
            bonus = 2;
        } else if (escapeSpeed <= 0.75) {
            bonus = 1;
        }

        team.updateRating(room.getDifficulty().getRank()+bonus);

    }

    @Override
    public EscapeRoom getEscapeRoomByName(String roomName) throws RoomNotFoundException {
        if (roomName==null||roomName.isBlank()||roomName.isEmpty())
            throw new IllegalArgumentException("Invalid room name");

        for (int i=0;i<roomsCount;i++) {
            if (rooms[i] != null && rooms[i].getName().equals(roomName))
                return rooms[i];
        }
        throw new RoomNotFoundException("Room not found");
    }

    @Override
    public void reviewEscapeRoom(String roomName, Review review) throws RoomNotFoundException {
        if (review==null)
            throw new IllegalArgumentException("Invalid review");
        EscapeRoom room=getEscapeRoomByName(roomName);
        room.addReview(review);
    }

    @Override
    public Review[] getReviews(String roomName) throws RoomNotFoundException {
        EscapeRoom room=getEscapeRoomByName(roomName);
        return room.getReviews();
    }

    @Override
    public Team getTopTeamByRating() {
        Team result=null;
        double bestScore=0.0;

        for(Team team:teams){
            if (team!=null&&team.getRating()>bestScore){
                result=team;
                bestScore=team.getRating();
            }
        }

        return result;
    }
}
