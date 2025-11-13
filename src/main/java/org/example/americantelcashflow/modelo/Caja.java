package org.example.americantelcashflow.modelo;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.Required;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "caja")
@Getter
@Setter
public class Caja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // PostgreSQL: SERIAL / IDENTITY
    @Column(name = "id_caja")
    private Long idCaja;

    @Column(name = "fecha_apertura", nullable = false)
    private LocalDateTime fechaApertura = LocalDateTime.now();

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Required
    @Column(name = "monto_inicial", nullable = false)
    private double montoInicial;

    @Column(name = "monto_final")
    private double montoFinal;

    @Column(name = "saldo_actual")
    private double saldoActual;

    // ---------------- RELACIONES ----------------

    @OneToMany(mappedBy = "caja", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Venta> ventas = new ArrayList<>();

    @OneToMany(mappedBy = "caja", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovimientoCaja> movimientos = new ArrayList<>();


    // ---------------- MÉTODOS ÚTILES ----------------

    public void registrarVenta(double monto) {
        this.saldoActual += monto;
    }

    public void registrarGasto(double monto) {
        this.saldoActual -= monto;
    }
}