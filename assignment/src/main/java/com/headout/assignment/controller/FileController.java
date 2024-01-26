package com.headout.assignment.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

	//private static String filePath = "/assignment/src/main/resources/static/";
	
	@GetMapping("/data")
	public ResponseEntity<Object> request(@RequestParam String n,
							@RequestParam(required = false) String m){
		String file = "static/"+n+".txt";
		Resource resource = new ClassPathResource(file);
		System.out.println(file);
		try {
			
			List<String> lines = Files.readAllLines(resource.getFile().toPath());
			//System.out.println(lines.size());
			if(m!=null) {
				int lineNumber = Integer.parseInt(m);
				if(lineNumber>=1 && lineNumber<=lines.size()) {
					return ResponseEntity.ok().body(lines.get(lineNumber-1));
				}else {
					return ResponseEntity.badRequest().body("Invalid line number");
				}
			}else {
				return ResponseEntity.ok().body(String.join("\n", lines));
			}
			
		} catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid line number format");
        }
	}
	
}
