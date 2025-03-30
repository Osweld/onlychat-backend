package german.dev.onlychatbackend.user.enums;

public enum UserStatusEnum {
    ACTIVE(1, "ACTIVE"),
    INACTIVE(2, "INACTIVE"),
    BLOCKED(3, "BLOCKED"),
    DELETED(4, "DELETED");

    private final int id;
    private final String description;

    UserStatusEnum(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static UserStatusEnum fromId(int id) {
        for (UserStatusEnum status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("No UserStatusEnum found for id: " + id);
    }

    @Override
    public String toString() {
        return this.name();
    }
}
