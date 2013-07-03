package com.example.ubibuscars;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

public class CadastroActivity extends Activity {

	private static final int REQUEST_CODE = 1;
	public int TAKE_PICTURE = 1;
	private Bitmap bitmap;
	private EditText edt_nome, edt_email, edt_senha, edt_nascimento;
	private Button bt_mudaFoto, bt_tiraFoto, bt_cadastrar;
	private ImageView img_perfil;
	private RadioGroup radioG_sexo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);

		initialise();

	}

	private void initialise() {
		edt_email = (EditText) findViewById(R.id.editTextCadastroEmail);
		edt_nascimento = (EditText) findViewById(R.id.editTextCadastroNascimento);
		edt_nome = (EditText) findViewById(R.id.editTextCadastroNome);
		edt_senha = (EditText) findViewById(R.id.editTextCadastroSenha);

		radioG_sexo = (RadioGroup) findViewById(R.id.radioGroupCadastroSexo);

		bt_cadastrar = (Button) findViewById(R.id.buttonCadastroCadastrar);
		bt_tiraFoto = (Button) findViewById(R.id.buttonTirarFoto);
		bt_mudaFoto = (Button) findViewById(R.id.buttonMudarFoto);

		img_perfil = (ImageView) findViewById(R.id.imageViewFotoPadrao);

		bt_tiraFoto.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(intent, TAKE_PICTURE);
			}
		});
		
		bt_mudaFoto.setVisibility(View.INVISIBLE);
		bt_tiraFoto.setVisibility(View.INVISIBLE);

		bt_cadastrar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if ((edt_nome.getText().length() > 0)
						&& (edt_email.getText().length() > 0)
						&& (edt_nascimento.getText().length() > 0)
						&& (edt_senha.getText().length() > 0)) {

					CadastroValidation user = new CadastroValidation();
					if (user.nascimento(edt_nascimento.getText().toString())) {
						Log.i("teste", "teste");
						
						CadastroAsync cadAsync = new CadastroAsync();
						cadAsync.execute();
						/*
						ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

						nameValuePairs.add(new BasicNameValuePair("nome",
								edt_nome.getText().toString()));
						nameValuePairs.add(new BasicNameValuePair("email",
								edt_email.getText().toString()));
						nameValuePairs.add(new BasicNameValuePair("senha",
								edt_senha.getText().toString()));
						nameValuePairs.add(new BasicNameValuePair("nascimento",
								edt_nascimento.getText().toString()));
						if (radioG_sexo.getCheckedRadioButtonId() == R.id.radioCadastroMasculino) {
							nameValuePairs.add(new BasicNameValuePair("sexo",
									"M"));
						} else {
							nameValuePairs.add(new BasicNameValuePair("sexo",
									"F"));
						}

						LocationManager LM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
						String bestProvider = LM.getBestProvider(
								new Criteria(), true);
						Location l = LM.getLastKnownLocation(bestProvider);

						// nameValuePairs.add(new
						// BasicNameValuePair("coord_x",String.valueOf(l.getLatitude())));
						// nameValuePairs.add(new
						// BasicNameValuePair("coord_y",String.valueOf(l.getLongitude())));

						nameValuePairs.add(new BasicNameValuePair("coord_x",
								"0"));
						nameValuePairs.add(new BasicNameValuePair("coord_y",
								"0"));

						BitmapDrawable drawable = (BitmapDrawable) img_perfil
								.getDrawable();
						bitmap = drawable.getBitmap();
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
						byte[] byte_arr = stream.toByteArray();
						//String image_str = Base64.encodeToString(byte_arr,
								//Base64.DEFAULT);
						
						String image_str = byte_arr.toString();

						nameValuePairs.add(new BasicNameValuePair("imagem",
								image_str));

						String resposta;
						resposta = CustomHttpPost.postData(
								Servidor.getServidor()
										+ "/insereUsuarioAndroid.php",
								nameValuePairs);
						
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(
								CadastroActivity.this);
						alertDialog.setMessage(resposta);
						alertDialog.setNeutralButton("Continuar", null);
						alertDialog.show();
						apagarDados();
						*/
						
					} else {
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(
								CadastroActivity.this);
						alertDialog
								.setMessage("O valor da data precisa ser valido.");
						alertDialog.setNeutralButton("Continuar", null);
						alertDialog.show();
					}

				} else {
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(
							CadastroActivity.this);
					alertDialog.setMessage("Todos os campos sao obrigatorios.");
					alertDialog.setNeutralButton("Continuar", null);
					alertDialog.show();
				}
			}
		});

		bt_mudaFoto = (Button) findViewById(R.id.buttonMudarFoto);
		
		
		
		
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<MUDAR FOTO>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
/*		bt_mudaFoto.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				pickImage(getCurrentFocus());
			}
		});
*/
		
		
		
		
		

		edt_nascimento.addTextChangedListener(new TextWatcher() {
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
						edt_nascimento.requestFocus();
						str = str.substring(0, 2) + '/' + str.substring(2);
					}
					if (str.length() > 5) {
						str = str.substring(0, 5) + '/' + str.substring(5);
					}
					isUpdating = true;
					edt_nascimento.setText(str);
					edt_nascimento.setSelection(edt_nascimento.getText()
							.length());
				} else {
					isUpdating = true;
					edt_nascimento.setText(str);
					edt_nascimento.setSelection(Math.max(
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

	}

	public void pickImage(View view) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, REQUEST_CODE);
	}

	
	
	
	
	
	
	
	
	
	
	
	
//	<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<nAO APAGAR>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/*
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
				img_perfil.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		super.onActivityResult(requestCode, resultCode, data);
	}
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	public class CadastroAsync extends AsyncTask<Void, Void, String>{
		
		@Override
		protected String doInBackground(Void... params) {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			nameValuePairs.add(new BasicNameValuePair("nome",
					edt_nome.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("email",
					edt_email.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("senha",
					edt_senha.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("nascimento",
					edt_nascimento.getText().toString()));
			if (radioG_sexo.getCheckedRadioButtonId() == R.id.radioCadastroMasculino) {
				nameValuePairs.add(new BasicNameValuePair("sexo",
						"M"));
			} else {
				nameValuePairs.add(new BasicNameValuePair("sexo",
						"F"));
			}

			LocationManager LM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			String bestProvider = LM.getBestProvider(
					new Criteria(), true);
			Location l = LM.getLastKnownLocation(bestProvider);

			// nameValuePairs.add(new
			// BasicNameValuePair("coord_x",String.valueOf(l.getLatitude())));
			// nameValuePairs.add(new
			// BasicNameValuePair("coord_y",String.valueOf(l.getLongitude())));

			nameValuePairs.add(new BasicNameValuePair("coord_x",
					"0" ));
			nameValuePairs.add(new BasicNameValuePair("coord_y",
					"0"));
			
			
			//<<<<<<<<<<<<<<<<<<<<<<<<<NAO APAGAR>>>>>>>>>>>>>>>>>>>>
			/*
			BitmapDrawable drawable = (BitmapDrawable) img_perfil
					.getDrawable();
			bitmap = drawable.getBitmap();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
			byte[] byte_arr = stream.toByteArray();
			String image_str = Base64.encodeToString(byte_arr,
					Base64.DEFAULT);

			nameValuePairs.add(new BasicNameValuePair("imagem",
					image_str));
*/
			
			
			
			
			
			String resposta;
			resposta = CustomHttpPost.postData(
					Servidor.getServidor()
							+ "/insereUsuarioAndroid.php",
					nameValuePairs);
			return resposta;
		}
		
		protected void onPostExecute(String r){
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					CadastroActivity.this);
			alertDialog.setMessage(r);
			alertDialog.setPositiveButton("Continuar", new OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					Intent telaDeLogin = new Intent(CadastroActivity.this,
							LoginActivity.class);
					startActivity(telaDeLogin);
				}
			});
			
			
			alertDialog.show();
			apagarDados();
			
			
			
		}
		
	}

	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	public void apagarDados() {
		edt_email.setText("");
		edt_nascimento.setText("");
		edt_nome.setText("");
		edt_senha.setText("");
	}

}