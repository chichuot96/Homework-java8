package vn.topica.itlab4.entity;

import vn.topica.itlab4.annotation.Column;
import vn.topica.itlab4.annotation.Table;

@Table(name = "class")
public class ClassOfSchool {
	@Column(name = "id")
	private Integer classId;
	@Column(name = "code")
	private String classCode;
	@Column(name = "descript")
	private String classDesc;
	@Column(name = "school_id")
	private Integer schoolId;

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getClassDesc() {
		return classDesc;
	}

	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	
	

//	public ClassOfSchool(String classCode, String classDesc, Integer schoolId) {
//		super();
//		this.classCode = classCode;
//		this.classDesc = classDesc;
//		this.schoolId = schoolId;
//	}

	@Override
	public String toString() {
		return "ClassOfSchool [classId=" + classId + ", classCode=" + classCode + ", classDesc=" + classDesc
				+ ", schoolId=" + schoolId + "]";
	}
	
}