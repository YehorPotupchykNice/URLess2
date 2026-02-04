package generator;

import exceptions.FailedToCreateURLException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Random;
import java.util.Set;

public class SHA1Generator implements IdGenerator{
    @Override
    public String generate(String url, Set<String> collisions) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (Exception ex) {
            throw new FailedToCreateURLException(url);
        }

        byte[] urlBytes = url.getBytes(StandardCharsets.UTF_8);
        var random = new Random();
        var collision = false;
        do {
            if (collision) {
                int rndByte = random.nextInt(urlBytes.length);
                urlBytes[rndByte] = (byte) random.nextInt();
            }
            byte[] hash = md.digest(urlBytes);
            var id = Base64.getUrlEncoder().encodeToString(hash).substring(0, 6);
            if (collisions.contains(id)) {
                collision = true;
            } else {
                return id;
            }
        } while (collision);

        throw new FailedToCreateURLException(url);
    }
}
