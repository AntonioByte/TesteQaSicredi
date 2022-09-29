package br.sicredi.simulacoes;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(1)
public class CriarSimulacao {

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/api/v1/simulacoes";
		
	}
	
	@Test
	public void criarSimulacao() {
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 97093236014,"
				+ "  \"email\": \"Fulaninho@hotmail.com\","
				+ "  \"valor\": 1250,"
				+ "  \"parcelas\": 2,"
				+ "  \"seguro\": true"
				+ "}";
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.post()
		.then()
			.log().all()
			.statusCode(201)
			.body("id", Matchers.is(Matchers.notNullValue()));
		
	}
	
	@Test
	public void criarSimulacaoMesmoCpf() {
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 97093236014,"
				+ "  \"email\": \"Fulaninho@hotmail.com\","
				+ "  \"valor\": 1250,"
				+ "  \"parcelas\": 3,"
				+ "  \"seguro\": true"
				+ "}";
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.post()
		.then()
			.log().all()
			.statusCode(400)
			.body("mensagem", Matchers.is("CPF duplicado"));
		
	}
	
	@Test
	public void criarSimulacaoNomeVazio() {
		
		String corpo = "{"
				+ "  \"nome\": null,"
				+ "  \"cpf\": 97093236014,"
				+ "  \"email\": \"Fulaninho@hotmail.com\","
				+ "  \"valor\": 1250,"
				+ "  \"parcelas\": 3,"
				+ "  \"seguro\": true"
				+ "}";
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.post()
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.nome", Matchers.is("Nome não pode ser vazio"));
		
	}
	
	@Test
	public void criarSimulacaoCpfVazio() {
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": null,"
				+ "  \"email\": \"Fulaninho@hotmail.com\","
				+ "  \"valor\": 1250,"
				+ "  \"parcelas\": 3,"
				+ "  \"seguro\": true"
				+ "}";
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.post()
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.cpf", Matchers.is("CPF não pode ser vazio"));
		
	}
	
	@Test
	public void criarSimulacaoEmailVazio() {
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 97093236014,"
				+ "  \"email\": null,"
				+ "  \"valor\": 1250,"
				+ "  \"parcelas\": 3,"
				+ "  \"seguro\": true"
				+ "}";
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.post()
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.email", Matchers.is("E-mail não deve ser vazio"));
		
	}
	
	@Test
	public void criarSimulacaoEmailSemArroba() {
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 97093236014,"
				+ "  \"email\": \"Fulaninho.hotmail.com\","
				+ "  \"valor\": 1250,"
				+ "  \"parcelas\": 3,"
				+ "  \"seguro\": true"
				+ "}";
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.post()
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.email", Matchers.is("E-mail deve ser um e-mail válido"));
		
	}
	
	@Test
	public void criarSimulacaoEmailSemCom() {
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 97093236014,"
				+ "  \"email\": \"Fulaninho@hotmail\","
				+ "  \"valor\": 1250,"
				+ "  \"parcelas\": 3,"
				+ "  \"seguro\": true"
				+ "}";
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.post()
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.email", Matchers.is("E-mail deve ser um e-mail válido"));
		
	}
	
	@Test
	public void criarSimulacaoValorVazio() {
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 97093236014,"
				+ "  \"email\": \"Fulaninho@hotmail.com\","
				+ "  \"valor\": null,"
				+ "  \"parcelas\": 3,"
				+ "  \"seguro\": true"
				+ "}";
		
		RestAssured
		.given()
		.contentType(ContentType.JSON)
		.body(corpo)
		.when()
		.post()
		.then()
		.log().all()
		.statusCode(400)
		.body("erros.valor", Matchers.is("Valor não pode ser vazio")); 
		
	}
	
	@Test
	public void criarSimulacaoValorMenorMil() {
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 97093236014,"
				+ "  \"email\": \"Fulaninho@hotmail.com\","
				+ "  \"valor\": 999,"
				+ "  \"parcelas\": 3,"
				+ "  \"seguro\": true"
				+ "}";
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.post()
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.valor", Matchers.is("Valor deve ser maior ou igual a R$ 1.000"));
		
	}
	
	@Test
	public void criarSimulacaoValorMaior40Mil() {
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 97093236014,"
				+ "  \"email\": \"Fulaninho@hotmail.com\","
				+ "  \"valor\": 40001,"
				+ "  \"parcelas\": 3,"
				+ "  \"seguro\": true"
				+ "}";
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.post()
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.valor", Matchers.is("Valor deve ser menor ou igual a R$ 40.000"));
		
	}
	
	
	@Test
	public void criarSimulacaoParcelasVazio() {
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 97093236015,"
				+ "  \"email\": \"Fulaninho@hotmail.com\","
				+ "  \"valor\": 39000,"
				+ "  \"parcelas\": null,"
				+ "  \"seguro\": true"
				+ "}";
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.post()
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.parcelas", Matchers.is("Parcelas não pode ser vazio"));
		
	}
	
	@Test
	public void criarSimulacaoParcelasMenorDois() {
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 97093236015,"
				+ "  \"email\": \"Fulaninho@hotmail.com\","
				+ "  \"valor\": 39000,"
				+ "  \"parcelas\": 1,"
				+ "  \"seguro\": true"
				+ "}";
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.post()
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.parcelas", Matchers.is("Parcelas deve ser igual ou maior que 2"));
		
	}

	@Test
	public void criarSimulacaoParcelasMaior48() {

		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 97093236015,"
				+ "  \"email\": \"Fulaninho@hotmail.com\","
				+ "  \"valor\": 39000,"
				+ "  \"parcelas\": 49,"
				+ "  \"seguro\": true"
				+ "}";

		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.post()
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.parcelas", Matchers.is("Parcelas deve ser igual ou menor que 48"));
		
	}
	
	@Test
	public void criarSimulacaoSeguroVazio() {
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Atlanta\","
				+ "  \"cpf\": 97093236016,"
				+ "  \"email\": \"fulaninho.a@hotmail.com\","
				+ "  \"valor\": 39000,"
				+ "  \"parcelas\": 10,"
				+ "  \"seguro\": null"
				+ "}";
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.post()
		.then()
			.log().all()
			.statusCode(201)
			.body("seguro", Matchers.is(false));
			//.statusCode(400)
			//.body("erros.seguro", Matchers.is("Uma das opõees de Seguro devem ser selecionadas")) 
	}
	
}



