package com.example.ubibuscars;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.ubibuscars.AlteraDadosActivity.CarregaDadosAsync;

import Evento.WS.EventoWS;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint.Join;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PerfilActivity extends Activity {

	private TextView txt_nome, txt_curso, txt_email, txt_usuario;
	private Button bt_alteraPerfil;
	private ImageView img_perfil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil);

		txt_curso = (TextView) findViewById(R.id.textViewPerfilCurso);
		txt_email = (TextView) findViewById(R.id.textViewPerfilEmail);
		txt_nome = (TextView) findViewById(R.id.textViewPerfilNome);
		txt_usuario = (TextView) findViewById(R.id.textViewPerfilUsuario);

		bt_alteraPerfil = (Button) findViewById(R.id.buttonPerfilEditar);

		img_perfil = (ImageView) findViewById(R.id.imageViewPerfilFoto);
		
		CarregaPerfilAsync carregaPerfil = new CarregaPerfilAsync();
		carregaPerfil.execute();

		/*
		 * String readJson = CustomHttpPost.readJson(Servidor.getServidor() +
		 * "/busca_um_usuario.php?id="+LoginActivity.getId_usuario()); try {
		 * JSONArray jsonArray = new JSONArray(readJson);
		 * 
		 * JSONObject jsonObject = jsonArray.getJSONObject(0);
		 * 
		 * txt_nome.setText(jsonObject.getString("nome"));
		 * txt_email.setText(jsonObject.getString("email"));
		 * txt_usuario.setText(jsonObject.getString("login"));
		 * txt_curso.setText(jsonObject.getString("curso"));
		 * 
		 * img_perfil.setImageBitmap(CustomHttpPost.getImagem(Servidor.getServidor
		 * ()+"/buscaImagemUsuario.php?cod="+LoginActivity.getId_usuario()));
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */

		bt_alteraPerfil.setOnClickListener(clk_edtPerfil);
	}

	private OnClickListener clk_edtPerfil = new OnClickListener() {

		public void onClick(View v) {
			Intent mudaPerfil = new Intent(getBaseContext(),
					AlteraDadosActivity.class);
			startActivity(mudaPerfil);
		}
	};

	public class CarregaPerfilAsync extends AsyncTask<Void, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(Void... params) {
			String readJson = CustomHttpPost.readJson(Servidor.getServidor()
					+ "/busca_um_usuario.php?id="
					+ LoginActivity.getId_usuario());
			JSONObject jsonObj = new JSONObject();
			try {
				JSONArray jsonArray = new JSONArray(readJson);

				JSONObject jsonObject = jsonArray.getJSONObject(0);
				jsonObj = jsonObject;

				//img_perfil.setImageBitmap(CustomHttpPost.getImagem(Servidor.getServidor()+"/buscaImagemUsuario.php?cod="+LoginActivity.getId_usuario()));
				EventoWS.insereEvento(LoginActivity.getId_usuario(), "Abriu a view de Perfil de Usu‡rio");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jsonObj;
		}

		protected void onPostExecute(JSONObject resp) {
			try {
				txt_nome.setText(resp.getString("nome"));
				txt_email.setText(resp.getString("email"));
				txt_usuario.setText(resp.getString("login"));
				txt_curso.setText(resp.getString("curso"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
