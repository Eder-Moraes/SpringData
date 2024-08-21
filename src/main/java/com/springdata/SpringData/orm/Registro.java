package com.springdata.SpringData.orm;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "registro")
public class Registro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private LocalDate data;
	@Column(nullable = false)
	private LocalTime hora;
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = true)
	private Usuario usuario;

	@ManyToMany
	@JoinTable(name = "registro_acoes",
			joinColumns = @JoinColumn(name = "registro_fk"),
			inverseJoinColumns = @JoinColumn(name = "acoes_fk")
	)
	List<Acoes> acoes;
	@Deprecated
	public Registro() {}

	public Registro(LocalDate data, LocalTime hora, String descricao, Usuario usuario) {
		this.data = data;
		this.hora = hora;
		this.descricao = descricao;
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Registro [id=" + id + ", data=" + data + ", hora=" + hora + ", descricao=" + descricao + ", usuario="
				+ usuario + "]";
	}


}