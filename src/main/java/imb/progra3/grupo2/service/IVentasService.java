package imb.progra3.grupo2.service;

import imb.progra3.grupo2.entity.Ventas;
import imb.progra3.grupo2.service.IVentasService;
import java.util.List;


public interface IVentasService {

   	public List<Ventas> findAll();
	public List<Ventas> findByDone(boolean done);
	public Ventas findById(Long id);
	public Ventas save(Ventas ventas);
	public void delete(Long id);
	public boolean exists(Long id);
    // Otros métodos de negocio específicos para la entidad Venta
}

