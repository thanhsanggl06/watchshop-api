    package com.api.watchshop.entity;


    import jakarta.persistence.*;
    import jakarta.validation.constraints.Min;
    import lombok.*;
    import org.hibernate.annotations.CreationTimestamp;
    import org.hibernate.annotations.UpdateTimestamp;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;

    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String title;
        private BigDecimal price;
        private int quantity;
        private String supplier;
        private String imageUrl;
        private String description;
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "category_id")
        private Category category;
        @CreationTimestamp
        private LocalDateTime dateCreated;
        @UpdateTimestamp
        private LocalDateTime lastUpdated;

    }
