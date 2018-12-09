package com.gaurav.springbootjoshlong;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
public class SpringBootJoshlongApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJoshlongApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ReservationRepository rr) {
        return args -> {
            Arrays.asList("Zack", "Zoella", "Josh", "William", "Andrew", "Diana").forEach(x -> rr.save(new Reservation(x)));

            rr.findAll().forEach(System.out::println);

            rr.findByReservationName("Zack").forEach(System.out::println);
        };
    }
}

@Controller
class RservationController {

    @RequestMapping("/reservation.html")
    public String reservation(Model model) {
        model.addAttribute("reservation", reservationRepository.findAll());
        return "index";
    }

    private final ReservationRepository reservationRepository;

    public RservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
}

//@RestController
//class ReservationController {
//
//    private final ReservationRepository reservationRepository;
//
//    public ReservationController(ReservationRepository reservationRepository) {
//        this.reservationRepository = reservationRepository;
//    }
//
//    @RequestMapping("/reservation")
//    Collection<Reservation> findAll() {
//        return reservationRepository.findAll();
//    }
//
//}

@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Collection<Reservation> findByReservationName(@Param("rn") String rn);
}

@Entity
class Reservation {

    @Id
    @GeneratedValue
    private Long id;
    private String reservationName;

    public Reservation() { //Why JPA Why???
    }

    public Reservation(String name) {
        this.reservationName = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReservationName() {
        return reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", reservationName='" + reservationName + '\'' +
                '}';
    }
}