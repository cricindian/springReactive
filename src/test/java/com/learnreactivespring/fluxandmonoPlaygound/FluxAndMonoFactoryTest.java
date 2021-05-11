package com.learnreactivespring.fluxandmonoPlaygound;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FluxAndMonoFactoryTest {

    List<String> names = Arrays.asList("Santosh", "Rashmi", "Anjan", "Adhi", "Dias", "Koushi");

    @Test
    public void fluxUsingIterable(){
        Flux<String> namesFlux = Flux.fromIterable(names);

        StepVerifier.create(namesFlux)
                .expectNext("Santosh", "Rashmi", "Anjan", "Adhi", "Dias", "Koushi")
                .verifyComplete();
    }

    @Test
    public void fluxUsingArray(){
        String[] names = new String[]{"Santosh", "Rashmi", "Anjan", "Adhi", "Dias", "Koushi"};

        Flux<String> namesFlux = Flux.fromArray(names);
        StepVerifier.create(namesFlux)
                .expectNext("Santosh", "Rashmi", "Anjan", "Adhi", "Dias", "Koushi")
                .verifyComplete();
    }

    @Test
    public void fluxUsingStream(){
        Flux<String> namesFlux = Flux.fromStream(names.stream());
        StepVerifier.create(namesFlux)
                .expectNext("Santosh", "Rashmi", "Anjan", "Adhi", "Dias", "Koushi")
                .verifyComplete();
    }

    @Test
    public void monoUsingJustOrEmpty(){
        Mono<String> mono = Mono.justOrEmpty(null); // Mono.Empty
        StepVerifier.create(mono.log())
                .verifyComplete(); //Nothing to emit, so directly complete
    }

    @Test
    public void monoUsingSupplier(){
        Supplier<String> stringSupplier = () -> "Santosh";
        Mono<String> stringMono = Mono.fromSupplier(stringSupplier);
        StepVerifier.create(stringMono.log())
                .expectNext("Santosh")
                .verifyComplete();
    }

    @Test
    public void fluxUsingRange()
    {
        Flux<Integer> integerFlux = Flux.range(1, 10);
        StepVerifier.create(integerFlux.log())
                .expectNext(1,2,3,4,5,6,7,8,9,10)
                .verifyComplete();

    }
}
