package optional;

@FunctionalInterface
public interface Provider<S> {

    S provide();

}
