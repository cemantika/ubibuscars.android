package com.example.ubibuscars;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class AlertaService extends Service {
	
	private String id_usuario;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	    
		id_usuario = intent.getStringExtra("id_usuario");
		
		
		BuscarAlertasAsync buscarAlertas = new BuscarAlertasAsync();
		buscarAlertas.execute();
		
		return Service.START_NOT_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private void gerarNotificacao(String alerta,String remetente, int i) {
		
	    Intent intent = new Intent(this, PrincipalActivity.class);
	    PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

	    NotificationCompat.Builder noti = new NotificationCompat.Builder(this)
	    	.setSmallIcon(R.drawable.cemantika_img)
	        .setContentTitle(remetente+" diz:")
	        .setContentText(alerta)
	        .setContentIntent(pIntent);
	        //.build();
	    
	    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    
	    //noti.flags |= Notification.FLAG_AUTO_CANCEL;
	    
	    noti.setAutoCancel(true);
	    
	    notificationManager.notify(i, noti.build());
	}
	
	public class BuscarAlertasAsync extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			nameValuePairs.add(new BasicNameValuePair("id_usuario", id_usuario));
			
			String resposta = CustomHttpPost.postData(Servidor.getServidor()+"/buscaAlertas.php", nameValuePairs);
			
			try {
				
				
				JSONArray jsonArray = new JSONArray(resposta);
				
				if(jsonArray.length()>0) {
					for(int i=0; i<jsonArray.length(); i++) {
						JSONObject jsonObj = jsonArray.getJSONObject(i);
						
						String alerta = jsonObj.getString("alerta");
						String remetente = jsonObj.getString("nome_remetente");
						gerarNotificacao(alerta,remetente, i);					
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}		
			
			return null;
		}
		
	}

}
