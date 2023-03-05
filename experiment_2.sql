select sno, sname from student where exists(select * from sc scx where scx.cno = '1' and scx.sno = student.sno);

select sno, sname from student where exists(select * from sc scx where exists(select * from course c where c.cname = '数据库' and c.cno = scx.cno and scx.sno = student.sno));

select sno, sname from student where not exists(select * from sc scx where scx.sno = student.sno and scx.cno = '1');

select sname from student where not exists(select * from course c where not exists(select * from sc scx where scx.sno = student.sno and scx.cno = c.cno));

