package com.onlineclass.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.onlineclass.pojo.Student;

/**
 * @author indext
 * @version 1.0
 * @since 2019.10.21
 */
public interface StudentDaoService {
    // 查询学生所有信息
    List<Student> SelStudent(Student student);

    // 修改学生信息
    int UpdataStudent(Student student);

    // 删除学生信息
    int DelStudent(@Param("student_sno") String student_sno);

    // 分页显示学生信息
    List<Student> SelStudentPage(@Param("student_sno") String student_sno,
                                 @Param("student_name") String student_name, @Param("student_class") String student_class,
                                 @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

}
