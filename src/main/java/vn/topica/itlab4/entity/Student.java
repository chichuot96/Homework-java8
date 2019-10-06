package vn.topica.itlab4.entity;

import vn.topica.itlab4.annotation.Column;
import vn.topica.itlab4.annotation.Table;

@Table(name = "student")
public class Student {
	@Column(name = "id")
	private Integer studentId;
	@Column(name = "name")
	private String studentName;
	@Column(name = "mobile")
	private String studentMobile;
	@Column(name = "class_id")
	private Integer classId;

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentMobile() {
		return studentMobile;
	}

	public void setStudentMobile(String studentMobile) {
		this.studentMobile = studentMobile;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

//	public Student(String studentName, String studentMobile, Integer classId) {
//		super();
//		this.studentName = studentName;
//		this.studentMobile = studentMobile;
//		this.classId = classId;
//	}

	
}