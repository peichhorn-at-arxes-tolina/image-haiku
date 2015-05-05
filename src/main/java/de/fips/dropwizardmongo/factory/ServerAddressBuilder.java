package de.fips.dropwizardmongo.factory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.ServerAddress;
import de.fips.common.lang.Exceptions;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ServerAddressBuilder {

    @NotNull
    private String host;

    @Min(1)
    @Max(65535)
    private int port;

    @JsonProperty
    public String getHost() {
        return host;
    }

    @JsonProperty
    public void setHost(String host) {
        this.host = host;
    }

    @JsonProperty
    public int getPort() {
        return port;
    }

    @JsonProperty
    public void setPort(int port) {
        this.port = port;
    }

    public ServerAddress build() {
        try {
            return new ServerAddress(host, port);
        } catch (Exception e) {
            throw Exceptions.sneakyThrow(e);
        }
    }
}
