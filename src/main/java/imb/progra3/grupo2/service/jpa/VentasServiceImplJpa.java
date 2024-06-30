package imb.progra3.grupo2.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imb.progra3.grupo2.entity.Ventas;
import imb.progra3.grupo2.repository.VentasRepository;
import imb.progra3.grupo2.service.IVentasService;

@Service
public class VentasServiceImplJpa implements IVentasService {
	
	@Autowired
	private VentasRepository ventasRepository;

	@Override
	public List<Ventas> findAll() {
		List<Ventas> ventas = ventasRepository.findByDueDateNotNullOrderByDueDate();
		ventas.addAll(ventasRepository.findByDueDateNull());
		return ventas;
	}

	@Override
	public Ventas findById(Long id) {
		return ventasRepository.findById(id).orElse(null);
	}

	@Override
	public Ventas save(Ventas ventas) {
		return ventasRepository.save(ventas);
	}

	@Override
	public void delete(Long id) {
		ventasRepository.deleteById(id);
	}

	@Override
	public boolean exists(Long id) {
		return id == null ? false : ventasRepository.existsById(id);
	}

	@Override
	public List<Ventas> findByDone(boolean done) {
		if(done) {
			List<Ventas> ventas = ventasRepository.findByDueDateNotNullAndDoneTrueOrderByDueDate();
			ventas.addAll(ventasRepository.findByDueDateNullAndDoneTrue());
			return ventas;
		}else {
			List<Ventas> ventas = ventasRepository.findByDueDateNotNullAndDoneFalseOrderByDueDate();
			ventas.addAll(ventasRepository.findByDueDateNullAndDoneFalse());
			return ventas;			
		}
	}

}
