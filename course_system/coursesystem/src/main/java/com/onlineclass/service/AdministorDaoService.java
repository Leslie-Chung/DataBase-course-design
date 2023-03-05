package com.onlineclass.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.onlineclass.pojo.Score;
import com.onlineclass.pojo.User;

public interface AdministorDaoService {
	// 显示或查询所有用户
	List<User> AdminUser(@Param("user_name") String user_name, @Param("user_type") String user_type);

	// 分页显示或查询所有用户
	List<User> AdminUserPage(@Param("user_name") String user_name, @Param("user_type") String user_type,
			@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

	// 修改用户类型
	int AdminUpdateUser(User user);

	// 删除用户
	int AdminDelUser(@Param("user_name") String user_name);

}
