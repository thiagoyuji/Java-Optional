package optional;

@FunctionalInterface
public interface Predicate<E> {

    boolean test( final E e );

}
