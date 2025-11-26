package org.example.americantelcashflow.modelo;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.Required;
import org.openxava.annotations.Stereotype;
import org.openxava.model.Identifiable;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter @Setter
public class Caja extends Identifiable {

    @Required
    @Stereotype("DATETIME")
    private Date fechaApertura = new Date();   // ? La fecha de apertura de la caja

    @Required
    @Stereotype("MONEY")
    private BigDecimal saldoInicial;          // ? Saldo con que se abre la caja

    @Stereotype("TEXT_AREA")
    private String descripcion;               // ? Opcional, notas de la caja
}
