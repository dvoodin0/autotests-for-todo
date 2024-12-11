package todo.apitest.helper;

import io.restassured.authentication.PreemptiveBasicAuthScheme;
import org.aeonbits.owner.ConfigFactory;
import todo.apitest.config.ApiConfig;

import static todo.apitest.config.EncryptionUtil.decodeToBase64;


public class AuthSchemeConfiguration {

    private static ApiConfig authConfig = ConfigFactory.create(ApiConfig.class);

    public static PreemptiveBasicAuthScheme getBasicAuthScheme() {
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName(authConfig.getUsername());
        authScheme.setPassword(decodeToBase64(authConfig.getPassword()));
        return authScheme;

    }


}
