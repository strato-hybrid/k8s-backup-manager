package kr.co.strato.migrationcore.gloabal.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.io.IOException;

@Configuration
public class MessageConfig {
    @Bean
    public MessageSource messageSource(@Value("${spring.messages.basename}") String basename, @Value("${spring.messages.encoding}") String encoding) throws IOException {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:"+basename);
        messageSource.setDefaultEncoding(encoding);

        return messageSource;
    }
}
