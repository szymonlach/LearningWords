package pl.lach.spring.learningwords.crypting;

import org.springframework.stereotype.Component;
import pl.lach.spring.learningwords.profiles.ProductionProfile;

@Component
@ProductionProfile
public class ProductionCrypt implements Cryptable {

    private static final int shift = 3;

    @Override
    public String encrypt(String stringToCrypt) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < stringToCrypt.length(); i++) {
            char shiftedChar = (char) (stringToCrypt.charAt(i) + shift);
            stringBuilder.append(shiftedChar);
        }
        return stringBuilder.toString();
    }

    @Override
    public String decrypt(String stringToDecrypt) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < stringToDecrypt.length(); i++) {
            char shiftedChar = (char) (stringToDecrypt.charAt(i) - shift);
            stringBuilder.append(shiftedChar);
        }
        return stringBuilder.toString();
    }
}
