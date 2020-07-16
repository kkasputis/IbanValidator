package iban.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import iban.service.IbanService;

@Controller
public class MainController {

	@Autowired
	IbanService ibanService;

	@RequestMapping(method = RequestMethod.GET)
	public String indexList(Model model) throws IOException {
		return "index";
	}

	@RequestMapping(value = "/checkibanfile", method = RequestMethod.POST)
	public String uploadIbanFile(@RequestBody MultipartFile ibanFile, Model model, HttpServletRequest request)
			throws IOException {
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		String fileName = ibanFile.getOriginalFilename().substring(0,
				ibanFile.getOriginalFilename().lastIndexOf(".") + 1) + "out.txt";
		File directory = new File(rootDirectory + "\\ibanFiles");
		if (!directory.exists()) {
			directory.mkdir();

		}
		if (ibanFile != null && !ibanFile.isEmpty()) {
			try {

				ibanFile.transferTo(new File(directory + "\\" + ibanFile.getOriginalFilename()));
			} catch (Exception e) {
				throw new RuntimeException("Iban file save failed", e);
			}
		}
		ibanService.vadilateFromFile(directory + "\\" + ibanFile.getOriginalFilename(), fileName);
		model.addAttribute("filename", fileName);
		return "validated";

	}

	@ResponseBody
	@RequestMapping(value = "/checksingleiban")
	public String checkIban(@RequestParam String iban, Model model, HttpServletRequest request) throws IOException {

		return String.valueOf(ibanService.validateIban(iban));

	}

}
