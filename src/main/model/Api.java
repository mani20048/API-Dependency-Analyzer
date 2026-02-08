package model;

public class Api {

    private String name;
    private String endpoint;
    private int serviceId;

    public Api() {}

    public String getName() {
        return name;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
