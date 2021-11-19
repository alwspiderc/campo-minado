package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;

/*
 * Os campos d„o referÍncia a cada "quadradinho" dentro do tabuleiro.
 * 
 * */

public class Campo {

	private final int linha;
	private final int coluna;
	
	private boolean aberto = false;
//	private boolean minado = false;
	private boolean marcado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	public Campo(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
	}
	
	
	/*
	 * ***************(linha, conluna)**********************
	 * 
	 * Calulos para saber quais campos s„so vizinhos do campo clicado, 
	 * sendo vizinho ser· adicionado ao ArrayList vizinhos.
	 * 
	 * Lados, encima e embaixo: (linha - linhha) + (coluna - coluna) = 1
	 * 
	 * Diagonais: (linha - linhha) + (coluna - coluna) = 2
	 * 
	 */
	public boolean adicionarVizinho(Campo vizinho){
		/*
		 *  Verifica se a linha e a coluna recebidas na vari·vel 
		 *  'vizinho' È diferente da linha e coluna selecionada, 
		 *  sendo fiferente significa que o vizinho pode estar em uma diaginal. 
		*/
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		
		// 'diagonal' ser√° true se 'linhaDiferente' e 'colunaDiferente' forem true;
		// 'diagonal' ser√° false se 'linhaDiferente' ou 'colunaDiferente' for false;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		/*
		 * 'deltaGeral' dever· dar 1 ou 2 de resultado para que seja
		 * 	um vizinho do campo selecionado.
		 * */
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaLinha + deltaColuna;
		
		/*
		 * 1. Verifica se o 'deltaGeral' È igual a 1 e se 'diagonal' retorna false, 
		 * caso retorne false significa que o campo n„o È uma diagonal. 
		 * 
		 * 2. Verifica se o 'deltaGeral' È igual a 2 e se 'diagonal' retorna true, 
		 * caso retorne true significa que o campo È uma diagonal.
		 * */
		
		if((deltaGeral == 1 && !diagonal) || (deltaGeral == 2 && diagonal)){
			vizinhos.add(vizinho);
			return true;
		}
		
		return false;
	}
	
	/*
	 * Ir· marcar um campo caso a casa n„o esteja aberta;
	 * 
	 * */
	void alterarMarcacao() {
		if(!aberto) {
			marcado = !marcado;
		}
	}
	
	boolean abrir() {
		
		return true;
	}
}
