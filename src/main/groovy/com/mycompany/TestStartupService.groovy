package com.mycompany


import com.mycompany.sample.TestContent
import com.mycompany.sample.TestContentRepository
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import jakarta.inject.Inject
import jakarta.inject.Singleton

import java.util.stream.Stream

@Singleton
class TestStartupService {
    @Inject
    TestContentRepository testContentRepository


    @EventListener
    void onStartup(StartupEvent event) {
        1000.times {
            TestContent testContent = new TestContent(
                    id: "test-$it"
            )

            testContentRepository.save(testContent)
        }

        Stream<TestContent> stream = testContentRepository.getAll()

        stream.forEach {
            println it
        }
    }


}
