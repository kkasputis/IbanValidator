package iban.controller;

import java.io.IOException;
import java.util.List;
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
	public List<String> checkIbanList(@RequestBody List<String> ibanList) throws IOException {
		return ibanService.vadilateList(ibanList);
	}
}
