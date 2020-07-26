package optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

public class JavaOptionalTest {

    @Test
    @DisplayName("Of :: Method Cannot Receive Null Value")
    public void ofMethodCannotReceiveNullValue(){
        Assertions.assertThrows( NullPointerException.class, () -> JavaOptional.of( null ) );
    }

    @Test
    @DisplayName("Of :: Method integer Values")
    public void ofMethodIntegerValues(){
        Assertions.assertEquals( Integer.class, JavaOptional.of( 10 ).get().getClass() );
    }

    @Test
    @DisplayName("OfNullable :: Method string Values")
    public void ofNullableMethodStringValues(){
        Assertions.assertEquals( String.class, JavaOptional.of( "hello world" ).get().getClass() );
    }

    @Test
    @DisplayName("Get :: Method Cannot Return Null Value from ofNullable")
    public void getMethodCannotReturnNullValueFromOfNullable(){
        Assertions.assertThrows( NoSuchElementException.class, () -> JavaOptional.ofNullable( null ).get() );
    }

    @Test
    @DisplayName("Get :: Method Cannot Return Null Value from empty")
    public void getMethodCannotReturnNullValueFromEmpty(){
        Assertions.assertThrows( NoSuchElementException.class, () -> JavaOptional.empty().get() );
    }

    @Test
    @DisplayName("OrElse :: Method Entering integer Returning integer")
    public void orElseEnteringIntegerAndReturningInteger(){
        Assertions.assertEquals( 10, JavaOptional.ofNullable( 10 ).orElse( 20 ) );
    }

    @Test
    @DisplayName("OrElse :: Method Entering null Returning integer")
    public void orElseEnteringNullAndReturningInteger(){
        Assertions.assertEquals( 20, JavaOptional.ofNullable( null ).orElse( 20 ) );
        Assertions.assertEquals( 20, JavaOptional.empty().orElse( 20 ) );
    }

    @Test
    @DisplayName("OrElse :: Method Entering null Returning null")
    public void orElseEnteringNullAndReturningNull(){
        Assertions.assertNull(JavaOptional.ofNullable( null ).orElse( null ));
        Assertions.assertNull(JavaOptional.empty().orElse( null ));
    }

    @Test
    @DisplayName("IsPresent :: Method value is present, not null")
    public void valueIsPresent(){
        Assertions.assertTrue( JavaOptional.of( 10 ).isPresent() );
        Assertions.assertTrue( JavaOptional.ofNullable( 10 ).isPresent() );
    }

    @Test
    @DisplayName("IsPresent :: Method value is not present, is null")
    public void valueIsNotPresent(){
        Assertions.assertFalse( JavaOptional.ofNullable( null ).isPresent() );
        Assertions.assertFalse( JavaOptional.empty().isPresent() );
    }

    @Test
    @DisplayName("IsNotPresent :: Method value is present, not null")
    public void valueIsPresentMethodIsNotPresent(){
        Assertions.assertFalse( JavaOptional.of( 10 ).isNotPresent() );
        Assertions.assertFalse( JavaOptional.ofNullable( 10 ).isNotPresent() );
    }

    @Test
    @DisplayName("IsNotPresent :: Method value is not present, is null")
    public void valueIsNotPresentMethodIsNotPresent(){
        Assertions.assertTrue( JavaOptional.ofNullable( null ).isNotPresent() );
        Assertions.assertTrue( JavaOptional.empty().isNotPresent() );
    }

    @Test
    @DisplayName("IsEmpty :: Method is Empty, is null")
    public void javaOptionalIsEmpty(){
        Assertions.assertTrue( JavaOptional.empty().isEmpty() );
        Assertions.assertTrue( JavaOptional.ofNullable( null ).isEmpty() );
    }

    @Test
    @DisplayName("IsEmpty :: Method is not Empty, not null")
    public void javaOptionalIsNotEmpty(){
        Assertions.assertFalse( JavaOptional.ofNullable( 10 ).isEmpty() );
        Assertions.assertFalse( JavaOptional.of( 10 ).isEmpty() );
    }

    @Test
    @DisplayName("Map :: Method map value to another")
    public void mapValueToAnother(){

        final String valueMap = JavaOptional
                .of( 10 )
                .map(String::valueOf)
                .get();

        Assertions.assertEquals( "10", valueMap );

    }

    @Test
    @DisplayName("Map :: Method map rule null")
    public void mapRuleNull(){
        Assertions.assertThrows( NullPointerException.class, () -> JavaOptional.of( 10 ).map( null ) );
    }

    @Test
    @DisplayName("Map :: Method value equal null")
    public void mapValueNull(){
        Assertions.assertFalse( JavaOptional.ofNullable( null ).map(String::valueOf).isPresent() );
    }

    @Test
    @DisplayName("FlatMap :: Method flat map value to another")
    public void flatMapValueToAnother(){

        final String valueMap = JavaOptional
                .of(10)
                .flatMap(integer -> JavaOptional.of(String.valueOf(integer)))
                .get();

        Assertions.assertEquals( "10", valueMap );

    }

    @Test
    @DisplayName("FlatMap :: Method flat map rule null")
    public void flatMapRuleNull(){
        Assertions.assertThrows( NullPointerException.class, () -> JavaOptional.of( 10 ).flatMap( null ) );
    }

    @Test
    @DisplayName("FlatMap :: Method value equal null")
    public void flatMapValueNull(){
        Assertions.assertFalse( JavaOptional.ofNullable( null ).flatMap(object -> JavaOptional.of(String.valueOf(object))).isPresent() );
    }

    @Test
    @DisplayName("Filter :: Method filter string for one")
    public void filterForOne(){

        final String valueFilter = JavaOptional
                .of( 1000 )
                .map(String::valueOf)
                .filter( s -> s.startsWith("1") )
                .get();

        Assertions.assertEquals( "1000", valueFilter );

    }

    @Test
    @DisplayName("Filter :: Method filter string for two, error")
    public void filterForTwo(){

        Assertions.assertThrows( NoSuchElementException.class,
                () -> JavaOptional
                        .of( 1000 )
                        .map(String::valueOf)
                        .filter( s -> s.startsWith("2") )
                        .get()
        );

    }

    @Test
    @DisplayName("Filter :: Method filter with null predicate")
    public void filterWithNullPredicate(){

        Assertions.assertThrows( NullPointerException.class,
                () -> JavaOptional
                        .of( 1000 )
                        .map(String::valueOf)
                        .filter( null )
                        .get()
        );

    }

    @Test
    @DisplayName("Filter :: Method filter with null input")
    public void filterWithInputNull(){

        Assertions.assertThrows( NoSuchElementException.class,
                () -> JavaOptional
                        .ofNullable( null )
                        .map(String::valueOf)
                        .filter( s -> s.startsWith("n") )
                        .get()
        );

    }

    @Test
    @DisplayName("IfPresent :: Method if present, sum")
    public void ifPresentSum(){

        AtomicInteger atomicInteger = new AtomicInteger( 1000 );

        JavaOptional.of( 100 ).ifPresent(atomicInteger::addAndGet);

        Assertions.assertEquals( 1100, atomicInteger.get() );

    }

    @Test
    @DisplayName("IfPresent :: Method if not present, do nothing")
    public void ifNotPresentDoNothing(){

        AtomicInteger atomicInteger = new AtomicInteger( 1000 );

        JavaOptional.ofNullable( null ).ifPresent( object -> {} );

        JavaOptional.empty().ifPresent( object -> {} );

        Assertions.assertEquals( 1000, atomicInteger.get() );

    }

    @Test
    @DisplayName("IfPresent :: Method consumer null")
    public void ifPresentConsumerNull(){
        Assertions.assertThrows( NullPointerException.class, () -> JavaOptional.ofNullable( 100 ).ifPresent( null ) );
    }

    @Test
    @DisplayName("IfNotPresent :: Method action null")
    public void ifNotPresentActionNull(){
        Assertions.assertThrows( NullPointerException.class, () -> JavaOptional.ofNullable( 100 ).ifNotPresent( null ) );
    }

    @Test
    @DisplayName("IfNotPresent :: Method if present do nothing")
    public void ifValuePresentDoNothing(){

        AtomicInteger atomicInteger = new AtomicInteger( 1000 );

        JavaOptional.ofNullable( 100 ).ifNotPresent( () ->  atomicInteger.addAndGet( 100 ) );

        Assertions.assertEquals( 1000, atomicInteger.get() );

    }

    @Test
    @DisplayName("IfNotPresent :: Method if not present sum")
    public void ifValueNotPresentSum(){

        AtomicInteger atomicInteger = new AtomicInteger( 1000 );

        JavaOptional.ofNullable( null ).ifNotPresent( () ->  atomicInteger.addAndGet( 100 ) );

        JavaOptional.empty().ifNotPresent( () ->  atomicInteger.addAndGet( 100 ) );

        Assertions.assertEquals( 1200, atomicInteger.get() );

    }

    @Test
    @DisplayName("IfPresentOrElse :: Method consumer null")
    public void ifPresentOrElseConsumerNull(){
        Assertions.assertThrows( NullPointerException.class, () -> JavaOptional.ofNullable( 100 ).ifPresentOrElse( null, System.out::println ) );
    }

    @Test
    @DisplayName("IfPresentOrElse :: Method action null")
    public void ifPresentOrElseActionNull(){
        Assertions.assertThrows( NullPointerException.class, () -> JavaOptional.ofNullable( 100 ).ifPresentOrElse( System.out::println, null ) );
    }

    @Test
    @DisplayName("IfPresentOrElse :: Method if present sum")
    public void ifPresentOrElseSumInteger(){

        AtomicInteger atomicInteger = new AtomicInteger( 1000 );

        JavaOptional.ofNullable( 100 ).ifPresentOrElse( atomicInteger::addAndGet, atomicInteger::incrementAndGet );

        Assertions.assertEquals( 1100, atomicInteger.get() );

    }

    @Test
    @DisplayName("IfPresentOrElse :: Method if not present increment")
    public void ifPresentOrElseIncrementeInteger(){

        AtomicInteger atomicInteger = new AtomicInteger( 1000 );

        JavaOptional.ofNullable( null ).ifPresentOrElse( object -> {}, atomicInteger::incrementAndGet );

        JavaOptional.empty().ifPresentOrElse( object -> {}, atomicInteger::incrementAndGet );

        Assertions.assertEquals( 1002, atomicInteger.get() );

    }

    @Test
    @DisplayName("Or :: Method optional value not Null")
    public void javaOptionalOrValueNotNull(){

        AtomicInteger atomicInteger = new AtomicInteger( 1000 );

        JavaOptional.ofNullable( 1000 ).or( () -> JavaOptional.of( 200 ) ).ifPresent(atomicInteger::addAndGet);

        Assertions.assertEquals( 2000, atomicInteger.get() );

    }

    @Test
    @DisplayName("Or :: Method optional value is Null")
    public void javaOptionalOrValueNull(){

        final JavaOptional<Object> result = JavaOptional.ofNullable( null ).or( () -> JavaOptional.of(200) );

        final JavaOptional<Object> result2 = JavaOptional.empty().or( () -> JavaOptional.of(200) );

        Assertions.assertEquals( 200, result.get() );
        Assertions.assertEquals( 200, result2.get() );

    }

    @Test
    @DisplayName("Or :: Method provider null")
    public void javaOptionalOrProviderNull(){
        Assertions.assertThrows( NullPointerException.class, () -> JavaOptional.ofNullable( 100 ).or( null ) );
    }

    @Test
    @DisplayName("OrElseGet :: Method provider null")
    public void orElseGetProviderNull(){
        Assertions.assertThrows( NullPointerException.class, () -> JavaOptional.ofNullable( 100 ).orElseGet( null ) );
    }

    @Test
    @DisplayName("OrElseGet :: Method input value not null")
    public void orElseGetInputValueNotNull(){

        AtomicInteger atomicInteger = new AtomicInteger( 2000 );

        final Integer value = JavaOptional.ofNullable( 1000 ).orElseGet(atomicInteger::get);

        Assertions.assertEquals( 1000, value );

    }

    @Test
    @DisplayName("OrElseGet :: Method input value null")
    public void orElseGetInputValueNull(){

        AtomicInteger atomicInteger = new AtomicInteger( 2000 );

        final Object value = JavaOptional.ofNullable(null).orElseGet(atomicInteger::get);

        final Object value2 = JavaOptional.empty().orElseGet(atomicInteger::get);

        Assertions.assertEquals( 2000, value );
        Assertions.assertEquals( 2000, value2 );

    }

    @Test
    @DisplayName("OrElseThrow :: Method input not null")
    public void orElseThrowInputNotNull(){
        Assertions.assertEquals( 1000, JavaOptional.ofNullable( 1000 ).orElseThrow( null ) );
    }

    @Test
    @DisplayName("OrElseThrow :: Method input null")
    public void orElseThrowInputNull(){
        Assertions.assertThrows( NullPointerException.class, () -> JavaOptional.ofNullable( null ).orElseThrow( null ) );
        Assertions.assertThrows( RuntimeException.class, () -> JavaOptional.empty().orElseThrow( RuntimeException::new ) );
    }

}
