package com.generation.esterblogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.esterblogpessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository repository;

	@BeforeAll
	public void star() {
		repository.save(new Usuario(0L, "Ester", "ester@gmail.com", "ester123", "https://imgur.com/gallery/1tPQlzM", null));

		repository.save(new Usuario(0L, "Jeny", "jeny@gmail.com", "uiuiui678", "https://imgur.com/gallery/8r7Tu6p", null));

		repository.save(new Usuario(0L, "Alan", "alan@gmail.com", "alanzoka321", "https://imgur.com/gallery/qsLtBb2", null));

		repository.save(new Usuario(0L, "Neymar", "meninoney@gmail.com", "ovomole12", "https://imgur.com/gallery/3zAva9Y", null));
	}

	@Test
	@DisplayName("Teste que retorna 1 usuario")
	public void retornaUmUsuario() {
		Optional<Usuario> usuario = repository.findByUsuario("ester@gmail.com");
		assertTrue(usuario.get().getUsuario().equals("ester@gmail.com"));
		
	}

	@Test
	@DisplayName("Teste que retorna 1 usuario pelo nome")
	public void retornaUmUsuarioPeloNome() {
		List<Usuario> usuario = repository.findAllByNomeContainingIgnoreCase("Ester");
		assertTrue(usuario.get(0).getUsuario().equals("Ester"));
	}

	@AfterAll
	public void end() {
		repository.deleteAll();
	}
}