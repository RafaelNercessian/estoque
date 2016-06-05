package br.com.cabolider.mb;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.cabolider.dao.ProdutoDao;
import br.com.cabolider.dao.SaidaDao;
import br.com.cabolider.modelo.Produto;
import br.com.cabolider.modelo.Saida;

@Named
@ViewScoped
public class SaidaBean {

	@Inject
	private ProdutoDao produtoDao;

	@Inject
	private SaidaDao saidaDao;

	private Produto produto = new Produto();
	private Saida saidaDeProduto = new Saida();
	private Integer valorASerRetirado;
	private String nomeDoCliente;
	private Integer activeTabIndex;
	private Produto produtoASerRetirado = new Produto();
	private List<Saida> itensDeSaida;
	private UIComponent retirada;
	
	public UIComponent getRetirada() {
		return retirada;
	}

	public void setRetirada(UIComponent retirada) {
		this.retirada = retirada;
	}

	public Saida getSaidaDeProduto() {
		return saidaDeProduto;
	}

	public void setSaidaDeProduto(Saida saidaDeProduto) {
		this.saidaDeProduto = saidaDeProduto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getValorASerRetirado() {
		return valorASerRetirado;
	}

	public void setValorASerRetirado(Integer valorASerRetirado) {
		this.valorASerRetirado = valorASerRetirado;
	}

	public String getNomeDoCliente() {
		return nomeDoCliente;
	}

	public void setNomeDoCliente(String nomeDoCliente) {
		this.nomeDoCliente = nomeDoCliente;
	}

	public Integer getActiveTabIndex() {
		return activeTabIndex;
	}

	public void setActiveTabIndex(Integer activeTabIndex) {
		this.activeTabIndex = activeTabIndex;
	}

	public List<Saida> getItensDeSaida() {
		return itensDeSaida;
	}

	public void produtoASerRetirado() throws Exception {
		produtoASerRetirado = produtoDao.retornaProduto(produto);
		if (produtoASerRetirado != null
				&& produtoASerRetirado.getSaldo() >= valorASerRetirado) {
			alterandoProdutoEInserindoSaida();
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.dispatch("500.xhtml");
		}

	}

	private void alterandoProdutoEInserindoSaida() {
		produtoASerRetirado.setSaldo(produtoASerRetirado.getSaldo()
				- valorASerRetirado);
		if (produtoASerRetirado.getSaldo() == 0
				&& !produtoASerRetirado.getTamanho().equals("100")) {
			produtoDao.remove(produtoASerRetirado);
		} else {
			produtoDao.altera(produtoASerRetirado);
		}
		saidaDeProduto.setCodigo(produtoASerRetirado.getCodigo());
		saidaDeProduto.setDescricao(produtoASerRetirado.getDescricao());
		saidaDeProduto.setTamanho(produtoASerRetirado.getTamanho());
		saidaDeProduto.setQuantidadeASerRetirada(valorASerRetirado);
		saidaDao.gravarSaida(saidaDeProduto);
		this.produto=new Produto();
		this.saidaDeProduto=new Saida();
		this.valorASerRetirado=null;
		this.itensDeSaida = saidaDao.listaItensDeSaida();
		FacesContext.getCurrentInstance().addMessage(
				retirada.getClientId(),
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Produto retirado com sucesso!", null));
	}

	public List<Saida> getRetornoProdutoRetirado() {
		if (this.itensDeSaida == null) {
			return this.itensDeSaida = saidaDao.listaItensDeSaida();
		}
		return this.itensDeSaida;
	}
}
