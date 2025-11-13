package org.example.americantelcashflow.modelo;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.Required;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento_caja")
@Getter
@Setter
public class MovimientoCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Long idMovimiento;

    @Required
    @Column(nullable = false, length = 20)
    private String tipo; // "INGRESO" o "EGRESO"

    @Required
    @Column(nullable = false)
    private double monto;

    @Column(length = 200)
    private String descripcion;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora = LocalDateTime.now();

    // ----------- RELACIÓN CON CAJA -----------
    @ManyToOne
    @JoinColumn(name = "id_caja", nullable = false)
    private Caja caja;

}
