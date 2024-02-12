package persistence.dto;

import java.util.List;

import persistence.entities.Company;

/**
 * Clase DTO de detalles de una película, para obtener datos de la API
 */
public class DetallesDTO {

	/** Adultos */
	private boolean adult;

	/** Presupuesto */
	private int budget;

	/** Id de la película */
	private int id;

	/** Compañías de la pelicula */
	private List<Company> productionCompanies;

	/** Ganancias */
	private int revenue;

	/** Media de votos */
	private double voteAverage;

	/**
	 * @return the adult
	 */
	public boolean isAdult() {
		return adult;
	}

	/**
	 * @param adult the adult to set
	 */
	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	/**
	 * @return the budget
	 */
	public int getBudget() {
		return budget;
	}

	/**
	 * @param budget the budget to set
	 */
	public void setBudget(int budget) {
		this.budget = budget;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the productionCompanies
	 */
	public List<Company> getProductionCompanies() {
		return productionCompanies;
	}

	/**
	 * @param productionCompanies the productionCompanies to set
	 */
	public void setProductionCompanies(List<Company> productionCompanies) {
		this.productionCompanies = productionCompanies;
	}

	/**
	 * 
	 * 
	 * /**
	 * 
	 * @return the revenue
	 */
	public int getRevenue() {
		return revenue;
	}

	/**
	 * @param revenue the revenue to set
	 */
	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}

	/**
	 * @return the voteAverage
	 */
	public double getVoteAverage() {
		return voteAverage;
	}

	/**
	 * @param voteAverage the voteAverage to set
	 */
	public void setVoteAverage(double voteAverage) {
		this.voteAverage = voteAverage;
	}

}
