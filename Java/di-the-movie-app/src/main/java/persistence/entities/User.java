package persistence.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un usuario de la tabla usuario en la base de datos
 */
@Entity
@Table(name = "usuario")
public class User {

	/** Id del usuario */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/** Email del usuario */
	@Column(name = "email")
	private String email;

	/** Contraseña de usuario */
	@Column(name = "password")
	private String password;

	/** Peliculas del usuario */
	@OneToMany(mappedBy = "id.usuario", cascade = CascadeType.ALL)
	private List<UsuarioPelicula> usuarioPelicula;

	/**
	 * 
	 */
	public User() {

	}

	/**
	 * 
	 * @param email
	 * @param password
	 */
	public User(String email, String password) {

		this.email = email;
		this.password = password;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
