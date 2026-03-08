package lib.validator.model;

public enum HashStatus {
    MATCH("Tak"),
    MISMATCH("Nie"),
    MISSING("Brak");

    private final String status;

    HashStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
