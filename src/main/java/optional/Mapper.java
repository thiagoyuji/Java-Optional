package optional;

@FunctionalInterface
public interface Mapper<E, S> {

    S map( final E e );

}
