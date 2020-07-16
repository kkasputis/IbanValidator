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
		if ((charIban.length > 34) || (charIban.length < 5)) {
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
		List<String> validatedList = vadilateFromList(list);
		System.out.println(validatedList);
		FileWriter writer = new FileWriter(file.substring(0, file.lastIndexOf(".") + 1) + "out.txt");
		for (String iban : validatedList) {
			writer.write(iban + System.lineSeparator());
		}
		writer.close();
	}

	public List<String> vadilateFromList(List<String> list) throws IOException {
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
}