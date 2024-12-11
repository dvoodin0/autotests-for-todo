package todo.apitest.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:${envName}.properties",// Loads configuration from a file whose name depends on the envName variable
        "classpath:local.properties", // If the first file is not found, loads configuration from local.properties
        "system:properties",  // If no configuration is found, uses Java system properties (e.g., -Dkey=value)
        "system:env" //If no data is found in system properties, uses environment variables from the operating system
})
public interface ApiConfig extends Config {
    @Key("url")
    String getUrl();

    @Key("socketUrl")
    String getSocketUrl();

    @Key("username")
    String getUsername();

    @Key("password")
    String getPassword();
}
