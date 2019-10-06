package vn.topica.itlab4.process;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import vn.topica.itlab4.connect.ConnectDb;
import vn.topica.itlab4.entity.*;
import vn.topica.itlab4.loading.LoadData;

public class Process {
	private static final List<Student> listSt=LoadData.loadList(ConnectDb.connectDB(), Student.class);
	private static final List<ClassOfSchool> listCl=LoadData.loadList(ConnectDb.connectDB(), ClassOfSchool.class);
	private static final List<School> listSc=LoadData.loadList(ConnectDb.connectDB(), School.class);
	private static final List<Subject> listSubj=LoadData.loadSubject(ConnectDb.connectDB());
	private static final List<StudentSubjectRegister> listStudentSubjectRegister=LoadData.loadList(ConnectDb.connectDB(), StudentSubjectRegister.class);
	
/*
 * Exercise 1: Sumarize of Student each School
 */
	public static void getAllStudent() {

		listSc.stream().forEach(sc->{
			System.out.println("school name:"+sc.getSchoolName());
			Optional<Long> a=listCl.stream()
					.filter(cl->cl.getSchoolId()==sc.getSchoolId())
					.map(cl->listSt.stream().filter(st->st.getClassId()==cl.getClassId()).count())
					.reduce(Long::sum);
			if(a.isEmpty()) {
				System.out.println("Number student :0");
			}else {
				System.out.println("Number student :"+a.get());
			}
		});

	}
	/*
	 * Exercise 2: Average mark of Subject in each school
	 */
	public static void  averageMark() {
		listSc.stream().forEach(sc->{
			List<Integer> listStudentId=new ArrayList<>();
			System.out.println("------ school name:"+sc.getSchoolName());
			listCl.stream().filter(cl->cl.getSchoolId()==sc.getSchoolId()).map(cl->{
				List<Integer> listStId =listSt.stream()
						.filter(st->st.getClassId()==cl.getClassId())
						.map(st->st.getStudentId())
						.collect(Collectors.toList());
				return listStId;
			}).forEach(listStudentId::addAll);;

			listSubj.stream().forEach(sb->{
				OptionalDouble score=listStudentSubjectRegister.stream()
						.filter(s->s.getSubjectId()==sb.getSubjectId())
						.filter(s->listStudentId.stream().anyMatch(sId->sId==s.getStudentId()))
						.mapToDouble(StudentSubjectRegister::getScore)
						.filter(value->value!=0).average();
				if(score.isEmpty()) {
					System.out.printf("+ No student learn : %s \n",sb.getSubjectName());
					 
				}else {
					System.out.printf("+Subject: %s --- Average score :%.2f \n",sb.getSubjectName(),score.getAsDouble());
				}
				
			});
		});
	}
	/*
	 * Exercise 3: Get class has max average mark in school
	 */
	public static void getMaxAverage() {
		listSc.stream().filter(sc->sc!=null).forEach(sc->{
			System.out.println("------ school name:"+sc.getSchoolName());
			ObjReturn obj= listCl.stream()
					.filter(cl->cl.getSchoolId()==sc.getSchoolId()) 
					.map(cl->{
						double subjScore= listStudentSubjectRegister.stream()
								.filter(ssr->listSt.stream()
										.filter(st->st.getClassId()==cl.getClassId())
										.anyMatch(st->st.getStudentId()==ssr.getStudentId()))
								.mapToDouble(StudentSubjectRegister::getScore)
								.filter(value->value!=0)
								.average().orElse(-1);
						ObjReturn o=new ObjReturn();	
						o.setName(cl.getClassCode());
						o.setScore(subjScore);
						return o;
				}).max(Comparator.comparingDouble(ObjReturn::getScore)).orElse(new ObjReturn());
			if(obj.getScore()==0) {
				System.out.println("No data");
			}else {
				System.out.println("Class :"+obj.getName() +"-- max average score: " + obj.getScore());
			}
			
		});
		
	}
	/*
	 * Exercise 4: Get top 10 and bottom 10 student each subject
	 */
	public static void getTop10() {
		listSubj.stream().forEach(sj->{
			List<StudentSubjectRegister> listSSR=listStudentSubjectRegister.stream()
					.filter(ssr->ssr.getSubjectId()==sj.getSubjectId())
					.sorted(Comparator.comparingDouble(StudentSubjectRegister::getScore))
					.collect(Collectors.toList());
			System.out.println("\n ---- Subject :"+sj.getSubjectName());
			System.out.println(" ++ Top 10 student :");
			listSSR.stream().limit(10).forEach(ssr->{
				listSt.stream().filter(st->st.getStudentId()==ssr.getStudentId()).forEach(st->{
					System.out.printf("%s -- ",st.getStudentName());
				});
				
			});
			System.out.println("\n ++ Bottom 10 student :");
			listSSR.stream().skip(listSSR.size()<10?0:listSSR.size()-10).forEach(ssr->{
				listSt.stream().filter(st->st.getStudentId()==ssr.getStudentId()).forEach(st->{
					System.out.printf("%s -- ",st.getStudentName());
				});
				
			});
		});
	}
	
	/*
	 * Exercise 5: Get class has max average mark by subject domain
	 */
	public static void getClassAvgMark() {
		Map<SubjectDomain, List<Subject>> mapSubject= listSubj.stream().collect(Collectors.groupingBy(Subject::getDomain));
		mapSubject.entrySet().stream().forEach(e->{
			ObjReturn obj=listCl.stream().map(cl->{
				List<Integer> listStId =listSt.stream()
						.filter(st->st.getClassId()==cl.getClassId())
						.map(st->st.getStudentId())
						.collect(Collectors.toList());
				Double sbjAvg= e.getValue().stream().map(sbj->{
					OptionalDouble avg= listStudentSubjectRegister.stream()
					.filter(ssr->ssr.getSubjectId()==sbj.getSubjectId())
					.filter(ssr->listStId.stream().anyMatch(sId->sId==ssr.getStudentId()))
					.filter(value->value!=null)
					.mapToDouble(ssr->ssr.getScore())
					.filter(value->value!=0)
					.average();
					return avg;
				}).mapToDouble(a->a.orElse(-1)).filter(value->value!=-1)
						.average().orElse(-1);
				ObjReturn o=new ObjReturn();
				o.setName(cl.getClassCode());
				o.setScore(sbjAvg);
				return o;
			}).max(Comparator.comparingDouble(ObjReturn::getScore)).orElse(new ObjReturn());
			System.out.println("Subject Domain: "+e.getKey().toString());
			if(obj.getScore()==0) {
				System.out.println("No data");
			}else {
				System.out.println("Class :"+obj.getName() +"-- max average score: " + obj.getScore());
			}
		});

		
	}
	/*
	 * Exercise 6: Get subject domain has max average and subject domain has most student
	 */
	public static void getDomain() {
		Map<SubjectDomain, List<Subject>> mapSubject= listSubj.stream().collect(Collectors.groupingBy(Subject::getDomain));
		listSc.stream().forEach(sc->{
			List<Integer> listStudentId=new ArrayList<>();
			System.out.println("------ school name:"+sc.getSchoolName());
			listCl.stream().filter(cl->cl.getSchoolId()==sc.getSchoolId()).map(cl->{
				List<Integer> listStId =listSt.stream()
						.filter(st->st.getClassId()==cl.getClassId())
						.map(st->st.getStudentId())
						.collect(Collectors.toList());
				return listStId;
			}).forEach(listStudentId::addAll);
			
			ObjReturn maxMark= mapSubject.entrySet().stream().map(e->{
				Double avgMark= e.getValue().stream().map(sbj->{
					Double avg= listStudentSubjectRegister.stream()
					.filter(ssr->ssr.getSubjectId()==sbj.getSubjectId())
					.filter(ssr->listStudentId.stream().anyMatch(sId->sId==ssr.getStudentId()))
					.mapToDouble(ssr->ssr.getScore())
					.filter(value->value!=0)
					.average().orElse(-1);
					return avg;
				}).mapToDouble(a->a).filter(value->value!=-1).average().orElse(-1);
				ObjReturn o=new ObjReturn();	
				o.setName(e.getKey().toString());
				o.setScore(avgMark);
				return o;
			}).max(Comparator.comparingDouble(ObjReturn::getScore)).orElse(new ObjReturn());
			ObjReturn hotDomain= mapSubject.entrySet().stream().map(domain->{
				Long sumStOfDomain= domain.getValue().stream().map(sbj->{
					Long numStOfSb= listStudentSubjectRegister.stream()
					.filter(ssr->ssr.getSubjectId()==sbj.getSubjectId())
					.filter(ssr->listStudentId.stream().anyMatch(sId->sId==ssr.getStudentId()))
					.count();
					return numStOfSb;
				}).mapToLong(a->a).sum();
				ObjReturn obj=new ObjReturn();
				obj.setName(domain.getKey().toString());
				obj.setCount(sumStOfDomain);
				return obj;
			}).max(Comparator.comparingLong(ObjReturn::getCount)).orElse(new ObjReturn());
			
			if(maxMark.getScore()==-1) {
				System.out.println("no data");
			}else {
				System.out.println("-- Subject Domain "+maxMark.getName()+" has max average mark : "+maxMark.getScore());
			}
			if(hotDomain.getCount()==0) {
				System.out.println("no data");
			}else {
				System.out.println("---Subject Domain"+hotDomain.getName()+" has most student register :"+ hotDomain.getCount());
			}
			
		});
		
		
	}
	
	public static void main(String[] args) {
		getAllStudent();
//		averageMark();
//		getMaxAverage();
//		getTop10();
//		getClassAvgMark();
//		getDomain();
	}
}
