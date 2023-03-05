package com.onlineclass.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onlineclass.pojo.OnlineClassPage;
import com.onlineclass.pojo.Score;
import com.onlineclass.pojo.Student;
import com.onlineclass.pojo.Teacher;
import com.onlineclass.service.ScoreDaoService;
import com.onlineclass.service.StudentDaoService;
import com.onlineclass.service.TeacherDaoService;
import com.onlineclass.util.Age;

/**
 *
 * @author indext
 * @since 2019.11.08
 * @version 1.0
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class TeacherController {
	@Autowired
	private TeacherDaoService teacherDaoService;

	@Autowired
	private StudentDaoService studentDaoService;

	@Autowired
	private ScoreDaoService scoreDaoService;

	/*
	 * 功能：显示所有已选课程,看哪些学生选了哪些属于该老师的课、还有成绩管理
	 * 使用者：老师&管理员
	 * 调用方式：管理员 点击 选课管理-搜索
	 * 			老师 进入teacher.html 或 点击 学生成绩管理/上一页/下一页/搜索、课程信息查看/上一页/下一页/搜索
	 * 调用接口:  scoresTeacherChange scoresTeacherChangePage
	 * */
	@RequestMapping("/teacher/teacherChange")
	@ResponseBody
	public OnlineClassPage courses(HttpServletRequest request) {
		String student_name = request.getParameter("student_name");
		String student_class = request.getParameter("student_class");
		String student_sno = request.getParameter("student_sno");
		String teacher_tno = request.getParameter("teacher_tno");
		// 获取显示的页
		String pageNoss = request.getParameter("pageNopss");
		int pageNos = Integer.parseInt(pageNoss);
		int pageSize = 10;

		List<Score> scores = teacherDaoService.scoresTeacherChange(teacher_tno, student_name, student_class,
				student_sno);
		// 显示分页的总页数
		int pageNo = scores.size() / 10;
		// 每页显示的行数
		// 判断最后一页行数是否能显示一页，不能则加1
		if (scores.size() % 10 != 0) {
			pageNo += 1;
		}
		List<Score> scoresPage = teacherDaoService.scoresTeacherChangePage(teacher_tno, student_name, student_class,
				student_sno, pageNos, pageSize);

		OnlineClassPage onlineClassPage = new OnlineClassPage();
		onlineClassPage.setScore(scoresPage);
		onlineClassPage.setPageNo(pageNo);
		System.out.println(onlineClassPage);
		return onlineClassPage;
	}

	/*
	 * 功能：分页显示学生信息
	 * 使用者：管理员
	 * 调用方式：点击 学生信息/上一页/下一页/搜索学生
	 * 调用接口:  SelStudent SelStudentPage
	 * */
	@RequestMapping("/teacher/teaSelStudentsPage")
	@ResponseBody
	public OnlineClassPage TeaSelStudentsPage(HttpServletRequest request, HttpServletResponse response,			HttpSession session) {
		String student_name = request.getParameter("student_name");
		String student_class = request.getParameter("student_class");
		String student_sno = request.getParameter("student_sno");
		String teacher_tno = request.getParameter("teacher_tno");

		System.out.println(student_name + " " + student_class + " " + student_sno);

		// 获取页数
		String pageNoss = request.getParameter("pageNopss");
		int pageNos = Integer.parseInt(pageNoss);
		Student student = new Student();
		student.setStudent_name(student_name);
		student.setStudent_class(student_class);
		student.setStudent_sno(student_sno);
		List<Student> students = studentDaoService.SelStudent(student);
//		 显示分页的总页数
		int pageNo = students.size() / 10;
		// 每页显示的行数
		int pageSize = 10;
		// 判断最后一页行数是否能显示一页，不能则加1
		if (students.size() % 10 != 0) {
			pageNo += 1;
		}
		List<Student> stu = studentDaoService.SelStudentPage(student_sno, student_name, student_class, pageNos,
				pageSize);
		OnlineClassPage onlineClassPage = new OnlineClassPage();
		onlineClassPage.setStudent(stu);
		onlineClassPage.setPageNo(pageNo);
		return onlineClassPage;
	}

	/*
	 * 功能：显示修改教师信息,如果存在该用户则返回true
	 * 使用者：老师
	 * 调用方式：进入teacher.html
	 * 调用接口:  SelTeacher
	 * */
	@RequestMapping("/teacher/teacherInformation")
	@ResponseBody
	public List<Teacher> teacherInformation(HttpServletRequest request) {
		String username = request.getParameter("userName");
		Teacher teacher = new Teacher();
		teacher.setTeacher_tno(username);
		teacher.setTeacher_name("");
		List<Teacher> teachers = teacherDaoService.SelTeacher(teacher);
		return teachers;
	}

	/*
	 * 功能：修改教师信息
	 * 使用者：老师&管理员
	 * 调用方式：老师点击 个人信息修改-确认修改
	 * 			管理员点击 教师信息-修改信息-确认
	 * 调用接口:  SelTeacher
	 * */
	@RequestMapping("/teacher/teacherUpdateInformation")
	@ResponseBody
	public boolean teacherUpdateInformation(HttpServletRequest request) throws ParseException {
		// 页面获取教师信息
		String teachername = request.getParameter("teacher_name");
		String teachertno = request.getParameter("teacher_tno");
		String teacherdata = request.getParameter("teacher_data");
		// 获取年龄
		Age age = new Age();
		int userAge = age.parse(teacherdata);
		String teacherage = Integer.toString(userAge);

		String teacherphone = request.getParameter("teacher_phone");
		String teacheraddress = request.getParameter("teacher_address");
		String teachercourseid = request.getParameter("teacher_course_id");

		// 插入数据到Teacher对象表中
		Teacher teacher = new Teacher();
		teacher.setTeacher_name(teachername);
		teacher.setTeacher_tno(teachertno);
		teacher.setTeacher_age(teacherage);

		teacher.setTeacher_date(teacherdata);
		teacher.setTeacher_phone(teacherphone);
		teacher.setTeacher_address(teacheraddress);
		teacher.setTeacher_course_id(teachercourseid);

		int teacherUpdate = teacherDaoService.UpdataTeacher(teacher);
		if (teacherUpdate > 0) {
			System.out.println("修改成功");
			return true;
		}
		System.out.println("修改失败");
		return false;
	}

	/*
	 * 功能：修改成绩信息
	 * 使用者：老师
	 * 调用方式：点击 学生成绩修改-修改-确认
	 * 调用接口:  scoresInput
	 * */
	@RequestMapping("/teacher/teacherScore")
	@ResponseBody
	public boolean teacherScore(HttpServletRequest request) {
		// 获取成绩录入信息
		String username = request.getParameter("userName");
		String score = request.getParameter("score");
		int sc = Integer.parseInt(score);
		String course_cno = request.getParameter("course_cno");

		int scores = scoreDaoService.scoresInput(username, course_cno, sc);
		if (scores > 0) {
			System.out.println("修改成功");
			return true;
		}
		System.out.println("修改失败");
		return false;
	}

}
