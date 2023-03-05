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

import com.onlineclass.pojo.Course;
import com.onlineclass.pojo.Student;
import com.onlineclass.pojo.Teacher;
import com.onlineclass.pojo.User;
import com.onlineclass.service.AdministorDaoService;
import com.onlineclass.service.CourseDaoService;
import com.onlineclass.service.StudentDaoService;
import com.onlineclass.service.TeacherDaoService;
import com.onlineclass.service.UserDaoService;
import com.onlineclass.util.Age;
import com.onlineclass.util.MD5Utils;

/**
 * @author indext
 * @version 1.0
 * @since 2019.10.10
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class UserController {
    @Autowired
    private UserDaoService userDaoService;

    @Autowired
    private TeacherDaoService teacherDaoService;

    @Autowired
    private CourseDaoService courseDaoService;

    /*
     * 功能：登录
     * 使用者：学生&管理员&老师
     * 调用方式：ALL 点击登录
     *          管理员 点击密码修改-查找
     * 调用接口：userLogin
     * */
    @RequestMapping(value = "/user/userLogin")
    @ResponseBody
    public String userLogin(HttpServletRequest request) {
        // 获取登录信息
        String username = request.getParameter("userName");
        String password = request.getParameter("userPwd");
        String usertype = request.getParameter("type");
        // MD5加密
        String userPwd = MD5Utils.doInputPassword(password);
        // 封装数据
        User user = new User();
        user.setUser_name(username);
        user.setUser_password(userPwd);
        user.setUser_type(usertype);
        // 判断用户登录是否成功
        List<User> users = userDaoService.userLogin(user);
        System.out.println(users.toString());
        // 用户登录类型判断
        if (users.size() > 0) {
            String type = users.get(0).getUser_type();
            if (type.equals("学生")) {
                System.out.println("学生登录");
                return "stu";
            } else if (type.equals("教师")) {
                System.out.println("教师登录");
                return "tea";
            } else {
                System.out.println("管理员");
                return "admin";
            }
        }
        System.out.println("登录失败");
        return "login";

    }

    /*
     * 功能：学生注册，既要插到stuser，还要插到student
     * 使用者：管理员
     * 调用方式：点击 添加学生
     * 调用接口:  useConflict StudentRegister userRegister
     * */
    @RequestMapping(value = "/user/studentRegister")
    @ResponseBody
    public boolean studentRegister(HttpServletRequest request)
            throws ParseException {
        String studentname = request.getParameter("student_name");
        // 登录学号
        String studentsno = request.getParameter("student_sno");
        // 登录密码
        String studentpassword = request.getParameter("student_password");
        // MD5加密
        String studentPwd = MD5Utils.doInputPassword(studentpassword);

        String studentdata = request.getParameter("student_data");
        // 获取年龄
        Age age = new Age();
        int userAge = age.parse(studentdata);
        String studentage = Integer.toString(userAge);

        String studentphone = request.getParameter("student_phone");
        String studentaddress = request.getParameter("student_address");
        String studentclass = request.getParameter("student_class");

        // 插入数据到Student对象表中
        Student student = new Student();
        student.setStudent_name(studentname);
        student.setStudent_sno(studentsno);
        student.setStudent_date(studentdata);
        student.setStudent_age(studentage);
        student.setStudent_phone(studentphone);
        student.setStudent_address(studentaddress);
        student.setStudent_class(studentclass);

        // 插入数据到User对象中
        User user = new User();
        user.setUser_name(studentsno);
        user.setUser_password(studentPwd);
        user.setUser_type("学生");
        List<User> n = userDaoService.useConflict(studentsno);// 判断注册用户是否存在
        if (n.size() > 0) {
            System.out.println("注册失败");
            return false;
        } else {
            int x = userDaoService.StudentRegister(student);// 判断是否注册成功
            int m = userDaoService.userRegister(user);// 用户名注册
            if (x > 0 && m > 0) {
                System.out.println("注册成功");
                return true;
            } else {
                System.out.println("注册失败");
                return false;
            }
        }

    }

    /*
     * 功能：教师注册，既要插到stuser，还要插到teacher
     * 使用者：管理员
     * 调用方式：点击 添加老师
     * 调用接口:  useConflict SelTeacher getCourse(如果老师没课也会注册失败) AddTeacher userRegister
     * */
    @RequestMapping(value = "/user/teacherRegister")
    @ResponseBody
    public boolean teacherRegister(HttpServletRequest request)
            throws ParseException {
        String teachername = request.getParameter("teacher_name");
        // 登录学号
        String teachertno = request.getParameter("teacher_tno");
        // 登录密码
        String teacherpassword = request.getParameter("teacher_password");
        // MD5加密
        String teacherPwd = MD5Utils.doInputPassword(teacherpassword);

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

        System.out.println(teacher);

        // 插入数据到User对象中
        User user = new User();
        user.setUser_name(teachertno);
        user.setUser_password(teacherPwd);
        user.setUser_type("教师");

        List<User> n = userDaoService.useConflict(teachertno);// 判断注册用户是否存在
        List<Course> c = courseDaoService.getCourse(teachercourseid, null);// 判断课程号是否存在
        if (n.size() > 0 || c.size() == 0) {
            System.out.println("注册失败");
            return false;
        } else {
            int x = teacherDaoService.AddTeacher(teacher);// 判断是否注册成功
            int m = userDaoService.userRegister(user);// 用户名注册
            if (x > 0 && m > 0) {
                System.out.println("注册成功");
                return true;
            } else {
                System.out.println("注册失败");
                return false;
            }
        }

    }

    /*
     * 功能：课程注册
     * 使用者：管理员
     * 调用方式：点击 添加课程
     * 调用接口:  getCourse判断课是否存在 coursesInsert
     * */

    @RequestMapping(value = "/user/courseRegister")
    @ResponseBody
    public boolean courseRegister(HttpServletRequest request)
            throws ParseException {
        String course_name = request.getParameter("course_name");
        String course_cno = request.getParameter("course_cno");
        String course_information = request.getParameter("course_information");

        Course course = new Course();
        course.setCourse_name(course_name);
        course.setCourse_cno(course_cno);
        course.setCourse_information(course_information);

        List<Course> courses = courseDaoService.getCourse(course_cno, "");
        if (courses.size() > 0) {
            System.out.println("课程已存在！！");
            return false;
        }
        int c = courseDaoService.coursesInsert(course);
        if (c > 0) {
            System.out.println("添加成功");
            return true;
        }
        return false;
    }

    /*
     * 功能：忘记密码,只针对学生,如果存在该用户则返回true
     * 使用者：学生
     * 调用方式：点击 忘记密码
     * 调用接口:  SelStudent
     * */
    @RequestMapping(value = "/user/forgetPassword")
    @ResponseBody
    public boolean forgetPassword(HttpServletRequest request)
            throws ParseException {
        // 获取用户找回信息
        String StudentSno = request.getParameter("studentSno");
        String StudentTelphone = request.getParameter("studentTelphone");
        // 判断是否有此用户
        List<Student> students = userDaoService.SelStudent(StudentSno, StudentTelphone);
        if (students.size() > 0) {
            System.out.println("查询成功");
            return true;
        }
        System.out.println("查询失败");
        return false;
    }

    /*
     * 功能：用户密码修改
     * 使用者：All
     * 调用方式：All 点击 密码修改-确认修改
     *          管理员 点击 修改密码-查找-确认修改
     * 调用接口:  UpdataUser
     * */
    @RequestMapping(value = "/user/updataPassword")
    @ResponseBody
    public boolean updataPassword(HttpServletRequest request) {
        String username = request.getParameter("userName");
        String userpassword = request.getParameter("userPassword");
        // MD5加密
        String password = MD5Utils.doInputPassword(userpassword);
        String usertype = request.getParameter("usertype");
        System.out.println(username + password + usertype);
        User user = new User();
        user.setUser_name(username);
        user.setUser_password(password);
        user.setUser_type(usertype);
        // 判断修改是否成功
        int x = userDaoService.UpdataUser(user);
        if (x > 0) {
            System.out.println("修改成功");
            return true;
        }
        System.out.println("修改失败");
        return false;
    }


}
