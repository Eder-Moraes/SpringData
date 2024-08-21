package com.springdata.SpringData.orm;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int nivel_acesso;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false, unique = true)
	private String email;
	private String senha;

	//fetching(busca) de maneira lazy(preguiçosa) por padrão, ele não traz os dados
	//Você deve colocar eager(ansioso) para buscar todos esses dados, no caso, registros, mas sempre buscara esse dados
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY) //cascade = CascadeType.ALL - para apagar todos os registros caso o professor seja deletado
	private List<Registro> registros;

	@Deprecated // só para indicar que nós não usuremos muito, ou que ela será usada por outras bibliotecas externas
	public Usuario() {}

	public Usuario(int nivel_acesso, String nome, String email, String senha) {
		this.nivel_acesso = nivel_acesso;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}

	public int getNivel_acesso() {
		return nivel_acesso;
	}

	public void setNivel_acesso(int nivel_acesso) {
		this.nivel_acesso = nivel_acesso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getId() {
		return id;
	}


	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nivel_acesso=" + nivel_acesso + ", nome=" + nome + ", email=" + email
				+ ", senha=" + senha + "]";
	}

	@PreRemove
	public void atualizaRegistroOnDelete() {
		System.out.println("=========atualiza registros on delete=========");
		for (Registro registro : this.getRegistros()) {
			registro.setUsuario(null);
		}
	}
}