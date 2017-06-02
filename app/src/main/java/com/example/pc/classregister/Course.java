package com.example.pc.classregister;

/**
 * Created by pc on 2017-03-28.
 */

public class Course {

    int courseID; // 강의 고유번호
    String courseUniversity; // 학부 혹은 대학원
    int courseYear; // 해당 년도
    String courseTerm; // 해당 학기
    String courseArea; // 강의 영역 (전공, 교양)
    String courseMajor; // 해당 학과
    String courseGrade; // 해당 학년
    String courseTitle; // 강의 제목
    int courseCredit; // 강의 학점
    int coursePersonnel; // 강의 인원
    String courseProfessor; // 강의 교수
    String courseTime; // 강의 시간
    String courseRoom; // 강의실
    int courseRival; // 강의 경쟁자 수

    public int getCourseID() {
        return courseID;
    }

    public String getCourseUniversity() {
        return courseUniversity;
    }

    public int getCourseYear() {
        return courseYear;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public String getCourseArea() {
        return courseArea;
    }

    public String getCourseMajor() {
        return courseMajor;
    }

    public String getCourseGrade() {
        return courseGrade;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public int getCoursePersonnel() {
        return coursePersonnel;
    }

    public String getCourseProfessor() {
        return courseProfessor;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public Course(int courseID, String courseGrade, String courseTitle, int coursePersonnel, int courseRival) {
        this.courseID = courseID;
        this.courseGrade = courseGrade;
        this.courseTitle = courseTitle;
        this.coursePersonnel = coursePersonnel;
        this.courseRival = courseRival;
    }

    public Course(int courseID, String courseGrade, String courseTitle, int coursePersonnel, int courseRival, int courseCredit) {
        this.courseID = courseID;
        this.courseGrade = courseGrade;
        this.courseTitle = courseTitle;
        this.coursePersonnel = coursePersonnel;
        this.courseRival = courseRival;
        this.courseCredit = courseCredit;
    }

    public int getCourseRival() {
        return courseRival;
    }

    public void setCourseRival(int courseRival) {
        this.courseRival = courseRival;
    }

    public Course(int courseID, String courseUniversity, int courseYear, String courseTerm, String courseArea, String courseMajor, String courseGrade, String courseTitle, int courseCredit, int coursePersonnel, String courseProfessor, String courseTime, String courseRoom) {
        this.courseID = courseID;
        this.courseUniversity = courseUniversity;
        this.courseYear = courseYear;
        this.courseTerm = courseTerm;
        this.courseArea = courseArea;
        this.courseMajor = courseMajor;
        this.courseGrade = courseGrade;
        this.courseTitle = courseTitle;
        this.courseCredit = courseCredit;
        this.coursePersonnel = coursePersonnel;
        this.courseProfessor = courseProfessor;
        this.courseTime = courseTime;
        this.courseRoom = courseRoom;
    }

    public Course(int courseID, String courseUniversity, int courseYear, String courseTerm, String courseArea, String courseMajor, String courseGrade, String courseTitle, int courseCredit, int coursePersonnel, String courseProfessor, String courseTime, String courseRoom, int courseRival) {
        this.courseID = courseID;
        this.courseUniversity = courseUniversity;
        this.courseYear = courseYear;
        this.courseTerm = courseTerm;
        this.courseArea = courseArea;
        this.courseMajor = courseMajor;
        this.courseGrade = courseGrade;
        this.courseTitle = courseTitle;
        this.courseCredit = courseCredit;
        this.coursePersonnel = coursePersonnel;
        this.courseProfessor = courseProfessor;
        this.courseTime = courseTime;
        this.courseRoom = courseRoom;
        this.courseRival = courseRival;
    }


}
