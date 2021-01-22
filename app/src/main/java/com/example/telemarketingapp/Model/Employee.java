package com.example.telemarketingapp.Model;

public class Employee   {

    String username,email,password,company_email,image;

        public Employee()
        {

        }
        public Employee(String username,String email,String password,String company_email,String image)
        {
                this.username=username;
                this.email=email;
                this.password=password;
                this.company_email=company_email;
                this.image=image;
        }
        public void setCompany_email(String company_email)
        {
            this.company_email=company_email;
        }
        public String getCompany_email()
        {
            return this.company_email;
        }
        public void setUsername(String username)
        {
            this.username=username;
        }
        public String getUsername()
        {
            return this.username;
        }
        public void setEmail(String email)
        {
            this.email=email;
        }
        public String getEmail()
        {
            return this.email;
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
