package pl.lach.spring.learningwords.crypting;

import org.springframework.stereotype.Component;
import pl.lach.spring.learningwords.profiles.DevProfle;

@Component
@DevProfle
public class DeveloperCrypt implements Cryptable {
    @Override
    public String encrypt(String stringToCrypt) {
        return stringToCrypt;
    }

    @Override
    public String decrypt(String stringToDecrypt) {
        return stringToDecrypt;
    }
}
