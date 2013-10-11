package com.example.ubibuscars;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RespostaSolicitacaoActivity extends Activity {
	
	private Button bt_aceitar, bt_recusar, bt_avaliar;
	private ImageButton bt_alerta;
	private TextView txt_origem, txt_destino, txt_horarioOrigem, txt_horarioDestino, txt_statusPedido, txt_nomeMotorista, 
	txt_nomeCaronista, txt_enviarAlerta;
	private ImageView img_motorista, img_caronista;
	String idSolicitacao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resposta_solicitacao);
		
		 // getting intent data
        Intent in = getIntent();
        
     // Get JSON values from previous intent
		
		idSolicitacao = in.getStringExtra("idSolicitacao");
		final String id_usuariocarona = in.getStringExtra("id_usuariocarona");
		final String idUsuarioSolicita = in.getStringExtra("idUsuarioSolicita");
		final String idCarona = in.getStringExtra("id_carona");
		String situacao = in.getStringExtra("situacao");
		String endDestino =in.getStringExtra("endDestino");
		String horarioDestino = in.getStringExtra("horarioDestino");
		String endOrigem = in.getStringExtra("endOrigem");
		String horarioOrigem= in.getStringExtra("horarioOrigem");
		String nomeUsuarioCarona= in.getStringExtra("nomeUsuarioCarona");
		String nomeUsuarioSolicita= in.getStringExtra("nomeUsuarioSolicita");
		
		bt_aceitar = (Button) findViewById(R.id.buttonRespostaAceitar);
		bt_recusar = (Button) findViewById(R.id.buttonRespostaRecusar);
		bt_avaliar = (Button) findViewById(R.id.button_avaliarUsuario);
		
		bt_alerta = (ImageButton) findViewById(R.id.buttonAlertas);
		txt_enviarAlerta = (TextView) findViewById(R.id.EnviarAlerta);
		
		txt_destino = (TextView) findViewById(R.id.textViewRespostaDestino);
		txt_origem = (TextView) findViewById(R.id.textViewRespostaOrigem);
		txt_horarioDestino = (TextView) findViewById(R.id.textViewRespostaHorarioDestino);
		txt_horarioOrigem = (TextView) findViewById(R.id.textViewRespostaHorarioOrigem);
		
		txt_nomeCaronista = (TextView) findViewById(R.id.textViewNomeCaronista);
		txt_nomeMotorista = (TextView) findViewById(R.id.textViewNomeMotorista);
		
		txt_statusPedido = (TextView) findViewById(R.id.textViewRespostaStatus);
	
		img_motorista = (ImageView) findViewById(R.id.imageViewMotorista);
		img_caronista = (ImageView) findViewById(R.id.imageViewCaronista);
		
		txt_destino.setText(endDestino);
		txt_origem.setText(endOrigem);
		txt_horarioDestino.setText(horarioDestino);
		txt_horarioOrigem.setText(horarioOrigem);
		txt_nomeCaronista.setText(nomeUsuarioCarona);
		txt_nomeMotorista.setText(nomeUsuarioSolicita);
		txt_statusPedido.setText(situacao);
		
		
		BuscaImagemCaronista caronista = new BuscaImagemCaronista();
		caronista.execute();
		
		BuscaImagemMotorista motorista = new BuscaImagemMotorista();
		motorista.execute();
		
		
		//img_motorista.setImageBitmap(CustomHttpPost.getImagem(Servidor.getServidor()+"/buscaImagemUsuario.php?cod="+idUsuarioSolicita));
		//img_caronista.setImageBitmap(CustomHttpPost.getImagem(Servidor.getServidor()+"/buscaImagemUsuario.php?cod="+id_usuariocarona));
		
		

		
		
		if(idUsuarioSolicita.equals(String.valueOf(LoginActivity.getId_usuario()))){
			bt_aceitar.setVisibility(View.INVISIBLE);
			bt_recusar.setVisibility(View.INVISIBLE);
			//bt_alerta.setVisibility(View.VISIBLE);
			txt_enviarAlerta.setVisibility(View.VISIBLE);
		}else{
			bt_aceitar.setVisibility(View.VISIBLE);
			bt_recusar.setVisibility(View.VISIBLE);
			//bt_alerta.setVisibility(View.INVISIBLE);
			txt_enviarAlerta.setVisibility(View.INVISIBLE);
		}
		
		if(situacao.equals("Solicitacao aceita")){
			bt_alerta.setVisibility(View.VISIBLE);
		}
		else
			bt_alerta.setVisibility(View.INVISIBLE);	
		
		
		//se o usuario logado for o dono da carona, nao pode avaliar
		//if(situacao.equals("Solicitacao aceita") && !(id_usuariocarona.equals(String.valueOf(LoginActivity.getId_usuario())))){
		//	bt_avaliar.setVisibility(View.VISIBLE);
			
			bt_avaliar.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					
					VerificaAvaliadorAsync verificaAvaliador = new VerificaAvaliadorAsync();
					verificaAvaliador.execute(); 
					
					
				}
			});
		//}
		//else
			//bt_avaliar.setVisibility(View.INVISIBLE);
			
		
		
		bt_alerta.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), AlertasActivity.class);
				i.putExtra("idSolicitacao", idSolicitacao);
				
				//parte adicionada
				i.putExtra("idCarona", idCarona);
				i.putExtra("idUsuarioSolicita",idUsuarioSolicita);
				i.putExtra("id_usuarioCarona", id_usuariocarona);
	        	startActivity(i);
			}
		});
		
		
		bt_aceitar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String resp = "aceitar";
				
				RespostaSolicitacao respostaDaSolicitacao = new RespostaSolicitacao();
				respostaDaSolicitacao.execute(resp);
				
				/*
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

				nameValuePairs.add(new BasicNameValuePair("id_solicitacao", idSolicitacao));
				nameValuePairs.add(new BasicNameValuePair("resposta", "aceitar"));
				
				String resposta;
				resposta=CustomHttpPost.postData(Servidor.getServidor()+"/responderSolicitacao.php", nameValuePairs);
				
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(RespostaSolicitacaoActivity.this);
	        	alertDialog.setMessage(resposta);
	        	alertDialog.setNeutralButton("Continuar", null);  
	        	alertDialog.show();
	        	
	        */
			}
		});
		
		bt_recusar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				String resp = "recusar";
				/*
				// TODO Auto-generated method stub
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

				nameValuePairs.add(new BasicNameValuePair("id_solicitacao", idSolicitacao));
				nameValuePairs.add(new BasicNameValuePair("resposta", "recusar"));
				
				String resposta;
				resposta=CustomHttpPost.postData(Servidor.getServidor()+"/responderSolicitacao.php", nameValuePairs);
				
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(RespostaSolicitacaoActivity.this);
	        	alertDialog.setMessage(resposta);
	        	alertDialog.setNeutralButton("Continuar", null);  
	        	alertDialog.show();
				
				*/
				RespostaSolicitacao respostaDaSolicitacao = new RespostaSolicitacao();
				respostaDaSolicitacao.execute(resp);
			}
		});
	}
	
	
	
	
	public class VerificaAvaliadorAsync extends AsyncTask<Void, Void, Boolean>{

		
		ProgressDialog aguardeCaronas = new ProgressDialog(RespostaSolicitacaoActivity.this);
		
		@Override
		protected void onPreExecute() {
			
			aguardeCaronas.setTitle("Carregando");
			aguardeCaronas.setMessage("Aguarde, por favor...");
			aguardeCaronas.setIndeterminate(true);
			aguardeCaronas.show();
			
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			Intent i = getIntent();
			String idCarona = i.getStringExtra("id_carona");
			String id_solicita = i.getStringExtra("idUsuarioSolicita");
			String id_usuariocarona = i.getStringExtra("id_usuariocarona");
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id_carona", idCarona));
			
			String resposta = CustomHttpPost.postData(Servidor.getServidor()+"/verificaAvaliador.php", nameValuePairs);
			Boolean verifica = false;
			try {
				
				
				JSONArray jsonArray = new JSONArray(resposta);
				for (int index = 0; index < jsonArray.length(); index++) {
					JSONObject jsonObject = jsonArray.getJSONObject(index);
					
					String id_user = jsonObject.getString("id_avaliador");
					String id_avaliado = jsonObject.getString("id_avaliado");
					
					if( (id_user.equals(String.valueOf(LoginActivity.getId_usuario())) && (id_solicita.equals(id_avaliado)) || ((id_user.equals(String.valueOf(LoginActivity.getId_usuario())) && (id_avaliado.equals(id_usuariocarona))))) ){
						verifica = true;
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return verifica;
			
			
		}
		
		@Override
		protected void onPostExecute(Boolean r){
			aguardeCaronas.dismiss();
			
			if(r){
				Toast toast = Toast.makeText(getApplicationContext(), "Você só pode avaliar cada carona uma vez", 
						Toast.LENGTH_SHORT);
				toast.show();
			}
			else{
				Intent previousIntent = getIntent();
				String idUsuarioSolicita = previousIntent.getStringExtra("idUsuarioSolicita");
				final String idCarona = previousIntent.getStringExtra("id_carona");
				final String id_usuariocarona = previousIntent.getStringExtra("id_usuariocarona");
				
				Intent i = new Intent(getBaseContext(), AvaliacaoActivity.class);
				i.putExtra("idUsuarioSolicita",idUsuarioSolicita);
				i.putExtra("idCarona", idCarona);
				i.putExtra("id_usuarioCarona", id_usuariocarona);
				startActivity(i);
			}
		}
		
	}
	
	public class RespostaSolicitacao extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			Intent i = getIntent();
			idSolicitacao = i.getStringExtra("idSolicitacao");
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			
			String resp = params[0];
			
			nameValuePairs.add(new BasicNameValuePair("id_solicitacao", idSolicitacao));
			nameValuePairs.add(new BasicNameValuePair("resposta", resp));
			
			String resposta;
			resposta=CustomHttpPost.postData(Servidor.getServidor()+"/responderSolicitacao.php", nameValuePairs);
			
			return resposta;
		}
		
		
		protected void onPostExecute(String r){
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(RespostaSolicitacaoActivity.this);
        	alertDialog.setMessage(r);
        	alertDialog.setNeutralButton("Continuar", null);  
        	alertDialog.show();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	public class BuscaImagemMotorista extends AsyncTask<Void, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(Void... params) {
			Intent i = getIntent();
			
			String idUsuarioSolicita = i.getStringExtra("idUsuarioSolicita");

			
			Bitmap imageBitmap = CustomHttpPost.getImagem(Servidor.getServidor()+"/buscaImagemUsuario.php?cod="+idUsuarioSolicita);
			return imageBitmap;
		}
		
		protected void onPostExecute(Bitmap image){
			img_motorista = (ImageView) findViewById(R.id.imageViewMotorista);
			img_motorista.setImageBitmap(image);
			
		}
    	
    }
	
	
	public class BuscaImagemCaronista extends AsyncTask<Void, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(Void... params) {
			Intent i = getIntent();
			
			String id_usuariocarona = i.getStringExtra("id_usuariocarona");

			
			Bitmap imageBitmap = CustomHttpPost.getImagem(Servidor.getServidor()+"/buscaImagemUsuario.php?cod="+id_usuariocarona);
			return imageBitmap;
		}
		
		protected void onPostExecute(Bitmap image){
			img_caronista = (ImageView) findViewById(R.id.imageViewCaronista);
			img_caronista.setImageBitmap(image);
		}
    	
    }
}
