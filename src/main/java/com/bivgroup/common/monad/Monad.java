package com.bivgroup.common.monad;

import com.google.common.base.Function;

/**
 * Created by bush on 15.07.2016.
 */
public interface Monad<T> {

    /**
     * FlatMaps this value to a new value with different component type.
     *
     * @param mapper A mapper
     * @param <U>    Component type of the mapped {@code AbstractMonad}
     * @return a mapped {@code AbstractMonad}
     * @throws NullPointerException if {@code mapper} is null
     */
    <U> Monad<U> flatMap(Function<? super T, ? extends Iterable<? extends U>> mapper);

    /**
     * Maps this value to a new value with different component type.
     *
     * @param mapper A mapper
     * @param <U>    Component type of the mapped {@code AbstractMonad}
     * @return a mapped {@code AbstractMonad}
     * @throws NullPointerException if {@code mapper} is null
     */
    <U> Monad<U> map(Function<? super T, ? extends U> mapper);
//
//
//    @Override
//    Value<T> filter(Predicate<? super T> predicate);
//
//    @Override
//    <U> Value<U> flatten();
//
//    @Override
//    <U> Value<U> flatMap(Function<? super T, ? extends java.lang.Iterable<? extends U>> mapper);
//
//
//
//    @Override
//    public Option<T> filter(Predicate<? super T> predicate) {
//        final T value = get();
//        if (predicate.test(value)) {
//            return new Some<>(value);
//        } else {
//            return None.instance();
//        }
//    }
//    @SuppressWarnings("unchecked")
//    @Override
//    public <U> Lazy<U> flatMap(Function<? super T, ? extends java.lang.Iterable<? extends U>> mapper) {
//        Objects.requireNonNull(mapper, "mapper is null");
//        if (isEmpty()) {
//            return (Lazy<U>) this;
//        } else {
//            return Lazy.of(() -> Value.get(mapper.apply(get())));
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public <U> Lazy<U> flatten() {
//        try {
//            return ((Lazy<? extends java.lang.Iterable<U>>) this).flatMap(Function.identity());
//        } catch(ClassCastException x) {
//            throw new UnsupportedOperationException("flatten of non-iterable elements");
//        }
//    }
//
//    @Override
//    public <U> Lazy<U> map(Function<? super T, ? extends U> mapper) {
//        return Lazy.of(() -> mapper.apply(get()));
//    }
}

