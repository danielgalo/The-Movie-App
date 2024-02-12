package dto;

/**
 * DTO usado para obtener las personas que aparecen en los créditos de una
 * película a través de la API
 */
public class PersonaCreditosDTO {

	private String name;

	private String knownForDepartment;

	/**
	 * @param name
	 * @param knownForDepartment
	 */
	public PersonaCreditosDTO(String name, String knownForDepartment) {
		this.name = name;
		this.knownForDepartment = knownForDepartment;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the knownForDepartment
	 */
	public String getKnownForDepartment() {
		return knownForDepartment;
	}

	/**
	 * @param knownForDepartment the knownForDepartment to set
	 */
	public void setKnownForDepartment(String knownForDepartment) {
		this.knownForDepartment = knownForDepartment;
	}

}
