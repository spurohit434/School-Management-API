package com.wg.controllertest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wg.controller.UserController;
import com.wg.model.Role;
import com.wg.model.User;
import com.wg.services.interfaces.InterfaceUserService;

class UserControllerTest {
	@Mock
	private InterfaceUserService userService;

	@InjectMocks
	private UserController userController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void testAddUser() throws Exception {
		User user = new User();
		user.setUserId("L123456");
		user.setName("John Doe");
		user.setContactNumber("1234567890");
		user.setRole(Role.Student); // Assuming Role is an enum with values like STUDENT or ADMIN
		user.setPassword("password123");
		user.setStandard(10);
		user.setAddress("123 Mock Street, Test City");
		user.setUsername("johndoe");
		user.setAge(29);
		user.setEmail("johndoe@example.com");
		user.setGender("Male");
		user.setRollNo("A123");

		when(userService.addUser(any(User.class))).thenReturn(true);

		// Convert the User object to JSON string
		ObjectMapper objectMapper = new ObjectMapper();
		String userJson = objectMapper.writeValueAsString(user);

		// Perform the POST request and assert the expected results
		mockMvc.perform(post("/api/user").contentType(MediaType.APPLICATION_JSON).content(userJson))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User Created Successfully"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Success"));
	}

	@Test
	public void testGetUserById() throws Exception {
		User user = new User();
		user.setUserId("1");
		user.setName("John Doe");

		when(userService.getUserById("1")).thenReturn(user);

		mockMvc.perform(get("/api/user/{id}", "1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.name").value("John Doe")).andExpect(jsonPath("$.status").value("Success"));
	}

	@Test
	public void testGetAllUsers() throws Exception {
		User user1 = new User();
		user1.setUserId("1");
		user1.setName("John Doe");

		User user2 = new User();
		user2.setUserId("2");
		user2.setName("Jane Doe");

		List<User> users = Arrays.asList(user1, user2);
		when(userService.getAllUser(1, 10)).thenReturn(users);
		when(userService.getTotalUserCount()).thenReturn(2);

		mockMvc.perform(
				get("/api/users").param("page", "1").param("size", "10").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.data.length()").value(5))
				.andExpect(jsonPath("$.status").value("Success"));
	}

	@Test
	public void testDeleteUserById_Success() throws Exception {
		when(userService.deleteUser("1")).thenReturn(true);

		mockMvc.perform(delete("/api/user/{id}", "1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("User Deleted Successfully"))
				.andExpect(jsonPath("$.status").value("Success"));
	}

	@Test
	public void testDeleteUserById_Failure() throws Exception {
		when(userService.deleteUser("1")).thenReturn(false);

		mockMvc.perform(delete("/api/user/{id}", "1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("User can not be Deleted"))
				.andExpect(jsonPath("$.status").value("Error"));
	}

	@Test
	public void testUpdateUser_Success() throws Exception {
		User updatedUser = new User();
		updatedUser.setName("Updated Name");

		when(userService.updateUser(eq("1"), any(User.class))).thenReturn(true);

		mockMvc.perform(put("/api/user/{id}", "1").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"name\": \"Updated Name\" }")).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("User Updated Successfully"))
				.andExpect(jsonPath("$.status").value("Success"));
	}

	@Test
	public void testUpdateUser_Failure() throws Exception {
		when(userService.updateUser(eq("1"), any(User.class))).thenReturn(false);

		mockMvc.perform(put("/api/user/{id}", "1").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"name\": \"Updated Name\" }")).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("User can not be updated"))
				.andExpect(jsonPath("$.status").value("Error"));
	}
}
