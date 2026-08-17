package com.YusufGocen.controller.İmpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.YusufGocen.controller.IRestGalleristCarController;
import com.YusufGocen.controller.RestBaseController;
import com.YusufGocen.controller.RootEntity;
import com.YusufGocen.dto.DtoGalleristCar;
import com.YusufGocen.dto.DtoGalleristCarIU;
import com.YusufGocen.model.GalleristCar;
import com.YusufGocen.repository.GalleristCarRepository;
import com.YusufGocen.service.IGalleristCarService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/gallerist-car")
@Tag(name = "Bayi Araç İşlemleri",description = "Bayiye ait araçların yönetimi")
public class RestGalleristCarİmpl extends RestBaseController implements IRestGalleristCarController{

	@Autowired
	private IGalleristCarService galleristCarService;
	
	@PostMapping("/save")
	@Override
	public RootEntity<DtoGalleristCar> saveGalleristCar(@Valid @RequestBody DtoGalleristCarIU dtoGalleristCarIU) {
		return ok(galleristCarService.saveGalleristCar(dtoGalleristCarIU));
	}

	@GetMapping("/list")
	@Override
	public RootEntity<List<DtoGalleristCar>> getAllGalleristCars() {
		return ok(galleristCarService.getAllGaleristCars());
	}

	@GetMapping("/{id}")
	@Override
	public RootEntity<DtoGalleristCar> getGalleristCarById(@PathVariable("id") Long id) {
		return ok(galleristCarService.getGalleristCarById(id));
	}

	@DeleteMapping("/delete/{id}")
	@Override
	public RootEntity<Void> deleteGalleristCar(@PathVariable("id") Long id) {
		galleristCarService.deleteGalleristCar(id);
		return ok(null);
	}



}
