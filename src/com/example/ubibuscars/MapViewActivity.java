package com.example.ubibuscars;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewActivity extends FragmentActivity{
	public GoogleMap map;
	private SupportMapFragment fragmentMapa;
	ArrayList<HashMap<String, Object>> pontos = new ArrayList<HashMap<String, Object>>();
	private static int origemOuDestino;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapview);
		/*
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectDiskReads()
        .detectDiskWrites()
        .detectNetwork()   // or .detectAll() for all detectable problems
        .penaltyLog()
        .build());
		*/
		
		
		fragmentMapa = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragMap);
		map = fragmentMapa.getMap();
		
		
		
		
		
		
	
		MostraMarkersMapa carregaMarkers = new MostraMarkersMapa();
		carregaMarkers.execute();
		
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			
			public void onInfoWindowClick(Marker marker) {
				// TODO Auto-generated method stub
				
				
				
				
				
				//ProgressDialog progress;
				//progress = ProgressDialog.show(MapViewActivity.this, "", "Aguarde...");
				
				Geocoder gc = new Geocoder(MapViewActivity.this, Locale.getDefault());
				LatLng localizacao = marker.getPosition();
				HashMap<String, Object> mapAux = new HashMap<String, Object>();
				String idDoPontoClicado=null;
				for (int i=0; i< pontos.size() ; i++) {
					mapAux = pontos.get(i);
					if(mapAux.get("nome_do_ponto").equals(marker.getTitle())){
						idDoPontoClicado=(String) mapAux.get("id_ponto");
					}
				}
			    try {
					List<android.location.Address> addresses = gc.getFromLocation(
							localizacao.latitude, localizacao.longitude, 1);
					String addressString;
					android.location.Address address = addresses.get(0);
					addressString = address.getThoroughfare();
					if(origemOuDestino==1){
						AnuncioCaronaActivity.setEndOrigem(addressString);
						AnuncioCaronaActivity.setIdEndOrigem(idDoPontoClicado);
						AnuncioCaronaActivity.setVerificador(1);
				 	    finish();
					}else{
						AnuncioCaronaActivity.setEndDestino(addressString);
						AnuncioCaronaActivity.setIdEndDestino(idDoPontoClicado);
						AnuncioCaronaActivity.setVerificador(2);
						finish();
					}
				      
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		/*
		 * MostraMarkersMapa carregaMarkers = new MostraMarkersMapa();
				carregaMarkers.execute();
		 * 
		 * 
		String readJson = CustomHttpPost.readJson(Servidor.getServidor()
				+ "/buscaPontos.php");
		
		
		try {
			JSONArray jsonArray = new JSONArray(readJson);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				
				String idPonto = jsonObject.getString("id_ponto");
				String nomePonto = jsonObject.getString("nome_do_ponto");

				Double latitude = (Double) Double.parseDouble(jsonObject
						.getString("latitude"));
				Double longitude = (Double) Double.parseDouble(jsonObject
						.getString("longitude"));

				LatLng localizacao = new LatLng(latitude, longitude);

				map.addMarker(new MarkerOptions().position(localizacao)
						.title(jsonObject.getString("nome_do_ponto"))
						.snippet(jsonObject.getString("descricao_do_ponto")));

				map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacao,
						15));

				// Zoom in, animating the camera.
				map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

				HashMap<String, Object> map = new HashMap<String, Object>();

				map.put("id_ponto", idPonto);
				map.put("nome_do_ponto", nomePonto);
				
				pontos.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if(origemOuDestino==1){
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					MapViewActivity.this);
			alertDialog.setTitle("Aten�‹o");
			alertDialog.setMessage("Escolha o ponto de origem para sua rota clicando na informa�‹o do mesmo.");
			alertDialog.setNeutralButton("Continuar", null);
			alertDialog.show();
			
		}else{
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					MapViewActivity.this);
			alertDialog.setTitle("Aten�‹o");
			alertDialog.setMessage("Escolha o ponto de destino para sua rota clicando na informa�‹o do mesmo.");
			alertDialog.setNeutralButton("Continuar", null);
			alertDialog.show();
		}
		super.onResume();
	}
	
	

	
	/*
	
	
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub

		
		ProgressDialog progress;
		progress = ProgressDialog.show(MapViewActivity.this, "", "Aguarde...");
		Geocoder gc = new Geocoder(this, Locale.getDefault());
		LatLng localizacao = marker.getPosition();
		HashMap<String, Object> mapAux = new HashMap<String, Object>();
		String idDoPontoClicado=null;
		for (int i=0; i< pontos.size() ; i++) {
			mapAux = pontos.get(i);
			if(mapAux.get("nome_do_ponto").equals(marker.getTitle())){
				idDoPontoClicado=(String) mapAux.get("id_ponto");
			}
		}
	    try {
			List<android.location.Address> addresses = gc.getFromLocation(
					localizacao.latitude, localizacao.longitude, 1);
			String addressString;
			android.location.Address address = addresses.get(0);
			addressString = address.getThoroughfare();
			if(origemOuDestino==1){
				AnuncioCaronaActivity.setEndOrigem(addressString);
				AnuncioCaronaActivity.setIdEndOrigem(idDoPontoClicado);
				AnuncioCaronaActivity.setVerificador(1);
		 	    finish();
			}else{
				AnuncioCaronaActivity.setEndDestino(addressString);
				AnuncioCaronaActivity.setIdEndDestino(idDoPontoClicado);
				AnuncioCaronaActivity.setVerificador(2);
				finish();
			}
		      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
*/
	public static int getOrigemOuDestino() {
		return origemOuDestino;
	}

	public static void setOrigemOuDestino(int origemOuDestino) {
		MapViewActivity.origemOuDestino = origemOuDestino;
	}
	
	
	public class MostraMarkersMapa extends AsyncTask<Void, Void, ArrayList<HashMap<String, Object>>>{

		
		ProgressDialog pontosAguarde = new ProgressDialog(MapViewActivity.this);
		
		@Override
		protected void onPreExecute() {
			
			pontosAguarde.setTitle("Carregando");
			pontosAguarde.setMessage("Aguarde, por favor...");
			pontosAguarde.setIndeterminate(true);
			pontosAguarde.show();
			
		}
		
		@Override
		protected ArrayList<HashMap<String, Object>> doInBackground(Void... params) {
			String readJson = CustomHttpPost.readJson(Servidor.getServidor()
					+ "/buscaPontos.php");
			
			
			try {
				JSONArray jsonArray = new JSONArray(readJson);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					
					String idPonto = jsonObject.getString("id_ponto");
					String nomePonto = jsonObject.getString("nome_do_ponto");
					
					String latitude_ponto = jsonObject.getString("latitude");
					String longitude_ponto = jsonObject.getString("longitude");
					String descricao_ponto = jsonObject.getString("descricao_do_ponto");
					
					Double latitude = (Double) Double.parseDouble(jsonObject
							.getString("latitude"));
					Double longitude = (Double) Double.parseDouble(jsonObject
							.getString("longitude"));

					
					HashMap<String, Object> Hmap = new HashMap<String, Object>();

					Hmap.put("id_ponto", idPonto);
					Hmap.put("nome_do_ponto", nomePonto);
					Hmap.put("latitudePonto", latitude);
					Hmap.put("longitudePonto", longitude);
					Hmap.put("descricao", descricao_ponto);
					
					pontos.add(Hmap);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return pontos;
		}
		
		protected void onPostExecute(ArrayList<HashMap<String, Object>> pts){
			
			//fragmentMapa = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragMap);
			//map = fragmentMapa.getMap();
			
			pontosAguarde.dismiss();
			
			HashMap<String, Object> mapAux = new HashMap<String, Object>();
			
			for(int i = 0;i<pts.size();i++){
				mapAux = pts.get(i);
				Double latitude = (Double) mapAux.get("latitudePonto");
				Double longitude = (Double) mapAux.get("longitudePonto");	
				
				LatLng localizacao = new LatLng(latitude, longitude);
				
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacao,
						15));
				
				map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
				
				String nomePonto = (String) mapAux.get("nome_do_ponto");
				String descricaoPonto = (String) mapAux.get("descricao");
				
				map.addMarker(new MarkerOptions().position(localizacao)
						.title(nomePonto)
						.snippet(descricaoPonto));	
			}
			
			
		}
		
	}
	



}
