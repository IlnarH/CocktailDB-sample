package com.technaxis.sample.livetyping.util;

import java.util.Collection;

public class Util {

    /**
     * Order doesn't matter. Not duplicate-proof.
     *
     * @param first
     * @param second
     * @param <T>
     * @return
     */
    public static <T> boolean contentsEqual(Collection<T> first, Collection<T> second) {
        if (first == null && second == null) {
            return true;
        }
        if (first == null || second == null || first.size() != second.size()) {
            return false;
        }
        return first.containsAll(second) && second.containsAll(first);
    }
}
