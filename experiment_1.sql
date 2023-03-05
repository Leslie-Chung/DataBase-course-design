-- Student(Sno,Sname,Sgender,Sage,Sdept)
CREATE TABLE Test(No SMALLINT);
DROP TABLE Test;
CREATE TABLE Student
(
    Sno     CHAR(9)
        CONSTRAINT PK_Sno PRIMARY KEY,
    Sname   VARCHAR2(20)
        CONSTRAINT U_Sname UNIQUE,
    Sgender CHAR(4)
        CONSTRAINT C_Sgender CHECK (Sgender IN ('男', '女')),
    Sage    SMALLINT
        CONSTRAINT C_Sage CHECK (Sage > 0),
    Sdept   VARCHAR2(20)
);


-- Course(Cno,Cname,Cpno,Ccredit)
CREATE TABLE Course
(
    Cno     VARCHAR2(4)
        CONSTRAINT PK_Cno PRIMARY KEY,
    Cname   VARCHAR2(40)
        CONSTRAINT NN_Cname NOT NULL,
    Cpno    VARCHAR2(4),
    Ccredit SMALLINT
        CONSTRAINT C_Ccredit CHECK (Ccredit > 0),
    CONSTRAINT FK_Cno FOREIGN KEY (Cpno) REFERENCES Course(Cno)
);


-- SC(Sno,Cno,Grade)
CREATE TABLE SC
(
    Sno   CHAR(9),
    Cno   VARCHAR2(4),
    Grade SMALLINT 
        CONSTRAINT C_Grade CHECK (Grade >= 0 AND Grade <= 100),
    CONSTRAINT PK_Sno_Cno PRIMARY KEY (Sno, Cno),
    CONSTRAINT FK_SCSno FOREIGN KEY (Sno) REFERENCES Student(Sno),
    CONSTRAINT FK_SCCno FOREIGN KEY (Cno) REFERENCES Course(Cno)
);


-- DESC Student;

-- select * from user_tab_columns where Table_Name='Student';

ALTER TABLE Student
    ADD SBirth DATE;
ALTER TABLE Student
    DROP COLUMN SBirth;

--CREATE UNIQUE INDEX StuSno ON Student (Sno); Oracle会为 Primary key 和 unique 自动添加unique index索引，索引名是约束名
--CREATE UNIQUE INDEX CouCno ON Course (Cno);
CREATE UNIQUE INDEX SCno ON SC(Sno ASC, Cno DESC);
DROP INDEX SCno;

/* 
SELECT INDEX_NAME, INDEX_TYPE, TABLE_OWNER, TABLE_NAME, UNIQUENESS
FROM USER_INDEXES
WHERE TABLE_NAME = 'STUDENT'
   OR TABLE_NAME = 'COURSE'
   OR TABLE_NAME = 'SC';
*/


INSERT INTO S
VALUES ('201215121', '李勇', '男', 20, 'CS');
INSERT INTO S
VALUES ('201215122', '刘晨', '女', 19, 'CS');
INSERT INTO S
VALUES ('201215123', '王敏', '女', 18, 'MA');
INSERT INTO S
VALUES ('201215124', '张立', '男', 19, 'IS');



INSERT INTO Course (CNO, CNAME, CCREDIT)
VALUES (2, '数学', 2);
INSERT INTO Course  (CNO, CNAME, CCREDIT)
VALUES (6, '数据处理', 2);
INSERT INTO Course
VALUES (7, 'PASCAL 语言', 6, 4);
INSERT INTO Course
VALUES (4, '操作系统', 6, 3);
INSERT INTO Course
VALUES (5, '数据结构', 7, 4);
INSERT INTO Course
VALUES (1, '数据库', 5, 4);
INSERT INTO Course
VALUES (3, '信息系统', 1, 4);



INSERT INTO SC
VALUES ('201215121',1,92);
INSERT INTO SC
VALUES ('201215121',2,85);
INSERT INTO SC
VALUES ('201215121',3,88);
INSERT INTO SC
VALUES ('201215122',2,90);
INSERT INTO SC
VALUES ('201215122',3,80);

COMMIT;


