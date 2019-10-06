package vn.topica.itlab4.entity;


import vn.topica.itlab4.annotation.Column;
import vn.topica.itlab4.annotation.Table;

@Table(name = "school")
public class School {
	@Column(name = "name")
	private String schoolName;
	@Column(name = "id")
	private Integer schoolId;
	@Column(name = "descript")
	private String schoolDesc;

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolDesc() {
		return schoolDesc;
	}

	public void setSchoolDesc(String schoolDesc) {
		this.schoolDesc = schoolDesc;
	}

//	public School(String schoolName, String schoolDesc) {
//		super();
//		this.schoolName = schoolName;
//		this.schoolDesc = schoolDesc;
//	}
	

	
}