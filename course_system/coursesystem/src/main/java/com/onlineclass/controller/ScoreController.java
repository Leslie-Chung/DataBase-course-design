package com.onlineclass.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onlineclass.pojo.Score;
import com.onlineclass.service.ScoreDaoService;

/**
 * @author indext
 * @version 1.0
 * @since 2019.11.06
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class ScoreController {
    @Autowired
    private ScoreDaoService scoreDaoService;

    /*
     * 功能：学生课程选择
     * 使用者：学生
     * 调用方式：点击 课程选择-确定选课
     * 调用接口：scoresSel(判断该课程是否已经被选) courseChange
     * */
    @RequestMapping(value = "/student/courseChange")
    @ResponseBody
    public boolean courseChange(@RequestBody JSONArray arr)
            throws ParseException {
        // 遍历选择课程的课程号
        for (int i = 0; i < arr.size(); i++) {
            JSONObject jsonObject = arr.getJSONObject(i);
            String teacher_tno = jsonObject.getString("teacher_tno");
            String course_cno = jsonObject.getString("course_cno");
            String sno = jsonObject.getString("userName");
            List<Score> scores = scoreDaoService.scoresSel(sno, course_cno);
            if (scores.size() > 0) {
                System.out.println("课程已存在！！");
            } else {
                int change = scoreDaoService.courseChange(sno, course_cno, teacher_tno);
                if (change > 0) {
                    System.out.println("选择成功");
                } else {
                    System.out.println("选课失败");
                }
            }
        }
        return true;
    }

}
