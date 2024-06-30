package imb.progra3.grupo2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import imb.progra3.grupo2.entity.Ventas;
import imb.progra3.grupo2.entity.Producto;
import imb.progra3.grupo2.service.IVentasService;
import imb.progra3.grupo2.service.IProductoService;
import imb.progra3.grupo2.util.APIResponse;
import imb.progra3.grupo2.util.ResponseUtil;

import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping(path="/api/v1/ventas")
public class VentasController {
	
	@Autowired
	private IVentasService ventasService;
	
	@Autowired
	private IProductoService productoService;
	
	@GetMapping
	public ResponseEntity<APIResponse<List<Ventas>>> getAllVentas() {
		List<Ventas> ventas = ventasService.findAll();
		return 	ventas.isEmpty()? ResponseUtil.notFound("No se encontraron ventas") :
				ResponseUtil.success(ventas);		
	}
	
	@GetMapping("/done")
	public ResponseEntity<APIResponse<List<Ventas>>> getAllVentaskDone() {
		return this._getVentasByDone(true);	
	}
	
	@GetMapping("/undone")
	public ResponseEntity<APIResponse<List<Ventas>>> getAllVentasUndone() {
		return this._getVentasByDone(false);	
	}	
	
	private ResponseEntity<APIResponse<List<Ventas>>> _getVentasByDone(boolean done) {
		List<Ventas> ventas = ventasService.findByDone(done);
		return 	ventas.isEmpty()? ResponseUtil.notFound("No se encontraron ventas") :
				ResponseUtil.success(ventas);		
	}	
	
	@GetMapping("{id}")
	public ResponseEntity<APIResponse<Ventas>> getVentasById(@PathVariable("id") Long id){
		return 	ventasService.exists(id)? ResponseUtil.success(ventasService.findById(id)):
				ResponseUtil.notFound("No se encontró las ventas con id {0}", id);
	}
	
	@PostMapping
	public ResponseEntity<APIResponse<Ventas>> createVentas(@RequestBody Ventas ventas){
		return 	ventasService.exists(ventas.getId_Venta())? ResponseUtil.badRequest("Ya existe una venta con id {0}", ventas.getId_Venta()):
				ResponseUtil.success(ventasService.save(ventas));
	}
	
	@PutMapping
	public ResponseEntity<APIResponse<Ventas>> updateVentas(@RequestBody Ventas ventas){
		return 	ventasService.exists(ventas.getId_Venta())? ResponseUtil.success(ventasService.save(ventas)):
				ResponseUtil.badRequest("No existe una venta con id {0}", ventas.getId_Venta());
	}
	
	@PatchMapping("/done/{id}")
	private ResponseEntity<APIResponse<Ventas>> setVentasAsDone(@PathVariable("id") Long id){
		return this._setVentasDone(id, true);
	}
	
	@PatchMapping("/undone/{id}")
	private ResponseEntity<APIResponse<Ventas>> setVentasAsUndone(@PathVariable("id") Long id){
		return this._setVentasDone(id, false);
	}	
	
	private ResponseEntity<APIResponse<Ventas>> _setVentasDone(Long id, boolean done){
		if(ventasService.exists(id)) {
			Ventas ventas = ventasService.findById(id);
			ventas.setDone(done);
			ventasService.save(ventas);
			return ResponseUtil.success(ventas);
		}else {
			return ResponseUtil.badRequest("No existe una venta con id {0}", id);
		}
	}

	
	@PatchMapping("/assignproducto/{ventasid}/{productoid}")
	private ResponseEntity<APIResponse<Ventas>> setVentasProducto(@PathVariable("ventasid") Long ventasId, @PathVariable("productoid") Long productoId){
		if(ventasService.exists(ventasId)) {
			if(productoService.exists(productoId)) {
				Ventas ventas = ventasService.findById(ventasId);
				Producto producto = productoService.findById(productoId);
				ventas.setProducto(producto);
				ventasService.save(ventas);
				return ResponseUtil.success(ventas);
			}else {
				return ResponseUtil.badRequest("No existe un producto con id {0}", productoId);
			}
		}else {
			return ResponseUtil.badRequest("No existe una venta con id {0}", ventasId);
		}		
	}
	
	@PatchMapping("/unassignproducto/{id}")
	private ResponseEntity<APIResponse<Ventas>> unsetVentasProducto(@PathVariable("id") Long id){
		if(ventasService.exists(id)) {
			Ventas ventas = ventasService.findById(id);
			ventas.setProducto(null);
			ventasService.save(ventas);
			return ResponseUtil.success(ventas);
		}else {
			return ResponseUtil.badRequest("No existe una venta con id {0}", id);
		}		
	}	
	
	@DeleteMapping("{id}")
	public ResponseEntity<APIResponse<Ventas>> deleteVentas(@PathVariable("id") Long id){
		if(ventasService.exists(id)) {
			ventasService.delete(id);
			return ResponseUtil.successDeleted("Se eliminó la venta con el id {0}", id);
		}else {
			return ResponseUtil.badRequest("No se encontró la venta con el id {0}", id);
		}		
	}
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Ventas>> handleException(Exception ex) {    	
    	return ResponseUtil.badRequest(ex.getMessage());
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<Ventas>> handleConstraintViolationException(ConstraintViolationException ex) {
    	return ResponseUtil.handleConstraintException(ex);
    } 

}