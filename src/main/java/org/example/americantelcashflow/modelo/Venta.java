package org.example.americantelcashflow.modelo;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.Required;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "venta")
@Getter
@Setter
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Compatible con PostgreSQL SERIAL
    @Column(name = "id_venta")
    private Long idVenta;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora = LocalDateTime.now();

    @Required
    @Column(nullable = false)
    private double total;

    @Column(length = 200)
    private String descripcion;

    // ---------------- RELACIÓN CON CAJA ----------------
    @ManyToOne
    @JoinColumn(name = "id_caja") // FK en PostgreSQL
    private Caja caja;
}
//