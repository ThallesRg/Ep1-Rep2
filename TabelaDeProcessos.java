package ep1;

import java.util.ArrayList;
import java.util.List;

import ep1.processo.Estado;

public class TabelaDeProcessos {
	
	public static List<BCP> prontos = new ArrayList<BCP>();
	public static List<BCP> bloqueados = new ArrayList<BCP>();
	
	public static void adicionaProntos(BCP bcp) {
		prontos.add(bcp);
		
	}
	
	public static void adicionaBloqueados(BCP bcp) {
		bcp.setSairBloquados(2);
		bloqueados.add(bcp);
		prontos.remove(bcp);
		bcp.getProcesso().setEstado(Estado.BLOQUEADO);
	}
	
	public static void removeProntos() {
		
	}
	
	public static void removeBloqueados(BCP bcp) {
		bloqueados.remove(bcp);
		prontos.add(bcp);
		bcp.getProcesso().setEstado(Estado.PRONTO);
	}
	
	

	
}
