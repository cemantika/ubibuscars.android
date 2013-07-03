package com.example.ubibuscars;


import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EsqueciSenhaActivity extends Activity {
	
	private Button bt_mostrarSenha;
	private EditText edt_esqSenhaEmail, edt_esqSenhaNascimento;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_esqueci_senha);
		
		bt_mostrarSenha = (Button) findViewById(R.id.buttonMostrarSenha);
		edt_esqSenhaEmail = (EditText) findViewById(R.id.editTextEsquSenhaEmail);
		edt_esqSenhaNascimento = (EditText) findViewById(R.id.editTextEsquSenhaNascimento);
		
		
		bt_mostrarSenha.setOnClickListener(clk_mostrSenha);
		
		edt_esqSenhaNascimento.addTextChangedListener(new TextWatcher() {
			boolean isUpdating; 
			public void onTextChanged(CharSequence s, int start, int before, int after) {
				// TODO Auto-generated method stub
				if (isUpdating) {  
                    isUpdating = false;  
                   return;  
                }  
                  
                  
                boolean hasMask = s.toString().indexOf('/') > -1;  
                String str = s.toString().replaceAll("[/]", "");  
                
				if (after > before) {  
                    if (str.length() > 1) {  
                    	edt_esqSenhaNascimento.requestFocus();  
                        str = str.substring(0, 2) + '/' + str.substring(2);  
                    }  
                    if (str.length() > 5) {  
                        str = str.substring(0, 5) + '/' + str.substring(5);  
                    }  
                    isUpdating = true;  
                    edt_esqSenhaNascimento.setText(str);  
                    edt_esqSenhaNascimento.setSelection(edt_esqSenhaNascimento.getText().length());  
                } else {  
                    isUpdating = true;  
                    edt_esqSenhaNascimento.setText(str);  
                    edt_esqSenhaNascimento.setSelection(Math .max(0, Math .min(hasMask ? start - before : start, str.length())));  
                }
				
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		
	}
	
	
	private OnClickListener clk_mostrSenha = new OnClickListener() {
		
		public void onClick(View v) {
			
			EsqSenha esqSen = new EsqSenha();
			esqSen.execute();
			
			/*
			String readJson = CustomHttpPost.readJson(Servidor.getServidor()+"/buscaUsuarios.php");
			Boolean usuarioExiste=false;
			try {
			      JSONArray jsonArray = new JSONArray(readJson);
			      
			      String senha="";
			      String data = mudaData(edt_esqSenhaNascimento.getText().toString());
	        	
		        	
		        	
			      for (int i = 0; i < jsonArray.length(); i++) {
			        JSONObject jsonObject = jsonArray.getJSONObject(i);
			        if((edt_esqSenhaEmail.getText().toString().equals(jsonObject.getString("email"))) && (data.equals(jsonObject.getString("data_nascimento"))) ){
			        	usuarioExiste=true;
			        	senha = jsonObject.getString("senha");
						break;
			        }
			      }
			      
			      if(usuarioExiste==true){
			    	  AlertDialog.Builder alertDialog = new AlertDialog.Builder(EsqueciSenhaActivity.this);
			        	alertDialog.setTitle("Senha");
			        	alertDialog.setMessage(senha);
			        	alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
							
							public void onClick(DialogInterface dialog, int which) {
								Intent voltaLogin = new Intent(getBaseContext(),LoginActivity.class);
								startActivity(voltaLogin);
								
							}
						});  
			        	alertDialog.show();
			        	
			        	
			      }else{
			    	  AlertDialog.Builder alertDialog = new AlertDialog.Builder(EsqueciSenhaActivity.this);
			        	alertDialog.setTitle("Dados não conferem");
			        	alertDialog.setMessage("Tente novamente.");
			        	alertDialog.setNeutralButton("OK", null);  
			        	alertDialog.show();
			      }
			      
			    } catch (Exception e) {
			      e.printStackTrace();
			    } 
			    */
		}
		
		
	};
	
	
	public static String mudaData(String s){
		if(s.equals("") || s.length() != 10){
			return s;
		}
		s = s.substring(6)+"-"+s.substring(3, 5)+"-"+s.substring(0, 2);
		return s;
	}
	
	public class EsqSenha extends AsyncTask<Void, Void, String>{

		
		ProgressDialog esqSenhaAguarde = new ProgressDialog(EsqueciSenhaActivity.this);
		
		@Override
		protected void onPreExecute() {
			
			esqSenhaAguarde.setTitle("Carregando");
			esqSenhaAguarde.setMessage("Aguarde, por favor...");
			esqSenhaAguarde.setIndeterminate(true);
			esqSenhaAguarde.show();
			
		}
		
		@Override
		protected String doInBackground(Void... params) {
			String readJson = CustomHttpPost.readJson(Servidor.getServidor()+"/buscaUsuarios.php");
			Boolean usuarioExiste=false;
			String senha="";
			try {
			      JSONArray jsonArray = new JSONArray(readJson);
			      String data = mudaData(edt_esqSenhaNascimento.getText().toString());
		        	
			      for (int i = 0; i < jsonArray.length(); i++) {
			        JSONObject jsonObject = jsonArray.getJSONObject(i);
			        if((edt_esqSenhaEmail.getText().toString().equals(jsonObject.getString("email"))) && (data.equals(jsonObject.getString("data_nascimento"))) ){
			        	usuarioExiste=true;
			        	senha = jsonObject.getString("senha");
						break;
			        }
			      }
			      
			    } catch (Exception e) {
			      e.printStackTrace();
			    }
			if(usuarioExiste != true){
				senha = "";
			}
			
			return senha;
			
		}
		
		protected void onPostExecute(String s){
			esqSenhaAguarde.dismiss();
			if(s == ""){
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(EsqueciSenhaActivity.this);
	        	alertDialog.setTitle("Dados não conferem");
	        	alertDialog.setMessage("Tente novamente.");
	        	alertDialog.setNeutralButton("OK", null);  
	        	alertDialog.show();
			}
			else{
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(EsqueciSenhaActivity.this);
	        	alertDialog.setTitle("Senha");
	        	alertDialog.setMessage(s);
	        	alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						Intent voltaLogin = new Intent(getBaseContext(),LoginActivity.class);
						startActivity(voltaLogin);
						
					}
				});  
	        	alertDialog.show();
			}
		}
		
	}


}

