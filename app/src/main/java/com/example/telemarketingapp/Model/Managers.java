package com.example.telemarketingapp.Model;

public class Managers {
    String username,company_email,password,image;


    public Managers()
    {

    }
    public Managers(String username,String company_email,String password,String image)
    {
        this.username=username;
        this.company_email=company_email;
        this.password=password;
        this.image=image;

    }
    public void setUsername(String username)
    {
        this.username=username;
    }
    public String getUsername()
    {
        return this.username;
    }
    public void setCompany_email(String company_email)
    {
        this.company_email=company_email;
    }
    public String getCompany_email()
    {
        return this.company_email;
    }
    public void setPassword(String password)
    {
        this.password=password;
    }
    public String getPassword()
    {
        return this.password;
    }
    public void setImage(String image)
    {
        this.image=image;
    }
    public String getImage()
    {
        return this.image;
    }

}
