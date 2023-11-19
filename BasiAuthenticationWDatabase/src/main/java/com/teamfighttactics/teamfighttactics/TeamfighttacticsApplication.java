package com.teamfighttactics.teamfighttactics;

import com.teamfighttactics.teamfighttactics.dto.CreateUserRequest;
import com.teamfighttactics.teamfighttactics.models.Role;
import com.teamfighttactics.teamfighttactics.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
public class TeamfighttacticsApplication  implements CommandLineRunner {

	private UserService userService;
	public TeamfighttacticsApplication(UserService userService){
		this.userService = userService;
	}


	public static void main(String[] args) {
		SpringApplication.run(TeamfighttacticsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createDummyData();
	}

	public void createDummyData(){
		userService.deleteAll();
		CreateUserRequest request = CreateUserRequest.builder()
				.name("admin")
				.username("admin")
				.password("pass")
				.authorities(Set.of(Role.ROLE_ADMIN))
				.build();

		userService.createUser(request);

		CreateUserRequest request1 = CreateUserRequest.builder()
				.name("admin1")
				.username("admin1")
				.password("pass")
				.authorities(Set.of(Role.ROLE_USER))
				.build();
		userService.createUser(request1);

		CreateUserRequest request2 = CreateUserRequest.builder()
				.name("admin2")
				.username("admin2")
				.password("pass")
				.authorities(Set.of(Role.ROLE_MOD))
				.build();
		userService.createUser(request2);


	}


}
