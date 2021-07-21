import com.google.gson.annotations.SerializedName;

public class Habitacio {

    @SerializedName("id")
    private int id;
    @SerializedName("room_name")
    private String room_name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomName() {
        return room_name;
    }

    public void setRoomName(String roomName) {
        this.room_name = roomName;
    }
}
