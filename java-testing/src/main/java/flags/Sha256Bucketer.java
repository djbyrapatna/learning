package flags;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException; 
import java.nio.charset.StandardCharsets;
import java.nio.ByteBuffer;

public class Sha256Bucketer implements Bucketer{

    @Override
    public int bucket(String experimentKey, String userId, String salt, int modulo){
        String toHash = experimentKey + userId + salt;
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
            int hashInt = ByteBuffer.wrap(hashBytes).getInt() & 0x7FFFFFFF; // Convert to positive integer
            return hashInt % modulo;
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException("SHA-256 algorithm not found");
        }
    }
}
