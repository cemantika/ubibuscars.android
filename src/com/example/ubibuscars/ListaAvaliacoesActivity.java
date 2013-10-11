package com.example.ubibuscars;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.ubibuscars.CaronasActivity.RateBinder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SimpleAdapter.ViewBinder;

public class ListaAvaliacoesActivity extends ListActivity {
	
	JSONArray avaliacoes = null;
	ArrayList<HashMap<String, Object>> avList = new ArrayList<HashMap<String, Object>>();
	
	private RatingBar rtBar;
	private TextView txtNome;
	
	String id_avaliado, media, nome;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_avaliacoes);
		
		Intent prevIntent = getIntent();
		id_avaliado = prevIntent.getStringExtra("id_avaliado");
		media = prevIntent.getStringExtra("media");
		nome = prevIntent.getStringExtra("nome");
		
		txtNome = (TextView) findViewById(R.id.textViewNomeAvaliacao);
		rtBar = (RatingBar) findViewById(R.id.ratingBarListAv);
		
		txtNome.setText(nome);
		
		if(media.equals("null")){
			rtBar.setRating(0);
		}
		else{
			
			rtBar.setRating(Float.parseFloat(media));

		}
				
		ListaAvaliacoesAsync listaAvalicaoes = new ListaAvaliacoesAsync();
		listaAvalicaoes.execute();
	}
	
	public class ListaAvaliacoesAsync extends AsyncTask<Void, Void, ArrayList<HashMap<String, Object>>> {

		ProgressDialog aguardeCaronas = new ProgressDialog(ListaAvaliacoesActivity.this);
		
		@Override
		protected void onPreExecute() {
			
			aguardeCaronas.setTitle("Carregando caronas");
			aguardeCaronas.setMessage("Aguarde, por favor...");
			aguardeCaronas.setIndeterminate(true);
			aguardeCaronas.show();
			
		}
		
		@Override
		protected ArrayList<HashMap<String, Object>> doInBackground(Void... params) {
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id_avaliado", id_avaliado));
			
			String readJson = CustomHttpPost.postData(Servidor.getServidor()
					+ "/buscaAvaliacoes.php", nameValuePairs);
			
			try {
				JSONArray jsonArray = new JSONArray(readJson);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					
						String comentario = jsonObject.getString("avaliacao");
						String media = jsonObject.getString("nota");
						String nome = jsonObject.getString("nome_usuario");
						
						HashMap<String, Object> map = new HashMap<String, Object>();

						map.put("avaliacao", comentario);
						map.put("nota", media);
						map.put("nome", nome);
					
						avList.add(map);

					}
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return avList;
		}
		
		
		
		protected void onPostExecute(ArrayList<HashMap<String, Object>> result){
			
			aguardeCaronas.dismiss();
			
			ListAdapter adapter = new SimpleAdapter(
				ListaAvaliacoesActivity.this,
				result,
				R.layout.list_avaliacao,
				new String[] 	{"nome","avaliacao","nota"},
				new int[] 		{R.id.TextNomeUsuarioListaAv,R.id.textComentario, R.id.ratingBarListaAv});
			
			((SimpleAdapter) adapter).setViewBinder(new RateBinder());
			
			setListAdapter(adapter);
			
		}
		
	}
	
	
	public class RateBinder implements ViewBinder{
		
		public boolean setViewValue(View view, Object data, String textRepresentation)
		{
			//se a view for a ratingBar, executa aqui, senao usa o binder do ListAdapter
			if(view.getId() == R.id.ratingBarListaAv){
				//tratando as notas nulas
				if(data.equals("null")){
					RatingBar ratingBar = (RatingBar) view;
					ratingBar.setRating(0);
					return true;
				}
				
				String stringVal = (String) data;
				Float ratingValue = Float.parseFloat(stringVal);
				RatingBar ratingBar = (RatingBar) view;
				ratingBar.setRating(ratingValue);
				return true;	
			}
			// TODO Auto-generated method stub
			return false;
		}
	}

}
