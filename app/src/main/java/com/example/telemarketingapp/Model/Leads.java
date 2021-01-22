package com.example.telemarketingapp.Model;

public class Leads {
    public String leadName,leadPhone,leadEmail,leadAddress,leadSource,leadNotes,leadCategory,company_email,EmployeeSelected;
    public Leads()
    {

    }
    public Leads(String leadName,String leadPhone,String leadEmail,String leadAddress,String leadSource,String leadNotes,String leadCategory,String company_email,String employee_selected)
    {
        this.leadName=leadName;
        this.leadPhone=leadPhone;
        this.leadEmail=leadEmail;
        this.leadAddress=leadAddress;
        this.leadSource=leadSource;
        this.leadNotes=leadNotes;
        this.leadCategory=leadCategory;
        this.company_email=company_email;
        this.EmployeeSelected=employee_selected;

    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public String getLeadPhone() {
        return leadPhone;
    }

    public void setLeadPhone(String leadPhone) {
        this.leadPhone = leadPhone;
    }

    public String getLeadEmail() {
        return leadEmail;
    }

    public void setLeadEmail(String leadEmail) {
        this.leadEmail = leadEmail;
    }

    public String getLeadAddress() {
        return leadAddress;
    }

    public void setLeadAddress(String leadAddress) {
        this.leadAddress = leadAddress;
    }

    public String getLeadSource() {
        return leadSource;
    }

    public void setLeadSource(String leadSource) {
        this.leadSource = leadSource;
    }

    public String getLeadNotes() {
        return leadNotes;
    }

    public void setLeadNotes(String leadNotes) {
        this.leadNotes = leadNotes;
    }

    public String getLeadCategory() {
        return leadCategory;
    }

    public void setLeadCategory(String leadCategory) {
        this.leadCategory = leadCategory;
    }
    public void setCompany_email(String company_email)
    {
        this.company_email=company_email;
    }
    public String getCompany_email()
    {
        return this.company_email;
    }
    public void setEmployee_selected(String employee_selected)
    {
        this.EmployeeSelected=employee_selected;
    }
    public String getEmployee_selected()
    {
        return this.EmployeeSelected;
    }
}
