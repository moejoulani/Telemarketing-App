package com.example.telemarketingapp.Model;

public class Prospects  {
    private String prosName,prosPhone,prosEmail,prosAddress,prosNotes,prosSource,company_email,Category,EmployeeSelected;

    public Prospects()
    {

    }
    public Prospects(String prosName,String prosPhone,String prosEmail,String prosAddress,String prosNotes,String prosSource,String company_email,String Category,String employeeSelected)
    {
        this.prosName=prosName;
        this.prosPhone=prosPhone;
        this.prosAddress=prosAddress;
        this.prosEmail=prosEmail;
        this.prosNotes=prosNotes;
        this.prosSource=prosSource;
        this.company_email=company_email;
        this.Category=Category;
        this.EmployeeSelected=employeeSelected;
    }

    public String getProsName() {
        return prosName;
    }

    public void setProsName(String prosName) {
        this.prosName = prosName;
    }

    public String getProsPhone() {
        return prosPhone;
    }

    public void setProsPhone(String prosPhone) {
        this.prosPhone = prosPhone;
    }

    public String getProsEmail() {
        return prosEmail;
    }

    public void setProsEmail(String prosEmail) {
        this.prosEmail = prosEmail;
    }

    public String getProsAddress() {
        return prosAddress;
    }

    public void setProsAddress(String prosAddress) {
        this.prosAddress = prosAddress;
    }

    public String getProsNotes() {
        return prosNotes;
    }

    public void setProsNotes(String prosNotes) {
        this.prosNotes = prosNotes;
    }

    public String getProsSource() {
        return prosSource;
    }

    public void setProsSource(String prosSource) {
        this.prosSource = prosSource;
    }

    public String getCompany_email() {
        return company_email;
    }

    public void setCompany_email(String company_email) {
        this.company_email = company_email;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getEmployeeSelected() {
        return EmployeeSelected;
    }

    public void setEmployeeSelected(String employeeSelected) {
        EmployeeSelected = employeeSelected;
    }
}
