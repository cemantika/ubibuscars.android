package com.example.ubibuscars;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	
	private Button cadastrar;
	private Button bt_entrar;
	private EditText edt_email;
	private EditText edt_senha;
	private TextView txt_esqSenha;
	private static int id_usuario;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		cadastrar = (Button) findViewById(R.id.buttonCadastrar);
		bt_entrar = (Button) findViewById(R.id.buttonEntrar);
		edt_email = (EditText) findViewById(R.id.LoginEditText);
		edt_senha = (EditText) findViewById(R.id.SenhaEditText);
		txt_esqSenha = (TextView) findViewById(R.id.textViewEsqueciSenha);

		txt_esqSenha.setOnClickListener(esq_senha);
		

		txt_esqSenha.setPaintFlags(txt_esqSenha.getPaintFlags()
				| Paint.UNDERLINE_TEXT_FLAG);

		cadastrar.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent telaDeCasdastro = new Intent(v.getContext(),
						CadastroActivity.class);

				startActivity(telaDeCasdastro);
			}
		});

		bt_entrar.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				LoginAsync login = new LoginAsync();
				login.execute();

			}
		});

	}

	private OnClickListener esq_senha = new OnClickListener() {

		public void onClick(View v) {
			Intent esqueciSenha = new Intent(getBaseContext(),
					EsqueciSenhaActivity.class);
			startActivity(esqueciSenha);
		}
	};

	public static int getId_usuario() {
		return id_usuario;
	}

	public static void setId_usuario(int id_usuario) {
		LoginActivity.id_usuario = id_usuario;
	}

	// classe para fazer a requisiscao assincrona
	public class LoginAsync extends AsyncTask<String, Integer, Boolean> {
		ProgressDialog loginAguarde = new ProgressDialog(LoginActivity.this);
		
		@Override
		protected void onPreExecute() {
			
			loginAguarde.setTitle("Efetuando Login");
			loginAguarde.setMessage("Aguarde, por favor...");
			loginAguarde.setIndeterminate(true);
			loginAguarde.show();
			
		}

		@Override
		protected Boolean doInBackground(String... params) {

			Boolean usuarioExiste = false;
			String readJson = CustomHttpPost.readJson(Servidor.getServidor()
					+ "/buscaUsuarios.php");
			try {
				JSONArray jsonArray = new JSONArray(readJson);

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					if( (jsonObject.getInt("ativo")== 1)&&(edt_email.getText().toString().equals(jsonObject
							.getString("email")))
							&& (edt_senha.getText().toString()
									.equals(jsonObject.getString("senha")))) {
						usuarioExiste = true;
						setId_usuario(Integer.parseInt(jsonObject
								.getString("id_usuario")));
						break;
					}
				}

			}

			catch (Exception e) {
				e.printStackTrace();
			}

			return usuarioExiste;
		}

		protected void onPostExecute(Boolean result) {
			
			
			if(result){
				Intent i = new Intent(getBaseContext(), PrincipalActivity.class);
				startActivity(i);
			}
			else{
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						LoginActivity.this);
				alertDialog.setTitle("Dados não conferem");
				alertDialog.setMessage("Tente novamente.");
				alertDialog.setNeutralButton("OK", null);
				alertDialog.show();
			}
			loginAguarde.dismiss();

		}

	}

}
