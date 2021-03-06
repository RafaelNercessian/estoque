package br.com.cabolider.mb;


import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.cabolider.dao.ProdutoDao;
import br.com.cabolider.modelo.Produto;

@Named
@ViewScoped
public class ProdutoBeanCopia {
	
	private Produto produto=new Produto();
	private List<Produto> produtos;
	private ProdutoDao dao;
	
	public ProdutoBeanCopia(ProdutoDao daoFalso){
		this.dao=daoFalso;
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public void pesquisar(){
		if("".equals(produto.getCodigo())){
			produto.setCodigo(null);
		}
		
		else if("".equals(produto.getDescricao())){
			produto.setDescricao(null);
		}
		
		this.produtos=dao.listaPorProduto(produto);
		produto=new Produto();
	}
	
	public List<Produto> getLista(){
		if(this.produtos==null){
			return this.produtos=dao.listaPorProduto(produto);
		}
		return this.produtos;
			
	}
}
