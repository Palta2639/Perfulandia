package Clientes_Microservicio.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "Clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El primer nombre es obligatorio")
    @Column(name = "primer_nombre", nullable = false)
    private String primerNombre;

    @NotBlank(message = "El último nombre es obligatorio")
    @Column(name = "ultimo_nombre", nullable = false)
    private String ultimoNombre;

    @Email(message = "El correo electrónico debe ser válido")
    @Column(name = "correo_electronico", nullable = false, unique = true)
    private String correoElectronico;

    @Column(name = "calle")
    private String calle;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "pais")
    private String pais;

    @Column(name = "codigo_postal")
    private String codigoPostal;

    @Column(name = "bloqueado", nullable = false)
    private boolean bloqueado = false;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDate fechaCreacion;

    // Constructores
    public Cliente() {}

    public Cliente(String primerNombre, String ultimoNombre, String correoElectronico, String calle, String ciudad, String pais, String codigoPostal) {
        this.primerNombre = primerNombre;
        this.ultimoNombre = ultimoNombre;
        this.correoElectronico = correoElectronico;
        this.calle = calle;
        this.ciudad = ciudad;
        this.pais = pais;
        this.codigoPostal = codigoPostal;
        this.fechaCreacion = LocalDate.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getUltimoNombre() {
        return ultimoNombre;
    }

    public void setUltimoNombre(String ultimoNombre) {
        this.ultimoNombre = ultimoNombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDate.now();
    }
}   
