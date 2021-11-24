package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.cod3r.cm.excecao.ExplosaoException;

/*
 * Os campos dão referência a cada "quadradinho" dentro do tabuleiro.
 * 
 * */

public class Campo {

	private final int linha;
	private final int coluna;
	
	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	public Campo(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
	}
	
	
	/*
	 * ***************(linha, conluna)**********************
	 * 
	 * Calulos para saber quais campos s�so vizinhos do campo clicado, 
	 * sendo vizinho será adicionado ao ArrayList vizinhos.
	 * 
	 * Lados, encima e embaixo: (linha - linhha) + (coluna - coluna) = 1
	 * 
	 * Diagonais: (linha - linhha) + (coluna - coluna) = 2
	 * 
	 */
	public boolean adicionarVizinho(Campo vizinho){
		/*
		 *  Verifica se a linha e a coluna recebidas na vari�vel 
		 *  'vizinho' é diferente da linha e coluna selecionada, 
		 *  sendo fiferente significa que o vizinho pode estar em uma diaginal. 
		*/
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		
		// 'diagonal' será true se 'linhaDiferente' e 'colunaDiferente' forem true;
		// 'diagonal' será false se 'linhaDiferente' ou 'colunaDiferente' for false;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		/*
		 * 'deltaGeral' deverá dar 1 ou 2 de resultado para que seja
		 * 	um vizinho do campo selecionado.
		 * */
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaLinha + deltaColuna;
		
		/*
		 * 1. Verifica se o 'deltaGeral' é igual a 1 e se 'diagonal' retorna false, 
		 * caso retorne false significa que o campo não é uma diagonal. 
		 * 
		 * 2. Verifica se o 'deltaGeral' é igual a 2 e se 'diagonal' retorna true, 
		 * caso retorne true significa que o campo é uma diagonal.
		 * */
		
		if((deltaGeral == 1 && !diagonal) || (deltaGeral == 2 && diagonal)){
			vizinhos.add(vizinho);
			return true;
		}
		
		return false;
	}
	
	/*
	 * Irá marcar um campo caso a casa não esteja aberta;
	 * 
	 * */
	void alterarMarcacao() {
		if(!aberto) {
			marcado = !marcado;
		}
	}
	
	boolean abrir() {
		if(!aberto && !marcado){
			aberto = true;
			if(minado){
				throw new ExplosaoException();
			}
			
			if(vizinhancaSegura()){
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		}
		return false;
	}
	
	boolean vizinhancaSegura(){
		
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	void minar(){
		minado = true;
	}
	
	public boolean isMinado(){
		return minado;
	}
	
	public boolean isMarcado(){
		return marcado;
	}
	
	
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}


	public boolean isAberto(){
		return aberto;
	}
	
	public boolean isFechado(){
		return !isAberto();
	}

	public int getLinha() {
		return linha;
	}


	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancado(){
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		
		return desvendado || protegido;
	}
	
	long minasNaVizinhanca(){
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reiniciar(){
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	@Override
	public String toString() {
		
		if(marcado){
			return "x";
		}
		
		if (aberto && minado){
			return "*";
		}
		
		if(aberto && minasNaVizinhanca() > 0){
			return Long.toString(minasNaVizinhanca());
		}
		
		if(aberto){
			return " "; 
		}
		
		return "?";
	}
}
