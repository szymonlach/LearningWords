package pl.lach.spring.learningwords;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.lach.spring.learningwords.crypting.Cryptable;
import pl.lach.spring.learningwords.model.Entry;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileService {

    private String fileName;
    private static Cryptable cryptType;

    @Autowired
    public FileService(@Value("${filename}") String fileName, Cryptable cryptType) {
        this.fileName = fileName;
        this.cryptType = cryptType;
    }

    public List<Entry> readAllFile() throws IOException {
        return Files.readAllLines(Paths.get(fileName))
                .stream()
                .map(cryptType::decrypt)
                .map(CsvEntryConverter::parse)
                .collect(Collectors.toList());
    }

    public void saveEntries(List<Entry> entries) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (Entry entry : entries) {
            writer.write(cryptType.encrypt(entry.toString()));
            writer.newLine();
        }
        writer.close();
    }

    private static class CsvEntryConverter {
        static Entry parse(String text) {
            String[] split = text.split(";");
            return new Entry(split[0], split[1]);
        }
    }
}
