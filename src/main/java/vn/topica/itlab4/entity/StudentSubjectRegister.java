package vn.topica.itlab4.entity;

import vn.topica.itlab4.annotation.Column;
import vn.topica.itlab4.annotation.Table;

@Table(name = "register_subject")
public class StudentSubjectRegister {
	@Column(name = "student_id")
	private Integer studentId;
	@Column(name = "subject_id")
	private Integer subjectId;
	@Column(name = "score")
	private Double score;
	
	
	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

//	public StudentSubjectRegister(Integer studentId, Integer subjectId, Double score) {
//		super();
//		this.studentId = studentId;
//		this.subjectId = subjectId;
//		this.score = score;
//	}

	
	
}