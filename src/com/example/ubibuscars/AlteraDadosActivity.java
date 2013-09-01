package com.example.ubibuscars;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AlteraDadosActivity extends Activity {
	private static final int REQUEST_CODE = 1;
	public int TAKE_PICTURE = 1;
	private Bitmap bitmap;
	private Button bt_tiraFoto, bt_alteraFoto, bt_alteraDados;
	private EditText edt_alteraNome, edt_alteraEmail, edt_alteraLogin,
			edt_alteraNascimento, edt_alterarCarroParticularLugares;
	private ImageView imgV_novaFoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_altera_dados);

		// bt_tiraFoto = (Button) findViewById(R.id.buttonUpdateTirarFoto);
		// bt_alteraFoto = (Button) findViewById(R.id.buttonUpdateMudarFoto);

		bt_alteraDados = (Button) findViewById(R.id.buttonUpdateDados);

		edt_alteraEmail = (EditText) findViewById(R.id.editTextUpdateEmail);
		//edt_alteraLogin = (EditText) findViewById(R.id.editTextUpdateLogin);
		edt_alteraNascimento = (EditText) findViewById(R.id.editTextUpdateNascimento);
		edt_alteraNome = (EditText) findViewById(R.id.editTextUpdateNome);

		
		//imgV_novaFoto = (ImageView) findViewById(R.id.imageViewUpdateFotoPadrao);

		/*
		 * String readJson = CustomHttpPost.readJson(Servidor.getServidor() +
		 * "/busca_um_usuario.php?id="+LoginActivity.getId_usuario());
		 * 
		 * 
		 * try { JSONArray jsonArray = new JSONArray(readJson);
		 * 
		 * JSONObject jsonObject = jsonArray.getJSONObject(0);
		 * 
		 * edt_alteraNome.setText(jsonObject.getString("nome"));
		 * edt_alteraEmail.setText(jsonObject.getString("email"));
		 * edt_alteraLogin.setText(jsonObject.getString("login"));
		 * edt_alteraNascimento
		 * .setText(jsonObject.getString("data_nascimento"));
		 * 
		 * imgV_novaFoto.setImageBitmap(CustomHttpPost.getImagem(Servidor.
		 * getServidor
		 * ()+"/buscaImagemUsuario.php?cod="+LoginActivity.getId_usuario()));
		 * 
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */

		// carrega os dados do usuario
		CarregaDadosAsync carregaDados = new CarregaDadosAsync();
		carregaDados.execute();
		/*
		 * bt_alteraFoto.setOnClickListener(new View.OnClickListener() {
		 * 
		 * public void onClick(View v) {
		 * 
		 * pickImage(getCurrentFocus()); } });
		 * 
		 * bt_tiraFoto.setOnClickListener(new View.OnClickListener() {
		 * 
		 * public void onClick(View arg0) {
		 * 
		 * Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		 * startActivityForResult(intent, TAKE_PICTURE); } });
		 */

		
		
		edt_alteraNascimento.addTextChangedListener(new TextWatcher() {
			boolean isUpdating;

			public void onTextChanged(CharSequence s, int start, int before,
					int after) {
				// TODO Auto-generated method stub
				if (isUpdating) {
					isUpdating = false;
					return;
				}

				boolean hasMask = s.toString().indexOf('/') > -1;
				String str = s.toString().replaceAll("[/]", "");

				if (after > before) {
					if (str.length() > 1) {
						edt_alteraNascimento.requestFocus();
						str = str.substring(0, 2) + '/' + str.substring(2);
					}
					if (str.length() > 5) {
						str = str.substring(0, 5) + '/' + str.substring(5);
					}
					isUpdating = true;
					edt_alteraNascimento.setText(str);
					edt_alteraNascimento.setSelection(edt_alteraNascimento.getText()
							.length());
				} else {
					isUpdating = true;
					edt_alteraNascimento.setText(str);
					edt_alteraNascimento.setSelection(Math.max(
							0,
							Math.min(hasMask ? start - before : start,
									str.length())));
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
		
		
		bt_alteraDados.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				if ((edt_alteraNome.getText().length() > 0)
						&& (edt_alteraEmail.getText().length() > 0)
						&& (edt_alteraNascimento.getText().length() == 10)) {

					CadastroValidation user = new CadastroValidation();
					if (user.nascimento(edt_alteraNascimento.getText()
							.toString())) {
						Log.i("teste", "teste");

						AlteraDadosAsync alteraDados = new AlteraDadosAsync();
						alteraDados.execute();
					}			
					else{
						
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(
									AlteraDadosActivity.this);
						alertDialog
									.setMessage("O valor da data precisa ser valido.");
						alertDialog.setNeutralButton("Continuar", null);
						alertDialog.show();
						
					}
					
				}
					else {
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(
								AlteraDadosActivity.this);
						alertDialog.setMessage("Todos os campos sao obrigatorios.");
						alertDialog.setNeutralButton("Continuar", null);
						alertDialog.show();	
				

				}
				/*
				 * ArrayList<NameValuePair> nameValuePairs = new
				 * ArrayList<NameValuePair>();
				 * 
				 * nameValuePairs.add(new BasicNameValuePair("nome",
				 * edt_alteraNome.getText().toString())); nameValuePairs.add(new
				 * BasicNameValuePair("email",
				 * edt_alteraEmail.getText().toString()));
				 * nameValuePairs.add(new BasicNameValuePair("login",
				 * edt_alteraLogin.getText().toString()));
				 * nameValuePairs.add(new BasicNameValuePair("nascimento",
				 * edt_alteraNascimento.getText().toString()));
				 * nameValuePairs.add(new
				 * BasicNameValuePair("id",String.valueOf(
				 * LoginActivity.getId_usuario())));
				 * 
				 * 
				 * 
				 * 
				 * BitmapDrawable drawable = (BitmapDrawable)
				 * imgV_novaFoto.getDrawable(); bitmap = drawable.getBitmap();
				 * ByteArrayOutputStream stream = new ByteArrayOutputStream();
				 * bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream); byte
				 * [] byte_arr = stream.toByteArray(); String image_str =
				 * Base64.encodeToString(byte_arr, Base64.DEFAULT);
				 * 
				 * 
				 * 
				 * nameValuePairs.add(new
				 * BasicNameValuePair("imagem",image_str));
				 * 
				 * 
				 * String resposta;
				 * resposta=CustomHttpPost.postData(Servidor.getServidor
				 * ()+"/alteraUsuarioAndroid.php", nameValuePairs);
				 * 
				 * AlertDialog.Builder alertDialog = new
				 * AlertDialog.Builder(AlteraDadosActivity.this);
				 * alertDialog.setMessage(resposta);
				 * alertDialog.setNeutralButton("Continuar", new
				 * DialogInterface.OnClickListener() {
				 * 
				 * public void onClick(DialogInterface dialog, int which) {
				 * Intent volta = new Intent(getBaseContext(),
				 * PrincipalActivity.class); startActivity(volta);
				 * 
				 * } }); alertDialog.show();
				 */

			}
		});

	}

	public void pickImage(View view) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
			try {
				// We need to recyle unused bitmaps
				if (bitmap != null) {
					bitmap.recycle();
				}
				InputStream stream = getContentResolver().openInputStream(
						data.getData());
				bitmap = BitmapFactory.decodeStream(stream);
				stream.close();
				imgV_novaFoto.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	public class AlteraDadosAsync extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			nameValuePairs.add(new BasicNameValuePair("nome", edt_alteraNome
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("email", edt_alteraEmail
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("login", edt_alteraLogin
					.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("nascimento",
					edt_alteraNascimento.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("id", String
					.valueOf(LoginActivity.getId_usuario())));
			

			/*
			 * BitmapDrawable drawable = (BitmapDrawable)
			 * imgV_novaFoto.getDrawable(); bitmap = drawable.getBitmap();
			 * ByteArrayOutputStream stream = new ByteArrayOutputStream();
			 * bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream); byte []
			 * byte_arr = stream.toByteArray(); String image_str =
			 * Base64.encodeToString(byte_arr, Base64.DEFAULT);
			 * 
			 * nameValuePairs.add(new BasicNameValuePair("imagem",image_str));
			 */

			String resposta;
			resposta = CustomHttpPost.postData(Servidor.getServidor()
					+ "/alteraUsuarioAndroid.php", nameValuePairs);

			return resposta;
		}

		protected void onPostExecute(String resp) {

			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					AlteraDadosActivity.this);
			alertDialog.setMessage(resp);
			alertDialog.setNeutralButton("Continuar",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							Intent volta = new Intent(getBaseContext(),
									PrincipalActivity.class);
							startActivity(volta);

						}
					});
			alertDialog.show();

		}

	}

	public class CarregaDadosAsync extends AsyncTask<Void, Void, JSONObject> {

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
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jsonObj;
		}

		protected void onPostExecute(JSONObject jsonObject) {
			try {
				 
				edt_alteraNome.setText(jsonObject.getString("nome"));
				edt_alteraEmail.setText(jsonObject.getString("email"));
				edt_alteraLogin.setText(jsonObject.getString("login"));
				
				String dataNasc = mudaFormatoData(jsonObject.getString("data_nascimento"));
				
				edt_alteraNascimento.setText(dataNasc);

				// imgV_novaFoto.setImageBitmap(CustomHttpPost.getImagem(Servidor.getServidor()+"/buscaImagemUsuario.php?cod="+LoginActivity.getId_usuario()));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	public String mudaFormatoData(String data){
		String mes;
		String ano;
		String dia;
		
		ano = data.substring(0,5);
		mes = data.substring(5,7);
		dia = data.substring(8);
		
		return dia+mes+ano;
	}
}
