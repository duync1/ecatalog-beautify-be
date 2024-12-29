package Beauty_ECatalog.Beauty_ECatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class BeautyECatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeautyECatalogApplication.class, args);
	}

}
