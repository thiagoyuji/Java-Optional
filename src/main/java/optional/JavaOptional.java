package optional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

public final class JavaOptional<T> {

    private final T t;

    private JavaOptional(final T t) {
        this.t = t;
    }

    public static <E> JavaOptional<E> of( final E e ){
        Objects.requireNonNull( e );
        return new JavaOptional<>( e );
    }

    public static <E> JavaOptional<E> ofNullable(final E e ){
        return new JavaOptional<>( e );
    }

    public static <E> JavaOptional<E> empty(){
        return new JavaOptional<>( null );
    }

    public T get(){
        if( isEmpty() ) throw new NoSuchElementException();
        return t;
    }

    public T orElse( final T other ){
        if( isNotPresent() ) return other;
        return t;
    }

    public JavaOptional<T> or( final Provider<? extends JavaOptional<T>> provider ){
        Objects.requireNonNull( provider );
        if( isPresent() ) return new JavaOptional<>( t );
        return provider.provide();
    }

    public T orElseGet( final Provider<? extends T> provider ){
        Objects.requireNonNull( provider );
        if( isPresent() ) return t;
        return provider.provide();
    }

    public <E extends Throwable> T orElseThrow( final Provider<? extends E> provider ) throws E {
        if( isNotPresent() ) throw provider.provide();
        return t;
    }

    public boolean isPresent(){ return t != null; }

    public boolean isNotPresent(){ return !isPresent(); }

    public boolean isEmpty(){ return !isPresent(); }

    @SuppressWarnings("unchecked")
    public <S> JavaOptional<S> map( final Mapper<? super T, ? extends S> mapper ){
        Objects.requireNonNull( mapper );
        if( isPresent() ) return new JavaOptional<>( mapper.map( t ) );
        return (JavaOptional<S>) new JavaOptional<>( null );
    }

    @SuppressWarnings("unchecked")
    public <S> JavaOptional<S> flatMap( final Mapper<? super T, ? extends JavaOptional<S>> mapper ){
        Objects.requireNonNull( mapper );
        if( isPresent() ) return mapper.map( t );
        return (JavaOptional<S>) new JavaOptional<>( null );
    }

    public JavaOptional<T> filter( final Predicate<? super  T> predicate ){
        Objects.requireNonNull( predicate );
        if( isPresent() && predicate.test( t ) ) return new JavaOptional<>( t );
        return new JavaOptional<>( null );
    }

    public void ifPresent( final Consumer<? super T> action ){
        Objects.requireNonNull( action );
        if( isPresent() ) action.consume( t );
    }

    public void ifPresentOrElse( final Consumer<? super T> consumer, final Action action ){
        Objects.requireNonNull( consumer );
        Objects.requireNonNull( action );
        if( isPresent() ) consumer.consume( t );
        else action.execute();
    }

    public void ifNotPresent( final Action action ){
        Objects.requireNonNull( action );
        if( isNotPresent() ) action.execute();
    }

    public Stream<T> stream(){
        return Stream.ofNullable( t );
    }

    @Override
    public boolean equals( final Object object ) {
        return Objects.requireNonNull( t ).equals( object );
    }

    @Override
    public String toString() {
        return Objects.requireNonNull( t ).toString();
    }

    @Override
    public int hashCode() {
        return Objects.requireNonNull( t ).hashCode();
    }

}
