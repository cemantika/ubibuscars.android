package com.example.ubibuscars;




import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;


@SuppressWarnings("deprecation")
public class PrincipalActivity extends TabActivity {
	TabHost tabHost;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewprincipal);
        
     // inicializa o AlertaService
        Intent i = new Intent(getBaseContext(), AlertaService.class);
        i.putExtra("id_usuario", Integer.toString(LoginActivity.getId_usuario()));
        getBaseContext().startService(i);
        //-----
        
        TabHost tabHost = getTabHost();
        
        
        // Tab para anunciar caronas
        tabHost.addTab(tabHost.newTabSpec("Anunciar")
                .setIndicator("Anunciar", getResources().getDrawable(R.drawable.bullhorn))
                .setContent(new Intent(this, AnuncioCaronaActivity.class)));
        
     // Tab para mostrar todas as caronas
        tabHost.addTab(tabHost.newTabSpec("Caronas")
                .setIndicator("Caronas", getResources().getDrawable(R.drawable.flags))
                .setContent(new Intent(this, CaronasActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        
        // Tab de notifica�›es de carona
        tabHost.addTab(tabHost.newTabSpec("Notifica�›es")
                .setIndicator("Notificações", getResources().getDrawable(R.drawable.envelope))
                .setContent(new Intent(this, SolicitacoesDeCaronaActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        
        // 	Tab para mostrar todas as caronas
        tabHost.addTab(tabHost.newTabSpec("Sugestões")
                .setIndicator("Sugestões", getResources().getDrawable(R.drawable.exclamation_24x24))
                .setContent(new Intent(this, SugestoesActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				
				
			}
		});
        
	}
	
	
	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_configuracoes, menu);
        return true;
    }
    
    
    
   @Override
    public boolean onOptionsItemSelected(MenuItem item){
	   
	   switch (item.getItemId()) {
	       /*
	       case R.id.menu_perfil:{
	    	   Intent perfil = new Intent(getBaseContext(), PerfilActivity.class);
	    	   startActivity(perfil);
	    	   return true;
	       }
	       */
	       case R.id.menu_editarCarona:{
	    	   Intent minhasCaronas = new Intent(getBaseContext(), MinhasCaronasActivity.class);
	    	   startActivity(minhasCaronas);
	    	   return true;
	       }
	       
	       case R.id.menu_delete:{
	    	   AlertDialog.Builder alertaDelete = new AlertDialog.Builder(PrincipalActivity.this);
	    	   alertaDelete.setTitle("Aviso!");
	    	   alertaDelete.setMessage("Você realmente quer deletar sua conta?");
	    	   alertaDelete.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					
					DeletaUserAsync deleta = new DeletaUserAsync();
					deleta.execute();
					
					Intent voltaLogin = new Intent(getBaseContext(),LoginActivity.class);
					startActivity(voltaLogin);
					
				/*
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

					nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(LoginActivity.getId_usuario())));
					
					CustomHttpPost.postData(Servidor.getServidor()+"/deletaUsuario.php", nameValuePairs);
					
					Intent voltaLogin = new Intent(getBaseContext(),LoginActivity.class);
					startActivity(voltaLogin);	
					
					*/
				}
				
			});
	    	   alertaDelete.setNegativeButton("Não", null);
	    	   alertaDelete.show();
	       }
	       
	      
	       case R.id.menu_sobre:{
	    	   Intent sobre =  new Intent(getBaseContext(),SobreActivity.class);
	    	   startActivity(sobre);
	       }
	       
	       default:
	    	   return super.onOptionsItemSelected(item);
	   }
    }
   
   
   public class DeletaUserAsync extends AsyncTask<Void, Void, Void>{
	   ProgressDialog deleteAguarde = new ProgressDialog(PrincipalActivity.this);
		
		@Override
		protected void onPreExecute() {
			
			deleteAguarde.setTitle("Deletando Conta");
			deleteAguarde.setMessage("Aguarde, por favor...");
			deleteAguarde.setIndeterminate(true);
			deleteAguarde.show();
			
		}
	@Override
	protected Void doInBackground(Void... params) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(LoginActivity.getId_usuario())));
		
		CustomHttpPost.postData(Servidor.getServidor()+"/deletaUsuario.php", nameValuePairs);
		return null;
	}
	
	protected void onPostExecute(){
		deleteAguarde.dismiss();
		
	}
	   
   }
   
   

}
