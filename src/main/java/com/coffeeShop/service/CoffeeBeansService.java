package com.coffeeShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.coffeeShop.exception.ProductNotFoundException;
import com.coffeeShop.model.CoffeeBean;
import com.coffeeShop.repository.CoffeeBeansRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoffeeBeansService {
	private final CoffeeBeansRepository repository;

	public void init() {
		CoffeeBean bean1 = new CoffeeBean("Tanzania Peaberry Coffee", 4.5f, "01/09/2023", "30/12/2023",
				"https://media.istockphoto.com/id/1034454764/photo/coffee-beans.jpg?s=2048x2048&w=is&k=20&c=bW-B463ZB_g4kR24KBzK0EdBb35lp90iz4BXzzvijGE=");
		CoffeeBean bean2 = new CoffeeBean("Hawaii Knoa Coffee",6.4f, "01/01/2023", "30/03/2023",
				"https://media.istockphoto.com/id/1055203192/photo/coffee-isolated-on-white.jpg?s=2048x2048&w=is&k=20&c=0xmQDdA5k9McJfkLZ7iBUiIItLYZDDsmG5jRbT79l1Q=");
		CoffeeBean bean3 = new CoffeeBean("Nicaragoan Coffee",5.3f, "01/03/2023", "30/06/2023",
				"https://media.istockphoto.com/id/690796426/photo/coffee-beans-on-white-background.jpg?s=2048x2048&w=is&k=20&c=xk8EDIysxkkziw8X2DlkIR9dDlW4cnWP47JXcu160Os=	");
		CoffeeBean bean4 = new CoffeeBean("Sumatra Mandhelling Coffee",7.3f ,"01/06/2023", "30/09/2023",
				"https://media.istockphoto.com/id/672083992/photo/coffee-beans-on-white-background.jpg?s=2048x2048&w=is&k=20&c=50fg_arHJF1gfFIbg2cmt6VPbtTe2DYBN1KjzEGtv_c=");
		repository.save(bean1);
		repository.save(bean2);
		repository.save(bean3);
		repository.save(bean4);
	}

	public List<CoffeeBean> findAll() {
		return repository.findAll();
	}

	public CoffeeBean save(CoffeeBean bean) {
		repository.save(bean);
		return bean;
	}

	public CoffeeBean findById(int id) {
		Optional<CoffeeBean> coffeeBeanOptional = repository.findById(id);
		if (coffeeBeanOptional.isPresent()) {
			return coffeeBeanOptional.get();
		} else {
			throw new ProductNotFoundException("Coffee Beans product is not found for id: " + id);
		}
	}
	
	public CoffeeBean findByName(String name) {
		Optional<CoffeeBean> coffeeBeanOptional = repository.findByName(name);
		if (coffeeBeanOptional.isPresent()) {
			return coffeeBeanOptional.get();
		} else {
			throw new ProductNotFoundException("Coffee Beans product is not found for name: " + name);
		}
	}
	
	public Boolean deleteById(int id) {
		repository.deleteById(id);
		return true;
	}

}
