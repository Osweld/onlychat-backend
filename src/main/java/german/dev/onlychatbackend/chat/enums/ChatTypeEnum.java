package german.dev.onlychatbackend.chat.enums;

public enum ChatTypeEnum {
    PERSONAL(1, "PERSONAL"),
    GROUP(2, "GRUUP");

    private final int id;
    private final String type;


    ChatTypeEnum(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }


    public static ChatTypeEnum fromId(int id) {
        for (ChatTypeEnum chatType : values()) {
            if (chatType.getId() == id) {
                return chatType;
            }
        }
        throw new IllegalArgumentException("No ChatTypeEnum found for id: " + id);
    }


    @Override
    public String toString() {
        return this.type;
    }

    

}
