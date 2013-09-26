package com.example.ubibuscars;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SolicitacoesDeCaronaActivity extends ListActivity {
	JSONArray solicitacoes = null;
	ArrayList<HashMap<String, Object>> solicitacoesList = new ArrayList<HashMap<String, Object>>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solicitacoes);
		ListaNotificacoes listaNotificacoes = new ListaNotificacoes();
		listaNotificacoes.execute();

		ListView lv = getListView();

		// Launching new screen on Selecting Single ListItem
		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				HashMap<String, Object> mapAux = new HashMap<String, Object>();
				mapAux = solicitacoesList.get(position);

				String idSolicitacao = (String) mapAux.get("id_solicitacao");
				String idCarona = (String) mapAux.get("id_carona");
				String id_usuariocarona = (String) mapAux
						.get("id_usuariocarona");
				String idUsuarioSolicita = (String) mapAux
						.get("id_usuariosolicita");
				String situacao = (String) mapAux.get("situacao");
				String endDestino = (String) mapAux.get("endereco_destino");
				String horarioDestino = (String) mapAux.get("horario_destino");
				String endOrigem = (String) mapAux.get("endereco_origem");
				String horarioOrigem = (String) mapAux.get("horario_origem");
				String nomeUsuarioCarona = (String) mapAux
						.get("nome_usuariocarona");
				String nomeUsuarioSolicita = (String) mapAux
						.get("nome_usuariosolicita");

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						RespostaSolicitacaoActivity.class);
				in.putExtra("idSolicitacao", idSolicitacao);
				in.putExtra("id_usuariocarona", id_usuariocarona);
				in.putExtra("id_carona", idCarona);
				in.putExtra("idUsuarioSolicita", idUsuarioSolicita);
				in.putExtra("situacao", situacao);
				in.putExtra("endDestino", endDestino);
				in.putExtra("horarioDestino", horarioDestino);
				in.putExtra("endOrigem", endOrigem);
				in.putExtra("horarioOrigem", horarioOrigem);
				in.putExtra("nomeUsuarioCarona", nomeUsuarioCarona);
				in.putExtra("nomeUsuarioSolicita", nomeUsuarioSolicita);
				startActivity(in);

				
			}
		});
	}
	
	
	
	


	public class ListaNotificacoes extends
			AsyncTask<Void, Void, ArrayList<HashMap<String, Object>>> {

		ProgressDialog aguardeNotificacoes = new ProgressDialog(
				SolicitacoesDeCaronaActivity.this);

		@Override
		protected void onPreExecute() {

			aguardeNotificacoes.setTitle("Carregando Notificações");
			aguardeNotificacoes.setMessage("Aguarde, por favor...");
			aguardeNotificacoes.setIndeterminate(true);
			aguardeNotificacoes.show();

		}

		@Override
		protected ArrayList<HashMap<String, Object>> doInBackground(
				Void... params) {
			String readJson = CustomHttpPost.readJson(Servidor.getServidor()
					+ "/buscaSolicitacoes.php?id_usuario="
					+ LoginActivity.getId_usuario());

			try {
				JSONArray jsonArray = new JSONArray(readJson);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);

					String idSolicitacao = jsonObject
							.getString("id_solicitacao");
					String id_usuariocarona = jsonObject
							.getString("id_usuariocarona");
					String idCarona = jsonObject
							.getString("id_carona");
					String idUsuarioSolicita = jsonObject
							.getString("id_usuariosolicita");
					String situacao = jsonObject.getString("situacao");
					String endDestino = jsonObject
							.getString("endereco_destino");
					String horarioDestino = jsonObject
							.getString("horario_destino");
					String endOrigem = jsonObject.getString("endereco_origem");
					String horarioOrigem = jsonObject
							.getString("horario_origem");
					String nomeUsuarioCarona = jsonObject
							.getString("nome_usuariocarona");
					String nomeUsuarioSolicita = jsonObject
							.getString("nome_usuariosolicita");

					HashMap<String, Object> map = new HashMap<String, Object>();

					map.put("id_carona", idCarona);
					map.put("id_solicitacao", idSolicitacao);
					map.put("id_usuariocarona", id_usuariocarona);
					map.put("id_usuariosolicita", idUsuarioSolicita);
					map.put("situacao", situacao);
					map.put("endereco_destino", endDestino);
					map.put("horario_destino", horarioDestino);
					map.put("endereco_origem", endOrigem);
					map.put("horario_origem", horarioOrigem);
					map.put("nome_usuariocarona", nomeUsuarioCarona);
					map.put("nome_usuariosolicita", nomeUsuarioSolicita);

					if (idUsuarioSolicita.equals(String.valueOf(LoginActivity
							.getId_usuario()))) {
						map.put("quem_solicitou", "Solicitada a "
								+ nomeUsuarioCarona);
						map.put("imagem", R.drawable.arrow_right);
					} else if (id_usuariocarona.equals(String
							.valueOf(LoginActivity.getId_usuario()))) {
						map.put("quem_solicitou", "Solicitada por "
								+ nomeUsuarioSolicita);
						map.put("imagem", R.drawable.arrow_left);
					}
					solicitacoesList.add(map);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return solicitacoesList;

		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, Object>> retorno) {

			aguardeNotificacoes.dismiss();

			ListAdapter adapter = new SimpleAdapter(
					SolicitacoesDeCaronaActivity.this, retorno,
					R.layout.list_item_solicitacoes, new String[] {
							"endereco_origem", "endereco_destino",
							"quem_solicitou", "situacao", "imagem" },
					new int[] { R.id.textOrigem, R.id.textDestino,
							R.id.textUsuarioSolicitacoes, R.id.textSituacao,
							R.id.imageViewTipoSolicitacao });
			setListAdapter(adapter);
		}

	}

}
