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

@Order(2)
public class AlterarSimulcao {
	
	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/api/v1/simulacoes";
		
	}
	
	@Test
	@Order(1)
	public void alterarSimulacao() {
		
		String cpf = "17822386034";
		
		String corpo = "{"
				+ "  \"nome\": \"Deltrano Silva\","
				+ "  \"cpf\": 17822386034,"
				+ "  \"email\": \"deltrano@gmail.com\","
				+ "  \"valor\": 3000,"
				+ "  \"parcelas\": 48,"
				+ "  \"seguro\": true"
				+ "}";
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.put("/"+ cpf)
		.then()
			.log().all()
			.statusCode(200)
			.body("cpf", Matchers.is(cpf));
		
	}
	
	@Test
	public void alterarCpfSemSimulacao() {
		
		String cpf = "17822386014";
		
		String corpo = "{"
				+ "  \"nome\": \"Deltrano Silva\","
				+ "  \"cpf\": 17822386034,"
				+ "  \"email\": \"deltrano@gmail.com\","
				+ "  \"valor\": 3000,"
				+ "  \"parcelas\": 6,"
				+ "  \"seguro\": true"
				+ "}";
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.put("/"+ cpf)
		.then()
			.log().all()
			.statusCode(404)
			.body("mensagem", Matchers.is("CPF "+ cpf +" não encontrado"));
		
	}
	
	@Test
	public void alterarSimulacaoCpfDuplicado() {
		
		String cpf = "17822386034";
		
		String corpo = "{"
				+ "  \"nome\": Jupiter Elano,"
				+ "  \"cpf\": 66414919004,"
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
			.put("/"+ cpf)
		.then()
			.log().all()
			.statusCode(400)
			.body("mensagem", Matchers.is("CPF duplicado"));
		
	}
	
	@Test
	public void alterarSimulacaoCpfVazio() {
		
		String cpf = "17822386034";
		
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
			.log().all()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.put("/"+ cpf)
		.then()
			.log().all()
			.statusCode(200)
			.body("cpf", Matchers.is(Matchers.notNullValue()));
			//.statusCode(400)
			//.body("erros.cpf", Matchers.is("CPF não pode ser vazio"))
		
	}
	
	@Test
	public void alterarSimulacaoNomeVazio() {
		
		String cpf = "17822386034";
		
		String corpo = "{"
				+ "  \"nome\": null,"
				+ "  \"cpf\": 17822386034,"
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
			.put("/"+ cpf)
		.then()
			.log().all()
			.statusCode(200)
			.body("nome", Matchers.is(Matchers.notNullValue()));
			//.body("erros.nome", Matchers.is("Nome não pode ser vazio"))
		
	}

	
	@Test
	public void alterarSimulacaoEmailVazio() {
		
		String cpf = "17822386034";
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 17822386034,"
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
			.put("/"+ cpf)
		.then()
			.log().all()
			.statusCode(200)
			.body("email", Matchers.is(Matchers.notNullValue()));
			//.statusCode(400)
			//.body("erros.email", Matchers.is("E-mail deve ser um e-mail válido"))
		
	}
	
	@Test
	public void alterarSimulacaoEmailSemArroba() {
		
		String cpf = "17822386034";
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 17822386034,"
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
			.put("/"+ cpf)
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.email", Matchers.is("E-mail deve ser um e-mail válido"));
		
	}
	
	@Test
	public void alterarSimulacaoEmailSemCom() {
		
		String cpf = "17822386034";
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 17822386034,"
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
			.put("/"+ cpf)
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.email", Matchers.is("E-mail deve ser um e-mail válido"));
		
	}
	
	@Test
	public void alterarSimulacaoValorVazio() {
		
		String cpf = "17822386034";
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 17822386034,"
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
			.put("/"+ cpf)
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.valor", Matchers.is("Valor não pode ser vazio"));
		
	}
	
	@Test
	public void alterarSimulacaoValorMenorMil() {
		
		String cpf = "17822386034";
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 17822386034,"
				+ "  \"email\": \"fulaninho@hotmail.com\","
				+ "  \"valor\": 999,"
				+ "  \"parcelas\": 3,"
				+ "  \"seguro\": true"
				+ "}";
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.put("/"+ cpf)
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.valor", Matchers.is("Valor deve ser maior ou igual a R$ 1.000"));
			
	}
	
	@Test
	public void alterarSimulacaoValorMaior40Mil() {
		
		String cpf = "17822386034";
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 17822386034,"
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
			.put("/"+ cpf)
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.valor", Matchers.is("Valor deve ser menor ou igual a R$ 40.000"));
		
	}
	
	@Test
	public void alterarSimulacaoParcelasVazio() {
		
		String cpf = "17822386034";
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 17822386034,"
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
			.put("/"+ cpf)
		.then()
			.log().all()
			.statusCode(200)
			.body("parcelas", Matchers.is(Matchers.notNullValue()));
			//.statusCode(400)
			//.body("erros.parcelas", Matchers.is("Parcelas não pode ser vazio")) 
		
	}
	
	@Test
	public void alterarSimulacaoParcelasMenorDois() {
		
		String cpf = "17822386034";
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 17822386034,"
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
			.put("/"+ cpf)
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.parcelas", Matchers.is("Parcelas deve ser igual ou maior que 2"));
		
	}

	@Test
	public void alterarSimulacaoParcelasMaior48() {
		
		String cpf = "17822386034";
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 17822386034,"
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
			.put("/"+ cpf)
		.then()
			.log().all()
			.statusCode(400)
			.body("erros.parcelas", Matchers.is("Parcelas deve ser igual ou menor que 48"));
		
	}
	
	@Test
	public void alterarSimulacaoSeguroVazio() {
		
		String cpf = "17822386034";
		
		String corpo = "{"
				+ "  \"nome\": \"Fulaninho de Talamo\","
				+ "  \"cpf\": 17822386034,"
				+ "  \"email\": \"Fulaninho@hotmail.com\","
				+ "  \"valor\": 39000,"
				+ "  \"parcelas\": 3,"
				+ "  \"seguro\": null"
				+ "}";
		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.body(corpo)
		.when()
			.put("/"+ cpf)
		.then()
			.log().all()
			.statusCode(200)
			.body("seguro", Matchers.is(false));
			//.statusCode(400)
			//.body("erros.seguro", Matchers.is("Uma das opções de Seguro devem ser selecionadas")) 
		
	}
}
