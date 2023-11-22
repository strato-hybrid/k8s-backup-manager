package kr.co.strato.migrationcore.gloabal.api.model;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ResponseMessageProvider implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ResponseMessageProvider.applicationContext = applicationContext;
    }

    public static ResponseMessage getResponseMessage(String code){
        MessageSource messageSource = applicationContext.getBean(MessageSource.class);
        String message = messageSource.getMessage(code, null, Locale.KOREA);

        return ResponseMessage.builder().message(message).code(code).build();
    }

    public static ResponseMessage getResponseMessageWithDetail(String code,String detail){
        MessageSource messageSource = applicationContext.getBean(MessageSource.class);
        String message = messageSource.getMessage(code, null, Locale.KOREA);

        return ResponseMessage.builder().message(message).code(code).detail(detail).build();
    }
}
