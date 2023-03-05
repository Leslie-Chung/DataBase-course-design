package com.onlineclass.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.onlineclass.pojo.Student;
import com.onlineclass.pojo.Teacher;
import com.onlineclass.pojo.User;

/**
 * 
 * @author indext
 * @since 2019.10.10
 * @version 1.0
 */

public interface UserDaoService {
	// 用户登录
	List<User> userLogin(User user);

	// 用户注册
	int userRegister(User user);

	// 学生用户注册
	int StudentRegister(Student student);

	// 判断用户是否已存在
	List<User> useConflict(@Param("username") String username);

	// 教师信息注册
	int TeacherRegister(Teacher teacher);

	// 忘记密码
	List<Student> SelStudent(@Param("studentSno") String studentSno,
			@Param("studentTelphone") String studentTelphone);

	// 修改登录用户
	int UpdataUser(User user);

	// 查询用户
	List<User> SelUser(@Param("user_name") String user_name);


}
