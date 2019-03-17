package pl.lach.spring.learningwords;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lach.spring.learningwords.textformat.TextFormaterType;
import pl.lach.spring.learningwords.textformat.TextFormatter;

@Component
public class ConsoleOutputWriter {

    TextFormatter textFormatter;

    @Autowired
    public ConsoleOutputWriter(@TextFormaterType(type = TextFormaterType.TextFormatType.BASIC) TextFormatter textFormatter) {
        this.textFormatter = textFormatter;
    }

    public void printlnFormatted(String string) {
        System.out.println(textFormatter.format(string));
    }
}
