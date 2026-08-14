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

import com.YusufGocen.controller.IRestGalleristController;
import com.YusufGocen.controller.RestBaseController;
import com.YusufGocen.controller.RootEntity;
import com.YusufGocen.dto.DtoGallerist;
import com.YusufGocen.dto.DtoGalleristIU;
import com.YusufGocen.service.IGalleristService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/gallerist")
@Tag(name = "Bayi İşlemleri",description = "Otomotiv bayi yönetim işlemleri")
public class RestGalleristControllerİmpl extends RestBaseController implements IRestGalleristController{

	@Autowired
	private IGalleristService galleristService;
	
	@PostMapping("/save")
	@Override
	public RootEntity<DtoGallerist> saveGallerist(@Valid @RequestBody DtoGalleristIU dtoGalleristIU) {
		return ok(galleristService.saveGallerist(dtoGalleristIU));
	}

	@GetMapping("/list")
	@Override
	public RootEntity<List<DtoGallerist>> getAllGallerist() {
		return ok(galleristService.getAllGallerist());
	}

	@GetMapping("/{id}")
	@Override
	public RootEntity<DtoGallerist> getGalleristById(@PathVariable("id") Long id ) {
		return ok(galleristService.getGalleristById(id));
	}

	@DeleteMapping("/delete/{id}")
	@Override
	public RootEntity<Void> deleteGallerist(@PathVariable("id") Long id) {
		galleristService.deleteGallerist(id);
		return ok(null);
	}

}
