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

	import imb.progra3.grupo2.entity.MediodePago;
	import imb.progra3.grupo2.service.IMediodePagoService;
	import imb.progra3.grupo2.util.APIResponse;
	import imb.progra3.grupo2.util.ResponseUtil;

	import jakarta.validation.ConstraintViolationException;

	@RestController
	@RequestMapping(path="/api/v1/MediodePago") 						//http://localhost:8080/api/v1/MediodePago
	public class MediodePagoController {
		
		@Autowired
		private IMediodePagoService mediodePagoService;
		
		@GetMapping("/metodo1/{id}")									//http://localhost:8080/api/v1/Mediodepago/metodo1/5
		public MediodePago getMediodePagoByIdForma1(@PathVariable("id") Long id) {
			return mediodePagoService.findById(id);
		}
		
		@GetMapping("/metodo2/{id}")									//http://localhost:8080/api/v1/Mediodepago/metodo2/5
		public APIResponse<MediodePago> getMediodePagoByIdForma2(@PathVariable("id") Long id){
			MediodePago mediodePago = new MediodePago(); 
			if(mediodePagoService.exists(id)) {
				mediodePago = mediodePagoService.findById(id);
				APIResponse<MediodePago> api = new APIResponse<MediodePago>(200, null, mediodePago);
				return api;
			}else {
				List<String> lstMessages = new ArrayList<>();
				lstMessages.add("No existe el elemento con el ID indicado");
				APIResponse<MediodePago> api = new APIResponse<MediodePago>(404, lstMessages, null);
				return api;
			}
		}
		
		@GetMapping("/metodo3/{id}")						//http://localhost:8080/api/v1/Mediodepago/metodo3/9
		public ResponseEntity<APIResponse<MediodePago>> getMediodePagoByIdForma3(@PathVariable("id") Long id){
			MediodePago mediodePago = new MediodePago(); 
			if(mediodePagoService.exists(id)) {
				mediodePago = mediodePagoService.findById(id);
				APIResponse<MediodePago> api = new APIResponse<MediodePago>(200, null, mediodePago);
				return ResponseEntity.status(200).body(api);
			}else {
				List<String> lstMessages = new ArrayList<>();
				lstMessages.add("No existe el elemento con el ID indicado");
				APIResponse<MediodePago> api = new APIResponse<MediodePago>(404, lstMessages, null);
				return ResponseEntity.status(404).body(api);
			}
		}	
		
		
		
		@GetMapping
		public ResponseEntity<APIResponse<List<MediodePago>>> getAllMediodePago() {
			List<MediodePago> mediodePago = mediodePagoService.findAll();
			return 	mediodePago.isEmpty()? ResponseUtil.notFound("No se encontraron Medios de Pagos") :
					ResponseUtil.success(mediodePago);		
		}
		
		@GetMapping("{id}")
		public ResponseEntity<APIResponse<MediodePago>> getMediodePagoById(@PathVariable("id") Long id){
			return 	mediodePagoService.exists(id)? ResponseUtil.success(mediodePagoService.findById(id)):
					ResponseUtil.notFound("No se encontró el medio de pago con id {0}", id);
		}	
		
		@PostMapping
		public ResponseEntity<APIResponse<MediodePago>> createMediodePago(@RequestBody MediodePago medioDePago){
			return 	mediodePagoService.exists(medioDePago.getId_MedioDePago())? ResponseUtil.badRequest("Ya existe un medio de pago con id {0}", medioDePago.getId_MedioDePago()):
					ResponseUtil.success(mediodePagoService.save(medioDePago));
		}
		
		@PutMapping
		public ResponseEntity<APIResponse<MediodePago>> updateMediodePago(@RequestBody MediodePago mediodePago){
			return 	mediodePagoService.exists(mediodePago.getId_MedioDePago())? ResponseUtil.success(mediodePago):
					ResponseUtil.badRequest("No existe un medio de pago con id {0}", mediodePago.getId_MedioDePago());
		}
		
		@DeleteMapping("{id}")
		public ResponseEntity<APIResponse<MediodePago>> deleteMediodePago(@PathVariable("id") Long id){
			if(mediodePagoService.exists(id)) {
				mediodePagoService.delete(id);
				return ResponseUtil.successDeleted("Se eliminó el medio de pago con el id {0}", id);
			}else {
				return ResponseUtil.badRequest("No se encontró el medio de pago con el id {0}", id);
			}		
		}
		
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<APIResponse<MediodePago>> handleException(Exception ex) {    	
	    	return ResponseUtil.badRequest(ex.getMessage());
	    }
	    
	    @ExceptionHandler(ConstraintViolationException.class)
	    public ResponseEntity<APIResponse<MediodePago>> handleConstraintViolationException(ConstraintViolationException ex) {
	    	return ResponseUtil.handleConstraintException(ex);
	    } 	

	}