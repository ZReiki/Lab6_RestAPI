package ua.edu.nuos.lab6_restapi.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class WatchDto {
    private Long watchId;
    private String brand;
    private LocalDate releaseDate;
    private String type;
    private double price;
    private double caseDiameter;
    private int quantity;
    private int warrantyMonths;
    private Long manufacturerId;

    @Override
    public String toString() {
        return String.format(
                "ID=%d | %s | %s | Дата=%s | Ціна=%.2f | Діаметр=%.1f | К-сть=%d | Гарантія=%d міс. | Виробник=%d",
                watchId, brand, type, releaseDate, price, caseDiameter, quantity, warrantyMonths, manufacturerId
        );
    }
}
