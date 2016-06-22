package by.epam.tc.hr_system.domain;

import java.io.Serializable;
import java.sql.Date;

public class Vacancy implements Serializable{
	private int id;
	private String name;
	private String descrption;
	private String requirement;
	private String company;
	private int salary;
	private Date dateSubmission;
	private String status;
	
	public Vacancy(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescrption() {
		return descrption;
	}

	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Date getDateSubmission() {
		return dateSubmission;
	}

	public void setDateSubmission(Date dateSubmission) {
		this.dateSubmission = dateSubmission;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((dateSubmission == null) ? 0 : dateSubmission.hashCode());
		result = prime * result
				+ ((descrption == null) ? 0 : descrption.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((requirement == null) ? 0 : requirement.hashCode());
		result = prime * result + salary;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vacancy other = (Vacancy) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (dateSubmission == null) {
			if (other.dateSubmission != null)
				return false;
		} else if (!dateSubmission.equals(other.dateSubmission))
			return false;
		if (descrption == null) {
			if (other.descrption != null)
				return false;
		} else if (!descrption.equals(other.descrption))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (requirement == null) {
			if (other.requirement != null)
				return false;
		} else if (!requirement.equals(other.requirement))
			return false;
		if (salary != other.salary)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

}
