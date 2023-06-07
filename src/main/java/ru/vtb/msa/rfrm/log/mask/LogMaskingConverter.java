package ru.vtb.msa.rfrm.log.mask;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;

@Plugin(name = "LogMaskingConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"mask"})
public class LogMaskingConverter extends LogEventPatternConverter {

    private static final LogMaskingConverter INSTANCE = new LogMaskingConverter();


    private LogMaskingConverter() {
        super("Mask", "mask");
    }

    public static LogMaskingConverter newInstance(final String[] options) {
        return INSTANCE;
    }

    @Override
    public void format(LogEvent event, StringBuilder outputMessage) {
        String message = event.getMessage().getFormattedMessage();

        outputMessage.append(message + "*****");
    }
}
