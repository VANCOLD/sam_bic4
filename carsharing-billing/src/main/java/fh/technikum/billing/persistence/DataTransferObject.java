package fh.technikum.billing.persistence;

import java.io.Serializable;

/**
 * Marker interface for Data Transfer Objects (DTOs).
 * <p>
 * This interface extends {@link Serializable} to ensure that DTOs can be serialized,
 * allowing them to be transmitted between different application layers or over a network.
 * </p>
 */
public interface DataTransferObject extends Serializable {
}
