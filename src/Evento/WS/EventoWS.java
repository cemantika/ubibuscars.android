package Evento.WS;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.ubibuscars.CustomHttpPost;
import com.example.ubibuscars.Servidor;

public class EventoWS {
	
	public static void insereEvento(int fkusuario, String nomeEvento){
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("fkusuario", String.valueOf(fkusuario)));
		nameValuePairs.add(new BasicNameValuePair("nome_acao", nomeEvento));
		
		String retorno = CustomHttpPost.postData(
				Servidor.getServidor()
						+ "/evento/insere.php",
				nameValuePairs);
		
	}

}
