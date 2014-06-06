package br.com.fatecpg.calculadoraderedeip;

import java.util.regex.Pattern;

public class Validate {
	// Faz a verificacao dos bits
	public boolean bitValidate(String bit) {
		try {
			if (bit.length() == 0) {
				return false;
			} else {
				int b = Integer.parseInt(bit);
				if (b >= 1 && b <= 30) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean ipValidate(String ip) {
		try {
			// Garante que sejam inseridos blocos de 1 a 3 digitos,
			// separados por ponto mas o ultimo bloco sem ponto
			String ipRegex = "\\d{1,3}(\\.\\d{1,3}){3}";
			boolean aux = true;
			if (ip.matches(ipRegex)) {
				String[] octeto = ip.split(Pattern.quote("."));
				for (int i = 0; i < 4; i++) {
					int octetoInt = Integer.parseInt(octeto[i]);
					if (octetoInt >= 0 && octetoInt <= 255) {
						aux = true;
					} else {
						i = 4;
						aux = false;
					}
				}
				return aux;
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}

}
