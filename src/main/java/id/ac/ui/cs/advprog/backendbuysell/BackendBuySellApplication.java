package id.ac.ui.cs.advprog.backendbuysell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class})
public class BackendBuySellApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendBuySellApplication.class, args);
    }

}
