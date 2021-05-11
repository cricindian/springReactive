package com.learnreactivespring.fluxandmonoPlaygound;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {
    @Test
    public void fluxTest(){
       Flux<String> stringFlux = Flux.just("Spring","Spring Boot","Reactive Spring")
//               .concatWith(Flux.error(new RuntimeException("Exception Occurred : ")))
               .concatWith(Flux.just("After Error"))
               .log();
       stringFlux.subscribe((s) -> System.out.println("Success Event "+ s),
               (e) -> System.err.println("Error Event :" + e),
               () -> System.out.println("Complete event"));
    }

    @Test
    public void fluxTestElements_WithoutError(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot")
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .verifyComplete();
    }

    @Test
    public void fluxTestElements_WithError(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot")
                .concatWith(Flux.error(new RuntimeException("Exception")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
//                .expectError(RuntimeException.class)
                .expectErrorMessage("Exception")
                .verify();
    }

    @Test
    public void fluxTestElements_count(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot")
                .concatWith(Flux.error(new RuntimeException("Exception")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNextCount(2)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void fluxTestElements_WithError1(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot")
                .concatWith(Flux.error(new RuntimeException("Exception")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring","Spring Boot")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void monoTest(){
        Mono<String> stringMono = Mono.just("Spring").log();
        StepVerifier.create(stringMono)
                .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    public void monoTest_withError(){
        StepVerifier.create(Mono.error(new RuntimeException("Exception occurred")).log())
                .expectError(RuntimeException.class)
                .verify();
    }
}

