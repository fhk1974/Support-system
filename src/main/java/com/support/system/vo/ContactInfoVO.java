package com.support.system.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public class ContactInfoVO {

    private String phone;
    private String department;

    public ContactInfoVO() {
    }

    public ContactInfoVO(String phone, String department) {
        this.phone = phone;
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
