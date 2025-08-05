package au.edu.rmit.sept.webapp.models;

public class Resources {

    private String resourceId;
    private String resourceName;
    private String resourceType;
    private String resourceDescription;

    // Default constructor
    public Resources() {
    }

    // Parameterized constructor
    public Resources(String resourceId, String resourceName, String resourceType, String resourceDescription) {
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.resourceDescription = resourceDescription;
    }

    // Getters and Setters
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }
}
