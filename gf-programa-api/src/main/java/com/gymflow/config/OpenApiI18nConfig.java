package com.gymflow.config;

import java.util.Locale;
import java.util.Map;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;

@Configuration
public class OpenApiI18nConfig {

  private final MessageSource messageSource;

  public OpenApiI18nConfig(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Bean
  public OpenApiCustomizer i18nOpenApiCustomizer() {
    return openApi -> {
      Map<String, PathItem> paths = openApi.getPaths();
      if (paths == null)
        return;

      for (Map.Entry<String, PathItem> entry : paths.entrySet()) {
        PathItem pathItem = entry.getValue();
        applyI18nToOperation(pathItem.getGet());
        applyI18nToOperation(pathItem.getPost());
        applyI18nToOperation(pathItem.getPut());
        applyI18nToOperation(pathItem.getDelete());
        applyI18nToOperation(pathItem.getPatch());
      }
    };
  }

  private void applyI18nToOperation(Operation operation) {
    if (operation == null)
      return;

    operation.setSummary(resolve(operation.getSummary()));
    operation.setDescription(resolve(operation.getDescription()));
  }

  private String resolve(String key) {
    if (key != null && key.startsWith("{") && key.endsWith("}")) {
      String realKey = key.substring(1, key.length() - 1).trim();
      Locale locale = LocaleContextHolder.getLocale();
      return messageSource.getMessage(realKey, null, realKey, locale);
    }
    return key;
  }
}