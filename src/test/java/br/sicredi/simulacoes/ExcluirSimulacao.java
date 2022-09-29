package br.sicredi.simulacoes;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;

import io.restassured.RestAssured;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(4)
public class ExcluirSimulacao {
	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/api/v1/simulacoes";
		
	}
	
	@Test
	public void exluirSimulacao() {
		
		
		
		String id = "12";
		
		RestAssured
		.given()
		.when()
			.delete("/"+ id)
		.then()
			.log().all()
			.statusCode(200)
			.body( Matchers.is("OK"));
		
	}
	
	@Test
	public void SimulacaoNaoEncontrada() {
		
		
		
		String id = "12";
		
		RestAssured
		.given()
		.when()
			.delete("/"+ id)
		.then()
			.log().all()
			.statusCode(404)
			.body("mensagem", Matchers.is("Simulação não encontrada"));
		
	}
}
