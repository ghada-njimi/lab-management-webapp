package com.outil.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outil.dao.OutilRepository;
import com.outil.entities.Outil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/outils")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class OutilController {

	private final OutilRepository outilRepository;

	@GetMapping
	public List<Outil> getAllOutils() {
		return outilRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Outil> getOutilById(@PathVariable Long id) {
		return outilRepository.findById(id).map(outil -> ResponseEntity.ok(outil))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Outil> addOutil(@RequestBody Outil outil) {
		return new ResponseEntity<>(outilRepository.save(outil), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Outil> updateOutil(@PathVariable Long id, @RequestBody Outil outil) {
		return outilRepository.findById(id).map(o -> {
			o.setDate(outil.getDate());
			o.setSource(outil.getSource());
			Outil updated = outilRepository.save(o);
			return ResponseEntity.ok(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOutil(@PathVariable Long id) {
		if (outilRepository.existsById(id)) {
			outilRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
