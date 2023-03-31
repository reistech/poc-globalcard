package com.globalcards.adapter.driver.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RegisterForReflection
public class BinResponse {
    private Long id;
    private Integer bin;
    private Integer pan;
    private LocalDateTime cancellingDate;
    private String descricao;
}


