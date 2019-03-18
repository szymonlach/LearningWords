package pl.lach.spring.learningwords.crypting;

public interface Cryptable {

    String encrypt(String stringToCrypt);

    String decrypt(String stringToDecrypt);
}
