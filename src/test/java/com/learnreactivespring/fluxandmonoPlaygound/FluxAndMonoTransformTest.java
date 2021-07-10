package com.learnreactivespring.fluxandmonoPlaygound;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FluxAndMonoTransformTest {

    List<String> names = Arrays.asList("Santosh","Rashmi");

    @Test
    public void transformUsingMap(){
       Flux<String> namesFlux = Flux.fromIterable(names)
               .map(s-> s.toUpperCase())
               .log();

        StepVerifier.create(namesFlux)
                .expectNext("SANTOSH","RASHMI")
                .verifyComplete();

    }

    @Test
    public void transformUsingMap_length(){
        Flux<Integer> namesFlux = Flux.fromIterable(names)
                .map(s-> s.length())
                .log();

        StepVerifier.create(namesFlux)
                .expectNext(7,6)
                .verifyComplete();

    }

    @Test
    public void transformUsingMap_length_repeat(){
        Flux<Integer> namesFlux = Flux.fromIterable(names)
                .map(s-> s.length())
                .repeat(1)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext(7,6,7,6)
                .verifyComplete();

    }

    @Test
    public void transformUsingMap_filter(){
        Flux<String> namesFlux = Flux.fromIterable(names)
                .filter(s-> s.length()>6)
                .map(s-> s.toUpperCase())
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("SANTOSH")
                .verifyComplete();

    }
    @Test
    public void transformUsingFlatMap(){
        Flux<String> namesFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F"))
                .flatMap(s -> {
                    return Flux.fromIterable(convertToList(s));
                })
                .log();
        //Flatmap is used when external service is called for every element, which returns FLux<String>
        //flux -> s -> Flux<String>

        StepVerifier.create(namesFlux)
                .expectNextCount(12)
                .verifyComplete();
    }

    private List<String> convertToList(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s, "newValue :"+s);
    }

//    @Test
//    public void transformUsingFlatMap_inParallel()
//    {
//        Flux<String> namesFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F"))
//                .window(2)
//                .flatMap( (s) -> {
//                    s.map(this::convertToList).subscribeOn()
//
//                })
//                .log();
//    }
}
