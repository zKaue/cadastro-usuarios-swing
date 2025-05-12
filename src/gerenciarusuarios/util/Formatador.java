package gerenciarusuarios.util;

public class Formatador {
    
	public static String formatarCpf(String cpfNumerico) {
	    if (cpfNumerico == null || cpfNumerico.length() != 11) {
	        return cpfNumerico;
	    } else {
	        return cpfNumerico.substring(0, 3) + "." +
	               cpfNumerico.substring(3, 6) + "." +
	               cpfNumerico.substring(6, 9) + "-" +
	               cpfNumerico.substring(9);
	    }
	}
	
	public static String limparCpf(String cpfFormatado) {
	    return cpfFormatado.replaceAll("[^0-9]", "");
	}
	
}