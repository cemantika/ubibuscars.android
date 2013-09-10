package com.example.ubibuscars;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class SugestoesActivity extends ListActivity {
	JSONObject sugestoes = null;
	ArrayList<HashMap<String, Object>> sugestaoList = new ArrayList<HashMap<String, Object>>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sugestoes);
		
		ListaSugestoes listarSugestoes = new ListaSugestoes();
		listarSugestoes.execute();
		
		
		
 /*
		String readJson = CustomHttpPost.readJson(Servidor.getServidor()
				+ "/buscaCaronas.php");
		try {
			JSONArray jsonArray = new JSONArray(readJson);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				String idUserAux = jsonObject.getString("id_usuario");
				
				//mostra so as caronas que nao sejam do usuario logado e ativas
				if ( (Integer.parseInt(idUserAux) != LoginActivity
						.getId_usuario()) && (jsonObject.getString("ativo").equals("1")) ) {
					String idCarona = jsonObject.getString("id_carona");
					String idUsuario = jsonObject.getString("id_usuario");
					String nome = jsonObject.getString("nome");
					String endOrigem = jsonObject.getString("endereco_origem");
					String endDestino = jsonObject
							.getString("endereco_destino");
					String horarioOrigem = jsonObject
							.getString("horario_origem");
					String horarioDestino = jsonObject
							.getString("horario_destino");
					String tipo = null;
					if (jsonObject.getString("tipo").equals("1")) {
						tipo = "Oferece";
					} else {
						tipo = "Pede";
					}

					HashMap<String, Object> map = new HashMap<String, Object>();

					map.put("id_carona", idCarona);
					map.put("id_usuario", idUsuario);
					map.put("nome", nome);
					map.put("tipo", tipo);
					map.put("endereco_origem", endOrigem);
					map.put("endereco_destino", endDestino);
					map.put("horario_origem", horarioOrigem);
					map.put("horario_destino", horarioDestino);

					if (jsonObject.getString("tipo").equals("1")) {
						map.put("imagem", R.drawable.oferece_carona);
					} else {
						map.put("imagem", R.drawable.pede_carona);
					}

					caronaList.add(map);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		ListAdapter adapter = new SimpleAdapter(this, caronaList,
				R.layout.list_item, new String[] { "nome", "endereco_origem",
						"endereco_destino", "imagem" }, new int[] {
						R.id.TextNomeUsuario, R.id.textEndOrig,
						R.id.textEndDest, R.id.imageViewTipoCarona });
		setListAdapter(adapter);
*/
		
		
		
		
		
		
		ListView lv = getListView();
		
		
//		lv.setOnItemClickListener(new OnItemClickListener() {
//
//			public void onItemClick(AdapterView<?> parent, View view, int position,
//					long id) {
//				
//				ClicaCarona clicaNaCarona = new ClicaCarona();
//				clicaNaCarona.execute(position);
//				
//			}
//		});
		
/*
		// Launching new screen on Selecting Single ListItem
		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				HashMap<String, Object> mapAux = new HashMap<String, Object>();
				mapAux = caronaList.get(position);

				String idCarona = (String) mapAux.get("id_carona");
				String idUsuario = (String) mapAux.get("id_usuario");
				String name = (String) mapAux.get("nome");
				String origem = (String) mapAux.get("endereco_origem");
				String destino = (String) mapAux.get("endereco_destino");
				String horarioOrigem = (String) mapAux.get("horario_origem");
				String horarioDestino = (String) mapAux.get("horario_destino");
				String tipo = (String) mapAux.get("tipo");

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						SolicitaCaronaActivity.class);
				in.putExtra("idCarona", idCarona);
				in.putExtra("idUsuario", idUsuario);
				in.putExtra("nome", name);
				in.putExtra("endereco_origem", origem);
				in.putExtra("endereco_destino", destino);
				in.putExtra("horario_origem", horarioOrigem);
				in.putExtra("horario_destino", horarioDestino);
				in.putExtra("tipo", tipo);
				startActivity(in);

			}
		});
*/
		
		
	}
	
	
	

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>	
	
	
	public class ClicaCarona extends AsyncTask<Integer, Void, Void>{

		protected Void doInBackground(Integer... params) {
			
			
			HashMap<String, Object> mapAux = new HashMap<String, Object>();
			
			int position = params[0];
			mapAux = sugestaoList.get(position);
			
			String idCarona = (String) mapAux.get("id_carona");
			String idUsuario = (String) mapAux.get("id_usuario");
			String name = (String) mapAux.get("nome");
			String origem = (String) mapAux.get("endereco_origem");
			String destino = (String) mapAux.get("endereco_destino");
			String horarioOrigem = (String) mapAux.get("horario_origem");
			String horarioDestino = (String) mapAux.get("horario_destino");
			String tipo = (String) mapAux.get("tipo");

			// Starting new intent
			Intent in = new Intent(getApplicationContext(),
					SolicitaCaronaActivity.class);
			in.putExtra("idCarona", idCarona);
			in.putExtra("idUsuario", idUsuario);
			in.putExtra("nome", name);
			in.putExtra("endereco_origem", origem);
			in.putExtra("endereco_destino", destino);
			in.putExtra("horario_origem", horarioOrigem);
			in.putExtra("horario_destino", horarioDestino);
			in.putExtra("tipo", tipo);
			startActivity(in);
			return null;
			
		}
		
		protected void onPostExecute(Intent i){
			
		}
		
		
		
	}
	
	//requisição assincrona para listar as sugestões
	public class ListaSugestoes extends AsyncTask<Void, Void, ArrayList<HashMap<String, Object>>>{
		ProgressDialog sugestoesDialog = new ProgressDialog(SugestoesActivity.this);
		
		@Override
		protected void onPreExecute() {
			
			sugestoesDialog.setTitle("Carregando sugestções");
			sugestoesDialog.setMessage("Aguarde, por favor...");
			sugestoesDialog.setIndeterminate(true);
			sugestoesDialog.show();
			
		}
		
		protected void addBlockedAchievement(String subText){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("imageSugestao", R.drawable.padlock_closed);
			map.put("textSugestao", "Sugestão Bloqueada");
			map.put("subTextSugestao", subText);
			
			sugestaoList.add(map);
		}
		
		protected void addSugestao(String title, String subTitle, int imageSugestao, boolean feito, boolean show, String alternateSubTitle){
			if(show){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map = new HashMap<String, Object>();
				map.put("imageSugestao", imageSugestao);
				map.put("textSugestao", title);
				map.put("subTextSugestao", subTitle);
				if(feito){ 
					map.put("imageSugestaoState", R.drawable.done);
				}
				sugestaoList.add(map);
			}else{
				addBlockedAchievement(alternateSubTitle);
			}
		}

		@Override
		protected ArrayList<HashMap<String, Object>> doInBackground(Void... params) {
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id_usuario", String.valueOf(LoginActivity
					.getId_usuario()) ));
			String resposta = CustomHttpPost.postData(Servidor.getServidor() + "/sugestoesState.php", nameValuePairs);
			
			try {
				sugestoes = new JSONObject(resposta);
				
				addSugestao(
					"Completar Perfil", 
					"Complete o seu perfil", 
					R.drawable.user_profile, 
					sugestoes.getBoolean("completar_perfil"), 
					true, 
					""
				);
				
				addSugestao(
					"Oferecer Carona", 
					"Ofereça carona para seus iguais", 
					R.drawable.oferece_carona, 
					sugestoes.getBoolean("oferecer_carona"), 
					true, 
					""
				);
				
				addSugestao(
					"Solicitar Carona", 
					"Solicite carona de seus iguais", 
					R.drawable.pede_carona, 
					sugestoes.getBoolean("solicitar_carona"), 
					true, 
					""
				);
				
				addSugestao(
					"Oferecer Carona 10x", 
					"Ofereça 10 caronas a seus iguais", 
					R.drawable.oferece_carona, 
					sugestoes.getBoolean("oferecer_10_carona"), 
					sugestoes.getBoolean("oferecer_carona"), 
					"Ofereça uma carona para desbloquear esta sugestão"
				);
				
				addSugestao(
					"Oferecer Carona 20x", 
					"Ofereça 20 caronas a seus iguais", 
					R.drawable.oferece_carona, 
					sugestoes.getBoolean("oferecer_20_carona"), 
					sugestoes.getBoolean("oferecer_10_carona"), 
					"Ofereça 10 caronas para desbloquear esta sugestão"
				);
				
				addSugestao(
					"Oferecer Carona 50x", 
					"Ofereça 50 caronas a seus iguais", 
					R.drawable.oferece_carona, 
					sugestoes.getBoolean("oferecer_50_carona"), 
					sugestoes.getBoolean("oferecer_20_carona"), 
					"Ofereça 20 caronas para desbloquear esta sugestão"
				);
				
				addSugestao(
					"Solicitar Carona 10x", 
					"Solicite 10 caronas de seus iguais", 
					R.drawable.pede_carona, 
					sugestoes.getBoolean("solicitar_10_carona"), 
					sugestoes.getBoolean("solicitar_carona"), 
					"Solicite uma carona para desbloquear esta sugestão"
				);
				
				addSugestao(
						"Solicitar Carona 20x", 
						"Solicite 20 caronas de seus iguais", 
						R.drawable.pede_carona, 
						sugestoes.getBoolean("solicitar_20_carona"), 
						sugestoes.getBoolean("solicitar_10_carona"), 
						"Solicite 10 caronas para desbloquear esta sugestão"
					);
				
				addSugestao(
						"Solicitar Carona 50x", 
						"Solicite 50 caronas de seus iguais", 
						R.drawable.pede_carona, 
						sugestoes.getBoolean("solicitar_50_carona"), 
						sugestoes.getBoolean("solicitar_20_carona"), 
						"Solicite 20 caronas para desbloquear esta sugestão"
					);
				
				addSugestao(
						"Oferecer 2 caronas no mesmo dia", 
						"Ofereça 2 caronas no mesmo dia a seus iguais", 
						R.drawable.oferece_carona, 
						sugestoes.getBoolean("carona_2_mesmo_dia"), 
						sugestoes.getBoolean("oferecer_carona"), 
						"Ofereça uma carona para desbloquear esta sugestão"
					);
				
				addSugestao(
						"Oferecer 4 caronas no mesmo dia", 
						"Ofereça 4 caronas no mesmo dia a seus iguais", 
						R.drawable.oferece_carona, 
						sugestoes.getBoolean("carona_4_mesmo_dia"), 
						sugestoes.getBoolean("carona_2_mesmo_dia"), 
						"Ofereça duas caronas no mesmo dia para desbloquear esta sugestão"
					);
				
				addSugestao(
						"Oferecer 10 caronas no mesmo dia", 
						"Ofereça 10 caronas no mesmo dia a seus iguais", 
						R.drawable.oferece_carona, 
						sugestoes.getBoolean("carona_10_mesmo_dia"), 
						sugestoes.getBoolean("carona_2_mesmo_dia"), 
						"Ofereça quatro caronas no mesmo dia para desbloquear esta sugestão"
					);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return sugestaoList;
		}
		
		@Override
		protected void onPostExecute(ArrayList<HashMap<String, Object>> result){
			
			sugestoesDialog.dismiss();
			
			ListAdapter adapter = new SimpleAdapter(SugestoesActivity.this, result,
					R.layout.list_item_sugestoes, new String[] { "imageSugestao", "textSugestao", "subTextSugestao", 
							"imageSugestaoState" }, new int[] {
							R.id.imageSugestao, R.id.textSugestao, R.id.subTextSugestao,
							R.id.imageSugestaoState });
			setListAdapter(adapter);
			
		}
		
		
		
	}
	
	
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	

}
