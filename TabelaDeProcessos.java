package ep1;

import java.util.ArrayList;
import java.util.List;

import ep1.processo.Estado;

public class TabelaDeProcessos {
	
	public static List<BCP> prontos = new ArrayList<BCP>(); 	//lista de prontos
	public static List<BCP> bloqueados = new ArrayList<BCP>();	//lista de bloqueados
	
	public static void adicionaBloqueados(BCP bcp) { //método para adicionar na lista de bloqueados e remover da lista de prontos
		bcp.setSairBloquados(2);
		bloqueados.add(bcp);
		prontos.remove(bcp);
		bcp.getProcesso().setEstado(Estado.BLOQUEADO); //muda estado para bloqueado
	}

	
	public static void removeBloqueados(BCP bcp) { //método para adicionar na lista de prontos e remover da lista de bloqueados
		bloqueados.remove(bcp);
		prontos.add(bcp);
		bcp.getProcesso().setEstado(Estado.PRONTO); //muda estado para pronto
	}
	
	

	
}
