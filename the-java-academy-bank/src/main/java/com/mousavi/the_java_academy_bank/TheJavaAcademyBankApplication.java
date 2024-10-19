package com.mousavi.the_java_academy_bank;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "The Java Academy Bank App",
				description = "Backend Rest APIs for TJA Bank",
				version = "v1.0",
				contact = @Contact(
						name = "Fateme Mousavi",
						email = "sadat.mousavi1376@gmail.com",
						url = "http://github.com/fatimasadat76"

				),
				license = @License(
						name = "The java Accademy",
						url = "https://github.com/fatimasadat76"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "TThe Java Academy Bank App ",
				url = "https://github.com/fatimasadat76"
		)
)
public class TheJavaAcademyBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheJavaAcademyBankApplication.class, args);
	}

}
