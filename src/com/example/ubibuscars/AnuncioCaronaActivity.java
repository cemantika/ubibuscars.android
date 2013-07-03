package com.example.ubibuscars;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;

public class AnuncioCaronaActivity extends Activity {

	private EditText edt_enderecoOrigem, edt_enderecoDestino,
			edt_horarioOrigem, edt_horarioDestino;
	private RadioGroup radioG_tipo;
	private Button bt_anunciar;

	private static String endOrigem;
	private static String endDestino;
	private static String idEndOrigem;
	private static String idEndDestino;
	private static int verificador = 0;

	int hora;
	int min;

	static final int ID_ORIGEM = 1;
	static final int ID_DESTINO = 2;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anunciar);

		edt_enderecoOrigem = (EditText) findViewById(R.id.editTextOrigemEndereco);
		edt_enderecoDestino = (EditText) findViewById(R.id.editTextDestinoEndereco);
		edt_horarioOrigem = (EditText) findViewById(R.id.editTextOrigemHorario);
		edt_horarioDestino = (EditText) findViewById(R.id.editTextDestinoHorario);

		radioG_tipo = (RadioGroup) findViewById(R.id.radioGroupAnunciar);

		bt_anunciar = (Button) findViewById(R.id.buttonAnunciarCadastrar);

		edt_horarioDestino.setOnClickListener(clkSetHoraDestino);
		edt_horarioOrigem.setOnClickListener(clkSetHoraOrigem);
		
		
		bt_anunciar.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				AnunciaCaronas anunciar = new AnunciaCaronas();
				anunciar.execute();
				
			}
		});
		
/*
		bt_anunciar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ((edt_enderecoOrigem.getText().length() > 0)
						|| (edt_enderecoDestino.getText().length() > 0)
						|| (edt_horarioOrigem.getText().length() > 0)
						|| (edt_horarioDestino.getText().length() > 0)) {
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

					int id_usuario = LoginActivity.getId_usuario();

					nameValuePairs.add(new BasicNameValuePair("id_usuario",
							String.valueOf(id_usuario)));
					nameValuePairs.add(new BasicNameValuePair("id_pontoOrigem",
							idEndOrigem));
					nameValuePairs.add(new BasicNameValuePair(
							"id_pontoDestino", idEndDestino));
					nameValuePairs.add(new BasicNameValuePair("enderecoOrigem",
							edt_enderecoOrigem.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair(
							"enderecoDestino", edt_enderecoDestino.getText()
									.toString()));
					nameValuePairs.add(new BasicNameValuePair("horarioOrigem",
							edt_horarioOrigem.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("horarioDestino",
							edt_horarioDestino.getText().toString()));

					if (radioG_tipo.getCheckedRadioButtonId() == R.id.radioOferece) {
						nameValuePairs.add(new BasicNameValuePair("tipo", "1"));
					} else {
						nameValuePairs.add(new BasicNameValuePair("tipo", "2"));
					}

					String resposta;
					resposta = CustomHttpPost.postData(Servidor.getServidor()
							+ "/insereCarona.php", nameValuePairs);

					AlertDialog.Builder alertDialog = new AlertDialog.Builder(
							AnuncioCaronaActivity.this);
					alertDialog.setMessage(resposta);
					alertDialog.setNeutralButton("Continuar",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									TabActivity tabs = (TabActivity) getParent();
									tabs.getTabHost().setCurrentTab(1);

								}
							});
					alertDialog.show();
					apagarDados();

				} else {
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(
							AnuncioCaronaActivity.this);
					alertDialog
							.setMessage("Todos os campos sï¿½o obrigatï¿½rios");
					alertDialog.setNeutralButton("Continuar", null);
					alertDialog.show();
				}
			}
		});
*/
	}

	public class AnunciaCaronas extends AsyncTask<Void, Void, Boolean> {
		ProgressDialog cadCaronaProgres = new ProgressDialog(
				AnuncioCaronaActivity.this);

		@Override
		protected void onPreExecute() {

			cadCaronaProgres.setTitle("Cadastrando Carona");
			cadCaronaProgres.setMessage("Aguarde, por favor...");
			cadCaronaProgres.setIndeterminate(true);
			cadCaronaProgres.show();

		}

		@Override
		protected Boolean doInBackground(Void... params) {

			// verifica se esta tudo preenchido
			if ((edt_enderecoOrigem.getText().length() > 0)
					&& (edt_enderecoDestino.getText().length() > 0)
					&& (edt_horarioOrigem.getText().length() > 0)
					&& (edt_horarioDestino.getText().length() > 0)) {

				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

				int id_usuario = LoginActivity.getId_usuario();

				nameValuePairs.add(new BasicNameValuePair("id_usuario", String
						.valueOf(id_usuario)));
				nameValuePairs.add(new BasicNameValuePair("id_pontoOrigem",
						idEndOrigem));
				nameValuePairs.add(new BasicNameValuePair("id_pontoDestino",
						idEndDestino));
				nameValuePairs.add(new BasicNameValuePair("enderecoOrigem",
						edt_enderecoOrigem.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("enderecoDestino",
						edt_enderecoDestino.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("horarioOrigem",
						edt_horarioOrigem.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("horarioDestino",
						edt_horarioDestino.getText().toString()));

				if (radioG_tipo.getCheckedRadioButtonId() == R.id.radioOferece) {
					nameValuePairs.add(new BasicNameValuePair("tipo", "1"));
				} else {
					nameValuePairs.add(new BasicNameValuePair("tipo", "2"));
				}

				String resposta;
				resposta = CustomHttpPost.postData(Servidor.getServidor()
						+ "/insereCarona.php", nameValuePairs);

				return true;

			} else {
				return false;
			}

		}

		@Override
		protected void onPostExecute(Boolean r) {

			cadCaronaProgres.dismiss();

			if (r == true) {
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						AnuncioCaronaActivity.this);
				alertDialog.setMessage("Carona Cadastrada com sucesso");
				alertDialog.setNeutralButton("Continuar",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								TabActivity tabs = (TabActivity) getParent();
								tabs.getTabHost().setCurrentTab(1);

							}
						});
				alertDialog.show();
				apagarDados();
			}
			
			else{
				AlertDialog.Builder alertDialogFalha = new AlertDialog.Builder(
						AnuncioCaronaActivity.this);
				alertDialogFalha.setMessage("Todos os campos são obrigatórios");
				alertDialogFalha.show();
			}
		}

	}

	@SuppressWarnings("static-access")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (verificador != 0) {
			if (verificador == 1) {
				edt_enderecoOrigem.setText(this.getEndOrigem());
			} else {
				edt_enderecoDestino.setText(this.getEndDestino());
			}
			verificador = 0;
		}
		super.onResume();
	}

	public void escolherEndOrigem(View view) {
		Intent mapa = new Intent(getBaseContext(), MapViewActivity.class);
		MapViewActivity.setOrigemOuDestino(1);
		startActivity(mapa);
	}

	public void escolherEndDestino(View view) {
		Intent mapa = new Intent(getBaseContext(), MapViewActivity.class);
		MapViewActivity.setOrigemOuDestino(2);
		startActivity(mapa);
	}

	private OnClickListener clkSetHoraOrigem = new OnClickListener() {

		public void onClick(View v) {
			showDialog(ID_ORIGEM);
		}
	};

	private OnClickListener clkSetHoraDestino = new OnClickListener() {

		public void onClick(View v) {
			showDialog(ID_DESTINO);
		}
	};

	//
	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == ID_ORIGEM) {
			return new TimePickerDialog(this, dialogoHorarioOrigem, hora, min,
					false);
		}

		else {
			return new TimePickerDialog(this, dialogoHorarioDestino, hora, min,
					false);
		}

	}

	private TimePickerDialog.OnTimeSetListener dialogoHorarioOrigem = new TimePickerDialog.OnTimeSetListener() {

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			hora = hourOfDay;
			min = minute;

			edt_horarioOrigem.setText(new StringBuilder()
					.append(completaComZero(hora)).append(":")
					.append(completaComZero(min)));
		}
	};

	private TimePickerDialog.OnTimeSetListener dialogoHorarioDestino = new TimePickerDialog.OnTimeSetListener() {

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			hora = hourOfDay;
			min = minute;

			edt_horarioDestino.setText(new StringBuilder()
					.append(completaComZero(hora)).append(":")
					.append(completaComZero(min)));
		}
	};

	// completa os minutos e horas com zero quando sï¿½o menores que 10
	private static String completaComZero(int time) {
		if (time >= 10)
			return String.valueOf(time);
		else
			return "0" + String.valueOf(time);
	}

	public void apagarDados() {
		edt_enderecoOrigem.setText("");
		edt_enderecoDestino.setText("");
		edt_horarioOrigem.setText("");
		edt_horarioDestino.setText("");
	}

	public static String getEndOrigem() {
		return endOrigem;
	}

	public static void setEndOrigem(String endOrigem) {
		AnuncioCaronaActivity.endOrigem = endOrigem;
	}

	public static String getEndDestino() {
		return endDestino;
	}

	public static void setEndDestino(String endDestino) {
		AnuncioCaronaActivity.endDestino = endDestino;
	}

	public static String getIdEndOrigem() {
		return idEndOrigem;
	}

	public static void setIdEndOrigem(String idEndOrigem) {
		AnuncioCaronaActivity.idEndOrigem = idEndOrigem;
	}

	public static String getIdEndDestino() {
		return idEndDestino;
	}

	public static void setIdEndDestino(String idEndDestino) {
		AnuncioCaronaActivity.idEndDestino = idEndDestino;
	}

	public static int getVerificador() {
		return verificador;
	}

	public static void setVerificador(int verificador) {
		AnuncioCaronaActivity.verificador = verificador;
	}

}
