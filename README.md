# optional

Custom Java optional - Work with Null Value

Methods:

```java
JavaOptional<T> of( final T value );
JavaOptional<T> ofNullable( final T value );
JavaOptional<T> empty();
```

---

```java
T get();
T orElse( final T value );
T orElseGet( final Provider<? extends T> provider );
```

---

```java
JavaOptional<T> filter( final Predicate<? super  T> predicate );
JavaOptional<T> or( final Provider<? extends JavaOptional<T>> provider );
```

---

```java
<S> JavaOptional<S> map(final Mapper<? super T, ? extends S> mapper );
<S> JavaOptional<S> flatMap( final Mapper<? super T, ? extends JavaOptional<S>> mapper );
<E extends Throwable> T orElseThrow( final Provider<? extends E> provider );
```

---

```java
void ifPresent( final Consumer<? super T> action );
void ifPresentOrElse( final Consumer<? super T> consumer, final Action action );
```

---

```java
Stream<T> stream();
```

---

```java
boolean isPresent();
boolean isEmpty();
boolean equals(final Object object );
```

---

```java
int hashCode();
```

---

```java
String toString();
```
