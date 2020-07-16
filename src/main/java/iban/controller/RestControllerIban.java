package iban.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import iban.service.IbanService;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class RestControllerIban {
	@Autowired
	IbanService ibanService;
	
	@RequestMapping(value = "/varyfyibanlist", method = RequestMethod.POST)
	public List<String> checkIbanList(@RequestBody List<String> ibanList, HttpServletRequest request) throws IOException {
		System.out.println("mane pasieke");
		List<String> verifiedIban = new ArrayList<String>();
		for (String iban : ibanList) {
			verifiedIban.add(iban + ";" + ibanService.validateIban(iban));
		}
		System.out.println(verifiedIban);
		return verifiedIban;
	}
}
