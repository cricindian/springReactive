package com.learnreactivespring.fluxandmonoPlaygound;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoCombineTest {
    @Test
    public void combineUsingMerge(){ // No order maintained.
        Flux<String> flux1 = Flux.just("A","B","C");
        Flux<String> flux2 = Flux.just("D","E","F");

        Flux<String> mergedFlux = Flux.merge(flux1,flux2);

        StepVerifier.create(mergedFlux.log())
                .expectSubscription()
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    public void combineUsingMerge_withDelay(){
        Flux<String> flux1 = Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D","E","F").delayElements(Duration.ofSeconds(1));

        Flux<String> mergedFlux = Flux.merge(flux1,flux2);

        StepVerifier.create(mergedFlux.log())
                .expectSubscription()
//                .expectNext("A","B","C","D","E","F")
                .expectNextCount(6)
                .verifyComplete();
    }

   @Test
   public void combineUsingConcat(){
            Flux<String> flux1 = Flux.just("A","B","C");
            Flux<String> flux2 = Flux.just("D","E","F");

            Flux<String> mergedFlux = Flux.concat(flux1,flux2);

            StepVerifier.create(mergedFlux.log())
                    .expectSubscription()
                    .expectNext("A","B","C","D","E","F")
//                    .expectNextCount(6)
                    .verifyComplete();
    }

    @Test
    public void combineUsingConcat_withDelay(){ //Concat maintains order.
        Flux<String> flux1 = Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D","E","F").delayElements(Duration.ofSeconds(1));

        Flux<String> mergedFlux = Flux.concat(flux1,flux2);

        StepVerifier.create(mergedFlux.log())
                .expectSubscription()
                .expectNext("A","B","C","D","E","F")
//                    .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void combineUsingZip(){ //Concat maintains order.
        Flux<String> flux1 = Flux.just("A","B","C");
        Flux<String> flux2 = Flux.just("D","E","F");

        Flux<String> mergedFlux = Flux.zip(flux1,flux2, (t1,t2) -> {
            return t1.concat(t2);
        }); // AD, BE, EF

        StepVerifier.create(mergedFlux.log())
                .expectSubscription()
                .expectNext("AD","BE","CF")
//                    .expectNextCount(6)
                .verifyComplete();
    }
}
