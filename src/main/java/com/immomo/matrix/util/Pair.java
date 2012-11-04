package com.immomo.matrix.util;

/**
 * Simple pair data structure.
 * <p>
 * Both left and right can not be <tt>null</tt>.
 * 
 * @author mixueqiang
 * @since Oct 19, 2012
 * 
 */
public class Pair<L, R> {
    public final L left;
    public final R right;

    public Pair(L left, R right) {
        if (left == null || right == null) {
            throw new NullPointerException("L: " + left + ", R:" + right);
        }

        this.left = left;
        this.right = right;
    }

}
