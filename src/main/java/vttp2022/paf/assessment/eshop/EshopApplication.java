package vttp2022.paf.assessment.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EshopApplication  {

	@Bean
	public RestTemplate restTemplateCreator(){
		return new RestTemplate();
	}
	
	public static void main(String[] args)  {
		SpringApplication.run(EshopApplication.class, args);
	}

}
