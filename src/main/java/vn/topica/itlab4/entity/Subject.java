package vn.topica.itlab4.entity;

import vn.topica.itlab4.annotation.Column;
import vn.topica.itlab4.annotation.Table;

@Table(name = "subject")
public class Subject {
	@Column(name = "name")
	private String subjectName;
	@Column(name = "id")
	private Integer subjectId;
	@Column(name = "descript")
	private String subjectDesc;
	@Column(name = "domain")
	private SubjectDomain domain;

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectDesc() {
		return subjectDesc;
	}

	public void setSubjectDesc(String subjectDesc) {
		this.subjectDesc = subjectDesc;
	}

	public SubjectDomain getDomain() {
		return domain;
	}
//
//	public void setDomain(SubjectDomain domain) {
//		this.domain = domain;
//	}
	
	public void setDomain(String domain) {
		switch(domain.toUpperCase()) {
		case "MATHEMATICS": this.domain=SubjectDomain.MATHEMATICS; break;
		case "LITERATURE" : this.domain= SubjectDomain.LITERATURE; break;
		case "PHYSICS" : this.domain=SubjectDomain.PHYSICS; break;
		case "CHEMISTRY" : this.domain=SubjectDomain.CHEMISTRY; break;
		case "BIOLOGY" : this.domain=SubjectDomain.BIOLOGY; break;
		case "HISTORY" : this.domain=SubjectDomain.HISTORY; break;
		default: this.domain=SubjectDomain.GEOGRAPHY; break;
		}
	}

//public Subject(String subjectName, String subjectDesc, SubjectDomain domain) {
//	super();
//	this.subjectName = subjectName;
//	this.subjectDesc = subjectDesc;
//	this.domain = domain;
//}
	
	

}