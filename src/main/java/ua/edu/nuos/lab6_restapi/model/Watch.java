package ua.edu.nuos.lab6_restapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "watches")
public class Watch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "watch_id", nullable = false, unique = true)
    private Long watchId;

    @Column(name = "brand", nullable = false)
    private String brand;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private double price;

    @Column(name = "case_diameter_mm")
    private double caseDiameter;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "warranty_months")
    private int warrantyMonths;

    @Column(name = "manufacturer_id", nullable = false)
    private Long manufacturerId;
}
