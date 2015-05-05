package de.fips.dropwizardmongo.factory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MongoFactory {

    @NotEmpty
    private List<ServerAddressBuilder> connections = new ArrayList<>();

    @JsonProperty
    public List<ServerAddressBuilder> getConnections() {
        return connections;
    }

    @JsonProperty
    public void setConnections(List<ServerAddressBuilder> connections) {
        this.connections = connections;
    }

    public MongoClient buildClient() {
        return new MongoClient(buildServerAddresses(getConnections()));
    }

    private List<ServerAddress> buildServerAddresses(List<ServerAddressBuilder> connections) {
        return connections.stream().map(ServerAddressBuilder::build).collect(Collectors.toList());
    }
}
