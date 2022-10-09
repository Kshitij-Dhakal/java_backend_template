package np.com.kshitij.commons;

public enum Order {
    ASC("ASC", 1), DESC("DESC", 2);

    private final String name;
    private final int number;

    Order(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public static Order forName(String name) {
        for (Order value : values()) {
            if (value.getName().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return DESC;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }
}
