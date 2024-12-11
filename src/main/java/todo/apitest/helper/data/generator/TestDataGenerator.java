package todo.apitest.helper.data.generator;


import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class TestDataGenerator {

    public static String createRandomString(int targetStringLength) {
        int leftLimit = 97; // letter a
        int rightLimit = 122; // letter z
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static BigInteger createNewToDoId() {
        return new BigInteger(64, new SecureRandom());
    }
}
