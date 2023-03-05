package com.onlineclass.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onlineclass.pojo.Score;
import com.onlineclass.pojo.Student;
import com.onlineclass.service.ScoreDaoService;
import com.onlineclass.service.StudentDaoService;
import com.onlineclass.service.UserDaoService;
import com.onlineclass.util.Age;

/**
 * @author indext
 * @version 1.0
 * @since 2019.11.6
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class StudentController {

    @Autowired
    private StudentDaoService studentDaoService;// 学生依赖注入

    /*
     * 功能：学生个人信息修改
     * 调用方式：学生 点击修改个人信息-确认
     *          管理员 点击学生信息-修改信息-确认
     * 使用者：学生&管理员
     * 调用接口：UpdataStudent
     * */
    @RequestMapping(value = "/student/studentUpdate")
    @ResponseBody
    public boolean studentUpdate(HttpServletRequest request)
            throws ParseException {
        String student_name = request.getParameter("student_name");
        String student_sno = request.getParameter("student_sno");
        String student_data = request.getParameter("student_data");
        String student_phone = request.getParameter("student_phone");
        String student_address = request.getParameter("student_address");
        String student_class = request.getParameter("student_class");

        // 获取年龄
        Age age = new Age();
        int userAge = age.parse(student_data);
        String studentage = Integer.toString(userAge);

        // 数据注入
        Student student = new Student();
        student.setStudent_name(student_name);
        student.setStudent_sno(student_sno);
        student.setStudent_date(student_data);
        student.setStudent_age(studentage);
        student.setStudent_phone(student_phone);
        student.setStudent_address(student_address);
        student.setStudent_class(student_class);
        // 用户是否修改信息成功
        int studentUpdate = studentDaoService.UpdataStudent(student);
        if (studentUpdate > 0) {
            System.out.println("修改成功");
            return true;
        }
        System.out.println("修改失败");
        return false;
    }

    /*
     * 功能：学生信息查询
     * 调用方式：进入student.html、点击修改密码、个人信息
     * 使用者：学生
     * 调用接口：SelStudent
     * */
    @RequestMapping(value = "/student/studentInformation")
    @ResponseBody
    public List<Student> studentInformation(HttpServletRequest request) {
        String student_name = request.getParameter("student_name");
        String student_sno = request.getParameter("student_sno");

        System.out.println(student_sno);
        // 数据注入
        Student student = new Student();
        student.setStudent_name(student_name);
        student.setStudent_sno(student_sno);
        // 学生个人信息查询
        List<Student> students = studentDaoService.SelStudent(student);
        return students;
    }

}
