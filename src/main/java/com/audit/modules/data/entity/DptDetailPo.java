package com.audit.modules.data.entity;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author : 袁礼斌
 * @Description : OA部门数据详情
 * @date : 2017/4/18
 * Copyright (c) , IsoftStone All Right reserved.
 */
public class DptDetailPo {
	
	//部门编号
	private String departmentNo;
	
	private String departmentParentNo;
	
	private String departmentName;
	
	private String companyCode;
	
	private String status;
	
	private String visible;
	
	private String areaCode;
	
	private String areaName;

	private Integer departmentLevel;
	
	private String departmentOrder;
	
	private String employees;

    //@XmlElement(name = "Department_No") 节点属性用法 
    //节点
    @XmlElement(name = "Department_No")
	public String getDepartmentNo() {
		return departmentNo;
	}
	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}
	
	@XmlElement(name = "Department_Parent_No")
	public String getDepartmentParentNo() {
		return departmentParentNo;
	}

	public void setDepartmentParentNo(String departmentParentNo) {
		this.departmentParentNo = departmentParentNo;
	}

	@XmlElement(name = "Department_Name")
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@XmlElement(name = "Company_Code")
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@XmlElement(name = "Status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@XmlElement(name = "Visible")
	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	@XmlElement(name = "Area_Code")
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@XmlElement(name = "Area_Name")
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@XmlElement(name = "Department_Order")
	public String getDepartmentOrder() {
		return departmentOrder;
	}

	public void setDepartmentOrder(String departmentOrder) {
		this.departmentOrder = departmentOrder;
	}
	
	@XmlElement(name = "Employees")
	public String getEmployees() {
		return employees;
	}

	public void setEmployees(String employees) {
		this.employees = employees;
	}

	@XmlElement(name = "Department_Level")
	public Integer getDepartmentLevel() {
		return departmentLevel;
	}

	public void setDepartmentLevel(Integer departmentLevel) {
		this.departmentLevel = departmentLevel;
	}

	@Override
	public String toString() {
		return "DepartmentPo [departmentNo=" + departmentNo + ", departmentParentNo=" + departmentParentNo
				+ ", departmentName=" + departmentName + ", companyCode=" + companyCode + ", status=" + status
				+ ", visible=" + visible + ", areaCode=" + areaCode + ", areaName=" + areaName + ", departmentOrder="
				+ departmentOrder + ", employees=" + employees + "]";
	}

}
