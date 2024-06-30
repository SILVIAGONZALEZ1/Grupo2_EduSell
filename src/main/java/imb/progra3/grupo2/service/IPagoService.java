package imb.progra3.grupo2.service;

import imb.progra3.grupo2.entity.Pago;
import imb.progra3.grupo2.service.IPagoService;

import java.util.List;

public interface IPagoService {

  	public List<Pago> findAll();
	public Pago findById(Long id);
	public boolean exists(Long id);
	public Pago save(Pago pago);
	public void delete(Long id);	
}


