package net.lilydev.aonia.util;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * A lockable ArrayList. <br>
 * When locked, all operations will silently fail, returning {@code null} or {@code false} depending on the method.
 * @param <T> This list's element type.
 */
public class LockableArrayList<T> extends ArrayList<T> {
    private boolean locked = false;

    /**
     * Locks this list, preventing further changes.
     */
    public void lock() {
        this.locked = true;
    }

    /**
     * Unlocks this list.
     * <br>
     * <b>WARNING: WILL CLEAR CONTENTS!</b>
     */
    public void unlock() {
        if (this.locked) {
            this.locked = false;
            this.clear();
        }
    }

    public boolean isLocked() {
        return this.locked;
    }

    @Override
    public boolean add(T t) {
        if (this.locked) {
            return false;
        }

        return super.add(t);
    }

    @Override
    public boolean remove(Object o) {
        if (this.locked) {
            return false;
        }

        return super.remove(o);
    }

    @Override
    public T remove(int index) {
        if (this.locked) {
            return null;
        }

        return super.remove(index);
    }

    @Override
    public T set(int index, T element) {
        if (this.locked) {
            return null;
        }

        return super.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        if (this.locked) {
            return;
        }

        super.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (this.locked) {
            return false;
        }

        return super.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (this.locked) {
            return false;
        }

        return super.addAll(index, c);
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        if (this.locked) {
            return;
        }

        super.removeRange(fromIndex, toIndex);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (this.locked) {
            return false;
        }

        return super.removeAll(c);
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        if (this.locked) {
            return false;
        }

        return super.removeIf(filter);
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        if (this.locked) {
            return;
        }

        super.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super T> c) {
        if (this.locked) {
            return;
        }

        super.sort(c);
    }

    @Override
    public void clear() {
        if (this.locked) {
            return;
        }

        super.clear();
    }
}
