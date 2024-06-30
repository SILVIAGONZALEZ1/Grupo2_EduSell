package imb.progra3.grupo2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imb.progra3.grupo2.entity.Carrito;
import imb.progra3.grupo2.service.ICarritoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")                        
public class CarritoController {
	
	@Autowired
	private ICarritoService service;
	
 // Métodos del controlador
    
    @GetMapping(path="/carrito")                  //http://localhost:8080/api/v1/carrito
    public List<Carrito> showAllCarritos() {
        return service.getAll();
    }

   
	@GetMapping(path="disabledcarritos")         //http://localhost:8080/api/v1/disabledcarritos
	public List<Carrito> showDisabledCarritos(){
		return service.getAllDisabled();
	}
	
	@GetMapping(path="enabledcarritos")           //http://localhost:8080/api/v1/enabledcarritos
	public List<Carrito> showEnabledCarritos(){
		return service.getAllEnabled();
	}
    
}
