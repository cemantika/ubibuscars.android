package com.example.ubibuscars;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;


public class AvaliacaoActivity extends Activity {

	private RatingBar rate_user;
	private Button bt_enviarAvaliacao;
	private EditText mensagemAvaliacao;
	
	String idCarona;
	String idSolicita;
	String idOferece;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_avaliacao);
		
		Intent i = getIntent();
		
		idCarona = i.getStringExtra("idCarona");
		idSolicita = i.getStringExtra("idUsuarioSolicita");
		idOferece = i.getStringExtra("id_usuarioCarona");
		
		rate_user = (RatingBar) findViewById(R.id.ratingBarAvaliacao);
		bt_enviarAvaliacao = (Button) findViewById(R.id.buttonEviarAvaliacao);
		mensagemAvaliacao =  (EditText) findViewById(R.id.editTextMensagemAvaliacao);
		
		bt_enviarAvaliacao.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				float av = rate_user.getRating();
				
				EnviaAvaliacaoAsync avaliacao = new EnviaAvaliacaoAsync();
				avaliacao.execute();
				
				finish();
				
			}
		});
		
	}

	
	
	public class EnviaAvaliacaoAsync extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			
			if(idOferece.equals(String.valueOf(LoginActivity.getId_usuario()))){
				nameValuePairs.add(new BasicNameValuePair("id_avaliado", idSolicita));
			}
			else 
				nameValuePairs.add(new BasicNameValuePair("id_avaliado", idOferece));
			
			
			nameValuePairs.add(new BasicNameValuePair("id_carona", idCarona));
			nameValuePairs.add(new BasicNameValuePair("mensagem",mensagemAvaliacao.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("nota",String.valueOf(rate_user.getRating())));
			nameValuePairs.add(new BasicNameValuePair("id_avaliador",String.valueOf(LoginActivity.getId_usuario())));
			
			String resposta;
			
			resposta = CustomHttpPost.postData(
					Servidor.getServidor()
							+ "/avaliaUsuario.php",
					nameValuePairs);
			return resposta;
		}
		
		@Override
		protected void onPostExecute(String r){
			
			Toast toast = Toast.makeText(getApplicationContext(), r, Toast.LENGTH_LONG);
			toast.show();
		}
		
	}

}
