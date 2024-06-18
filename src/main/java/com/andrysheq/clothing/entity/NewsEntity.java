package com.andrysheq.clothing.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
public class NewsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Schema(description = "Идентификатор новости")
    private Long id;
    @Schema(description = "Дата размещения новости")
    private Date placedAt;
    @Schema(description = "Пользователь")
    @ManyToOne
    private User user;
    @Schema(description = "Название новости")
    private String nameOfNews;
    @Schema(description = "Текст новости")
    private String text;

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }

    public String formatPlacedAt() {
        if (placedAt == null) {
            return "";  // или другое значение по умолчанию
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm, dd/MM/yyyy");
        return dateFormat.format(placedAt);
    }
}
