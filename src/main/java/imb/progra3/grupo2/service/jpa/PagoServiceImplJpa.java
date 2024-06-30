package imb.progra3.grupo2.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imb.progra3.grupo2.entity.Pago;
import imb.progra3.grupo2.repository.PagoRepository;
import imb.progra3.grupo2.service.IPagoService;

@Service
public class PagoServiceImplJpa implements IPagoService {

	@Autowired
	private PagoRepository pago;
	
	@Override
	public List<Pago> findAll() {
		return pago.findAll();
	}

	@Override
	public Pago findById(Long id) {
		return pago.findById(id).orElse(null);
	}

	@Override
	public boolean exists(Long id) {
		return id == null ? false : pago.existsById(id);
	}

	@Override
	public Pago save(Pago pago) {
		return pago.save(pago);
	}

	@Override
	public void delete(Long id) {
		pago.deleteById(id);
	}

}