package com.onlineclass.pojo;

public class ClassTime extends OnlineClassObject {
	/**
	 * 选课时间安排
	 */
	private static long serialVersionUID = 1L;
	private String course_cno;// 课程号
	private String class_weekend;// 周
	private String class_time;// 时间

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCourse_cno() {
		return course_cno;
	}

	public String getClass_weekend() {
		return class_weekend;
	}

	public String getClass_time() {
		return class_time;
	}

	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}

	public void setCourse_cno(String course_cno) {
		this.course_cno = course_cno;
	}

	public void setClass_weekend(String class_weekend) {
		this.class_weekend = class_weekend;
	}

	public void setClass_time(String class_time) {
		this.class_time = class_time;
	}

	@Override
	public String toString() {
		return "ClassTime [course_cno=" + course_cno + ", class_weekend=" + class_weekend
				+ ", class_time=" + class_time + "]";
	}

	public ClassTime(String course_cno, String class_weekend, String class_time) {
		super();
		this.course_cno = course_cno;
		this.class_weekend = class_weekend;
		this.class_time = class_time;
	}

	public ClassTime() {

	}

}
