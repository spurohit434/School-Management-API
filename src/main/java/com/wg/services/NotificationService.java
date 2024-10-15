package com.wg.services;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wg.model.Notification;
import com.wg.repository.NotificationDAO;
import com.wg.repository.interfaces.InterfaceNotificationDAO;

@Service
public class NotificationService {
	private InterfaceNotificationDAO notificationDAO = new NotificationDAO();

	public NotificationService() {
	}

	@Autowired
	public NotificationService(InterfaceNotificationDAO notificationDAO) {
		this.notificationDAO = notificationDAO;
	}

	public boolean sendNotification(Notification notification) {
		boolean sendStatus = false;
		try {
			String randomString = UUID.randomUUID().toString();
			int desiredLength = 7;
			if (desiredLength > randomString.length()) {
				desiredLength = randomString.length();
			}
			String notificationId = randomString.substring(0, desiredLength);
			notificationId = 'L' + notificationId;
			notification.setNotificationId(notificationId);
			sendStatus = notificationDAO.sendNotification(notification);
			return sendStatus;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return sendStatus;
	}

	public List<Notification> readNotifications(String userId) {
		List<Notification> notificationList = null;
		try {
			notificationList = notificationDAO.readNotifications(userId);
			return notificationList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return notificationList;
	}
}
