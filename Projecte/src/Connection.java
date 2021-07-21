import com.google.gson.annotations.SerializedName;

public class Connection {
    @SerializedName("id")
    private int id;
    @SerializedName("connection_name")
    private String connection_name;
    @SerializedName("room_connected")
    private int[] room_connected;
    @SerializedName("enemy_probability")
    private int enemy_probability;

    public Connection(int id, String connectionName, int[] roomConnected, int enemyProbability) {
        this.id = id;
        this.connection_name = connectionName;
        this.room_connected = new int[5];
        this.enemy_probability = enemy_probability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConnectionName() {
        return connection_name;
    }

    public void setConnectionName(String connectionName) {
        this.connection_name = connectionName;
    }

    public int[] getRoomConnected() {
        return room_connected;
    }

    public void setRoomConnected(int[] roomConnected) {
        this.room_connected = roomConnected;
    }

    public int getEnemyProbability() {
        return enemy_probability;
    }

    public void setEnemyProbability(int enemyProbability) {
        this.enemy_probability = enemyProbability;
    }
}
