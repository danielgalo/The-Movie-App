package persistence.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@MappedSuperclass
public abstract class AbstractEntity {

	@Column(name = "fecha_de_alta", nullable = false, updatable = false)
	private Date fechaDeAlta;

	@Column(name = "ultima_fecha_modificacion")
	private Date ultimaFechaModificacion;

	public Date getFechaDeAlta() {
		return fechaDeAlta;
	}

	public void setFechaDeAlta(Date fechaDeAlta) {
		this.fechaDeAlta = fechaDeAlta;
	}

	public Date getUltimaFechaModificacion() {
		return ultimaFechaModificacion;
	}

	public void setUltimaFechaModificacion(Date ultimaFechaModificacion) {
		this.ultimaFechaModificacion = ultimaFechaModificacion;
	}

	@PrePersist
	protected void onCreate() {
		Date currentDate = new Date();
		fechaDeAlta = currentDate;
		ultimaFechaModificacion = currentDate;
	}

	@PreUpdate
	protected void onUpdate() {
		ultimaFechaModificacion = new Date();
	}
}
