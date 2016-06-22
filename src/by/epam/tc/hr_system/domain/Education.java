package by.epam.tc.hr_system.domain;

import java.io.Serializable;
import java.sql.Date;

public class Education implements Serializable  {
	private int id;
	private int idPerson;
	private String institution;
	private String departament;
	private String specialty;
	private String formEduction;
	private Date dateEntry;
	private Date dateGraduation;

	public Education() {

	}

	public Education(int id, int idPerson, String institution,
			String departament, String specialty, String formEduction,
			Date dateEntry, Date dateGraduation) {
		this.id = id;
		this.idPerson = idPerson;
		this.institution = institution;
		this.departament = departament;
		this.specialty = specialty;
		this.formEduction = formEduction;
		this.dateEntry = dateEntry;
		this.dateGraduation = dateGraduation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getDepartament() {
		return departament;
	}

	public void setDepartament(String departament) {
		this.departament = departament;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getFormEduction() {
		return formEduction;
	}

	public void setFormEduction(String formEduction) {
		this.formEduction = formEduction;
	}

	public Date getDateEntry() {
		return dateEntry;
	}

	public void setDateEntry(Date dateEntry) {
		this.dateEntry = dateEntry;
	}

	public Date getDateGraduation() {
		return dateGraduation;
	}

	public void setDateGraduation(Date dateGraduation) {
		this.dateGraduation = dateGraduation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateEntry == null) ? 0 : dateEntry.hashCode());
		result = prime * result
				+ ((dateGraduation == null) ? 0 : dateGraduation.hashCode());
		result = prime * result
				+ ((departament == null) ? 0 : departament.hashCode());
		result = prime * result
				+ ((formEduction == null) ? 0 : formEduction.hashCode());
		result = prime * result + id;
		result = prime * result + idPerson;
		result = prime * result
				+ ((institution == null) ? 0 : institution.hashCode());
		result = prime * result
				+ ((specialty == null) ? 0 : specialty.hashCode());
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
		Education other = (Education) obj;
		if (dateEntry == null) {
			if (other.dateEntry != null)
				return false;
		} else if (!dateEntry.equals(other.dateEntry))
			return false;
		if (dateGraduation == null) {
			if (other.dateGraduation != null)
				return false;
		} else if (!dateGraduation.equals(other.dateGraduation))
			return false;
		if (departament == null) {
			if (other.departament != null)
				return false;
		} else if (!departament.equals(other.departament))
			return false;
		if (formEduction == null) {
			if (other.formEduction != null)
				return false;
		} else if (!formEduction.equals(other.formEduction))
			return false;
		if (id != other.id)
			return false;
		if (idPerson != other.idPerson)
			return false;
		if (institution == null) {
			if (other.institution != null)
				return false;
		} else if (!institution.equals(other.institution))
			return false;
		if (specialty == null) {
			if (other.specialty != null)
				return false;
		} else if (!specialty.equals(other.specialty))
			return false;
		return true;
	}

}
