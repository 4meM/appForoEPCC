package com.app.config;

import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;

public class MyLuceneAnalysisConfigurer implements LuceneAnalysisConfigurer {
   @Override
  public void configure(LuceneAnalysisConfigurationContext context) {
    context.analyzer("english").custom()
                    .tokenizer("standard")
                    .tokenFilter("lowercase")
                    .tokenFilter("snowballPorter").param("language", "English")
                    .tokenFilter("asciiFolding");

    // context.analyzer("name").custom()
    //                 .tokenizer("standard")
    //                 .tokenFilter("lowercase")
    //                 .tokenFilter("asciiFolding");

    // Analizador para texto en español
    context.analyzer("spanish").custom()
           .tokenizer("standard")
           .tokenFilter("lowercase")
           .tokenFilter("stop")
              .param("words", "spanish.txt")
              .param("ignoreCase", "true")
           .tokenFilter("spanishLightStem")
           .tokenFilter("asciiFolding");

    // Analizador para busqueda multilanguage
    context.analyzer("multilingual").custom()
           .tokenizer("standard")
           .tokenFilter("lowercase")
           .tokenFilter("stop")
               .param("words", "spanish.txt") // Archivo con stop words en español e inglés
               .param("ignoreCase", "true")
           .tokenFilter("snowballPorter")
               .param("language", "Spanish")
           .tokenFilter("snowballPorter")
               .param("language", "English")
           .tokenFilter("asciiFolding");

    // analizador para nombres
    context.analyzer("name").custom()
           .tokenizer("standard")
           .tokenFilter("lowercase")
           .tokenFilter("wordDelimiterGraph")
           .tokenFilter("asciiFolding")
           .tokenFilter("stop")
           .tokenFilter("removeDuplicates");

    // analizador para busqueda exacta
    context.analyzer("exact").custom()
           .tokenizer("keyword")
           .tokenFilter("lowercase")
           .tokenFilter("asciiFolding");

    context.normalizer("exact").custom()
           .tokenFilter("lowercase")
           .tokenFilter("asciiFolding");
    
  }

}
