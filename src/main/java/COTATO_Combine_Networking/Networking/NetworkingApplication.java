package COTATO_Combine_Networking.Networking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		servers = {
				//@Server(url = "http://localhost:8080", description = "Local Server"),
				@Server(url = "http://3.133.128.168:8080", description = "Production Server"),

		}
)
@SpringBootApplication
public class NetworkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetworkingApplication.class, args);
	}

}
