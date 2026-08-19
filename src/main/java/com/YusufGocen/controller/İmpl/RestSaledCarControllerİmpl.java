package com.YusufGocen.controller.İmpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.YusufGocen.controller.IRestSaledCarController;
import com.YusufGocen.controller.RestBaseController;
import com.YusufGocen.controller.RootEntity;
import com.YusufGocen.dto.DtoCarSaled;
import com.YusufGocen.dto.DtoSaledCarIU;
import com.YusufGocen.service.ISaledCarService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/saled-car")
@Tag(name = "Satış İşlemleri",description = "Araç satış kayıtlarının yönetimi")
public class RestSaledCarControllerİmpl extends RestBaseController implements IRestSaledCarController{

	@Autowired
	private ISaledCarService saledCarService;
	
	@PostMapping("/save")
	@Override
	public RootEntity<DtoCarSaled> buyCar(@Valid @RequestBody DtoSaledCarIU dtoSaledCarIU) {
		return ok(saledCarService.buyCar(dtoSaledCarIU));
	}

	@GetMapping("list")
	@Override
	public RootEntity<List<DtoCarSaled>> getAllSaledCars() {
		return ok(saledCarService.getAllSaledCars());
	}

	@GetMapping("/{id}")
	@Override
	public RootEntity<DtoCarSaled> getSaledCarById(@PathVariable("id") Long id) {
		return ok(saledCarService.getSaledCarById(id));
	}

}
