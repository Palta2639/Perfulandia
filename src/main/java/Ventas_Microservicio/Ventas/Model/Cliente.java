package Ventas_Microservicio.Ventas.Model;

import java.time.LocalDate;

public class Cliente {

    private Long id;
    private String primerNombre;
    private String ultimoNombre;
    private String correoElectronico;
    private String calle;
    private String ciudad;
    private String pais;
    private String codigoPostal;
    private LocalDate fechaCreacion;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPrimerNombre() { return primerNombre; }
    public void setPrimerNombre(String primerNombre) { this.primerNombre = primerNombre; }

    public String getUltimoNombre() { return ultimoNombre; }
    public void setUltimoNombre(String ultimoNombre) { this.ultimoNombre = ultimoNombre; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
