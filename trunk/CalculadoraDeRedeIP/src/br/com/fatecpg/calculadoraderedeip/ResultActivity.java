package br.com.fatecpg.calculadoraderedeip;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		// pega o valor calculado e insere no campo Mascara
		Intent intent = getIntent();
		String mask = intent.getStringExtra("MASK");
		TextView textMask = (TextView) findViewById(R.id.textMask);
		textMask.setText(mask);

		// pega o valor calculado e insere no campo NetID
		String net = intent.getStringExtra("NET");
		TextView textNet = (TextView) findViewById(R.id.textNet);
		textNet.setText(net);

		// pega o valor calculado e insere no campo Broadcast
		String broad = intent.getStringExtra("BROAD");
		TextView textBroad = (TextView) findViewById(R.id.textBroad);
		textBroad.setText(broad);

		// pega o valor calculado e insere no campo Tipo de Rede
		String tipo = intent.getStringExtra("TIPO");
		TextView textTipo = (TextView) findViewById(R.id.textTipo);
		textTipo.setText(tipo);

		// pega o valor calculado e insere no campo Primeiro endereco da rede
		String first = intent.getStringExtra("FIRST");
		TextView textFirst = (TextView) findViewById(R.id.textFirst);
		textFirst.setText(first);

		// pega o valor calculado e insere no campo Ultimo endereco da rede
		String last = intent.getStringExtra("LAST");
		TextView textLast = (TextView) findViewById(R.id.textLast);
		textLast.setText(last);
	}

	// método que retorna a tela anterior
	public void voltar(View view) {
		finish();
	}

}
