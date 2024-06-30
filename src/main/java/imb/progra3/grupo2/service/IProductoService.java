package imb.progra3.grupo2.service;

import imb.progra3.grupo2.entity.Producto;
import imb.progra3.grupo2.service.IProductoService;
import java.util.List;

public interface IProductoService {

		public List<Producto> findAll();
		public List<Producto> findByDone(boolean done);
		public Producto findById(Long id);
		public Producto save(Producto producto);
		public void delete(Long id);
		public boolean exists(Long id);
}

