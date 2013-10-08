package com.example.ubibuscars;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AlertasActivity extends ListActivity {
	
	JSONArray alertas = null;
	ArrayList<HashMap<String, Object>> alertasList = new ArrayList<HashMap<String, Object>>();
	
	private Button bt_salvarAlerta;
	private EditText edt_alerta;
	private static final int DIALOG_ALERT = 10;
	
	String idSolicitacao, alerta, idUsuarioSolicita, id_usuarioCarona;
	int id_usuario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alertas);
		
		Intent in = getIntent();
		idSolicitacao = in.getStringExtra("idSolicitacao");
		//parte adcicionada
		idUsuarioSolicita = in.getStringExtra("idUsuarioSolicita");
		id_usuarioCarona = in.getStringExtra("id_usuarioCarona");
		
		id_usuario = LoginActivity.getId_usuario();
		if(getIntent().getStringExtra("id_usuario") != null ) {
			id_usuario = Integer.parseInt(getIntent().getStringExtra("id_usuario"));
		}
		
		
		
		bt_salvarAlerta = (Button) findViewById(R.id.buttonSalvarAlerta);
		edt_alerta = (EditText) findViewById(R.id.editAlerta);
		
		bt_salvarAlerta.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//chamar serviço para gravar novo tipo de alerta
				if(!edt_alerta.getText().toString().equals("")) {
					NovoAlertaASync novoAlerta = new NovoAlertaASync();
					novoAlerta.execute();
				} else {
					edt_alerta.requestFocus();
					Toast.makeText(getApplicationContext(), "Digite o alerta", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		
		ListarAlertas listaAlertas = new ListarAlertas();
		listaAlertas.execute();
		
		ListView lv = getListView();
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int pos ,long id) {
				
				TextView tv = (TextView) view.findViewById(R.id.TextAlerta);
				alerta = tv.getText().toString();
				
				EnviarAlertaASync enviarAlerta = new EnviarAlertaASync();
				enviarAlerta.execute();
				
				//Toast.makeText(getApplicationContext(), alerta+" "+id_usuario+" "+idSolicitacao, Toast.LENGTH_LONG).show();
			}			
		});
	    
		
	}


	
	public class ListarAlertas extends AsyncTask<Void, Void, ArrayList<HashMap<String, Object>>> {
		ProgressDialog aguardeAlertas = new ProgressDialog(AlertasActivity.this);
		
		@Override
		protected void onPreExecute() {
			aguardeAlertas.setTitle("Carregando alertas");
			aguardeAlertas.setMessage("Aguarde, por favor...");
			aguardeAlertas.setIndeterminate(true);
			aguardeAlertas.show();
		}
		
		@Override
		protected ArrayList<HashMap<String, Object>> doInBackground(
				Void... params) {
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id_usuario", String.valueOf(id_usuario)));
			
			String readJson = CustomHttpPost.postData(Servidor.getServidor()+"/buscaTiposAlertas.php", nameValuePairs);
			
			try {
				JSONArray jsonArray = new JSONArray(readJson);
				JSONObject alertasObj = jsonArray.getJSONObject(0);
				
				String alertas = alertasObj.getString("alertas");
				alertas = alertas.substring(1, alertas.length()-1);
				
				String[] aux = alertas.split(",");
				
				for(int i=0; i<aux.length; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("alerta", aux[i]);
					alertasList.add(map);
				}				
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			return alertasList;
		}
		
		@Override
		protected void onPostExecute(ArrayList<HashMap<String, Object>> result){
			
			aguardeAlertas.dismiss();
			
			HashMap<String, Object> aux = alertasList.get(0);
			if(aux.get("alerta").equals("ul")){
				Toast toast = Toast.makeText(getApplicationContext(), "Você não tem nehum alerta salvo.", Toast.LENGTH_SHORT);
				toast.show();
			}
			
			else{
			
			ListAdapter adapter = new SimpleAdapter(AlertasActivity.this, result,
					R.layout.list_alertas, new String[] { "alerta" }, new int[] {
							R.id.TextAlerta });
			setListAdapter(adapter);
			}
			
		}
	}
	
	public class NovoAlertaASync extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... alerta) {
			
			//insere novo alerta para o usuário
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("alerta", edt_alerta.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("id_usuario", String.valueOf(id_usuario)));
			
			String resposta;
			resposta = CustomHttpPost.postData(Servidor.getServidor()+"/insereAlerta.php", nameValuePairs);
			
			return resposta;
			
		}
		
		protected void onPostExecute(String r){
			Intent i = new Intent(getBaseContext(), AlertasActivity.class);
			i.putExtra("id_usuario", id_usuario);
			i.putExtra("idUsuarioSolicita",idUsuarioSolicita);
			i.putExtra("id_usuarioCarona", id_usuarioCarona);
        	startActivity(i);
		}
	}
	
	public class EnviarAlertaASync extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			String readJsonUsuario = CustomHttpPost.readJson(Servidor.getServidor()
					+ "/busca_um_usuario.php?id="
					+ id_usuario);
			String nome_remetente = null;
			
			try {
				JSONObject jsonObj = new JSONObject();
				JSONArray jsonArray = new JSONArray(readJsonUsuario);
				
				jsonObj = jsonArray.getJSONObject(0);
				nome_remetente = jsonObj.getString("nome");			
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			if(nome_remetente!=null) {				
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("id_usuario", String.valueOf(id_usuario)));
				
				//parte adicionada
				if(idUsuarioSolicita.equals(String.valueOf(LoginActivity.getId_usuario()))){
					nameValuePairs.add(new BasicNameValuePair("idUsuarioSolicita", String.valueOf(id_usuarioCarona)));
				}
				else
					nameValuePairs.add(new BasicNameValuePair("idUsuarioSolicita", String.valueOf(idUsuarioSolicita)));
				
				//nameValuePairs.add(new BasicNameValuePair("id_solicitacao", String.valueOf(idSolicitacao)));
				nameValuePairs.add(new BasicNameValuePair("alerta", alerta));
				nameValuePairs.add(new BasicNameValuePair("nome_remetente", String.valueOf(nome_remetente)));
				
				String resposta = CustomHttpPost.postData(Servidor.getServidor()+"/enviarAlertas.php", nameValuePairs);
				
				
				return resposta;
			} else {
				return "Erro ao enviar alerta";
			}
		}
		
		protected void onPostExecute(String r){
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(AlertasActivity.this);
        	alertDialog.setMessage(r);
        	alertDialog.setNeutralButton("Continuar", null);  
        	alertDialog.show();
		}
		
	}

}
