package com.generation.esterblogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.esterblogpessoal.model.Usuario;
import com.generation.esterblogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UsuarioService usuarioService;

	@Test
	@Order(1)
	@DisplayName("Cadastrar um usuário")
	public void deveCriarUmUsuario() {
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(
				new Usuario(0L, "Ana Ester", "anandaester@gmail.com", "anaester123", "https://imgur.com/gallery/1tPQlzM", null));

		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao,
				Usuario.class);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
	}

	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {
		usuarioService.cadastrarUsuario(
				new Usuario(0L, "Elizama Jey", "lizzatop@gmail.com", "123lizaliza", null, null));

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(
				new Usuario(0L, "Elizama Jey", "lizzatop@gmail.com", "123lizaliza", null, null));

		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao,
				Usuario.class);
	}

	@Test
	@Order(3)
	@DisplayName("Alterar um Usuário")
	public void deveAtualizarUsuario() {
		Optional<Usuario> usuarioCreate = usuarioService.cadastrarUsuario(
				new Usuario(0L, "Ana Ester Ferr", "anandaesterferr@gmail.com", "anaesterferr123", "https://imgur.com/gallery/8r7Tu6p", null));

		Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(),"Ana Ester Costa", "anandaestercosta@gmail.com", "anaestercosta123", "https://imgur.com/gallery/3zAva9Y", null);

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);

		ResponseEntity<Usuario> resposta = testRestTemplate.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());
	}

	@Test
	@Order(3)
	@DisplayName("Listar todos os usuarios")
	public void deveMostrarTodosusuarios() {
		usuarioService.cadastrarUsuario(
				new Usuario(0L, "Tassy Walk", "walk@gmail.com", "walk123", "https://imgur.com/gallery/qsLtBb2", null));

		usuarioService.cadastrarUsuario(
				new Usuario(0L, "Tassy Moon", "moon@gmail.com", "moon123", "https://imgur.com/gallery/3zAva9Y", null));

		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root", "root").exchange("/usuarios/all",
				HttpMethod.GET, null, String.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());

	}

}
