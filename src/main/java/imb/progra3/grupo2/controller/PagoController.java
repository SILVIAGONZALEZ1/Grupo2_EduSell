package imb.progra3.grupo2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imb.progra3.grupo2.entity.Pago;
import imb.progra3.grupo2.service.IPagoService;
import imb.progra3.grupo2.util.APIResponse;
import imb.progra3.grupo2.util.ResponseUtil;

import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping(path="/api/v1/pago")			//http://localhost:8080/api/v1/pago
public class PagoController {
	
	@Autowired
	private IPagoService pagoService;
	
	@GetMapping("/metodo1/{id}")				//http://localhost:8080/api/v1/pago/metodo1/3
	public Pago getPagoByIdForma1(@PathVariable("id") Long id) {
		return pagoService.findById(id);
	}
	
	@GetMapping("/metodo2/{id}")				//http://localhost:8080/api/v1/pago/metodo2/3
	public APIResponse<Pago> getPagoByIdForma2(@PathVariable("id") Long id){
		Pago pago = new Pago(); 
		if(pagoService.exists(id)) {
			pago = pagoService.findById(id);
			APIResponse<Pago> api = new APIResponse<Pago>(200, null, pago);
			return api;
		}else {
			List<String> lstMessages = new ArrayList<>();
			lstMessages.add("No existe el elemento con el ID indicado");
			APIResponse<Pago> api = new APIResponse<Pago>(404, lstMessages, null);
			return api;
		}
	}
	
	@GetMapping("/metodo3/{id}")				//http://localhost:8080/api/v1/pago/metodo3/3
	public ResponseEntity<APIResponse<Pago>> getPagoByIdForma3(@PathVariable("id") Long id){
		Pago pago = new Pago(); 
		if(pagoService.exists(id)) {
			pago = pagoService.findById(id);
			APIResponse<Pago> api = new APIResponse<Pago>(200, null, pago);
			return ResponseEntity.status(200).body(api);
		}else {
			List<String> lstMessages = new ArrayList<>();
			lstMessages.add("No existe el elemento con el ID indicado");
			APIResponse<Pago> api = new APIResponse<Pago>(404, lstMessages, null);
			return ResponseEntity.status(404).body(api);
		}
	}	
	
	
	
	@GetMapping
	public ResponseEntity<APIResponse<List<Pago>>> getAllPago() {
		List<Pago> pago = pagoService.findAll();
		return 	pago.isEmpty()? ResponseUtil.notFound("No se encontraron pagos") :
				ResponseUtil.success(pago);		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<APIResponse<Pago>> getPagoById(@PathVariable("id") Long id){
		return 	pagoService.exists(id)? ResponseUtil.success(pagoService.findById(id)):
				ResponseUtil.notFound("No se encontró el pago con id {0}", id);
	}	
	
	@PostMapping
	public ResponseEntity<APIResponse<Pago>> createPago(@RequestBody Pago pago){
		return 	pagoService.exists(pago.getId_Pago())? ResponseUtil.badRequest("Ya existe un pago con id {0}", pago.getId_Pago()):
				ResponseUtil.success(pagoService.save(pago));
	}
	
	@PutMapping
	public ResponseEntity<APIResponse<Pago>> updatePago(@RequestBody Pago pago){
		return 	pagoService.exists(pago.getId_Pago())? ResponseUtil.success(pago):
				ResponseUtil.badRequest("No existe un pago con id {0}", pago.getId_Pago());
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<APIResponse<Pago>> deletePago(@PathVariable("id") Long id){
		if(pagoService.exists(id)) {
			pagoService.delete(id);
			return ResponseUtil.successDeleted("Se eliminó el pago con el id {0}", id);
		}else {
			return ResponseUtil.badRequest("No se encontró el pago con el id {0}", id);
		}		
	}
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Pago>> handleException(Exception ex) {    	
    	return ResponseUtil.badRequest(ex.getMessage());
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<Pago>> handleConstraintViolationException(ConstraintViolationException ex) {
    	return ResponseUtil.handleConstraintException(ex);
    } 	

}