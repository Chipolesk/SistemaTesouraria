package org.exemploTesouraria.model.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.exemploTesouraria.model.enums.SourceModule;

import java.util.Objects;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionReference {
    @Enumerated(EnumType.STRING)
    @Column(name = "source_module", nullable = false, updatable = false, length = 40)
    private SourceModule sourceModule;

    @Column(name = "source_id", nullable = false, updatable = false, length = 80)
    private String sourceId;

    private TransactionReference(SourceModule sourceModule, String sourceId) {
        this.sourceModule = Objects.requireNonNull(sourceModule, "Source module is required.");
        this.sourceId = normalizeSourceId(sourceId);
    }

    public static TransactionReference of(SourceModule sourceModule, Object sourceId) {
        Objects.requireNonNull(sourceId, "Source id is required.");
        return new TransactionReference(sourceModule, String.valueOf(sourceId));
    }

    private String normalizeSourceId(String sourceId) {
        String normalizedSourceId = Objects.requireNonNull(sourceId, "Source id is required.").trim();
        if (normalizedSourceId.isBlank()) {
            throw new IllegalArgumentException("Source id cannot be blank.");
        }
        return normalizedSourceId;
    }
}
