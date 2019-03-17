package pl.lach.spring.learningwords.textformat;

import org.springframework.stereotype.Component;

@Component
@TextFormaterType(type = TextFormaterType.TextFormatType.BASIC)
public class BasicTextFormater implements TextFormatter {
    @Override
    public String format(String input) {
        return input;
    }
}
