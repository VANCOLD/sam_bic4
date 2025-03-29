package fh.technikum.sam.utils;

import java.io.Serializable;

/**
 * Will be used later with a mapper to easily transform incoming data into a model.
 * the model to dto changes will also be implemented via this methodology
 * @param <T> the type of model this DTO is representing
 */
public interface DataTransferObject <T> extends Serializable {
}
