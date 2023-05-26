package br.ce.wcaquino.servicos;



import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;

public class LocacaoServiceTest {

	private LocacaoService service;


	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp(){
		service = new LocacaoService();
	}
	
	@Test
	public void testeLocacao() throws Exception {
		//cenario
		Usuario usuario = new Usuario("Usuario 1");

		List<Filme> filmes = new ArrayList<>();
		Filme filme1 = new Filme("Filme 1", 1, 5.0);
		filmes.add(filme1);
		Filme filme2 = new Filme("Filme 1", 1, 5.0);
		filmes.add(filme2);


		//acao
		Locacao locacao = service.alugarFilme(usuario, filmes);
			
		//verificacao
		error.checkThat(locacao.getValor(), is(equalTo(10.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}
	
	@Test(expected = Exception.class)
	public void testLocacao_filmeSemEstoque() throws Exception{
		//cenario
		Usuario usuario = new Usuario("Usuario 1");

		List<Filme> filmes = new ArrayList<>();
		Filme filme = new Filme("Filme 2", 0, 4.0);
		filmes.add(filme);
		
		//acao
		service.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void testLocacao_filmeSemEstoque_2() {
		//cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = new ArrayList<>();
		Filme filme = new Filme("Filme 2", 0, 4.0);
		filmes.add(filme);
		
		//acao
		try {
			service.alugarFilme(usuario, filmes);
			Assert.fail("Deveria ter lancado uma excecao");
		} catch (Exception e) {
			assertThat(e.getMessage(), is("java.lang.Exception: Filme sem estoque"));
		}
	}
	

	@Test
	public void testLocacao_filmeSemEstoque_3() throws Exception {
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");

		List<Filme> filmes = new ArrayList<>();
		Filme filme = new Filme("Filme 2", 0, 4.0);
		filmes.add(filme);

		
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
		
		//acao
		service.alugarFilme(usuario, filmes);
	}
}
