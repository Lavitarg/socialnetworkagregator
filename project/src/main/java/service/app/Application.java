package service.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.xml.sax.SAXException;
import service.services.Parse;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


@SpringBootApplication
@ComponentScan(basePackages = "service")
public class Application {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        Parse.parseFile();
        SpringApplication.run(Application.class);
    }
}

