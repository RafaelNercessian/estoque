package br.com.cabolider.mb;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.cabolider.dao.EntradaDao;
import br.com.cabolider.dao.ProdutoDao;
import br.com.cabolider.modelo.Entrada;
import br.com.cabolider.modelo.Produto;

@Named
@ViewScoped
public class EntradaBeanCopia implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Produto produto = new Produto();
	private Produto produtoASerInserido=new Produto();
	private Entrada produtoDeEntrada=new Entrada();
	private Integer valorASerInserido;
	private List<Entrada> itensDeEntrada;
	private ProdutoDao produtoDao;
	private EntradaDao entradaDao;
	private List<Produto> retornoPesquisaPeloCodigo;
	private UIComponent inserir;
	private UIComponent novoProduto;
	
	public EntradaBeanCopia(ProdutoDao produtoDao,EntradaDao entradaDao){
		this.produtoDao=produtoDao;
		this.entradaDao=entradaDao;
	}

	public UIComponent getNovoProduto() {
		return novoProduto;
	}

	public void setNovoProduto(UIComponent novoProduto) {
		this.novoProduto = novoProduto;
	}

	public UIComponent getInserir() {
		return inserir;
	}

	public void setInserir(UIComponent inserir) {
		this.inserir = inserir;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Produto getProdutoASerInserido() {
		return produtoASerInserido;
	}

	public void setProdutoASerInserido(Produto produtoASerInserido) {
		this.produtoASerInserido = produtoASerInserido;
	}

	public Entrada getProdutoDeEntrada() {
		return produtoDeEntrada;
	}

	public void setProdutoDeEntrada(Entrada produtoDeEntrada) {
		this.produtoDeEntrada = produtoDeEntrada;
	}

	public Integer getValorASerInserido() {
		return valorASerInserido;
	}

	public void setValorASerInserido(Integer valorASerInserido) {
		this.valorASerInserido = valorASerInserido;
	}

	public void produtoASerInserido() throws IOException {
		produtoASerInserido = produtoDao.retornaProduto(produto);
		if (produtoASerInserido != null) {
			alterandoProdutoEInserindoEntrada();
		} else {
			chamaMetodoParaVerificarSeCodigoJaExiste(produto);
		
//			FacesContext.getCurrentInstance().getExternalContext()
//					.dispatch("entrada_erro.xhtml");
		}

	}

	private void chamaMetodoParaVerificarSeCodigoJaExiste(Produto produto) {
		if (verificaSeCodigoJaExisteComOutrosTamanhos(produto) != null) {
			this.produto = verificaSeCodigoJaExisteComOutrosTamanhos(produto);
			this.produto.setTamanho(produto.getTamanho());
			this.produto.setSaldo(valorASerInserido);
		}
		else{
			this.produto.setCor(null);
			this.produto.setDescricao(null);
			this.produto.setLocalizacao(null);
			this.produto.setTipoDeCobre(null);
			this.produto.setTamanho(produto.getTamanho());
			this.produto.setSaldo(valorASerInserido);
		}
	}

	private void alterandoProdutoEInserindoEntrada() {
		produtoASerInserido.setSaldo(produtoASerInserido.getSaldo()
				+ valorASerInserido);
		produtoDao.altera(produtoASerInserido);
		produtoDeEntrada.setCodigo(produtoASerInserido.getCodigo());
		produtoDeEntrada.setDescricao(produtoASerInserido.getDescricao());
		produtoDeEntrada.setTamanho(produtoASerInserido.getTamanho());
		produtoDeEntrada.setQuantidadeEntrada(valorASerInserido);
		entradaDao.gravarEntrada(produtoDeEntrada);
		itensDeEntrada = entradaDao.listarProdutosInseridos();
		this.produto = new Produto();
		this.produtoDeEntrada = new Entrada();
		this.valorASerInserido = null;
//		FacesContext.getCurrentInstance().addMessage(
//				inserir.getClientId(),
//				new FacesMessage(FacesMessage.SEVERITY_INFO,
//						"Produto inserido com sucesso!", null));
	}

	public List<Entrada> getRetornoProdutosInseridos() {
		if (itensDeEntrada == null) {
			itensDeEntrada = entradaDao.listarProdutosInseridos();
		}
		return this.itensDeEntrada;
	}

	public void inserindoNovoProduto() {
		produtoDeEntrada.setCodigo(produto.getCodigo());
		produtoDeEntrada.setDescricao(produto.getDescricao());
		produtoDeEntrada.setTamanho(produto.getTamanho());
		produtoDeEntrada.setQuantidadeEntrada(produto.getSaldo());
		entradaDao.gravarEntrada(produtoDeEntrada);
		produtoDao.gravar(produto);
		itensDeEntrada=entradaDao.listarProdutosInseridos();
//		FacesContext.getCurrentInstance().addMessage(
//				novoProduto.getClientId(),
//				new FacesMessage(FacesMessage.SEVERITY_INFO,
//						"Produto inserido com sucesso!", null));
	}

	public Produto verificaSeCodigoJaExisteComOutrosTamanhos(Produto produto) {
		retornoPesquisaPeloCodigo = produtoDao
				.pesquisaProdutoPeloCodigo(produto);
		if (retornoPesquisaPeloCodigo != null) {
			Produto produtoRetornadoCodigo = new Produto();
			produtoRetornadoCodigo.setCodigo(retornoPesquisaPeloCodigo.get(0)
					.getCodigo());
			produtoRetornadoCodigo.setCor(retornoPesquisaPeloCodigo.get(0)
					.getCor());
			produtoRetornadoCodigo.setDescricao(retornoPesquisaPeloCodigo
					.get(0).getDescricao());
			produtoRetornadoCodigo.setLocalizacao(retornoPesquisaPeloCodigo
					.get(0).getLocalizacao());
			produtoRetornadoCodigo.setTipoDeCobre(retornoPesquisaPeloCodigo
					.get(0).getTipoDeCobre());
			return produtoRetornadoCodigo;
		} else {
			return null;
		}
	}

	public List<Produto> getRetornoPesquisaPeloCodigo() {
		return retornoPesquisaPeloCodigo;
	}
}
