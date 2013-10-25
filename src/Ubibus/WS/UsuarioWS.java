package Ubibus.WS;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ubibuscars.CustomHttpPost;
import com.example.ubibuscars.LoginActivity;
import com.example.ubibuscars.Servidor;


public class UsuarioWS {

	public static Boolean autenticacaoUsuario(String login, String senha) throws JSONException {
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("login", login));
		nameValuePairs.add(new BasicNameValuePair("senha", senha));
		
		String readJson = CustomHttpPost.postData(
				Servidor.getServidor()
						+ "/autenticacao/authentication.php",
				nameValuePairs);
		
		if(!readJson.equals("Falha na conexão.")){
			JSONObject jsonObject = new JSONObject(readJson); 
			int sucesso=jsonObject.getInt("success"); 
			if(sucesso==1){
				LoginActivity.setId_usuario(Integer.parseInt(jsonObject
					.getString("pkusuario")));
				return true;
			}
		}
		
		return false;
		
	}

}
