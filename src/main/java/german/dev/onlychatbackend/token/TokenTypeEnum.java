package german.dev.onlychatbackend.token;

public enum TokenTypeEnum {
    ACTIVATE_ACCOUNT(1, "ACTIVATE_ACCOUNT"),
    RESET_PASSWORD(2, "RESET_PASSWORD");

    private final int id;
    private final String description;

    TokenTypeEnum(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static TokenTypeEnum fromId(int id) {
        for (TokenTypeEnum tokenType : values()) {
            if (tokenType.getId() == id) {
                return tokenType;
            }
        }
        throw new IllegalArgumentException("No TokenTypeEnum found for id: " + id);
    }

    @Override
    public String toString() {
        return this.name();
    }

}
