package uk.gov.bristol.send.model;

// Although this is in model package, it does not map to an entity in the database
// This class is a convenience class for anything we might store about the owner during runtime.
public class Owner {
    String ownerName;
    String ownerEmail;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail.toLowerCase();
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail.toLowerCase();
    }

}
