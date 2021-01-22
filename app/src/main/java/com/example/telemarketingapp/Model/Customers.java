package com.example.telemarketingapp.Model;

public class Customers {

        private String custName,custPhone,custAddress,custEmail,custNotes,custSource,Category,company_email;

        public Customers()
        {

        }
        public Customers(String custName, String custPhone, String custAddress, String custEmail, String custNotes, String custSource)
        {

        }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustNotes() {
        return custNotes;
    }

    public void setCustNotes(String custNotes) {
        this.custNotes = custNotes;
    }

    public String getCustSource() {
        return custSource;
    }

    public void setCustSource(String custSource) {
        this.custSource = custSource;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCompany_email() {
        return company_email;
    }

    public void setCompany_email(String company_email) {
        this.company_email = company_email;
    }
}
