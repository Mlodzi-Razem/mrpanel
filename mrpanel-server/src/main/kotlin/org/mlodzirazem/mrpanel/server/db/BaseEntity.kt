package org.mlodzirazem.mrpanel.server.db

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Generated
import org.hibernate.generator.EventType
import java.time.LocalDateTime

/**
 * BaseEntity serves as a base class for other entity classes, providing common properties
 * and configurations for database entity mappings.
 *
 * This class is marked as an abstract mapped superclass, meaning it does not define its own
 * table in the database but provides mappings and fields to be inherited by subclasses.
 *
 * Commonly inherited fields include:
 * - A unique identifier (`id`) that subclasses must override.
 * - A timestamp (`addedAt`) indicating when the entity was added to the database.
 *   The field is set to be non-nullable and immutable after creation.
 *
 * By extending this class, concrete entity classes can avoid redundancy and enforce consistency
 * across database entities in the application.
 */
@MappedSuperclass
abstract class BaseEntity {
    companion object {
        const val SEQUENCE_NAME = "id_sequence"
    }

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    var id: Long? = null

    @Column(updatable = false, name = "added_at")
    @ColumnDefault("current_timestamp")
    @Generated(event = [EventType.INSERT])
    var addedAt: LocalDateTime? = null
}