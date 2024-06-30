package imb.progra3.grupo2.service;

import imb.progra3.grupo2.entity.Carrito;
import imb.progra3.grupo2.service.ICarritoService;
import java.util.List;

public interface ICarritoService {
    //void guardar(Carrito carrito);
    //void eliminar(Long id);
    //List<Carrito> obtenerTodosCarritos();

    // Otros métodos de negocio específicos para la entidad Carrito
    public List<Carrito> getAll();
	public Carrito getById(Long id);
	public Carrito save(Carrito carrito);
	public void deleteById(Long id);
	public boolean exists(Long id);
	public List<Carrito>getAllEnabled();
	public List<Carrito>getAllDisabled();
}
