package com.example.blackboard.ui.home;

public class Blackboard {
    public String teacher;
    public String className;
    public String subject;
    public String classImagepath;
    public String id;

    public Blackboard(String teacher, String className, String subject, String classImagepath, String id) {
        this.teacher = teacher;
        this.className = className;
        this.subject = subject;
        this.classImagepath = classImagepath;
        this.id = id;
    }

    public Blackboard(String teacher, String className, String subject, String classImagepath) {
        this.teacher = teacher;
        this.className = className;
        this.subject = subject;
        this.classImagepath = classImagepath;
    }

    public Blackboard() {
    }

    public String getTeacher() {
        return teacher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClassImagepath() {
        return classImagepath;
    }

    public void setClassImagepath(String classImagepath) {
        this.classImagepath = classImagepath;
    }
}
