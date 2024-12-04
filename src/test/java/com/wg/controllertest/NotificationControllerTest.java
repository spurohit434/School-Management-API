package com.wg.controllertest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wg.controller.NotificationController;
import com.wg.model.Notification;
import com.wg.services.NotificationService;

class NotificationControllerTest {

	@Mock
	private NotificationService notificationService;

	@InjectMocks
	private NotificationController notificationController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
	}

	@Test
	public void testSendNotification() throws Exception {
		Notification notification = new Notification();
		notification.setNotificationId("N123");
		notification.setDescription("Test notification message");
		notification.setUserId("L123456");

		// Convert the Notification object to JSON
		ObjectMapper objectMapper = new ObjectMapper();
		String notificationJson = objectMapper.writeValueAsString(notification);

		// Perform POST request to send notification and validate response
		mockMvc.perform(post("/api/user/{id}/notification", "L123456").contentType(MediaType.APPLICATION_JSON)
				.content(notificationJson)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.message").value("Notification created and send successfully"))
				.andExpect(jsonPath("$.status").value("Success"));
	}

	@Test
	public void testReadNotifications() throws Exception {
		Notification notification1 = new Notification();
		notification1.setNotificationId("N1");
		notification1.setDescription("Notification 1");
		notification1.setUserId("L123456");

		Notification notification2 = new Notification();
		notification2.setNotificationId("N2");
		notification2.setDescription("Notification 2");
		notification2.setUserId("L123456");

		List<Notification> notifications = Arrays.asList(notification1, notification2);

		when(notificationService.readNotifications("L123456")).thenReturn(notifications);

		// Perform GET request to fetch notifications and validate response
		mockMvc.perform(get("/api/user/{id}/notification", "L123456").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("Notification fetched successfully"))
				.andExpect(jsonPath("$.status").value("Success")).andExpect(jsonPath("$.data.length()").value(2))
				.andExpect(jsonPath("$.data[0].description").value("Notification 1"))
				.andExpect(jsonPath("$.data[1].description").value("Notification 2"));
	}
}
