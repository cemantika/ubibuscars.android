package com.example.ubibuscars;


import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import Evento.WS.EventoWS;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SolicitaCaronaActivity extends Activity {
	
	private Button bt_solicitar;
	private Button bt_avaliacoes;
	private TextView txt_nome, txt_tipoCarona, txt_localOrigem, txt_localDestino, txt_horarioOrigem, txt_horarioDestino, txt_vagasDisponiveis;
	private EditText edt_mensagem;
	private ImageView img_usuarioCarona;
	private String idCarona;
	String idUsuario, media, nome;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicita_carona);
        
        
        
        BuscaImagem colocaFoto = new BuscaImagem();
        
        
     // getting intent data
        Intent in = getIntent();
        
     // Get JSON values from previous intent
        nome = in.getStringExtra("nome");
        media = in.getStringExtra("media");
        idCarona = in.getStringExtra("idCarona");
        idUsuario = in.getStringExtra("idUsuario");
        final String name = in.getStringExtra("nome");
        String origem = in.getStringExtra("endereco_origem");
        String destino = in.getStringExtra("endereco_destino");
        String horarioOrigem = in.getStringExtra("horario_origem");
        String horarioDestino = in.getStringExtra("horario_destino");
        String tipo = in.getStringExtra("tipo");
        String vagasDisponiveis = in.getStringExtra("vagas_disponiveis");        
        
        bt_solicitar = (Button) findViewById(R.id.buttonEnviarSolicitacao);
        bt_avaliacoes = (Button) findViewById(R.id.buttonVerAvaliacoes);
        
        txt_nome = (TextView) findViewById(R.id.textViewNomeUsuario);
        txt_tipoCarona = (TextView) findViewById(R.id.textViewTipoCarona);
        txt_localOrigem = (TextView) findViewById(R.id.textViewSolicitaOrigem);
        txt_localDestino = (TextView) findViewById(R.id.textViewSolicitaDestino);
        txt_horarioOrigem = (TextView) findViewById(R.id.textViewSolicitaHorarioOrigem);
        txt_horarioDestino = (TextView) findViewById(R.id.textViewSolicitaHorarioDestino);
        txt_vagasDisponiveis = (TextView) findViewById(R.id.textViewVagasDisponiveis);
        
        //img_usuarioCarona = (ImageView) findViewById(R.id.imageViewSolicitacaoFoto);
        
        edt_mensagem = (EditText) findViewById(R.id.editTextSolicitaMensagem);
        
        //a foto é carregada a partir de uma asynk task
        colocaFoto.execute();
        
        txt_nome.setText(name);
        txt_localOrigem.setText(origem);
        txt_localDestino.setText(destino);
        txt_horarioOrigem.setText(horarioOrigem);
        txt_horarioDestino.setText(horarioDestino);
        txt_tipoCarona.setText("Usu‡rio "+tipo+" carona");
        txt_vagasDisponiveis.setText(vagasDisponiveis);
       // img_usuarioCarona.setImageBitmap(CustomHttpPost.getImagem(Servidor.getServidor()+"/buscaImagemUsuario.php?cod="+idUsuario));
        
        if(
        	idUsuario.equals(String.valueOf(LoginActivity.getId_usuario()))
        	||
        	vagasDisponiveis.equals("0")
        ){
			bt_solicitar.setVisibility(View.INVISIBLE);
			edt_mensagem.setVisibility(View.INVISIBLE);
			
		}
        
        
        bt_avaliacoes.setOnClickListener(new View.OnClickListener() {
		
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),ListaAvaliacoesActivity.class);
				i.putExtra("id_avaliado", idUsuario);
				i.putExtra("nome", name);
				i.putExtra("media", media);
				
				startActivity(i);
				
			}
		});
        
        
        bt_solicitar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				VerificaSolicitacaoCarona verifica = new VerificaSolicitacaoCarona();
				verifica.execute();
				
				//Solicitacao solicitacao = new Solicitacao();
				//solicitacao.execute();
				
				/*
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

				nameValuePairs.add(new BasicNameValuePair("id_carona", idCarona));
				nameValuePairs.add(new BasicNameValuePair("id_usuario", String.valueOf(LoginActivity.getId_usuario())));
				nameValuePairs.add(new BasicNameValuePair("mensagem", edt_mensagem.getText().toString()));
				
				String resposta;
				resposta=CustomHttpPost.postData(Servidor.getServidor()+"/insereSolicitacao.php", nameValuePairs);
				
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(SolicitaCaronaActivity.this);
	        	alertDialog.setMessage(resposta);
	        	alertDialog.setNeutralButton("Continuar", null);  
	        	alertDialog.show();
			*/
			}
		});
        
    }
    
    
    
public class VerificaSolicitacaoCarona extends AsyncTask<Void, Void, Boolean>{

		
		ProgressDialog aguardeCaronas = new ProgressDialog(SolicitaCaronaActivity.this);
		
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
			String idCarona = i.getStringExtra("idCarona");
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id_carona", idCarona));
			
			String resposta = CustomHttpPost.postData(Servidor.getServidor()+"/verificaSolicitacaoCarona.php", nameValuePairs);
			Boolean verifica = false;
			try {
				
				
				JSONArray jsonArray = new JSONArray(resposta);
				for (int index = 0; index < jsonArray.length(); index++) {
					JSONObject jsonObject = jsonArray.getJSONObject(index);
					
					String id_user = jsonObject.getString("id_usuario");
				
					
					if( (id_user.equals(String.valueOf(LoginActivity.getId_usuario()))) ){
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
				Toast toast = Toast.makeText(getApplicationContext(), "Você já solicitou esta carona.", 
						Toast.LENGTH_SHORT);
				toast.show();
			}
			else{
				
				Solicitacao solicita = new Solicitacao();
				solicita.execute();
				
			}
		}
		
	}
    
    public class Solicitacao extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			nameValuePairs.add(new BasicNameValuePair("id_carona", idCarona));
			nameValuePairs.add(new BasicNameValuePair("id_usuario", String.valueOf(LoginActivity.getId_usuario())));
			nameValuePairs.add(new BasicNameValuePair("mensagem", edt_mensagem.getText().toString()));
			
			String resposta;
			resposta=CustomHttpPost.postData(Servidor.getServidor()+"/insereSolicitacao.php", nameValuePairs);
			EventoWS.insereEvento(LoginActivity.getId_usuario(), "Solicitou uma carona");
			return resposta;
			
		}
		
		protected void onPostExecute(String r){
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(SolicitaCaronaActivity.this);
        	alertDialog.setMessage(r);
        	alertDialog.setNeutralButton("Continuar", null);  
        	alertDialog.show();
		}
    }
    
    
    
    public class BuscaImagem extends AsyncTask<Void, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(Void... params) {
			Intent i = getIntent();
			String id = i.getStringExtra("idUsuario");
			Bitmap imageBitmap = CustomHttpPost.getImagem(Servidor.getServidor()+"/buscaImagemUsuario.php?cod="+id);
			return imageBitmap;
		}
		
		protected void onPostExecute(Bitmap image){
			img_usuarioCarona = (ImageView) findViewById(R.id.imageViewSolicitacaoFoto);
			img_usuarioCarona.setImageBitmap(image);
		}
    	
    }
    

    
}
