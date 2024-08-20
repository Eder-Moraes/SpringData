package com.springdata.SpringData;

import com.springdata.SpringData.services.CrudRegistroService;
import com.springdata.SpringData.services.CrudUsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

	private CrudUsuarioService usuarioService;
	private CrudRegistroService registroService;

	// os objetos passados por parâmetro são injetados automaticamente pelo spring
	// pq suas classes possuem anotações @Service
	public SpringDataApplication(CrudUsuarioService usuarioService, CrudRegistroService registroService) {
		this.usuarioService = usuarioService;
		this.registroService = registroService;
	}

	public static void main(String[] args) {
		SpringApplication.run(com.springdata.SpringData.SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Boolean isTrue = true;
		Scanner sc = new Scanner(System.in);

		while(isTrue) {
			System.out.println("Qual entidade você deseja interagir?");
			System.out.println("0 - Sair");
			System.out.println("1 - Usuario");
			System.out.println("2 - Registro");

			int opcao = sc.nextInt();

			switch (opcao) {
				case 1:
					this.usuarioService.menu(sc);
					break;
				case 2:
					this.registroService.menu(sc);
					break;
				default:
					isTrue = false;
			}
		}
	}

}
