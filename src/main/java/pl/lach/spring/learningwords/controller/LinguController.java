package pl.lach.spring.learningwords.controller;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.lach.spring.learningwords.ConsoleOutputWriter;
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
    private ConsoleOutputWriter consoleOutputWriter;

    @Autowired
    public LinguController(EntryRepository entryRepository, FileService fileService, Scanner scanner, ConsoleOutputWriter consoleOutputWriter) {
        this.entryRepository = entryRepository;
        this.fileService = fileService;
        this.scanner = scanner;
        this.consoleOutputWriter = consoleOutputWriter;
    }

    public void mainLoop() {
        consoleOutputWriter.printlnFormatted("Witaj w aplikacji LinguApp");
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
                consoleOutputWriter.printlnFormatted("Opcja niezdefiniowana");
        }
    }

    private void test() {
        if (entryRepository.isEmpty()) {
            consoleOutputWriter.printlnFormatted("Dodaj przynajmniej jedną frazę do bazy.");
            return;
        }
        final int testSize = entryRepository.size() > 10 ? 10 : entryRepository.size();
        Set<Entry> randomEntries = entryRepository.getRandomEntries(testSize);
        int score = 0;
        for (Entry entry : randomEntries) {
            consoleOutputWriter.printlnFormatted(String.format("Podaj tłumaczenie dla :\"%s\"\n", entry.getOriginal()));
            String translation = scanner.nextLine();
            if (entry.getTranslation().equalsIgnoreCase(translation)) {
                consoleOutputWriter.printlnFormatted("Odpowiedź poprawna");
                score++;
            } else {
                consoleOutputWriter.printlnFormatted("Odpowiedź niepoprawna - " + entry.getTranslation());
            }
        }
        consoleOutputWriter.printlnFormatted(String.format("Twój wynik: %d/%d\n", score, testSize));
    }

    private void addEntry() {
        consoleOutputWriter.printlnFormatted("Podaj oryginalną frazę");
        String original = scanner.nextLine();
        consoleOutputWriter.printlnFormatted("Podaj tłumaczenie");
        String translation = scanner.nextLine();
        Entry entry = new Entry(original, translation);
        entryRepository.add(entry);
    }

    private void close() {
        try {
            fileService.saveEntries(entryRepository.getAll());
            consoleOutputWriter.printlnFormatted("Zapisano stan aplikacji");
        } catch (IOException e) {
            consoleOutputWriter.printlnFormatted("Nie udało się zapisać zmian");
        }
        consoleOutputWriter.printlnFormatted("Bye Bye!");
    }

    private void printMenu() {
        consoleOutputWriter.printlnFormatted("Wybierz opcję:");
        consoleOutputWriter.printlnFormatted("0 - Dodaj frazę");
        consoleOutputWriter.printlnFormatted("1 - Test");
        consoleOutputWriter.printlnFormatted("2 - Koniec programu");
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