package com.coffeeShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.coffeeShop.exception.ProductNotFoundException;
import com.coffeeShop.model.Drink;
import com.coffeeShop.repository.DrinksRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DrinksService {
	private final DrinksRepository repository;
	
	public void init() {
		Drink drink1 = new Drink("Cappoccino", 3.2f, "01/09/2023", "30/12/2023",
				"https://media.istockphoto.com/id/530570283/photo/the-cup-of-cappuccino.jpg?s=2048x2048&w=is&k=20&c=3MW9UNPxvWnkXOwszQ9h85zNZMsOOx9ruAVdYTIh4zw=");
		Drink drink2 = new Drink("Espresso", 2.5f, "01/01/2023", "30/03/2023",
				"https://media.istockphoto.com/id/1154548877/photo/white-cup-with-espresso-coffee-on-wooden-table-for-background.jpg?s=2048x2048&w=is&k=20&c=5SE_GzNe3-kj1a3Wai7jiPpmS4G9s7pHWncvoVc5dQQ=");
		Drink drink3 = new Drink("Latte", 4.4f, "01/03/2023", "30/06/2023",
				"https://media.istockphoto.com/id/1134280946/photo/hot-coffee-cappuccino-latte-art-heart-flower-shape-top-view-isolated-on-white-background.jpg?s=2048x2048&w=is&k=20&c=nPtKVD_OmAGTK93modv6rBNdrYzbzZbO6_6j-kC5QGM=");
		Drink drink4 = new Drink("Mocha", 3.9f, "01/06/2023", "30/09/2023",
				"https://media.istockphoto.com/id/1221390350/photo/frappe-latte-or-frappuccino-chilled-coffee.jpg?s=2048x2048&w=is&k=20&c=4cUVyhMikC0oUpE9C5mISKG6oWwmeyaS1fMIOBLb4mE=");
		repository.save(drink1);
		repository.save(drink2);
		repository.save(drink3);
		repository.save(drink4);
	}

	public List<Drink> findAll() {
		return repository.findAll();
	}

	public Drink save(Drink drink) {
		repository.save(drink);
		return drink;
	}

	public Drink findById(int id) {
		Optional<Drink> drinkOptional = repository.findById(id);
		return drinkOptional
				.orElseThrow(() -> new ProductNotFoundException("Drink Product is not found for id: " + id));
	}
	
	public Drink findByName(String name) {
		Optional<Drink> drinkOptional = repository.findByName(name);
		return drinkOptional
				.orElseThrow(() -> new ProductNotFoundException("Drink Product is not found for name: " + name));
	}

	public Boolean deleteDrinkById(int id) {
		repository.deleteById(id);
		return true;
	}
}
