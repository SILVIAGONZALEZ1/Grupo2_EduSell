package imb.progra3.grupo2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import imb.progra3.grupo2.entity.Ventas;

public interface VentasRepository extends JpaRepository <Ventas, Long> {


	    List<Ventas> findByDueDateNotNullOrderByDueDate();    
	    List<Ventas> findByDueDateNull();
	    List<Ventas> findByDueDateNotNullAndDoneTrueOrderByDueDate();
	    List<Ventas> findByDueDateNullAndDoneTrue();
	    List<Ventas> findByDueDateNotNullAndDoneFalseOrderByDueDate();
	    List<Ventas> findByDueDateNullAndDoneFalse();    
	    
}
