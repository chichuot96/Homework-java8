package vn.topica.itlab4.loading;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.topica.itlab4.annotation.Column;
import vn.topica.itlab4.annotation.Table;
import vn.topica.itlab4.entity.Subject;


public class LoadData {
	public static  <T> List<T> loadList(Statement stmt , Class<T> cl){
		List<T> list=new ArrayList<T>();
		String sql="select * from "+cl.getAnnotation(Table.class).name();
		Field[] fields=cl.getDeclaredFields();
		
		List<Field> f=Arrays.asList(fields);
		for(Field ff: f) {
			ff.setAccessible(true);
		}
		
		try {
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				T t = cl.getDeclaredConstructor().newInstance();
				
				for(int i=0;i<fields.length;i++) {
					Column column=fields[i].getAnnotation(Column.class);
					Object o=null;
					if(column!=null) {
						if(column.name().equals("domain")) {
							o=rs.getObject(column.name(),String.class);
							System.out.println(o);
						}else {
							o=rs.getObject(column.name(),fields[i].getType());
						}
						
					}
					fields[i].set(t,o );
				}
				list.add(t);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Subject> loadSubject(Statement stmt){
		List<Subject> listSb=new ArrayList<Subject>();
		String sql="Select * from "+Subject.class.getAnnotation(Table.class).name();
		try {
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				Subject sb=new Subject();
				sb.setSubjectId(rs.getInt("id"));
				sb.setSubjectName(rs.getString("name"));
				sb.setSubjectDesc(rs.getString("descript"));
				sb.setDomain(rs.getString("domain"));
				listSb.add(sb);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listSb;
	}
}
