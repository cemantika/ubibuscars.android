package com.example.ubibuscars;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import Evento.WS.EventoWS;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ExcluirCaronaActivity extends Activity {
	
	private Button bt_excluirCarona;
	private TextView txt_nome, txt_tipoCarona, txt_localOrigem, txt_localDestino, txt_horarioOrigem, txt_horarioDestino;
	private ImageView img_usuarioCarona;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_excluir_carona);
		
		Intent in = getIntent();
		
		final String idCarona = in.getStringExtra("idCarona");
        String idUsuario = in.getStringExtra("idUsuario");
        String name = in.getStringExtra("nome");
        String origem = in.getStringExtra("endereco_origem");
        String destino = in.getStringExtra("endereco_destino");
        String horarioOrigem = in.getStringExtra("horario_origem");
        String horarioDestino = in.getStringExtra("horario_destino");
        String tipo = in.getStringExtra("tipo");
        
        
        bt_excluirCarona = (Button) findViewById(R.id.buttonEcluirCarona);
        
        txt_nome = (TextView) findViewById(R.id.textViewExcluiCaronaNome);
        txt_tipoCarona = (TextView) findViewById(R.id.textViewEcluiTipoCarona);
        txt_localOrigem = (TextView) findViewById(R.id.textViewExcluiOrigem);
        txt_localDestino = (TextView) findViewById(R.id.textViewExcluiDestino);
        txt_horarioOrigem = (TextView) findViewById(R.id.textViewExcluiHorarioOrigem);
        txt_horarioDestino = (TextView) findViewById(R.id.textViewExcluiHorarioDestino);
        
        img_usuarioCarona = (ImageView) findViewById(R.id.imageViewExcluiFoto);
		
        txt_nome.setText(name);
        txt_horarioDestino.setText(horarioDestino);
        txt_horarioOrigem.setText(horarioOrigem);
        txt_localDestino.setText(destino);
        txt_localOrigem.setText(origem);
        txt_tipoCarona.setText(tipo);
        
        
        
        bt_excluirCarona.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				ExcluiCaronaASync excluiCarona = new ExcluiCaronaASync();
				excluiCarona.execute();
				
				/*
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

				nameValuePairs.add(new BasicNameValuePair("id_carona", idCarona));
				
				String resposta;
				resposta=CustomHttpPost.postData(Servidor.getServidor()+"/deletaCarona.php", nameValuePairs);
				
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(ExcluirCaronaActivity.this);
	        	alertDialog.setMessage(resposta);
	        	alertDialog.setNeutralButton("Continuar", null);  
	        	alertDialog.show();
				*/
			}
		});
	}
	
	public class ExcluiCaronaASync extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			Intent in = getIntent();
			String idCarona = in.getStringExtra("idCarona");
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			nameValuePairs.add(new BasicNameValuePair("id_carona", idCarona));
			
			String resposta;
			resposta=CustomHttpPost.postData(Servidor.getServidor()+"/deletaCarona.php", nameValuePairs);
			EventoWS.insereEvento(LoginActivity.getId_usuario(), "Excluiu uma Carona");
			
			return resposta;
		}
		
		protected void onPostExecute(String r){
			
			Intent i = new Intent(getBaseContext(), MinhasCaronasActivity.class);
        	
        	startActivity(i);
		}
		
	}
	
	


}
