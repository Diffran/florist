package florist.models;

public enum MaterialDecorationType {
    WOOD("Wood"),
    PLASTIC("Plastic");

    private final String TYPE;

    private MaterialDecorationType(String type) {
        TYPE = type;
    }

    public String getType() {
        return TYPE;
    }
}
