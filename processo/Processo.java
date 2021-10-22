 package ep1.processo;

import java.util.ArrayList;
import java.util.List;

import ep1.BCP;

public class Processo {
	
	private Estado estado = Estado.PRONTO; // variavel correspondente ao estado do processo (ENUM Estado)
	private List<String> instrucoes = new ArrayList<String>(); // lista que recebe todas ass intruções extraidas dos txt's.
	private String nome; // nome do processo

    private BCP bcp = new BCP(this); //variável para pegar a referência do BCP

    
	public Processo(Estado estado, List<String> instrucoes, String nome) {
		this.estado = estado;
		this.nome = nome;
		this.instrucoes.addAll(instrucoes); //cópia da lista recebida para a lista de instruções
		bcp.setNome(nome);
	}


	public Estado getEstado() {
		return estado;
	}

	public List<String> getInstrucoes() {
		return instrucoes;
	}

	public String getNome() {
		return nome;
	}


	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public BCP getBcp() {
		return bcp;
	}

 
}