package pl.lach.spring.learningwords.textformat;

import org.springframework.stereotype.Component;

@Component
@TextFormaterType(type = TextFormaterType.TextFormatType.UPPER_CASE)
public class UpperCaseTextFormater implements TextFormatter {
    @Override
    public String format(String input) {
        return input.toUpperCase();
    }
}
