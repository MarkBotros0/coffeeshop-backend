package com.coffeeShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.coffeeShop.exception.ProductNotFoundException;
import com.coffeeShop.model.Goodie;
import com.coffeeShop.repository.GoodiesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoodiesService {
	private final GoodiesRepository repository;

	public void init() {
		Goodie goodie1 = new Goodie("Cookies", 3.6f, "01/01/2023", "30/03/2023",
				"https://media.istockphoto.com/id/1416053005/photo/cookies.jpg?s=2048x2048&w=is&k=20&c=cgr3kdf_oFDUq0Nn3ux-E49W7_k2-mE-opSqe-DiGf0=");
		Goodie goodie2 = new Goodie("Muffins", 2.8f, "01/03/2023", "30/06/2023",
				"https://media.istockphoto.com/id/155099605/photo/overcooked-cinnamon-and-sugar-muffin.jpg?s=2048x2048&w=is&k=20&c=-FtpI44y6HBZ5OVFz4EXVkBwddFppJot_KW7WBNVkvg=");
		Goodie goodie3 = new Goodie("Croissant", 5.3f, "06/09/2023", "30/09/2023",
				"https://media.istockphoto.com/id/175196179/photo/butter-croissant.jpg?s=2048x2048&w=is&k=20&c=CtWYT3B2aXc8mboFJWQIRpe7P6_65ljlLxPPyIYPV5s=");
		Goodie goodie4 = new Goodie("Biscuits", 4.5f, "09/12/2023", "30/12/2023",
				"https://media.istockphoto.com/id/183755648/photo/choc-chip-cookie-isolated-clipping-path.jpg?s=2048x2048&w=is&k=20&c=dxP1wYz7ZHUq8kkfrs5Y41e9aDRI_mD1HLcquu3_WPU=");
		repository.save(goodie1);
		repository.save(goodie2);
		repository.save(goodie3);
		repository.save(goodie4);
	}

	public List<Goodie> findAll() {
		return repository.findAll();
	}

	public Goodie save(Goodie goodie) {
		repository.save(goodie);
		return goodie;
	}

	public Goodie findById(int id) {
		Optional<Goodie> goodieOptional = repository.findById(id);
		if (goodieOptional.isPresent()) {
			return goodieOptional.get();
		} else {
			throw new ProductNotFoundException("Goodies product is not found for id: " + id);
		}
	}
	
	public Goodie findByName(String name) {
		Optional<Goodie> goodieOptional = repository.findByName(name);
		if (goodieOptional.isPresent()) {
			return goodieOptional.get();
		} else {
			throw new ProductNotFoundException("Goodies product is not found for name: " + name);
		}
	}

	public Boolean deleteGoodieById(int id) {
		repository.deleteById(id);
		return true;
	}
}
