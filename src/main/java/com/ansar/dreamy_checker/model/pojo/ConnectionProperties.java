package com.ansar.dreamy_checker.model.pojo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConnectionProperties {

    private String host;
    private int port;
    private String databaseName;
    private String userName;
    private String password;

}
