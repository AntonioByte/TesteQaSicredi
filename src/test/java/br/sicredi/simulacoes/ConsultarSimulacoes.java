package br.sicredi.simulacoes;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;

import io.restassured.RestAssured;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(3)
public class ConsultarSimulacoes {

	@BeforeAll
	public static void setup() {
		
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/api/v1/simulacoes"; 
	}
	
	@Test
	public void consultarSimulacoes() {
		
		RestAssured
		.given()
		.when()
			.get()
		.then()
			.log().all()
			.statusCode(200)
		;
	}

	@Test
	public void consultarSemSimulacoes() {
		
		RestAssured
		.given()
		.when()
			.get()
		.then()
			.log().all()
			.statusCode(404)
		;
	}
	
	@Test
	public void consultaSimulacaoCpf() {
		
		String cpf = "17822386034";
		
		RestAssured
		.given()
		.when()
			.get("/"+ cpf)
		.then()
			.log().all()
			.statusCode(200)
		;
	}
	
	@Test
	public void cpfSemSimulacao() {
		
		String cpf = "17822386035";
		
		RestAssured
		.given()
		.when()
			.get("/"+ cpf)
		.then()
			.log().all()
			.statusCode(404)
			.body("mensagem", Matchers.is("CPF "+ cpf +" não encontrado"))
		;
	}
	
}
