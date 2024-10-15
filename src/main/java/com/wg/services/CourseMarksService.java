package com.wg.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wg.model.CourseMarks;
import com.wg.repository.interfaces.InterfaceCourseMarksDAO;

@Service
public class CourseMarksService {
	private InterfaceCourseMarksDAO courseMarksDAO;

	public CourseMarksService() {

	}

	@Autowired
	public CourseMarksService(InterfaceCourseMarksDAO courseMarksDAO) {
		this.courseMarksDAO = courseMarksDAO;
	}

	public void addMarks(CourseMarks courseMarks) {
		boolean flag = false;
		try {
			flag = courseMarksDAO.addMarks(courseMarks);
			if (flag == true) {
				System.out.println("Marks added successfully");
			} else {
				System.out.println("Marks cannot be added");
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Marks can not be added" + e.getMessage());
			// e.printStackTrace();
		}
	}

	public List<CourseMarks> checkMarks(String userId) {
		List<CourseMarks> courseMarks = null;
		try {
			courseMarks = courseMarksDAO.checkMarks(userId);
			return courseMarks;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return courseMarks;
	}
}