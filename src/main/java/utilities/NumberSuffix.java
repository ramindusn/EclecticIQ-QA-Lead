package utilities;

public enum NumberSuffix {
    K(1000.0),
    M(1000000.0),
    B(1000000000.0);

    private double value;

    NumberSuffix(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
