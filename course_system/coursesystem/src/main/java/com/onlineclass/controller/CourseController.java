package com.onlineclass.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onlineclass.pojo.ClassTime;
import com.onlineclass.pojo.Course;
import com.onlineclass.pojo.OnlineClassPage;
import com.onlineclass.pojo.Score;
import com.onlineclass.pojo.Teacher;
import com.onlineclass.service.ClassTimeDaoService;
import com.onlineclass.service.CourseDaoService;
import com.onlineclass.service.ScoreDaoService;
import com.onlineclass.service.TeacherDaoService;

/**
 * @author indext
 * @version 1.0
 * @since 2019.11.06
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class CourseController {
    @Autowired
    private CourseDaoService courseDaoService;

    @Autowired
    private ScoreDaoService ScoreDaoService;

    @Autowired
    private TeacherDaoService teacherDaoService;

    @Autowired
    private ClassTimeDaoService classTimeDaoService;


    /*
     * 功能：分页查询所有课程(课程信息页面调用)
     * 使用者：管理员
     * 调用方式：点击 课程信息/上一页/下一页/搜索课程
     * 调用接口：getCourse getCoursePages
     * */
    @RequestMapping("/courseSel")
    @ResponseBody
    public OnlineClassPage courseSelPage(HttpServletRequest request)
            throws Exception {
        String course_name = request.getParameter("course_name");
        String course_cno = request.getParameter("course_cno");
        String pageNoss = request.getParameter("pageNopss");

        System.out.println("asdasdasd" + course_name);

        int pageNo = Integer.parseInt(pageNoss);
        List<Course> courses = courseDaoService.getCourse(course_cno, course_name);
        // 显示分页的总页数
        int pageNos = courses.size() / 10;
        int pageSize = 10;
        if (courses.size() % 10 != 0) {
            pageNos += 1;
        }
        // 分页显示记录
        List<Course> coursePage = courseDaoService.getCoursePages(course_cno, course_name, pageNo, pageSize);

        OnlineClassPage onlineClassPage = new OnlineClassPage();
        onlineClassPage.setPageNo(pageNos);
        onlineClassPage.setCourse(coursePage);
        System.out.println(onlineClassPage.toString());
        return onlineClassPage;
    }

    /*
     * 功能：分页查询所有可选课程
     * 使用者：学生
     * 调用方式：进入student.html 或 点击 课程选择/上一页/下一页
     * 调用接口：coursesSel coursesSelPage
     * */
    @RequestMapping("/student/courseTeaSel")
    @ResponseBody
    public OnlineClassPage courseTeaSel(HttpServletRequest request)
            throws Exception {
        String course_name = request.getParameter("course_name");
        String course_cno = request.getParameter("course_cno");
        String teacher_name = request.getParameter("teacher_name");
        String pageNoss = request.getParameter("pageNopss");

        int pageNo = Integer.parseInt(pageNoss);
        List<Teacher> courses = courseDaoService.coursesSel(course_name, course_cno, teacher_name);
        // 显示分页的总页数
        int pageNos = courses.size() / 10;
        int pageSize = 10;
        if (courses.size() % 10 != 0) {
            pageNos += 1;
        }
        // 分页显示记录
        List<Teacher> coursePage = courseDaoService.coursesSelPage(course_name, course_cno, teacher_name, pageNo,
                pageSize);

        OnlineClassPage onlineClassPage = new OnlineClassPage();
        onlineClassPage.setPageNo(pageNos);
        onlineClassPage.setTeacher(coursePage);
        System.out.println(onlineClassPage.toString());
        return onlineClassPage;
    }

    /*
     * 功能：显示所有已选课程
     * 使用者：学生&管理员
     * 调用方式：学生点击 查看选课信息、查看成绩
     *         管理员点击 选课管理、查看学生成绩/上一页/下一页
     * 调用接口：scores scoresPage
     * */
    @RequestMapping(value = "/student/courses")
    @ResponseBody
    public OnlineClassPage studentcourses(HttpServletRequest request, HttpServletResponse response,
                                          HttpSession session) {
        String student_sno = request.getParameter("student_sno");
        String course_cno = request.getParameter("course_cno");
        String teacher_tno = request.getParameter("teacher_tno");
        // 获取所选课程所有页数
        List<Score> scores = courseDaoService.scores(student_sno, course_cno, teacher_tno);
        String pageNoss = request.getParameter("pageNopss");
        int pageNo = Integer.parseInt(pageNoss);
        int pageSize = 10;
        // 显示分页的总页数
        int pageNos = scores.size() / 10;

        if (scores.size() % 10 != 0) {
            pageNos += 1;
        }
        System.out.println(scores.toString());
        System.out.println(scores.size());
        // 分页显示记录
        List<Score> scoresPage = courseDaoService.scoresPage(student_sno, course_cno, teacher_tno, pageNo, pageSize);
        System.out.println(scoresPage.size());
        OnlineClassPage onlineClassPage = new OnlineClassPage();
        onlineClassPage.setPageNo(pageNos);
        onlineClassPage.setScore(scoresPage);
        return onlineClassPage;
    }

    /*
     * 功能：根据学号、姓名、课程名查询学生成绩
     * 使用者：管理员
     * 调用方式：点击 学生成绩-搜索
     * 调用接口：scoresStu scoresStuPage
     * */
    @RequestMapping(value = "/student/coursesStu")
    @ResponseBody
    public OnlineClassPage coursesStu(HttpServletRequest request) {
        String student_sno = request.getParameter("student_sno");
        String student_name = request.getParameter("student_name");
        String teacher_name = request.getParameter("teacher_name");
        String course_name = request.getParameter("course_name");

        System.out.println("student_sno:" + student_sno);
        System.out.println("student_name:" + student_name);
        System.out.println("teacher_name:" + teacher_name);
        System.out.println("course_name:" + course_name);

        // 获取所选课程所有页数
        List<Score> scores = courseDaoService.scoresStu(student_sno, student_name, teacher_name, course_name);
        String pageNoss = request.getParameter("pageNopss");
        int pageNo = Integer.parseInt(pageNoss);
        int pageSize = 10;
        // 显示分页的总页数
        int pageNos = scores.size() / 10;

        if (scores.size() % 10 != 0) {
            pageNos += 1;
        }
        System.out.println(scores.toString());
        // 分页显示记录
        List<Score> scoresPage = courseDaoService.scoresStuPage(student_sno, student_name, teacher_name, course_name,
                pageNo, pageSize);
        OnlineClassPage onlineClassPage = new OnlineClassPage();
        onlineClassPage.setPageNo(pageNos);
        onlineClassPage.setScore(scoresPage);
        return onlineClassPage;
    }

    /*
     * 功能：修改课程信息
     * 使用者：管理员
     * 调用方式：点击 课程信息-修改课程
     * 调用接口：(判断修改的课程时间是否合法) getCoursePages(可有可无)
     * 			classTimes(判断课程时间是否与其他课程冲突) coursesUpdate UpdateClassTime getCoursePages(返回更新后的)
     * */
    @RequestMapping(value = "/courses/coursesUpdate")
    @ResponseBody
    public OnlineClassPage co0ursesUpdate(HttpServletRequest request) {
        String course_cno = request.getParameter("course_cno");
        String course_name = request.getParameter("course_name");
        String course_information = request.getParameter("course_information");

        System.out.println(course_cno + "  " + course_name + "  " + course_information);

        // 获取课程时间
        String class_weekend = request.getParameter("class_weekend");
        String class_time = request.getParameter("class_time");

        System.out.println(class_time + "  " + class_weekend);

        ClassTime classTime = new ClassTime();
        classTime.setCourse_cno(course_cno);
        classTime.setClass_weekend(class_weekend);
        classTime.setClass_time(class_time);

        String pageNoss = request.getParameter("pageNopss");
        int pageNo = Integer.parseInt(pageNoss);
        List<Course> courses = courseDaoService.getCourse(course_cno, null);
        // 显示分页的总页数
        int pageNos = courses.size() / 10;
        int pageSize = 10;
        if (courses.size() % 10 != 0) {
            pageNos += 1;
        }
        if (Integer.parseInt(class_weekend) > 7 || Integer.parseInt(class_weekend) < 1 || Integer.parseInt(class_time) > 6 || Integer.parseInt(class_time) < 1) {
            System.out.println("修改失败！！！");
            List<Course> coursePage = courseDaoService.getCoursePages(course_cno, null, pageNo, pageSize);
            System.out.println(course_cno);
            System.out.println(coursePage);
            OnlineClassPage onlineClassPage = new OnlineClassPage();
            onlineClassPage.setCourse(coursePage);
            onlineClassPage.setPageNo(pageNos);
            onlineClassPage.setMessgae("0");

            return onlineClassPage;
        }
        List<ClassTime> classtimes = classTimeDaoService.classTimes(null, class_weekend, class_time);
        if (classtimes.size() > 0) {
            System.out.println("修改失败！！！");
            List<Course> coursePage = courseDaoService.getCoursePages(course_cno, null, pageNo, pageSize);
            System.out.println(course_cno);
            System.out.println(coursePage);
            OnlineClassPage onlineClassPage = new OnlineClassPage();
            onlineClassPage.setCourse(coursePage);
            onlineClassPage.setPageNo(pageNos);
            onlineClassPage.setMessgae("0");

            return onlineClassPage;
        }

        // 分页显示记录
        int courseUpdates = courseDaoService.coursesUpdate(course_name, course_cno, course_information);
        int classtimesUpdates = classTimeDaoService.UpdateClassTime(classTime);
        System.out.println(classtimesUpdates);
        if (courseUpdates > 0 && classtimesUpdates > 0) {
            System.out.println("修改成功");
            // 分页显示记录
            List<Course> coursePage = courseDaoService.getCoursePages(course_cno, null, pageNo, pageSize);
            // 返回数据打包
            OnlineClassPage onlineClassPage = new OnlineClassPage();
            onlineClassPage.setCourse(coursePage);
            onlineClassPage.setPageNo(pageNos);
            onlineClassPage.setMessgae("1");

            return onlineClassPage;
        }
        System.out.println("修改失败");
        List<Course> coursePage = courseDaoService.getCoursePages(course_cno, null, pageNo, pageSize);

        OnlineClassPage onlineClassPage = new OnlineClassPage();
        onlineClassPage.setCourse(coursePage);
        onlineClassPage.setPageNo(pageNos);
        onlineClassPage.setMessgae("0");
        return onlineClassPage;
    }

    /*
     * 功能：删除课程信息
     * 使用者：管理员
     * 调用方式：点击 课程信息-删除
     * 调用接口：scoresSel(判断课程是否被学生选择) SelTeacherss(判断课程是否被学生选择)
     * */
    @RequestMapping(value = "/courses/coursesDel")
    @ResponseBody
    public OnlineClassPage coursesDel(HttpServletRequest request) {
        String course_cno = request.getParameter("course_cno");

        String pageNoss = request.getParameter("pageNopss");
        int pageNo = Integer.parseInt(pageNoss);
        System.out.println(course_cno);
        // 判断课程是否被学生选择
        List<Score> scores = ScoreDaoService.scoresSel(null, course_cno);
        // 待处理
        List<Teacher> teachers = teacherDaoService.SelTeacherss(course_cno);
        if (scores.size() > 0 || teachers.size() > 0) {
            System.out.println("课程不可删除！！学生或教师已选择！！！");
            List<Course> coursePage = courseDaoService.getCoursePages(course_cno, null, 1, 12);
            // 返回数据打包
            OnlineClassPage onlineClassPage = new OnlineClassPage();
            onlineClassPage.setCourse(coursePage);
            onlineClassPage.setPageNo(1);
            onlineClassPage.setMessgae("0");
            return onlineClassPage;
        }

        // 判断是否被删除
        int coursedel = courseDaoService.coursesDel(course_cno);
        int classtime = classTimeDaoService.DelClassTime(course_cno);
        if (coursedel > 0 && classtime > 0) {
            System.out.println("删除成功");
            List<Course> courses = courseDaoService.getCourse(null, null);
            // 显示分页的总页数
            int pageNos = courses.size() / 10;
            int pageSize = 10;
            if (courses.size() % 10 != 0) {
                pageNos += 1;
            }
            // 分页显示记录
            List<Course> coursePage = courseDaoService.getCoursePages(null, null, pageNo, pageSize);
            // 返回数据打包
            OnlineClassPage onlineClassPage = new OnlineClassPage();
            onlineClassPage.setCourse(coursePage);
            onlineClassPage.setPageNo(pageNos);
            onlineClassPage.setMessgae("1");
            return onlineClassPage;
        }
        System.out.println("删除失败");
        // 分页显示记录
        List<Course> coursePage = courseDaoService.getCoursePages(course_cno, null, 1, 12);
        // 返回数据打包
        OnlineClassPage onlineClassPage = new OnlineClassPage();
        onlineClassPage.setCourse(coursePage);
        onlineClassPage.setPageNo(1);
        onlineClassPage.setMessgae("0");
        return onlineClassPage;
    }

    /*
     * 功能：添加课程信息
     * 使用者：管理员
     * 调用方式：点击 课程信息-添加课程
     * 调用接口：判断时间是否合法 getCourse、getCoursePages(可有可无)
     * 			getCourse classTimes 判断是否有课冲突
                coursesInsert InsertClassTime getCoursePages(返回更新后的)
     * */
    @RequestMapping(value = "/courses/coursesIns")
    @ResponseBody
    public OnlineClassPage coursesIns(HttpServletRequest request) {
        String course_cno = request.getParameter("course_cno");
        String course_name = request.getParameter("course_name");
        String course_information = request.getParameter("course_information");

        Course course = new Course();
        course.setCourse_cno(course_cno);
        course.setCourse_name(course_name);
        course.setCourse_information(course_information);

        System.out.println(course);
        // 获取课程时间
        String class_weekend = request.getParameter("class_weekend");
        String class_time = request.getParameter("class_time");

        ClassTime classTime = new ClassTime();
        classTime.setCourse_cno(course_cno);
        classTime.setClass_weekend(class_weekend);
        classTime.setClass_time(class_time);

        // 获取页面
        String pageNoss = request.getParameter("pageNopss");
        int pageNo = Integer.parseInt(pageNoss);
        if (Integer.parseInt(class_weekend) > 7 || Integer.parseInt(class_weekend) < 1 || Integer.parseInt(class_time) > 6 || Integer.parseInt(class_time) < 1) {
            List<Course> courses = courseDaoService.getCourse(null, null);
            int pageNos = courses.size() / 10;
            int pageSize = 10;
            if (courses.size() % 10 != 0) {
                pageNos += 1;
            }
            System.out.println("课程时间错误！！！");
            List<Course> coursePage = courseDaoService.getCoursePages(null, null, pageNo, pageSize);
            // 返回数据打包
            OnlineClassPage onlineClassPage = new OnlineClassPage();
            onlineClassPage.setCourse(coursePage);
            onlineClassPage.setPageNo(pageNos);
            onlineClassPage.setMessgae("0");
            return onlineClassPage;
        }

        List<Course> courses = courseDaoService.getCourse(course_cno, null);
        List<ClassTime> classTimes = classTimeDaoService.classTimes(null, class_weekend, class_time);
        List<ClassTime> classTimess = classTimeDaoService.classTimes(course_cno, "", "");
        if (courses.size() > 0 || classTimes.size() > 0 || classTimess.size() > 0) {
            // 显示分页的总页数
            int pageNos = courses.size() / 10;
            int pageSize = 10;
            if (courses.size() % 10 != 0) {
                pageNos += 1;
            }
            System.out.println("课程已存在");
            List<Course> coursePage = courseDaoService.getCoursePages(null, null, pageNo, pageSize);
            // 返回数据打包
            OnlineClassPage onlineClassPage = new OnlineClassPage();
            onlineClassPage.setCourse(coursePage);
            onlineClassPage.setPageNo(pageNos);
            onlineClassPage.setMessgae("0");
            return onlineClassPage;
        }
        int CourseInsert = courseDaoService.coursesInsert(course);
        int classtimesInsert = classTimeDaoService.InsertClassTime(classTime);
        // 显示分页的总页数
        if (CourseInsert > 0 && classtimesInsert > 0) {
            System.out.println("添加成功");
            // 分页显示记录

            List<Course> coursePage = courseDaoService.getCoursePages(course_cno, null, pageNo, 1);
            System.out.println(coursePage);
            // 返回数据打包
            OnlineClassPage onlineClassPage = new OnlineClassPage();
            onlineClassPage.setCourse(coursePage);
            onlineClassPage.setPageNo(1);
            onlineClassPage.setMessgae("1");
            return onlineClassPage;
        }
        int pageNos = courses.size() / 10;
        int pageSize = 10;
        if (courses.size() % 10 != 0) {
            pageNos += 1;
        }
        System.out.println("添加失败");
        List<Course> coursePage = courseDaoService.getCoursePages(null, null, pageNo, pageSize);
        // 返回数据打包
        OnlineClassPage onlineClassPage = new OnlineClassPage();
        onlineClassPage.setCourse(coursePage);
        onlineClassPage.setPageNo(pageNos);
        onlineClassPage.setMessgae("0");
        return onlineClassPage;
    }
}
