package br.sicredi.restricoes;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;


public class ConsultarRestricoes {
	
	@BeforeAll
	public static void setup() {
		
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/api/v1/restricoes"; 
	}
	
	@Test
	public void CpfSemRestricao() {
		String cpf = "12345678902";
		
		RestAssured
		.given()
		.when()
			.get("/" + cpf)
		.then()
			.log().all()
			.statusCode(204);
	}
	
	@Test
	public void cpfComRestricao() {
		
		String cpf = "97093236014";
		
		RestAssured
		.given()
		.when()
			.get("/" + cpf)
		.then()
			.log().all()
			.statusCode(200)
			.body("mensagem", Matchers.is("O CPF "+ cpf +" tem problema"));
	}
	
	

}
