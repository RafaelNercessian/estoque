package br.com.cabolider.modelo;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Saida {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar data=Calendar.getInstance();
	private String nomeDoCliente;
	private Integer quantidadeASerRetirada;
	private String codigo;
	private String tamanho;
	private String descricao;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getTamanho() {
		return tamanho;
	}
	
	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Calendar getData() {
		return data;
	}
	
	public void setData(Calendar data) {
		this.data = data;
	}
	
	public String getNomeDoCliente() {
		return nomeDoCliente;
	}
	
	public void setNomeDoCliente(String nomeDoCliente) {
		this.nomeDoCliente = nomeDoCliente;
	}
	
	public Integer getQuantidadeASerRetirada() {
		return quantidadeASerRetirada;
	}
	
	public void setQuantidadeASerRetirada(Integer quantidadeASerRetirada) {
		this.quantidadeASerRetirada = quantidadeASerRetirada;
	}
}
