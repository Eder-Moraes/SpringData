package com.springdata.SpringData.services;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.springdata.SpringData.orm.Registro;
import org.springframework.stereotype.Service;

import com.springdata.SpringData.orm.Usuario;
import com.springdata.SpringData.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CrudUsuarioService {

	private UsuarioRepository usuarioRepository; // dependencia da classe CrudUsuarioService

	// O Spring automaticamente cria um objeto com a interface 'UsuarioRepository',
	// e o injeta para nós no construtor da classe atuaç ==> Injeção de depedência
	public CrudUsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Transactional
	//Foi colocado aqui tbm, assim como no visualizarUsuario, pois tem que indicar também que vai ter uma transação dentro do menu
	// é possível colocar a anotação acima na classe toda, para dizer que em toda classe pode haver transação
	public void menu(Scanner sc) {
		Boolean isTrue = true;

		while(isTrue) {
			System.out.println("\nQual ação você quer executar?");
			System.out.println("0 - Voltar");
			System.out.println("1 - Cadastrar novo usuario");
			System.out.println("2 - Atualizar um usuario");
			System.out.println("3 - Visualizar todos usuarios");
			System.out.println("4 - Deletar um usuarios");
			System.out.println("5 - Visualizar um usuario");

			int opcao = sc.nextInt();

			switch (opcao) {
				case 1:
					this.cadastrar(sc);
					break;
				case 2:
					this.atualizar(sc);
					break;
				case 3:
					for(Usuario user:this.visualizar()) {
						System.out.println(user);
					}
					break;
				case 4:
					this.deletar(sc);
					break;
				case 5:
					this.visualizarUsuario(sc);
					break;
				default:
					isTrue = false;
					break;
			}
		}
		System.out.println();
	}


	private void cadastrar(Scanner sc) {
		System.out.print("Digite o nivel de acesso do usuario: ");
		int nivel = sc.nextInt();

		System.out.print("Digite o nome do usuario: ");
		String nome = sc.next();

		System.out.print("Digite o nome do email: ");
		String email = sc.next();

		System.out.print("Digite o nome do senha: ");
		String senha = sc.next();

		Usuario user = new Usuario(nivel, nome, email, senha);
		this.usuarioRepository.save(user);
		System.out.println("Usuario salvo no banco!!\n");
	}

	private void atualizar(Scanner sc) {
		System.out.print("Digite o ID do usuario: ");
		int id = sc.nextInt();

		Optional<Usuario> userOptional = this.usuarioRepository.findById(id);

		//Se o hibernate conseguiu achar uma tupla/registro na tabela de usuario, igual ao id passo pelo usuario
		if(userOptional.isPresent()) {
			Usuario user = userOptional.get();
			System.out.print("Digite o nivel de acesso do usuario: ");
			int nivel = sc.nextInt();

			System.out.print("Digite o nome do usuario: ");
			String nome = sc.next();

			System.out.print("Digite o nome do email: ");
			String email = sc.next();

			System.out.print("Digite o nome do senha: ");
			String senha = sc.next();

			user.setNivel_acesso(nivel);
			user.setNome(nome);
			user.setEmail(email);
			user.setSenha(senha);
			this.usuarioRepository.save(user); //atualiza (persiste0 o objeto/registro/tupla no BD
			System.out.println("Usuario ID: "+id+" atualizado com sucesso!!");
		}
		else {
			System.out.println("[ERRO] - Usuário não encontrado. ID '"+id+"' é inválido");
		}
	}

	private List<Usuario> visualizar(){
		return (List<Usuario>) this.usuarioRepository.findAll();
	}

	private void deletar(Scanner sc) {
		System.out.print("Digite o id do usuário que quer deletar: ");
		int id = sc.nextInt();
		try {
			this.usuarioRepository.findById(id).get();
			this.usuarioRepository.deleteById(id);
			System.out.println("Usuário deletado com sucesso!!");
		}
		catch(Exception erro) {
			System.out.println("[ERRO] - O usuário id '"+id+"' não existe. "+erro);
		}
	}

	@Transactional //faz uma transação com o banco que não estava preparada. Você pode buscar dados, caso esteja no modo fetching preguiçoso (lazy)
	private void visualizarUsuario(Scanner sc) {
		System.out.print("Digite o id do usuario: ");
		int id = sc.nextInt();

		Optional<Usuario> usuarioOp = this.usuarioRepository.findById(id);

		if(usuarioOp.isPresent()) {
			Usuario user = usuarioOp.get();
			System.out.println("Nome: "+user.getNome());
			System.out.println("Nível: "+user.getNivel_acesso());
			System.out.println("Email: "+user.getEmail());
			System.out.println("Registro de movimentações:");
			List<Registro> registros = user.getRegistros();
			for(Registro registro:registros) {
				System.out.println("	id: "+registro.getId());
				System.out.println("	Data: "+registro.getData()+" - "+registro.getHora());
				System.out.println("	Descrição: "+registro.getDescricao()+"\n");
			}
		}
	}
}