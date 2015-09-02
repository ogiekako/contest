package net.ogiekako.algorithm.geometry;

public interface GeometricalObject {
    /**
     * Return the geometrical equality taking numerical errors into account.
     */
    boolean isEqualTo(GeometricalObject other);
}
