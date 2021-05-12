package com.learnreactivespring.fluxandmonoPlaygound;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoFilterTest {
    List<String> names = Arrays.asList("Santosh", "Rashmi","Anjan","Dias", "Adhi");

    @Test
    public void filterTest(){
        Flux<String> namesFlux = Flux.fromIterable(names)
                .filter(s -> s.startsWith("A"))
//                .filter(s -> s.length() > 4)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Anjan","Adhi")
                .verifyComplete();
    }
}
