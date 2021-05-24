package utilities;

public enum Column {
    NAME("Name"),
    NUMBER_OF_CASES("Number of cases"),
    AVERAGE_IMPACT_SCORE("Average impact score"),
    COMPLEXITY("Complexity");

    private String name;

    Column(String name) {
        this.name = name;
    }

    public static Column getColumn(String name) {
        for (Column column : values()) {
            if (column.name.equalsIgnoreCase(name)) {
                return column;
            }
        }
        throw new IllegalArgumentException(name);
    }
}
