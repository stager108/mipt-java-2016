package ru.mipt.java2016.homework.g595.yakusheva.task4;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import ru.mipt.java2016.homework.g000.lavrentyev.task1.CachedJEvalCalculator;

/**
 * curl http://localhost:9001/eval \
 *     -X POST \
 *     -H "Content-Type: text/plain" \
 *     -H "Authorization: Basic $(echo -n "username:password" | base64)" \
 *     --data-raw "44*3+2"
 */

@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class})
@Configuration
@ComponentScan(basePackageClasses = NetworkCalculatorApplication.class)
public class NetworkCalculatorApplication {

    @Bean
    public MyFirstCalculator calculator() {
        return new MyFirstCalculator();
    }

    @Bean
    public MyCalculatorDao dao() {
        return new MyCalculatorDao();
    }

    @Bean
    public EmbeddedServletContainerCustomizer customizer(
            @Value("${ru.mipt.java2016.homework.g595.yakusheva.task4.httpPort:9001}") int port) {
        return container -> container.setPort(port);
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(NetworkCalculatorApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
