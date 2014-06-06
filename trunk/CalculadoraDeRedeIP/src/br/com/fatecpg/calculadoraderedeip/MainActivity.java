package br.com.fatecpg.calculadoraderedeip;

import java.text.NumberFormat;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	// Metodo que faz os cálculos de rede
	public void calcular(View view) {
		Validate val = new Validate();
		Calculate calc = new Calculate();
		// Variavel que armazena o valor digitado pelo usuario
		TextView editIP = (TextView) findViewById(R.id.editIP);
		String textIP[] = editIP.getText().toString().split("/");
		// Verifica se o ip esta completo, senao exibe alerta
		if (textIP.length < 2) {
			new AlertDialog.Builder(this).setTitle("Aviso")
					.setMessage("IP inválido, digite novamente")
					.setIcon(R.drawable.ic_ex)
					.setPositiveButton("Voltar", null).show();
			return;
		}

		if (val.ipValidate(textIP[0]) && val.bitValidate(textIP[1])) {
			String ip = String.valueOf(textIP[0]);
			String m = calc.calcMask(Integer.parseInt(textIP[1]));
			String n = calc.calcNetId(ip, m);
			String b = calc.calcBroad(n, m);
			String first = calc.calcFirstIp(textIP[0],
					Integer.parseInt(textIP[1]));
			String last = calc.calcLastIp(textIP[0],
					Integer.parseInt(textIP[1]));
			boolean particular = calc.isParticular(ip);

			// Pega os valores e envia para a classe ResultActivity
			Intent intent = new Intent(this, ResultActivity.class);
			intent.putExtra("MASK", m);
			intent.putExtra("NET", n);
			intent.putExtra("BROAD", b);
			intent.putExtra("FIRST", first);
			intent.putExtra("LAST", last);
			// Verifica se o tipo eh privado ou publico
			if (particular)
				intent.putExtra("TIPO", "Privado");
			else
				intent.putExtra("TIPO", "Público");

			// Inicia a Activity
			startActivity(intent);

		} else {
			showErrors(textIP[0], textIP[1]);
		}
	}

	// Calcula o total de ips na rede
	public void calcTotalIp(View view) {
		Validate val = new Validate();
		Calculate calc = new Calculate();

		TextView editIP = (TextView) findViewById(R.id.editIP);
		String textIP[] = editIP.getText().toString().split("/");
		// Verifica se o ip esta completo, senao exibe alerta
		if (textIP.length < 2) {
			new AlertDialog.Builder(this).setTitle("Aviso")
					.setMessage("IP inválido, digite novamente")
					.setIcon(R.drawable.ic_ex)
					.setPositiveButton("Voltar", null).show();
			return;
		}

		if (val.bitValidate(textIP[1])) {
			long total = calc.calcTotalIps(Integer.parseInt(textIP[1]));
			new AlertDialog.Builder(this)
					.setTitle("Total de IPs")
					.setMessage(
							"Total: "
									+ NumberFormat.getNumberInstance().format(
											total))
					.setPositiveButton("Voltar", null).show();
		} else {
			new AlertDialog.Builder(this).setTitle("Aviso")
					.setMessage("O valor do Bit deve ser entre 1 e 30.")
					.setIcon(R.drawable.ic_ex)
					.setPositiveButton("Voltar", null).show();
		}

	}

	// Verifica se o IP na rede eh valido
	public void verifyIp(View view) {
		Validate val = new Validate();
		Calculate calc = new Calculate();

		TextView editIP = (TextView) findViewById(R.id.editIP);
		String textIP[] = editIP.getText().toString().split("/");
		// Verifica se o ip esta completo, senao exibe alerta
		if (textIP.length < 2) {
			new AlertDialog.Builder(this).setTitle("Aviso")
					.setMessage("IP inválido, digite novamente")
					.setIcon(R.drawable.ic_ex)
					.setPositiveButton("Voltar", null).show();
			return;
		}

		if (val.ipValidate(textIP[0]) && val.bitValidate(textIP[1])) {
			String ip = textIP[0];
			int bit = Integer.parseInt(textIP[1]);
			String msg = "";
			if (calc.verifyValido(ip, bit) == 0) {
				msg = "IP inválido, este IP é o Net Id";
			} else if (calc.verifyValido(ip, bit) == 1) {
				msg = "IP inválido, este IP é o Broadcast";
			} else {
				msg = "IP válido";
			}
			new AlertDialog.Builder(this).setTitle(ip).setMessage(msg)
					.setPositiveButton("Voltar", null).show();
		} else {
			showErrors(textIP[0], textIP[1]);
		}
	}

	// Exibe o tipo de rede IP
	public void verifyTipo(View view) {
		Validate val = new Validate();
		Calculate calc = new Calculate();

		TextView editIP = (TextView) findViewById(R.id.editIP);
		String textIP[] = editIP.getText().toString().split("/");
		// Verifica se o ip esta completo, senao exibe alerta
		if (textIP.length < 2) {
			new AlertDialog.Builder(this).setTitle("Aviso")
					.setMessage("IP inválido, digite novamente")
					.setIcon(R.drawable.ic_ex)
					.setPositiveButton("Voltar", null).show();
			return;
		}

		if (val.ipValidate(textIP[0])) {
			if (calc.isParticular(textIP[0])) {
				new AlertDialog.Builder(this)
						.setTitle(editIP.getText().toString())
						.setMessage("Este IP é particular")
						.setIcon(R.drawable.ic_ex)
						.setPositiveButton("Voltar", null).show();
			} else {
				new AlertDialog.Builder(this)
						.setTitle(editIP.getText().toString())
						.setMessage("Este IP é público")
						.setIcon(R.drawable.ic_ex)
						.setPositiveButton("Voltar", null).show();
			}
		} else {
			new AlertDialog.Builder(this).setTitle("Aviso")
					.setMessage("Verifique se o IP está correto.")
					.setIcon(R.drawable.ic_ex)
					.setPositiveButton("Voltar", null).show();
		}
	}

	// Exibe o primeiro e o ultimo IP da rede
	public void gerarIp(View view) {
		Validate val = new Validate();
		Calculate calc = new Calculate();

		TextView editIP = (TextView) findViewById(R.id.editIP);
		String textIP[] = editIP.getText().toString().split("/");
		// Verifica se o ip esta completo, senao exibe alerta
		if (textIP.length < 2) {
			new AlertDialog.Builder(this).setTitle("Aviso")
					.setMessage("IP inválido, digite novamente")
					.setIcon(R.drawable.ic_ex)
					.setPositiveButton("Voltar", null).show();
			return;
		}

		if (val.ipValidate(textIP[0]) && val.bitValidate(textIP[1])) {
			String primeiro = calc.calcFirstIp(textIP[0],
					Integer.parseInt(textIP[1]));
			String ultimo = calc.calcLastIp(textIP[0],
					Integer.parseInt(textIP[1]));
			new AlertDialog.Builder(this)
					.setTitle("IPs na Rede")
					.setMessage("Primeiro: " + primeiro + "\nÚltimo: " + ultimo)
					.setPositiveButton("Voltar", null).show();
		} else {
			showErrors(textIP[0], textIP[1]);
		}
	}

	// Metodo para exibicao de mensagens de erro
	public void showErrors(String ip, String bit) {
		Validate val = new Validate();

		if (!val.ipValidate(ip) && (!val.bitValidate(bit))) {
			new AlertDialog.Builder(this).setTitle("Aviso")
					.setMessage("Verifique se o IP e o Bit estão corretos.")
					.setIcon(R.drawable.ic_ex)
					.setPositiveButton("Voltar", null).show();
		} else if (!val.ipValidate(ip)) {
			new AlertDialog.Builder(this).setTitle("Aviso")
					.setMessage("Verifique se o IP está correto.")
					.setIcon(R.drawable.ic_ex)
					.setPositiveButton("Voltar", null).show();
		} else if (!val.bitValidate(bit)) {
			new AlertDialog.Builder(this).setTitle("Aviso")
					.setMessage("O valor do Bit deve ser entre 1 e 30.")
					.setIcon(R.drawable.ic_ex)
					.setPositiveButton("Voltar", null).show();
		}
	}

	// Metodo que chama o botao para limpar texto inserido
	public void limpar(View view) {
		EditText editIP = (EditText) findViewById(R.id.editIP);
		editIP.setText("");
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		TextView editIP = (TextView) findViewById(R.id.editIP);
		outState.putString("TEXT_IP", editIP.getText().toString());
	}

}

