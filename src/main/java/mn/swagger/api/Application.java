package mn.swagger.api;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;

@OpenAPIDefinition(
        info = @Info(
                title = "product-service",
                version = "1.0",
                description = "This service is used for product operations",
                contact = @Contact(url = "https://github.com/sudhirsinha-github", name = "Sudhir")))
public class Application {
    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
