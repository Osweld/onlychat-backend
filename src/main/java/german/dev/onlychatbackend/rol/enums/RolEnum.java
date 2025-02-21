package german.dev.onlychatbackend.rol.enums;

public enum RolEnum {
    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");

    private final int id;
    private final String role;

    RolEnum(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public static RolEnum fromId(int id) {
        for (RolEnum rol : values()) {
            if (rol.getId() == id) {
                return rol;
            }
        }
        throw new IllegalArgumentException("No RolEnum found for id: " + id);
    }

    @Override
    public String toString() {
        return this.role;
    }
}
