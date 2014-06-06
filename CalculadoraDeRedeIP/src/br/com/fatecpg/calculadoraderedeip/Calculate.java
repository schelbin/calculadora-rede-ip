package br.com.fatecpg.calculadoraderedeip;

import java.util.regex.Pattern;

public class Calculate {
	// Divide o IP em 4 octetos, faz conversao e une
	public String convertIP(String ip) {
		String[] octeto = ip.split(Pattern.quote("."));
		String bin = Integer.toBinaryString(Integer.parseInt(octeto[0])) + "."
				+ Integer.toBinaryString(Integer.parseInt(octeto[1])) + "."
				+ Integer.toBinaryString(Integer.parseInt(octeto[2])) + "."
				+ Integer.toBinaryString(Integer.parseInt(octeto[3]));
		// Transforma cada bloco em octeto
		String[] octetoBin = bin.split(Pattern.quote("."));
		String saida = "";
		for (int i = 0; i < 4; i++) {
			int t = 8 - octetoBin[i].length();
			for (int j = 0; j < t; j++) {
				saida += "0";
			}
			saida += octetoBin[i] + ".";
		}
		return saida.substring(0, saida.length() - 1);
	}

	// Divide o binario em 4 octetos, faz conversao e une
	public String reverseIP(String bin) {
		String[] octeto = bin.split(Pattern.quote("."));
		String ip = Integer.parseInt(octeto[0], 2) + "."
				+ Integer.parseInt(octeto[1], 2) + "."
				+ Integer.parseInt(octeto[2], 2) + "."
				+ Integer.parseInt(octeto[3], 2);
		return ip;
	}

	// Metodo para calcular mascara
	public String calcMask(int bit) {
		int x = (int) bit / 8;
		int y = 4 - x;
		int z = bit % 8;
		String saida = "";
		int c = 0;

		for (int i = 0; i < x; i++) {
			String bin = "";
			for (int j = 0; j < 8; j++) {
				bin += "1";
			}
			saida += String.valueOf(Integer.parseInt(bin, 2)) + ".";
		}

		for (int i = 0; i < y; i++) {
			String bin = "";
			for (int j = 0; j < 8; j++) {
				if (c < z) {
					bin += "1";
				} else {
					bin += 0;
				}
				c++;
			}
			saida += String.valueOf(Integer.parseInt(bin, 2)) + ".";
		}
		return saida.substring(0, saida.length() - 1);
	}

	// Retorna o bit que gerou a mascara
	public int reverseMask(String mascara) {
		String mascaraBin = convertIP(mascara);
		int c = 0;
		for (int i = 0; i < mascaraBin.length(); i++) {
			if (mascaraBin.charAt(i) == '1') {
				c++;
			}
		}
		return c;
	}

	// Método para calcular o NetId
	public String calcNetId(String ip, String masc) {
		String saida = "";
		String[] octetoIp = ip.split(Pattern.quote("."));
		String[] octetoMasc = masc.split(Pattern.quote("."));
		for (int i = 0; i < 4; i++) {
			if (octetoMasc[i].equals("255")) {
				saida += octetoIp[i] + ".";
			} else if (octetoMasc[i].equals("0")) {
				saida += "0.";
			} else {
				int aux = 256 - Integer.parseInt(octetoMasc[i]);
				int result = (int) (Integer.parseInt(octetoIp[i]) / aux) * aux;
				saida += result + ".";
			}
		}
		return saida.substring(0, saida.length() - 1);
	}

	// Metodo para calcular o Broadcast
	public String calcBroad(String netId, String masc) {
		String saida = "";
		String[] octetoIp = netId.split(Pattern.quote("."));
		String[] octetoMasc = masc.split(Pattern.quote("."));
		int aux = 0;
		for (int i = 0; i < 4; i++) {
			aux = 255 - Integer.parseInt(octetoMasc[i])
					+ Integer.parseInt(octetoIp[i]);
			saida += aux + ".";
		}
		return saida.substring(0, saida.length() - 1);
	}

	// Verifica se o ip eh valido
	public int verifyValido(String ip, int bit) {
		String m = calcMask(bit);
		String n = calcNetId(ip, m);
		String b = calcBroad(n, m);
		if (ip.equals(n)) {
			return 0; // Se igual a n, o IP eh o NetID
		} else if (ip.equals(b)) {
			return 1; // Se igual a b, o IP eh o Broadcast
		} else {
			return 2; // Caso contrario, o IP eh valido
		}
	}

	// Verifica se o IP eh publico ou particular
	public boolean isParticular(String ip) {
		String[] octeto = ip.split(Pattern.quote("."));
		if (octeto[0].equals("10") || (octeto[0] + octeto[1]).equals("192168")) {
			return true;
		} else if (Integer.parseInt(octeto[0] + octeto[1]) >= 17216
				&& Integer.parseInt(octeto[0] + octeto[1]) <= 17231) {
			return true;
		} else {
			return false;
		}
	}

	// Calculo do total de IPs na rede
	public long calcTotalIps(int bit) {
		String m = convertIP(calcMask(bit));
		String aux = "";
		String[] octetoBin = m.split(Pattern.quote("."));
		for (int i = 0; i < 4; i++) {
			aux += octetoBin[i];
		}
		int c = 0;
		for (int i = 0; i < aux.length(); i++) {
			if (aux.charAt(i) == '0') {
				c++;
			}
		}
		return (long) Math.pow(2, c);
	}

	// Calculo do primeiro IP valido na rede
	public String calcFirstIp(String ip, int bit) {
		String[] octeto = calcNetId(ip, calcMask(bit))
				.split(Pattern.quote("."));
		return octeto[0] + "." + octeto[1] + "." + octeto[2] + "."
				+ String.valueOf(Integer.parseInt(octeto[3]) + 1);
	}

	// Calculo do ultimo IP valido na rede
	public String calcLastIp(String ip, int bit) {
		String m = calcMask(bit);
		String[] octeto = calcBroad(calcNetId(ip, m), m).split(
				Pattern.quote("."));
		return octeto[0] + "." + octeto[1] + "." + octeto[2] + "."
				+ String.valueOf(Integer.parseInt(octeto[3]) - 1);
	}
}
