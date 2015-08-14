package be.sandervl.admin.services.upload;

/**
 * Created by sander on 12/08/2015.
 */
public abstract class TransferHost {
    private String name,host;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
