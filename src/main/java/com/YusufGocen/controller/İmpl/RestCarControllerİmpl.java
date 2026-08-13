package com.YusufGocen.controller.İmpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.YusufGocen.controller.IRestCarController;
import com.YusufGocen.controller.RestBaseController;
import com.YusufGocen.controller.RootEntity;
import com.YusufGocen.dto.DtoCar;
import com.YusufGocen.dto.DtoCarIU;
import com.YusufGocen.service.ICarService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/car")
@Tag(name = "Araç İşlemleri",description = "Araç kayıt ve yönetim işlemleri")
public class RestCarControllerİmpl extends RestBaseController implements IRestCarController{

	@Autowired
	private ICarService carService;
	
	@PostMapping("/save")
	@Override
	public RootEntity<DtoCar> saveCar(@Valid @RequestBody DtoCarIU dtoCarIU) {
		return ok(carService.saveCar(dtoCarIU));
	}

	@GetMapping("/list")
	@Override
	public RootEntity<List<DtoCar>> getAllCars() {
		return ok(carService.getAllCars());
	}

	@GetMapping("/{id}")
	@Override
	public RootEntity<DtoCar> getCarById(@PathVariable("id") Long id) {
		return ok(carService.getCarbyId(id));
	}

	@PutMapping("/update/{id}")
	@Override
	public RootEntity<DtoCar> updateCar(@PathVariable Long id, @Valid @RequestBody DtoCarIU dtoCarIU) {
		return ok(carService.updateCar(id, dtoCarIU));
	}

	@DeleteMapping("/delete/{id}")
	@Override
	public RootEntity<Void> deleteCar(@PathVariable("id") Long id) {
		
		carService.deleteCar(id);
		
		return ok(null);
	}

}
