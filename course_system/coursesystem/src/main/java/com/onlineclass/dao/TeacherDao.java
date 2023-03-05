package com.onlineclass.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.onlineclass.pojo.Score;
import com.onlineclass.pojo.Teacher;
import com.onlineclass.pojo.User;

/**
 * 
 * @author indext
 * @since 2019.10.21
 * @version 1.0
 */
@Repository(value = "teacherDao")
public interface TeacherDao {
	// 查询教师所有信息
	List<Teacher> SelTeacher(Teacher teacher);

	// 查询教师
	List<Teacher> SelTeacherss(@Param("course_cno") String course_cno);

	// 分页显示教师信息
	List<Teacher> SelTeacherPage(@Param("teacher_name") String teacher_name,
			@Param("teacher_tno") String teacher_tno, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

	// 修改教师信息
	int UpdataTeacher(Teacher teacher);

	// 删除教师信息
	int DelTeacher(@Param("teacher_tno") String teacher_tno);

	// 添加教师信息
	int AddTeacher(Teacher teacher);

	// 根据学号、班级、学生姓名查询教师选课人数
	List<Score> scoresTeacherChange(@Param("teacher_tno") String teacher_tno,
			@Param("student_name") String student_name, @Param("student_class") String student_class,
			@Param("student_sno") String student_sno);

	// 根据学号、班级、学生姓名分页查询教师选课人数
	List<Score> scoresTeacherChangePage(@Param("teacher_tno") String teacher_tno,
			@Param("student_name") String student_name, @Param("student_class") String student_class,
			@Param("student_sno") String student_sno, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
}
