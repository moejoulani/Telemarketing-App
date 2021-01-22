package com.example.telemarketingapp.Model;

import android.content.pm.PackageManager;

public class Tasks {
    String employee ,message,subject,manager,circle,id;

    public Tasks()
    {

    }
    public Tasks(String employee,String message,String subject,String manager,String circle,String id)
    {
        this.employee=employee;
        this.message=message;
        this.subject=subject;
        this.circle=circle;
        this.manager=manager;
        this.id=id;
    }

    public String getEmployee() {
        return employee;
    }
    public void setManager(String manager)
    {
        this.manager=manager;
    }
    public void setId(String id)
    {
        this.id=id;

    }
    public String getId()
    {
        return id;
    }
    public void setCircle(String circle)
    {
        this.circle=circle;
    }
    public String getCircle()
    {
        return this.circle;
    }
    public String getManager()
    {
        return this.manager;

    }
    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
