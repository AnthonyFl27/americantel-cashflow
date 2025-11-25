package org.example.americantelcashflow.modelo;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.Required;
import org.openxava.annotations.Stereotype;
import org.openxava.model.Identifiable;

import javax.persistence.Entity;

@Entity
@Getter @Setter
public class Cliente extends Identifiable {

    @Required
    private String nombre;

    private String telefono;

    @Stereotype("EMAIL")
    private String email;
}
