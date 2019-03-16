package pl.lach.spring.learningwords.controller;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.lach.spring.learningwords.FileService;
import pl.lach.spring.learningwords.model.*;
import pl.lach.spring.learningwords.repository.*;

@Controller
public class LinguController {
    private static final int UNDEFINED = -1;
    private static final int ADD_ENTRY = 0;
    private static final int TEST = 1;
    private static final int CLOSE_APP = 2;

    private EntryRepository entryRepository;
    private FileService fileService;
    private Scanner scanner;

    @Autowired
    public LinguController(EntryRepository entryRepository, FileService fileService, Scanner scanner) {
        this.entryRepository = entryRepository;
        this.fileService = fileService;
        this.scanner = scanner;
    }

    public void mainLoop() {
        System.out.println("Witaj w aplikacji LinguApp");
        int option = UNDEFINED;
        while (option != CLOSE_APP) {
            printMenu();
            option = chooseOption();
            executeOption(option);
        }
    }

    private void executeOption(int option) {
        switch (option) {
            case ADD_ENTRY:
                addEntry();
                break;
            case TEST:
                test();
                break;
            case CLOSE_APP:
                close();
                break;
            default:
                System.out.println("Opcja niezdefiniowana");
        }
    }

    private void test() {
        if (entryRepository.isEmpty()) {
            System.out.println("Dodaj przynajmniej jedną frazę do bazy.");
            return;
        }
        final int testSize = entryRepository.size() > 10 ? 10 : entryRepository.size();
        Set<Entry> randomEntries = entryRepository.getRandomEntries(testSize);
        int score = 0;
        for (Entry entry : randomEntries) {
            System.out.printf("Podaj tłumaczenie dla :\"%s\"\n", entry.getOriginal());
            String translation = scanner.nextLine();
            if (entry.getTranslation().equalsIgnoreCase(translation)) {
                System.out.println("Odpowiedź poprawna");
                score++;
            } else {
                System.out.println("Odpowiedź niepoprawna - " + entry.getTranslation());
            }
        }
        System.out.printf("Twój wynik: %d/%d\n", score, testSize);
    }

    private void addEntry() {
        System.out.println("Podaj oryginalną frazę");
        String original = scanner.nextLine();
        System.out.println("Podaj tłumaczenie");
        String translation = scanner.nextLine();
        Entry entry = new Entry(original, translation);
        entryRepository.add(entry);
    }

    private void close() {
        try {
            fileService.saveEntries(entryRepository.getAll());
            System.out.println("Zapisano stan aplikacji");
        } catch (IOException e) {
            System.out.println("Nie udało się zapisać zmian");
        }
        System.out.println("Bye Bye!");
    }

    private void printMenu() {
        System.out.println("Wybierz opcję:");
        System.out.println("0 - Dodaj frazę");
        System.out.println("1 - Test");
        System.out.println("2 - Koniec programu");
    }

    private int chooseOption() {
        int option;
        try {
            option = scanner.nextInt();
        } catch (InputMismatchException e) {
            option = UNDEFINED;
        } finally {
            scanner.nextLine();
        }
        if (option > UNDEFINED && option <= CLOSE_APP)
            return option;
        else
            return UNDEFINED;
    }
}