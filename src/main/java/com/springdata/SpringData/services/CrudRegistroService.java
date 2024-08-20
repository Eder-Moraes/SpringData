package com.springdata.SpringData.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.springdata.SpringData.orm.Registro;
import com.springdata.SpringData.orm.Usuario;
import com.springdata.SpringData.repository.RegistroRepository;
import com.springdata.SpringData.repository.UsuarioRepository;

@Service
public class CrudRegistroService {

	private RegistroRepository registroRepository; // dependencia da classe CrudUsuarioService
	private UsuarioRepository usuarioRepository;

	// O Spring automaticamente cria um objeto com a interface 'RegistroRepository',
	// e o injeta para nós no construtor da classe atuaç ==> Injeção de depedência
	public CrudRegistroService(RegistroRepository registroRepository, UsuarioRepository usuarioRepository) {
		this.registroRepository = registroRepository;
		this.usuarioRepository = usuarioRepository;
	}


	public void menu(Scanner sc) {
		Boolean isTrue = true;

		while(isTrue) {
			System.out.println("\nQual ação você quer executar?");
			System.out.println("0 - Voltar");
			System.out.println("1 - Cadastrar novo registro");
			System.out.println("2 - Atualizar um registro");
			System.out.println("3 - Visualizar todos registro");
			System.out.println("4 - Deletar um registro");

			int opcao = sc.nextInt();

			switch (opcao) {
				case 1:
					this.cadastrar(sc);
					break;
				case 2:
					this.atualizar(sc);
					break;
				case 3:
					for(Registro registro:this.visualizar()) {
						System.out.println(registro);
					}
					break;
				case 4:
					this.deletar(sc);
					break;
				default:
					isTrue = false;
					break;
			}
		}
		System.out.println();
	}


	private void cadastrar(Scanner sc) {

		System.out.print("Digite o descrição do registro: ");
		String descricao = sc.next();

		sc.nextLine();
		System.out.print("Digite o id do usuario que fez o registro: ");
		int id = sc.nextInt();

		try {
			Registro registro = new Registro(LocalDate.now(), LocalTime.now(), descricao, this.usuarioRepository.findById(id).get());
			this.registroRepository.save(registro);
			System.out.println("Usuario salvo no banco!!\n");
		}
		catch(Exception erro) { System.out.println("[ERRO] - Usuario id'"+id+"' não encontrado");}
	}

	private void atualizar(Scanner sc) {
		System.out.print("Digite o ID do registro: ");
		int id = sc.nextInt();

		Optional<Registro> registroOptional = this.registroRepository.findById(id);

		//Se o hibernate conseguiu achar uma tupla/registro na tabela de usuario, igual ao id passo pelo usuario
		if(registroOptional.isPresent()) {
			Registro registro = registroOptional.get();
			System.out.print("Digite o descrição do registro: ");
			String descricao = sc.next();

			sc.nextLine();
			System.out.print("Digite o id do usuario que fez o registro: ");
			int id_user = sc.nextInt();
			Optional<Usuario> userOptional = this.usuarioRepository.findById(id_user);

			if(userOptional.isPresent()) {
				registro.setDescricao(descricao);
				registro.setUsuario(userOptional.get());
				this.registroRepository.save(registro); //atualiza (persiste0 o objeto/registro/tupla no BD
				System.out.println("Usuario ID: "+id+" atualizado com sucesso!!");
			}
			else System.out.println("[ERRO] - Usuário não encontrado. ID '"+id_user+"' é inválido");

		}
		else {
			System.out.println("[ERRO] - Registro não encontrado. ID '"+id+"' é inválido");
		}
	}

	private List<Registro> visualizar(){
		return (List<Registro>) this.registroRepository.findAll();
	}

	private void deletar(Scanner sc) {
		System.out.print("Digite o id do registro que quer deletar: ");
		int id = sc.nextInt();
		try {
			this.registroRepository.findById(id).get();
			this.registroRepository.deleteById(id);
			System.out.println("Registro deletado com sucesso!!");
		}
		catch(Exception erro) {
			System.out.println("[ERRO] - O registro id '"+id+"' não existe. "+erro);
		}
	}
}