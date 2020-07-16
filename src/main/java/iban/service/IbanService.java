package iban.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public class IbanService {

	public boolean validateIban(String iban) {
		char[] charIban = iban.replace(" ", "").toCharArray();
		if (!checkIbanLenght(String.valueOf(charIban[0]) + String.valueOf(charIban[1]), charIban.length)) {
			return false;
		}

		String finalNumber = "";
		charIban = moveChars(charIban);
		for (int i = 0; i < charIban.length; i++) {
			char c = charIban[i];
			if (c >= '0' && c <= '9') {
				finalNumber = finalNumber + c;
			} else if (c >= 'A' && c <= 'Z') {

				finalNumber = finalNumber + Character.getNumericValue(c);
			} else if (c != ' ') {
				return false;
			}
		}
		BigInteger sum = new BigInteger(new String(finalNumber));
		BigInteger remainder = sum.remainder(new BigInteger("97"));
		return remainder.intValue() == 1;
	}

	public void vadilateFromFile(String file, String fileName) throws IOException {
		Scanner s = new Scanner(new File(file));
		List<String> list = new ArrayList<String>();
		while (s.hasNextLine()) {
			list.add(s.nextLine());
		}
		s.close();
		//Žinau, kad daug paprasčiau būtų nenaudoti REST'o
		List<String> validatedList = vadilateFromRest(list);
		FileWriter writer = new FileWriter(file.substring(0, file.lastIndexOf(".") + 1) + "out.txt");
		for (String iban : validatedList) {
			writer.write(iban + System.lineSeparator());
		}
		writer.close();
	}

	public List<String> vadilateList(List<String> list) {
		List<String> verifiedIbanList = new ArrayList<String>();
		for (String iban : list) {
			verifiedIbanList.add(iban + ";" + validateIban(iban));
		}
		return verifiedIbanList;
	}
	
	
	public List<String> vadilateFromRest(List<String> list) throws IOException {
		String jsonas = new JSONArray(list).toString();
		URL url = new URL("http://localhost:8080/varyfyibanlist");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		out.write(jsonas);
		out.flush();
		out.close();

		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		JSONArray jArray = new JSONArray(br.readLine());
		List<String> responseList = new ArrayList<String>();
		if (jArray != null) {
			for (int i = 0; i < jArray.length(); i++) {
				responseList.add(jArray.getString(i));
			}
		}
		connection.disconnect();
		return responseList;
	}
	char[] moveChars(char[] iban) {
		char[] rearanged = new char[iban.length];
		for (int x = 0; x < 4; x++) {
			rearanged[rearanged.length - 4 + x] = iban[x];
		}
		for (int x = 4; x < iban.length; x++) {
			rearanged[x - 4] = iban[x];
		}
		return rearanged;
	}
	boolean checkIbanLenght(String country, int lenght) {
		if ((country.contentEquals("AL")) && (lenght == 28)) {
			return true;
		}
		if ((country.contentEquals("DZ")) && (lenght == 24)) {
			return true;
		}
		if ((country.contentEquals("AD")) && (lenght == 24)) {
			return true;
		}
		if ((country.contentEquals("AT")) && (lenght == 20)) {
			return true;
		}
		if ((country.contentEquals("AZ")) && (lenght == 28)) {
			return true;
		}
		if ((country.contentEquals("BH")) && (lenght == 22)) {
			return true;
		}
		if ((country.contentEquals("BY")) && (lenght == 28)) {
			return true;
		}
		if ((country.contentEquals("BE")) && (lenght == 16)) {
			return true;
		}
		if ((country.contentEquals("BJ")) && (lenght == 28)) {
			return true;
		}
		if ((country.contentEquals("BA")) && (lenght == 20)) {
			return true;
		}
		if ((country.contentEquals("BR")) && (lenght == 29)) {
			return true;
		}
		if ((country.contentEquals("VG")) && (lenght == 24)) {
			return true;
		}
		if ((country.contentEquals("BG")) && (lenght == 22)) {
			return true;
		}
		if ((country.contentEquals("BF")) && (lenght == 27)) {
			return true;
		}
		if ((country.contentEquals("BI")) && (lenght == 16)) {
			return true;
		}
		if ((country.contentEquals("CM")) && (lenght == 27)) {
			return true;
		}
		if ((country.contentEquals("CV")) && (lenght == 25)) {
			return true;
		}
		if ((country.contentEquals("FR")) && (lenght == 27)) {
			return true;
		}
		if ((country.contentEquals("CG")) && (lenght == 27)) {
			return true;
		}
		if ((country.contentEquals("CR")) && (lenght == 21)) {
			return true;
		}
		if ((country.contentEquals("HR")) && (lenght == 21)) {
			return true;
		}
		if ((country.contentEquals("CY")) && (lenght == 28)) {
			return true;
		}
		if ((country.contentEquals("CZ")) && (lenght == 24)) {
			return true;
		}
		if ((country.contentEquals("DK")) && (lenght == 18)) {
			return true;
		}
		if ((country.contentEquals("DO")) && (lenght == 28)) {
			return true;
		}
		if ((country.contentEquals("EG")) && (lenght == 27)) {
			return true;
		}
		if ((country.contentEquals("EE")) && (lenght == 20)) {
			return true;
		}
		if ((country.contentEquals("FO")) && (lenght == 18)) {
			return true;
		}
		if ((country.contentEquals("FI")) && (lenght == 18)) {
			return true;
		}
		if ((country.contentEquals("FR")) && (lenght == 27)) {
			return true;
		}
		if ((country.contentEquals("GA")) && (lenght == 27)) {
			return true;
		}
		if ((country.contentEquals("GE")) && (lenght == 22)) {
			return true;
		}
		if ((country.contentEquals("DE")) && (lenght == 22)) {
			return true;
		}
		if ((country.contentEquals("GI")) && (lenght == 23)) {
			return true;
		}
		if ((country.contentEquals("GR")) && (lenght == 27)) {
			return true;
		}
		if ((country.contentEquals("GL")) && (lenght == 18)) {
			return true;
		}
		if ((country.contentEquals("GT")) && (lenght == 28)) {
			return true;
		}
		if ((country.contentEquals("GG")) && (lenght == 22)) {
			return true;
		}
		if ((country.contentEquals("HU")) && (lenght == 28)) {
			return true;
		}
		if ((country.contentEquals("IS")) && (lenght == 26)) {
			return true;
		}
		if ((country.contentEquals("IR")) && (lenght == 26)) {
			return true;
		}
		if ((country.contentEquals("IQ")) && (lenght == 23)) {
			return true;
		}
		if ((country.contentEquals("IE")) && (lenght == 22)) {
			return true;
		}
		if ((country.contentEquals("IM")) && (lenght == 22)) {
			return true;
		}
		if ((country.contentEquals("IL")) && (lenght == 23)) {
			return true;
		}
		if ((country.contentEquals("IT")) && (lenght == 27)) {
			return true;
		}
		if ((country.contentEquals("CI")) && (lenght == 28)) {
			return true;
		}
		if ((country.contentEquals("JE")) && (lenght == 22)) {
			return true;
		}
		if ((country.contentEquals("JO")) && (lenght == 30)) {
			return true;
		}
		if ((country.contentEquals("KZ")) && (lenght == 20)) {
			return true;
		}
		if ((country.contentEquals("XK")) && (lenght == 20)) {
			return true;
		}
		if ((country.contentEquals("KW")) && (lenght == 30)) {
			return true;
		}
		if ((country.contentEquals("LV")) && (lenght == 21)) {
			return true;
		}
		if ((country.contentEquals("LB")) && (lenght == 28)) {
			return true;
		}
		if ((country.contentEquals("LI")) && (lenght == 21)) {
			return true;
		}
		if ((country.contentEquals("LT")) && (lenght == 20)) {
			return true;
		}
		if ((country.contentEquals("LU")) && (lenght == 20)) {
			return true;
		}
		if ((country.contentEquals("MK")) && (lenght == 19)) {
			return true;
		}
		if ((country.contentEquals("MG")) && (lenght == 27)) {
			return true;
		}
		if ((country.contentEquals("ML")) && (lenght == 28)) {
			return true;
		}
		if ((country.contentEquals("MT")) && (lenght == 31)) {
			return true;
		}
		if ((country.contentEquals("MR")) && (lenght == 27)) {
			return true;
		}
		if ((country.contentEquals("FR")) && (lenght == 27)) {
			return true;
		}
		if ((country.contentEquals("MU")) && (lenght == 30)) {
			return true;
		}
		if ((country.contentEquals("MD")) && (lenght == 24)) {
			return true;
		}
		if ((country.contentEquals("MC")) && (lenght == 27)) {
			return true;
		}
		if ((country.contentEquals("ME")) && (lenght == 22)) {
			return true;
		}
		if ((country.contentEquals("MZ")) && (lenght == 25)) {
			return true;
		}
		if ((country.contentEquals("NL")) && (lenght == 18)) {
			return true;
		}
		if ((country.contentEquals("NO")) && (lenght == 15)) {
			return true;
		}
		if ((country.contentEquals("PK")) && (lenght == 24)) {
			return true;
		}
		if ((country.contentEquals("PS")) && (lenght == 29)) {
			return true;
		}
		if ((country.contentEquals("PL")) && (lenght == 28)) {
			return true;
		}
		if ((country.contentEquals("PT")) && (lenght == 25)) {
			return true;
		}
		if ((country.contentEquals("QA")) && (lenght == 29)) {
			return true;
		}
		if ((country.contentEquals("RO")) && (lenght == 24)) {
			return true;
		}
		if ((country.contentEquals("LC")) && (lenght == 32)) {
			return true;
		}
		if ((country.contentEquals("SM")) && (lenght == 27)) {
			return true;
		}
		if ((country.contentEquals("ST")) && (lenght == 25)) {
			return true;
		}
		if ((country.contentEquals("SA")) && (lenght == 24)) {
			return true;
		}
		if ((country.contentEquals("SN")) && (lenght == 28)) {
			return true;
		}
		if ((country.contentEquals("RS")) && (lenght == 22)) {
			return true;
		}
		if ((country.contentEquals("SC")) && (lenght == 31)) {
			return true;
		}
		if ((country.contentEquals("SK")) && (lenght == 24)) {
			return true;
		}
		if ((country.contentEquals("SI")) && (lenght == 19)) {
			return true;
		}
		if ((country.contentEquals("ES")) && (lenght == 24)) {
			return true;
		}
		if ((country.contentEquals("ES")) && (lenght == 24)) {
			return true;
		}
		if ((country.contentEquals("CH")) && (lenght == 21)) {
			return true;
		}
		if ((country.contentEquals("TL")) && (lenght == 23)) {
			return true;
		}
		if ((country.contentEquals("TN")) && (lenght == 24)) {
			return true;
		}
		if ((country.contentEquals("TR")) && (lenght == 26)) {
			return true;
		}
		if ((country.contentEquals("UA")) && (lenght == 29)) {
			return true;
		}
		if ((country.contentEquals("AE")) && (lenght == 23)) {
			return true;
		}
		if ((country.contentEquals("GB")) && (lenght == 22)) {
			return true;
		}
		if ((country.contentEquals("VA")) && (lenght == 22)) {
			return true;
		}
		else {
		return false;
		}
	}
}
